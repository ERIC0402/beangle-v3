/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.idstar.auth;

import org.beangle.security.idstar.util.IdstarUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wiscom.is.IdentityManager;

public class DefaultIdstarValidator implements IdstarValidator {

	public Logger log = LoggerFactory.getLogger(IdstarUtils.class);

	public boolean verifyPassword(String name, String password) {
		try {
			log.info("DefaultIdstarValidator.verifyPassword start");
			IdentityManager im = IdstarUtils.getIdentityManager();
			boolean verifyPassword = im.checkPassword(name, password);
			log.info("DefaultIdstarValidator.verifyPassword end");
			return verifyPassword;
		} catch (Exception e) {
			return false;
		} 
	}
}
