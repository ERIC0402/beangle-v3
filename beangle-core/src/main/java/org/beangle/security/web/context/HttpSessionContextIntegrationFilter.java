/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.context;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.Validate;
import org.beangle.security.core.context.SecurityContext;
import org.beangle.security.core.context.SecurityContextBean;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.web.filter.GenericHttpFilterBean;
import org.springframework.util.ReflectionUtils;

/**
 * Populates the {@link SecurityContextHolder} with information obtained from
 * the <code>HttpSession</code>.
 * <p/>
 * <p/>
 * The <code>HttpSession</code> will be queried to retrieve the <code>SecurityContext</code> that
 * should be stored against the <code>SecurityContextHolder</code> for the duration of the web
 * request. At the end of the web request, any updates made to the
 * <code>SecurityContextHolder</code> will be persisted back to the <code>HttpSession</code> by this
 * filter.
 * </p>
 * <p/>
 * If a valid <code>SecurityContext</code> cannot be obtained from the <code>HttpSession</code> for
 * whatever reason, a fresh <code>SecurityContext</code> will be created and used instead. The
 * created object will be of the instance defined by the {@link #setContextClass(Class)} method
 * (which defaults to {@link org.beangle.security.context.SecurityContextBean}.
 * </p>
 * <p/>
 * No <code>HttpSession</code> will be created by this filter if one does not already exist. If at
 * the end of the web request the <code>HttpSession</code> does not exist, a
 * <code>HttpSession</code> will <b>only</b> be created if the current contents of the
 * <code>SecurityContextHolder</code> are not {@link java.lang.Object#equals(java.lang.Object)} to a
 * <code>new</code> instance of {@link #setContextClass(Class)}. This avoids needless
 * <code>HttpSession</code> creation, but automates the storage of changes made to the
 * <code>SecurityContextHolder</code>. There is one exception to this rule, that is if the
 * {@link #forceEagerSessionCreation} property is <code>true</code>, in which case sessions will
 * always be created irrespective of normal session-minimisation logic (the default is
 * <code>false</code>, as this is resource intensive and not recommended).
 * </p>
 * <p/>
 * This filter will only execute once per request, to resolve servlet container (specifically
 * Weblogic) incompatibilities.
 * </p>
 * <p/>
 * If for whatever reason no <code>HttpSession</code> should <b>ever</b> be created (eg this filter
 * is only being used with Basic authentication or similar clients that will never present the same
 * <code>jsessionid</code> etc), the {@link #setAllowSessionCreation(boolean)} should be set to
 * <code>false</code>. Only do this if you really need to conserve server memory and ensure all
 * classes using the <code>SecurityContextHolder</code> are designed to have no persistence of the
 * <code>SecurityContext</code> between web requests. Please note that if
 * {@link #forceEagerSessionCreation} is <code>true</code>, the <code>allowSessionCreation</code>
 * must also be <code>true</code> (setting it to <code>false</code> will cause a startup time
 * error).
 * </p>
 * <p/>
 * This filter MUST be executed BEFORE any authentication processing mechanisms. Authentication
 * processing mechanisms (eg BASIC, CAS processing filters etc) expect the
 * <code>SecurityContextHolder</code> to contain a valid <code>SecurityContext</code> by the time
 * they execute.
 * </p>
 * 
 * @author beangle
 */

public class HttpSessionContextIntegrationFilter extends GenericHttpFilterBean {
	static final String FILTER_APPLIED = "__beangle_security_session_integration_filter_applied";

	public static final String SECURITY_CONTEXT_KEY = "BEANGLE_SECURITY_CONTEXT";

	private Class<? extends SecurityContext> contextClass = SecurityContextBean.class;

	private Object contextObject;

	/**
	 * Indicates if this filter can create a <code>HttpSession</code> if needed
	 * (sessions are always created sparingly, but setting this value to <code>false</code> will
	 * prohibit sessions from ever being created).
	 * Defaults to <code>true</code>. Do not set to <code>false</code> if you
	 * are have set {@link #forceEagerSessionCreation} to <code>true</code>, as
	 * the properties would be in conflict.
	 */
	private boolean allowSessionCreation = true;

	/**
	 * Indicates if this filter is required to create a <code>HttpSession</code> for every request
	 * before proceeding through the filter chain, even if the <code>HttpSession</code> would not
	 * ordinarily have been created. By
	 * default this is <code>false</code>, which is entirely appropriate for
	 * most circumstances as you do not want a <code>HttpSession</code> created
	 * unless the filter actually needs one. It is envisaged the main situation
	 * in which this property would be set to <code>true</code> is if using
	 * other filters that depend on a <code>HttpSession</code> already existing,
	 * such as those which need to obtain a session ID. This is only required in
	 * specialised cases, so leave it set to <code>false</code> unless you have
	 * an actual requirement and are conscious of the session creation overhead.
	 */
	private boolean forceEagerSessionCreation = false;

	/**
	 * Indicates whether the <code>SecurityContext</code> will be cloned from
	 * the <code>HttpSession</code>. The default is to simply reference (ie the
	 * default is <code>false</code>). The default may cause issues if
	 * concurrent threads need to have a different security identity from other
	 * threads being concurrently processed that share the same <code>HttpSession</code>. In most
	 * normal environments this does not
	 * represent an issue, as changes to the security identity in one thread is
	 * allowed to affect the security identitiy in other threads associated with
	 * the same <code>HttpSession</code>. For unusual cases where this is not
	 * permitted, change this value to <code>true</code> and ensure the {@link #contextClass} is set
	 * to a <code>SecurityContext</code> that
	 * implements {@link Cloneable} and overrides the <code>clone()</code> method.
	 */
	private boolean cloneFromHttpSession = false;

	public boolean isCloneFromHttpSession() {
		return cloneFromHttpSession;
	}

	public void setCloneFromHttpSession(boolean cloneFromHttpSession) {
		this.cloneFromHttpSession = cloneFromHttpSession;
	}

	public HttpSessionContextIntegrationFilter() throws ServletException {
		this.contextObject = generateNewContext();
	}

	protected void initFilterBean() throws ServletException {
		if ((this.contextClass == null) || (!SecurityContext.class.isAssignableFrom(this.contextClass))) { throw new IllegalArgumentException(
				"context must be defined and implement SecurityContext "
						+ "(typically use org.beangle.security.context.SecurityContextBean; existing class is "
						+ this.contextClass + ")"); }

		if (forceEagerSessionCreation && !allowSessionCreation) { throw new IllegalArgumentException(
				"If using forceEagerSessionCreation, you must set allowSessionCreation to also be true"); }

		contextObject = generateNewContext();
	}

	public void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if (request.getAttribute(FILTER_APPLIED) != null) {
			// ensure that filter is only applied once per request
			chain.doFilter(request, response);
			return;
		}

		HttpSession httpSession = safeGetSession(request, forceEagerSessionCreation);
		boolean httpSessionExistedAtStartOfRequest = httpSession != null;
		SecurityContext contextBeforeChainExecution = readSecurityContextFromSession(httpSession);

		// Make the HttpSession null, as we don't want to keep a reference to it
		// lying
		// around in case chain.doFilter() invalidates it.
		httpSession = null;

		if (contextBeforeChainExecution == null) {
			contextBeforeChainExecution = generateNewContext();
			logger.debug("New SecurityContext instance will be associated with SecurityContextHolder");
		} else {
			logger.debug("Obtained a valid SecurityContext from Beangle_SECURITY_CONTEXT to "
					+ "associate with SecurityContextHolder: '{}'", contextBeforeChainExecution);
		}

		int contextHashBeforeChainExecution = contextBeforeChainExecution.hashCode();
		request.setAttribute(FILTER_APPLIED, Boolean.TRUE);

		// Create a wrapper that will eagerly update the session with the
		// security context
		// if anything in the chain does a sendError() or sendRedirect().
		// See SEC-398
		OnRedirectUpdateSessionResponseWrapper responseWrapper = new OnRedirectUpdateSessionResponseWrapper(
				response, request, httpSessionExistedAtStartOfRequest, contextHashBeforeChainExecution);
		// Proceed with chain
		try {
			// This is the only place in this class where
			// SecurityContextHolder.setContext() is called
			SecurityContextHolder.setContext(contextBeforeChainExecution);

			chain.doFilter(request, responseWrapper);
		} finally {
			// This is the only place in this class where
			// SecurityContextHolder.getContext() is called
			SecurityContext contextAfterChainExecution = SecurityContextHolder.getContext();

			// Crucial removal of SecurityContextHolder contents - do this
			// before anything else.
			SecurityContextHolder.clearContext();

			request.removeAttribute(FILTER_APPLIED);

			// storeSecurityContextInSession() might already be called by the
			// response wrapper
			// if something in the chain called sendError() or sendRedirect().
			// This ensures we only call it
			// once per request.
			if (!responseWrapper.isSessionUpdateDone()) {
				storeSecurityContextInSession(contextAfterChainExecution, request,
						httpSessionExistedAtStartOfRequest, contextHashBeforeChainExecution);
			}

			logger.debug("SecurityContextHolder now cleared, as request processing completed");
		}
	}

	/**
	 * Gets the security context from the session (if available) and returns it.
	 * <p/>
	 * If the session is null, the context object is null or the context object stored in the
	 * session is not an instance of SecurityContext it will return null.
	 * <p/>
	 * If <tt>cloneFromHttpSession</tt> is set to true, it will attempt to clone the context object
	 * and return the cloned instance.
	 * 
	 * @param httpSession
	 *            the session obtained from the request.
	 */
	private SecurityContext readSecurityContextFromSession(HttpSession httpSession) {
		if (httpSession == null) {
			logger.debug("No HttpSession currently exists");
			return null;
		}

		// Session exists, so try to obtain a context from it.
		Object contextFromSessionObject = httpSession.getAttribute(SECURITY_CONTEXT_KEY);
		if (contextFromSessionObject == null) {
			logger.debug("HttpSession returned null object for BEANGLE_SECURITY_CONTEXT");
			return null;
		}

		// We now have the security context object from the session.

		// Clone if required (see SEC-356)
		if (cloneFromHttpSession) {
			Validate.isTrue(contextFromSessionObject instanceof Cloneable,
					"Context must implement Clonable and provide a Object.clone() method");
			try {
				Method m = contextFromSessionObject.getClass().getMethod("clone", new Class[] {});
				if (!m.isAccessible()) {
					m.setAccessible(true);
				}
				contextFromSessionObject = m.invoke(contextFromSessionObject, new Object[] {});
			} catch (Exception ex) {
				ReflectionUtils.handleReflectionException(ex);
			}
		}

		if (!(contextFromSessionObject instanceof SecurityContext)) {
			if (logger.isWarnEnabled()) {
				logger.warn("BEANGLE_SECURITY_CONTEXT did not contain a SecurityContext but contained: '"
						+ contextFromSessionObject
						+ "'; are you improperly modifying the HttpSession directly "
						+ "(you should always use SecurityContextHolder) or using the HttpSession attribute "
						+ "reserved for this class?");
			}

			return null;
		}

		// Everything OK. The only non-null return from this method.

		return (SecurityContext) contextFromSessionObject;
	}

	/**
	 * Stores the supplied security context in the session (if available) and if
	 * it has changed since it was set at the start of the request. If the
	 * AuthenticationTrustResolver identifies the current user as anonymous,
	 * then the context will not be stored.
	 * 
	 * @param securityContext
	 *            the context object obtained from the SecurityContextHolder
	 *            after the request has been processed by the filter chain.
	 *            SecurityContextHolder.getContext() cannot be used to obtain
	 *            the context as it has already been cleared by the time this
	 *            method is called.
	 * @param request
	 *            the request object (used to obtain the session, if one
	 *            exists).
	 * @param httpSessionExistedAtStartOfRequest
	 *            indicates whether there was a session in place before the
	 *            filter chain executed. If this is true, and the session is
	 *            found to be null, this indicates that it was invalidated
	 *            during the request and a new session will now be created.
	 * @param contextHashBeforeChainExecution
	 *            the hashcode of the context before the filter chain executed.
	 *            The context will only be stored if it has a different
	 *            hashcode, indicating that the context changed during the
	 *            request.
	 */
	private void storeSecurityContextInSession(SecurityContext securityContext, HttpServletRequest request,
			boolean httpSessionExistedAtStartOfRequest, int contextHashBeforeChainExecution) {
		HttpSession httpSession = safeGetSession(request, false);

		if (httpSession == null) {
			if (httpSessionExistedAtStartOfRequest) {
				if (logger.isDebugEnabled()) {
					logger.debug("HttpSession is now null, but was not null at start of request; "
							+ "session was invalidated, so do not create a new session");
				}
			} else {
				// Generate a HttpSession only if we need to

				if (!allowSessionCreation) {
					if (logger.isDebugEnabled()) {
						logger.debug("The HttpSession is currently null, and the "
								+ "HttpSessionContextIntegrationFilter is prohibited from creating an HttpSession "
								+ "(because the allowSessionCreation property is false) - SecurityContext thus not "
								+ "stored for next request");
					}
				} else if (!contextObject.equals(securityContext)) {
					if (logger.isDebugEnabled()) {
						logger.debug("HttpSession being created as SecurityContextHolder contents are non-default");
					}

					httpSession = safeGetSession(request, true);

				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("HttpSession is null, but SecurityContextHolder has not changed from default: ' "
								+ securityContext
								+ "'; not creating HttpSession or storing SecurityContextHolder contents");
					}
				}
			}
		}

		// If HttpSession exists, store current SecurityContextHolder contents
		// but only if
		// the SecurityContext has actually changed (see JIRA SEC-37)
		if (httpSession != null && securityContext.hashCode() != contextHashBeforeChainExecution) {
			// // See SEC-766
			// if
			// (authenticationTrustResolver.isAnonymous(securityContext.getAuthentication()))
			// {
			// if (logger.isDebugEnabled()) {
			// logger
			// .debug("SecurityContext contents are anonymous - context will not be stored in HttpSession. ");
			// }
			// } else {
			httpSession.setAttribute(SECURITY_CONTEXT_KEY, securityContext);
			if (logger.isDebugEnabled()) {
				logger.debug("SecurityContext stored to HttpSession: '" + securityContext + "'");
			}
			// }
		}
	}

	private HttpSession safeGetSession(HttpServletRequest request, boolean allowCreate) {
		try {
			return request.getSession(allowCreate);
		} catch (IllegalStateException ignored) {
			return null;
		}
	}

	public SecurityContext generateNewContext() throws ServletException {
		try {
			return (SecurityContext) this.contextClass.newInstance();
		} catch (InstantiationException ie) {
			throw new ServletException(ie);
		} catch (IllegalAccessException iae) {
			throw new ServletException(iae);
		}
	}

	public boolean isAllowSessionCreation() {
		return allowSessionCreation;
	}

	public void setAllowSessionCreation(boolean allowSessionCreation) {
		this.allowSessionCreation = allowSessionCreation;
	}

	protected Class<? extends SecurityContext> getContextClass() {
		return contextClass;
	}

	public void setContextClass(Class<? extends SecurityContext> secureContext) {
		this.contextClass = secureContext;
	}

	public boolean isForceEagerSessionCreation() {
		return forceEagerSessionCreation;
	}

	public void setForceEagerSessionCreation(boolean forceEagerSessionCreation) {
		this.forceEagerSessionCreation = forceEagerSessionCreation;
	}

	/**
	 * Wrapper that is applied to every request to update the <code>HttpSession<code> with
	 * the <code>SecurityContext</code> when a <code>sendError()</code> or <code>sendRedirect</code>
	 * happens. See SEC-398. The class contains the
	 * fields needed to call <code>storeSecurityContextInSession()</code>
	 */
	private class OnRedirectUpdateSessionResponseWrapper extends HttpServletResponseWrapper {

		HttpServletRequest request;
		boolean httpSessionExistedAtStartOfRequest;
		int contextHashBeforeChainExecution;

		// Used to ensure storeSecurityContextInSession() is only
		// called once.
		boolean sessionUpdateDone = false;

		/**
		 * Takes the parameters required to call <code>storeSecurityContextInSession()</code> in
		 * addition to the
		 * response object we are wrapping.
		 * 
		 * @see HttpSessionContextIntegrationFilter#storeSecurityContextInSession(SecurityContext,
		 *      HttpServletRequest, boolean, int)
		 */
		public OnRedirectUpdateSessionResponseWrapper(HttpServletResponse response,
				HttpServletRequest request, boolean httpSessionExistedAtStartOfRequest,
				int contextHashBeforeChainExecution) {
			super(response);
			this.request = request;
			this.httpSessionExistedAtStartOfRequest = httpSessionExistedAtStartOfRequest;
			this.contextHashBeforeChainExecution = contextHashBeforeChainExecution;
		}

		/**
		 * Makes sure the session is updated before calling the superclass <code>sendError()</code>
		 */
		public void sendError(int sc) throws IOException {
			doSessionUpdate();
			super.sendError(sc);
		}

		/**
		 * Makes sure the session is updated before calling the superclass <code>sendError()</code>
		 */
		public void sendError(int sc, String msg) throws IOException {
			doSessionUpdate();
			super.sendError(sc, msg);
		}

		/**
		 * Makes sure the session is updated before calling the superclass
		 * <code>sendRedirect()</code>
		 */
		public void sendRedirect(String location) throws IOException {
			doSessionUpdate();
			super.sendRedirect(location);
		}

		/**
		 * Calls <code>storeSecurityContextInSession()</code>
		 */
		private void doSessionUpdate() {
			if (sessionUpdateDone) { return; }
			SecurityContext securityContext = SecurityContextHolder.getContext();
			storeSecurityContextInSession(securityContext, request, httpSessionExistedAtStartOfRequest,
					contextHashBeforeChainExecution);
			sessionUpdateDone = true;
		}

		/**
		 * Tells if the response wrapper has called <code>storeSecurityContextInSession()</code>.
		 */
		public boolean isSessionUpdateDone() {
			return sessionUpdateDone;
		}

	}

}
