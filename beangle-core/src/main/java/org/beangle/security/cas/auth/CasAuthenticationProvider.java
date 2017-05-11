/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.cas.auth;

import static org.beangle.security.cas.auth.CasAuthentication.STATELESS_ID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.beangle.commons.lang.StrUtils;
import org.beangle.security.auth.AuthenticationProvider;
import org.beangle.security.auth.BadCredentialsException;
import org.beangle.security.auth.UsernamePasswordAuthentication;
import org.beangle.security.cas.web.CasPreauthFilter;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.userdetail.AccountStatusChecker;
import org.beangle.security.core.userdetail.UserDetail;
import org.beangle.security.core.userdetail.UserDetailChecker;
import org.beangle.security.core.userdetail.UserDetailService;
import org.beangle.security.core.userdetail.UsernameNotFoundException;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * An {@link AuthenticationProvider} implementation that integrates with JA-SIG
 * Central Authentication Service (CAS).
 * <p>
 * This <code>AuthenticationProvider</code> is capable of validating
 * {@link UsernamePasswordAuthenticationToken} requests which contain a <code>principal</code> name
 * equal to either {@link CasPreauthFilter#STATEFUL_ID} or {@link CasPreauthFilter#STATELESS_ID} .
 * It can also validate a previously created {@link CasAuthentication}.
 * 
 * @author beangle
 */
public class CasAuthenticationProvider implements AuthenticationProvider, InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(CasAuthenticationProvider.class);
	private UserDetailService<Authentication> userDetailService;
	private UserDetailChecker userDetailChecker;
	private StatelessTicketCache statelessTicketCache = new NullTicketCache();
	private String key;
	private TicketValidator ticketValidator;

	public void afterPropertiesSet() throws Exception {
		Validate.notNull(this.userDetailService, "A userDetailsService must be set");
		Validate.notNull(this.ticketValidator, "A ticketValidator must be set");
		Validate.notNull(this.statelessTicketCache, "A statelessTicketCache must be set");
		Validate.notEmpty(this.key,
				"A Key is required so CasAuthenticationProvider can identify tokens it previously authenticated");
		if (null == userDetailChecker) {
			AccountStatusChecker checker = new AccountStatusChecker();
			userDetailChecker = checker;
		}
	}

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		CasAuthentication casauth = (CasAuthentication) authentication;
		// If an existing CasAuthenticationToken, just check we created it
		if (casauth.isAuthenticated()) {
			if (key.hashCode() == casauth.getKeyHash()) {
				return authentication;
			} else {
				throw new BadCredentialsException("CasAuthenticationProvider.incorrectKey");
			}
		}
		// Ensure credentials are presented
		if (StringUtils.isEmpty(String.valueOf(casauth.getCredentials()))) { throw new BadCredentialsException(
				"CasAuthenticationProvider.noServiceTicket"); }
		boolean stateless = false;
		if (STATELESS_ID.equals(casauth.getPrincipal())) {
			stateless = true;
		}
		CasAuthentication result = null;
		// Try to obtain from cache
		if (stateless) {
			result = statelessTicketCache.get(casauth.getCredentials().toString());
		}
		if (result == null) {
			result = authenticateNow(casauth);
			result.setDetails(casauth.getDetails());
		}
		// Add to cache
		if (stateless) {
			statelessTicketCache.put(result);
		}
		return result;
	}

	private CasAuthentication authenticateNow(CasAuthentication auth) throws AuthenticationException {
		try {
			final Assertion assertion = ticketValidator.validate(auth.getCredentials().toString(),
					auth.getLoginUrl());
			String name = assertion.getPrincipal().getName();
			final UserDetail userDetail = userDetailService.loadDetail(new UsernamePasswordAuthentication(
					name, null));
			if (null == userDetail) {
				logger.error("cannot load {}'s detail from system", name);
				throw new UsernameNotFoundException(StrUtils.concat("user ", name, " not found in system"));
			}
			userDetailChecker.check(userDetail);
			return new CasAuthentication(key, userDetail, auth.getCredentials(), userDetail.getAuthorities(),
					userDetail, assertion);
		} catch (final TicketValidationException e) {
			throw new BadCredentialsException("Bad credentials :" + auth.getCredentials().toString(), e);
		}
	}

	protected UserDetailService<Authentication> getUserDetailService() {
		return userDetailService;
	}

	public void setUserDetailService(UserDetailService<Authentication> userDetailsService) {
		this.userDetailService = userDetailsService;
	}

	protected String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public StatelessTicketCache getStatelessTicketCache() {
		return statelessTicketCache;
	}

	protected TicketValidator getTicketValidator() {
		return ticketValidator;
	}

	public void setStatelessTicketCache(StatelessTicketCache statelessTicketCache) {
		this.statelessTicketCache = statelessTicketCache;
	}

	public void setTicketValidator(TicketValidator ticketValidator) {
		this.ticketValidator = ticketValidator;
	}

	public boolean supports(final Class<? extends Authentication> authentication) {
		if (CasAuthentication.class.isAssignableFrom(authentication)) {
			return true;
		} else {
			return false;
		}
	}
}
