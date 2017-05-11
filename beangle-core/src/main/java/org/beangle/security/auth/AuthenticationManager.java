/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.auth;

import org.beangle.security.core.Authentication;
import org.beangle.security.core.AuthenticationException;

/**
 * 认证服务
 * 
 * @author beangle
 */
public interface AuthenticationManager {
	/**
	 * 认证
	 * 
	 * @param auth
	 * @return
	 * @throws AuthenticationException
	 */
	public Authentication authenticate(Authentication auth) throws AuthenticationException;
}
