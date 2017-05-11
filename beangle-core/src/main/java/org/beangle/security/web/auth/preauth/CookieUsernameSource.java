package org.beangle.security.web.auth.preauth;

import javax.servlet.http.HttpServletRequest;

import org.beangle.security.codec.EncryptUtil;
import org.beangle.web.util.CookieUtils;

public class CookieUsernameSource implements UsernameSource {
	
	public static final String KEY_USER_NAME = "username";
	public static final String KEY_UCHECK = "ucheck";
	

	public String obtainUsername(HttpServletRequest request) {
		String username = null;
		try {
			String cusername = CookieUtils.getCookieValue(request, KEY_USER_NAME);
			String ucheck =  CookieUtils.getCookieValue(request, KEY_UCHECK);
			if(getUcheck(request, cusername).equals(ucheck)){
				username = cusername;
			}
		} catch (Exception e) {
		}
		return username;
	}


	public static String getUcheck(HttpServletRequest request, String username) {
		String key = username + "6B6BDF4328D069FFD14E8AA8EF0244FA" + request.getRemoteAddr();
		return EncryptUtil.encode(key);
	}

}
