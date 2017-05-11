package org.beangle.security.idstar.preauth;

import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.beangle.security.idstar.util.IdstarUtils;
import org.beangle.security.web.auth.preauth.UsernameSource;

import com.opensymphony.xwork2.ActionContext;
import com.wiscom.is.IdentityManager;

public class IdStarUsernameSource implements UsernameSource {
	
	public String obtainUsername(HttpServletRequest request) {
		String userName = null;
		try {
			IdentityManager im = IdstarUtils.getIdentityManager();
			if(im == null){
				return null;
			}
			Cookie all_cookies[] = request.getCookies();
			Cookie myCookie;
			String decodedCookieValue = null;
			if (all_cookies != null) {
				for (int i = 0; i < all_cookies.length; i++) {
					myCookie = all_cookies[i];
					if (myCookie.getName().equals("iPlanetDirectoryPro")) {
						decodedCookieValue = URLDecoder.decode(myCookie.getValue(), "GB2312");
					}
				}
			}
			System.out.println("decodedCookieValue: " + decodedCookieValue);
			if (decodedCookieValue != null) {
				userName = im.getCurrentUser(decodedCookieValue);
			}
			// userName = "admin";
		} catch (Exception e) {
		}
		System.out.println("idstar username:" + userName);
//		ServletActionContext.getRequest().getSession().setAttribute("IDSTAR_USER_NAME", userName);
		return userName;
	}

}
