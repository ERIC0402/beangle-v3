/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.restrict.service;

import java.util.Collection;
import java.util.List;

import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.ems.security.Resource;
import org.beangle.ems.security.User;
import org.beangle.ems.security.restrict.RestrictField;
import org.beangle.ems.security.restrict.Restriction;

/**
 * 资源访问约束服务
 * 
 * @author beangle
 */
public interface RestrictionService {

	public List<Restriction> getRestrictions(User user, Resource resource);

	public List<Restriction> getAuthorityRestrictions(User user, Resource resource);

	/**
	 * Get field enumerated values.
	 * 
	 * @param fieldName
	 * @return
	 */
	public List<?> getFieldValues(String fieldName);

	/**
	 * Extract field values;
	 * 
	 * @param restrictions
	 * @param fieldName
	 * @return
	 */
	public List<?> getFieldValues(String fieldName, List<? extends Restriction> restrictions);

	/**
	 * @param field
	 * @param restriction
	 * @return
	 */
	public Object getFieldValue(RestrictField field, Restriction restriction);

	/**
	 * @param builder
	 * @param restrictions
	 */
	public void apply(OqlBuilder<?> builder, Collection<? extends Restriction> restrictions);

}
