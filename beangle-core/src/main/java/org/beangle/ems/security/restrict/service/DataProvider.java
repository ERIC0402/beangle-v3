/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.restrict.service;

import java.util.List;

import org.beangle.ems.security.restrict.RestrictField;

/**
 * @author beangle
 * @version $Id: SourceProvider.java Nov 9, 2010 7:18:38 PM beangle $
 */
public interface DataProvider {

	/**
	 * extract data from source
	 * 
	 * @param <T>
	 * @param type
	 * @param source
	 * @return
	 */
	public <T> List<T> getData(RestrictField field, String source);

	/**
	 * provider's unique name
	 * 
	 * @return
	 */
	public String getName();
}
