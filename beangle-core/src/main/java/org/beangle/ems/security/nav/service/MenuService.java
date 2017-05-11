/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.nav.service;

import java.util.List;

import org.beangle.ems.security.Group;
import org.beangle.ems.security.User;
import org.beangle.ems.security.nav.Menu;
import org.beangle.ems.security.nav.MenuProfile;

/**
 * @author beangle
 * @version $Id: MenuService.java Jun 5, 2011 9:24:23 PM beangle $
 */
public interface MenuService {

	/**
	 * 查询用户能够适用的菜单配置
	 * @param user
	 * @return
	 */
	public List<MenuProfile> getProfiles(User user);
	/**
	 * 获取用户的直接权限范围内的资源和所具有用户组的资源.
	 * 
	 * @param userId
	 * @return
	 */
	public List<Menu> getMenus(List<MenuProfile> profile, User user);

	/**
	 * 用户组内对应的资源
	 * 
	 * @param groupId
	 * @return
	 */
	public List<Menu> getMenus(List<MenuProfile> profile, Group group, Boolean enabled);

	/**
	 * 移动菜单到指定的位置
	 * 
	 * @param menu
	 * @param location
	 *            新的位置
	 * @param indexno
	 *            新位置的顺序号
	 */
	public void move(Menu menu, Menu location, int indexno);
	
	public Menu getParentMenu(Menu menu);

}
