/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.website.system.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.system.model.DictTree;
import org.beangle.website.system.service.DictTreeService;

/**
 * @author beangle
 * @version $Id: MenuServiceImpl.java Jun 5, 2011 9:25:49 PM beangle $
 */
public class DictTreeServiceImpl extends BaseServiceImpl implements DictTreeService {

	public void move(DictTree tree, DictTree parent, int indexno) {
		if (ObjectUtils.equals(tree.getParent(), parent)) {
			if (NumberUtils.toInt(tree.getIndexno()) != indexno) {
				shiftCode(tree, parent, indexno);
			}
		} else {
			if (null != tree.getParent()) {
				tree.getParent().getChildren().remove(tree);
			}
			tree.setParent(parent);
			shiftCode(tree, parent, indexno);
		}
	}

	private void shiftCode(DictTree menu, DictTree newParent, int indexno) {
		List<DictTree> sibling = null;
		if (null != newParent) {
			sibling = newParent.getChildren();
		}else {
			sibling = CollectUtils.newArrayList();
			OqlBuilder<DictTree> query = OqlBuilder.from(DictTree.class);
			query.where("parent is null");
			sibling = entityDao.search(query);
		}
		Collections.sort(sibling);
		sibling.remove(menu);
		indexno--;
		if (indexno > sibling.size()) {
			indexno = sibling.size();
		}
		sibling.add(indexno, menu);
		int nolength = String.valueOf(sibling.size()).length();
		Set<DictTree> menus = CollectUtils.newHashSet();
		for (int seqno = 1; seqno <= sibling.size(); seqno++) {
			DictTree one = sibling.get(seqno - 1);
			generateCode(one, StringUtils.leftPad(String.valueOf(seqno), nolength, '0'), menus);
		}
		entityDao.saveOrUpdate(menus);
	}

	private void generateCode(DictTree menu, String indexno, Set<DictTree> menus) {
		menus.add(menu);
		if (null != indexno) {
			((DictTree) menu).generateCode(indexno);
		} else {
			((DictTree) menu).generateCode();
		}
		if (null != menu.getChildren()) {
			for (DictTree m : menu.getChildren()) {
				generateCode(m, null, menus);
			}
		}
	}
	
	/**
	 * 根据代码获取字典树节点
	 * @param dm 代码
	 * @return
	 */
	protected DictTree getDictTreeByDM(String dm){
		OqlBuilder<DictTree> query = OqlBuilder.from(DictTree.class,"d");
		query.where("d.dm=:dm",dm);
		List<DictTree> dictTrees = entityDao.search(query);
		DictTree dictTree = null;
		if(dictTrees != null && dictTrees.size() > 0){
			dictTree = dictTrees.get(0);
		}
		return dictTree;
	}

	public List<DictTree> findDictTreeByCode(String code) {
		DictTree dictTree = getDictTreeByDM(code);
		if(dictTree != null){
			OqlBuilder<DictTree> query = OqlBuilder.from(DictTree.class,"d");
			query.where("d.code like '" + dictTree.getCode() + "%'");
			query.where("d.enabled=1");
			query.orderBy("d.code");
			return entityDao.search(query);
		}else{
			return null;
		}
	}

	public Map<String, DictTree> getDictTreeMap(String code) {
		List<DictTree> list = findDictTreeByCode(code);
		Map<String, DictTree> map = new HashMap<String, DictTree>(list==null?0:list.size());
		for(DictTree dictTree : list){
			map.put(dictTree.getName(), dictTree);
		}
		return map;
	}

}
