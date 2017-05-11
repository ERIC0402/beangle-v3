/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.restrict;

import java.util.Set;

import org.beangle.model.pojo.LongIdEntity;

/**
 * 数据限制对象
 * 
 * @author beangle
 */
public interface RestrictEntity extends LongIdEntity {

	public String getName();

	public void setName(String name);

	public String getType();

	public void setType(String type);

	public String getRemark();

	public void setRemark(String remark);

	public Set<RestrictField> getFields();

	public void setFields(Set<RestrictField> fields);

	public RestrictField getField(String fieldName);

}
