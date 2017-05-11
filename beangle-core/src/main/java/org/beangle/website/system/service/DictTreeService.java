/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.website.system.service;

import java.util.List;
import java.util.Map;

import org.beangle.website.system.model.DictTree;

/**
 * @author beangle
 * @version $Id: MenuService.java Jun 5, 2011 9:24:23 PM beangle $
 */
public interface DictTreeService {

	/**
	 * 移动菜单到指定的位置
	 * 
	 * @param menu
	 * @param location
	 *            新的位置
	 * @param indexno
	 *            新位置的顺序号
	 */
	public void move(DictTree menu, DictTree parent, int indexno);
	
	/**
	 * 根据code查找字典树
	 * @param code
	 * @return
	 */
	public List<DictTree> findDictTreeByCode(String code);
	
	/**
	 * 根据code查找字典树map
	 * @param code
	 * @return
	 */
	public Map<String, DictTree> getDictTreeMap(String code);

}
