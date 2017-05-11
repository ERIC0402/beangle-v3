/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security;

import java.util.Set;

import org.beangle.ems.security.restrict.GroupRestriction;
import org.beangle.ems.security.restrict.RestrictionHolder;
import org.beangle.model.pojo.HierarchyEntity;
import org.beangle.model.pojo.LongIdTimeEntity;

/**
 * 系统用户组的基本信息
 * 
 * @author beangle 2005-9-26
 */
public interface Group extends LongIdTimeEntity, RestrictionHolder<GroupRestriction>, HierarchyEntity<Group> {

	/**
	 * 名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 设置名称
	 * 
	 * @param name
	 */
	public void setName(String name);

	/**
	 * 查询权限
	 * 
	 * @return
	 */
	public Set<Authority> getAuthorities();

	/**
	 * 设置权限
	 * 
	 * @param authorities
	 */
	public void setAuthorities(Set<Authority> authorities);

	/**
	 * 关联的系统用户
	 * 
	 * @return
	 */
	public Set<GroupMember> getMembers();

	/**
	 * 关联的系统用户
	 * 
	 * @param users
	 */
	public void setMembers(Set<GroupMember> members);

	/**
	 * Owner
	 * 
	 * @return
	 */
	public User getOwner();

	/**
	 * 设置创建者
	 * 
	 * @param owner
	 */
	public void setOwner(User owner);

	/**
	 * 用户组对应的类别.
	 * 
	 * @return
	 */
	public Category getCategory();

	/**
	 * 设置用户组对应的类别.
	 * 
	 * @param categories
	 */
	public void setCategory(Category userCategory);

	/**
	 * 状态
	 * 
	 * @return
	 */
	public boolean isEnabled();

	/**
	 * 设置状态
	 * 
	 * @param isEnabled
	 */
	public void setEnabled(boolean isEnabled);

	/**
	 * 备注
	 * 
	 * @return
	 */
	public String getRemark();

	/**
	 * 设置备注
	 * 
	 * @param remark
	 */
	public void setRemark(String remark);

}
