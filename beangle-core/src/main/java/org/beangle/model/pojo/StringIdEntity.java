/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.pojo;

import javax.persistence.MappedSuperclass;

import org.beangle.model.Entity;

/**
 * @author beangle
 * @version $Id: StringIdEntity.java Jul 15, 2011 7:58:42 AM beangle $
 */
@MappedSuperclass
public interface StringIdEntity extends Entity<String> {

	public String getId();

	public void setId(String id);

}
