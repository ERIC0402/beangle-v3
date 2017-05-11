/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.portal.action;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beangle.ems.security.User;
import org.beangle.security.auth.AuthenticationDetailsSource;
import org.beangle.security.auth.AuthenticationManager;
import org.beangle.security.auth.UsernamePasswordAuthentication;
import org.beangle.security.codec.RSAUtil;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;
import org.beangle.security.core.context.AuthenticationUtils;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.security.web.auth.preauth.CookieUsernameSource;
import org.beangle.struts2.action.BaseAction;
import org.beangle.web.util.CookieUtils;
import org.bouncycastle.util.encoders.Hex;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;
import com.opensymphony.xwork2.ActionContext;

public class LoginAction extends BaseAction {

	private CaptchaService captchaService;

	private AuthenticationDetailsSource<HttpServletRequest, Object> authenticationDetailsSource;

	private AuthenticationManager authenticationManager;

	private SessionRegistry sessionRegistry;

	public static final String LOGIN_FAILURE_COUNT = "loginFailureCount";

	public String index() {
		RSAPublicKey publicKey = RSAUtil.getDefaultPublicKey();
		put("modulus", new String(Hex.encode(publicKey.getModulus().toByteArray())));
		put("exponent", new String(Hex.encode(publicKey.getPublicExponent().toByteArray())));
		String backurl = get("backurl");
		if (StringUtils.isEmpty(backurl)) {
			backurl = (String) getSession().get("backurl");
		}
		if (StringUtils.isNotBlank(backurl)) {
			if (backurl.indexOf("!save") > 0 || backurl.indexOf("method=save") > 0) {
				backurl = null;
			}
		}
		put("backurl", backurl);
		if (AuthenticationUtils.hasValidAuthentication()) {
			// return "home";
		} else {
			if (!shouldLogin()) {
				notFailEnough();
				return "failure";
			}
			String errorMsg = doLogin();
			if (StringUtils.isNotEmpty(errorMsg)) {
				addActionError(getText(errorMsg));
				increaseLoginFailure();
				return "failure";
			}
			clearLoginFailure();
		}
		if (StringUtils.isNotEmpty(backurl) && backurl.indexOf("logout.action") < 0) {
			try {
				ServletActionContext.getResponse().sendRedirect(backurl);
			} catch (IOException e) {
			}
			return null;
		}
		return "home";
	}

	protected boolean shouldLogin() {
		String username = get("username");
		String password = get("encodedPassword");
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return false;
		}
		if (notFailEnough()) {
			return true;
		}
		// 校验验证码
		if (null != captchaService) {
			try {
				String sessionId = getRequest().getSession().getId();
				String captchaText = get("captcha");
				/*if (StringUtils.isEmpty(captchaText)) {
					addActionError(getText("security.EmptyCaptcha"));
					return false;
				}
				Boolean valid = captchaService.validateResponseForID(sessionId, captchaText);
				if (Boolean.FALSE.equals(valid)) {
					addActionError(getText("security.WrongCaptcha"));
					return false;
				}*/
			} catch (CaptchaServiceException e) {
				addActionError(getText("security.WrongCaptcha"));
				return false;
			}
		}
		return true;
	}

	protected String doLogin() {
		String username = get("username");
		String password = get("password");
		password = RSAUtil.decryptStringByJs(get("encodedPassword"));
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return "failure";
		}
		username = username.trim();
		HttpServletRequest request = getRequest();
		UsernamePasswordAuthentication auth = new UsernamePasswordAuthentication(username, password);
		auth.setDetails(authenticationDetailsSource.buildDetails(request));
		Authentication authRequest = auth;
		try {
			authRequest = authenticationManager.authenticate(authRequest);
			sessionRegistry.register(authRequest, request.getSession().getId());
			SecurityContextHolder.getContext().setAuthentication(authRequest);
		} catch (AuthenticationException e) {
			return e.getMessage();
		}

		User user = entityDao.getEntity(User.class, "name", username);
		Long categoryId = user.getDefaultCategory().getId();
		ActionContext.getContext().getSession().put("security.categoryId", categoryId);

		String keepLogin = get("keepLogin");
		if (keepLogin != null) {
			int time = 2 * 7 * 24 * 3600;
			CookieUtils.addCookie(request, getResponse(), CookieUsernameSource.KEY_USER_NAME, username, time);
			CookieUtils.addCookie(request, getResponse(), CookieUsernameSource.KEY_UCHECK, CookieUsernameSource.getUcheck(request, username), time);
		}
		return null;
	}

	private boolean notFailEnough() {
		Integer loginFailureCount = (Integer) getSession().get(LOGIN_FAILURE_COUNT);
		if (null == loginFailureCount) {
			loginFailureCount = Integer.valueOf(0);
		}
		if (loginFailureCount.intValue() <= 1) {
			put("needCaptcha", false);
			return true;
		} else {
			put("needCaptcha", true);
			return false;
		}
	}

	private void increaseLoginFailure() {
		Integer loginFailureCount = (Integer) getSession().get(LOGIN_FAILURE_COUNT);
		if (null == loginFailureCount) {
			loginFailureCount = Integer.valueOf(0);
		}
		loginFailureCount++;
		getSession().put(LOGIN_FAILURE_COUNT, loginFailureCount);
	}

	private void clearLoginFailure() {
		getSession().remove(LOGIN_FAILURE_COUNT);
	}

	public void setCaptchaService(CaptchaService captchaService) {
		this.captchaService = captchaService;
	}

	public void setAuthenticationDetailsSource(AuthenticationDetailsSource<HttpServletRequest, Object> authenticationDetailsSource) {
		this.authenticationDetailsSource = authenticationDetailsSource;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}
}
