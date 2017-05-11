/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.Validate;
import org.beangle.security.auth.UsernamePasswordAuthentication;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;

/**
 * Processes an authentication form.
 * <p>
 * Login forms must present two parameters to this filter: a username and password. The default
 * parameter names to use are contained in the static fields {@link #SECURITY_FORM_USERNAME_KEY} and
 * {@link #SECURITY_FORM_PASSWORD_KEY}. The parameter names can also be changed by setting the
 * <tt>usernameParameter</tt> and <tt>passwordParameter</tt> properties.
 * 
 * @author beangle
 */
public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationFilter {

	public static final String SECURITY_FORM_USERNAME_KEY = "j_username";
	public static final String SECURITY_FORM_PASSWORD_KEY = "j_password";
	public static final String SECURITY_LAST_USERNAME_KEY = "BEANGLE_SECURITY_LAST_USERNAME";

	private String usernameParameter = SECURITY_FORM_USERNAME_KEY;
	private String passwordParameter = SECURITY_FORM_PASSWORD_KEY;

	public Authentication attemptAuthentication(HttpServletRequest request) throws AuthenticationException {
		String username = obtainUsername(request);
		String password = obtainPassword(request);

		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();

		UsernamePasswordAuthentication authRequest = new UsernamePasswordAuthentication(username, password);

		// Place the last username attempted into HttpSession for views
		HttpSession session = request.getSession(false);

		if (session != null || getAllowSessionCreation()) {
			request.getSession().setAttribute(SECURITY_LAST_USERNAME_KEY, username);
		}

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return getAuthenticationManager().authenticate(authRequest);
	}

	/**
	 * This filter by default responds to <code>/j_security_check</code>.
	 * 
	 * @return the default
	 */
	public String getDefaultFilterProcessesUrl() {
		return "/j_security_check";
	}

	/**
	 * Enables subclasses to override the composition of the password, such as
	 * by including additional values and a separator.
	 * <p>
	 * This might be used for example if a postcode/zipcode was required in addition to the
	 * password. A delimiter such as a pipe (|) should be used to separate the password and extended
	 * value(s). The <code>AuthenticationDao</code> will need to generate the expected password in a
	 * corresponding manner.
	 * </p>
	 * 
	 * @param request
	 *            so that request attributes can be retrieved
	 * @return the password that will be presented in the <code>Authentication</code> request token
	 *         to the <code>AuthenticationManager</code>
	 */
	protected String obtainPassword(HttpServletRequest request) {
		return request.getParameter(passwordParameter);
	}

	/**
	 * Enables subclasses to override the composition of the username, such as
	 * by including additional values and a separator.
	 * 
	 * @param request
	 *            so that request attributes can be retrieved
	 * @return the username that will be presented in the <code>Authentication</code> request token
	 *         to the <code>AuthenticationManager</code>
	 */
	protected String obtainUsername(HttpServletRequest request) {
		return request.getParameter(usernameParameter);
	}

	/**
	 * Provided so that subclasses may configure what is put into the
	 * authentication request's details property.
	 * 
	 * @param request
	 *            that an authentication request is being created for
	 * @param authRequest
	 *            the authentication request object that should have its details
	 *            set
	 */
	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthentication authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	/**
	 * Sets the parameter name which will be used to obtain the username from
	 * the login request.
	 * 
	 * @param usernameParameter
	 *            the parameter name. Defaults to "j_username".
	 */
	public void setUsernameParameter(String usernameParameter) {
		Validate.notEmpty(usernameParameter, "Username parameter must not be empty or null");
		this.usernameParameter = usernameParameter;
	}

	/**
	 * Sets the parameter name which will be used to obtain the password from
	 * the login request..
	 * 
	 * @param passwordParameter
	 *            the parameter name. Defaults to "j_password".
	 */
	public void setPasswordParameter(String passwordParameter) {
		Validate.notEmpty(passwordParameter, "Password parameter must not be empty or null");
		this.passwordParameter = passwordParameter;
	}

	String getUsernameParameter() {
		return usernameParameter;
	}

	String getPasswordParameter() {
		return passwordParameter;
	}
}
