/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.web.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.dispatcher.SessionMap;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.context.AuthenticationUtils;
import org.beangle.security.core.context.SecurityContextHolder;
import org.beangle.security.web.auth.logout.LogoutHandlerStack;
import org.beangle.security.web.auth.preauth.CookieUsernameSource;
import org.beangle.struts2.action.BaseAction;
import org.beangle.web.util.CookieUtils;

import com.opensymphony.xwork2.ActionContext;

public class LogoutAction extends BaseAction {

	private LogoutHandlerStack handlerStack;

	public String index() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//CookieUtils.deleteCookieByName(getRequest(), getResponse(), CookieUsernameSource.KEY_USER_NAME);
		CookieUtils.deleteCookieByName(getRequest(), getResponse(), CookieUsernameSource.KEY_UCHECK);
		String result = "success";
		if (AuthenticationUtils.isValid(auth)) {
			if (null != handlerStack) handlerStack.logout(getRequest(), getResponse(), auth);
			((SessionMap<?, ?>) ActionContext.getContext().getSession()).invalidate();
			String targetUrl = determineTargetUrl(getRequest());
			if (null != targetUrl) {
//				result = "redirect:" + targetUrl;
//				return result;
				try {
					getResponse().sendRedirect(targetUrl);
				} catch (IOException e) {
				}
				return null;
			}
		}
		return result;
	}

	protected String determineTargetUrl(HttpServletRequest request) {
		String gotoURL = get("goto");
		if(StringUtils.isEmpty(gotoURL)){
			gotoURL = get("logoutSuccessUrl");
		}
		return gotoURL;
	}

	public void setHandlerStack(LogoutHandlerStack handlerStack) {
		this.handlerStack = handlerStack;
	}

}
