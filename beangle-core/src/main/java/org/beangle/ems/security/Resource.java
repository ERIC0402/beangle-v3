/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security;

import java.util.Set;

import org.beangle.model.pojo.LongIdEntity;
import org.beangle.ems.security.restrict.RestrictEntity;

/**
 * 系统资源.<br>
 * 
 * @author beangle 2008-7-28
 */
public interface Resource extends LongIdEntity {

	/**
	 * 资源名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 资源名称
	 * 
	 * @param name
	 */
	public void setName(String name);

	/**
	 * 资源标题
	 * 
	 * @return
	 */
	public String getTitle();

	/**
	 * 资源标题
	 * 
	 * @param title
	 */
	public void setTitle(String title);

	/**
	 * 返回资源描述
	 * 
	 * @return
	 */
	public String getRemark();

	/**
	 * 资源状态
	 * 
	 * @return
	 */
	public boolean isEnabled();

	/**
	 * 设置资源状态
	 * 
	 * @param IsActive
	 * @return
	 */
	public void setEnabled(boolean isEnabled);

	/**
	 * 适用的用户类别
	 * 
	 * @return
	 */
	public Set<Category> getCategories();

	/**
	 * 查询适用的用户类别
	 * 
	 * @param categories
	 */
	public void setCategories(Set<Category> categories);

	/**
	 * 资源访问范围
	 * 
	 * @return
	 */
	public int getScope();

	/**
	 * 设置资源访问范围
	 * 
	 * @param scope
	 */
	public void setScope(int scope);

	public Set<RestrictEntity> getEntities();

	public void setEntities(Set<RestrictEntity> entities);

	/**
	 * 访问时需要其它参数
	 * 
	 * @return
	 */
	public boolean isNeedParams();

	/**
	 * 设置访问时需要其它参数
	 * 
	 * @param needParams
	 */
	public void setNeedParams(boolean needParams);

	public static class Scope {
		/** 不受保护的公共资源 */
		public static final int PUBLIC = 0;
		/** 受保护的公有资源 */
		public static final int PROTECTED = 1;
		/** 受保护的私有资源 */
		public static final int PRIVATE = 2;
	}
}
