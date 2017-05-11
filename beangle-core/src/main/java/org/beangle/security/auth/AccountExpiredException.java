/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.auth;

/**
 * 账户过期异常
 * 
 * @author beangle
 */
public class AccountExpiredException extends AccountStatusException {

	private static final long serialVersionUID = 1L;

	public AccountExpiredException(String msg) {
		super(msg);
	}

	public AccountExpiredException(String msg, Object extraInformation) {
		super(msg, extraInformation);
	}
}
