/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.website.system.action;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.exporter.PropertyExtractor;
import org.beangle.model.util.HierarchyEntityUtil;
import org.beangle.website.common.action.FileAction;
import org.beangle.website.system.helper.DictTreePropertyExtractor;
import org.beangle.website.system.model.DictTree;
import org.beangle.website.system.service.DictTreeService;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 */
public class DictTreeAction extends FileAction {

	private DictTreeService dictTreeService;
	
	public void setDictTreeService(DictTreeService dictTreeService) {
		this.dictTreeService = dictTreeService;
	}

	protected void indexSetting() {
	}

	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		OqlBuilder<?> oql =  (OqlBuilder<?>) super.getQueryBuilder();
		return oql;
	}
	
	protected void editSetting(Entity<?> entity) {
		DictTree tree = (DictTree) entity;
		List<DictTree> folders = CollectUtils.newArrayList();
		OqlBuilder<DictTree> query = OqlBuilder.from(DictTree.class, "t");
		query.where("enabled = 1");
		query.orderBy("code");
		folders = entityDao.search(query);
		folders.removeAll(HierarchyEntityUtil.getFamily(tree));
		put("parents", folders);
	}

	@Override
	protected String removeAndForward(Collection<?> entities) {
//		@SuppressWarnings("unchecked")
//		List<DictTree> trees = (List<DictTree>) entities;
//		List<DictTree> parents = CollectUtils.newArrayList();
//		for (DictTree tree : trees) {
//			if (null != tree.getParent()) {
//				tree.getParent().getChildren().remove(tree);
//				parents.add(tree.getParent());
//			}
//		}
//		entityDao.saveOrUpdate(parents);
		return super.removeAndForward(entities);
	}

	protected String saveAndForward(Entity<?> entity) {
		DictTree tree = (DictTree) entity;
		tree.setUpdateTime(new Date());
		if(tree.getCreateTime() == null){
			tree.setCreateTime(new Date());
		}
		try {
			Long newParentId = getLong("parent.id");
			int indexno = getInteger("indexno");
			DictTree parent = null;
			if (null != newParentId) {
				parent = entityDao.get(DictTree.class, newParentId);
			}
			dictTreeService.move(tree, parent, indexno);
			entityDao.saveOrUpdate(tree);
		} catch (Exception e) {
			e.printStackTrace();
			return forward(ERROR);
		}
		return redirect("search", "info.save.success");
	}

	/**
	 * 禁用或激活一个或多个模块
	 * 
	 * @return
	 */
	public String activate() {
		Long[] treeIds = getEntityIds(getShortName());
		Boolean enabled = getBoolean("isActivate");
		if (null == enabled) {
			enabled = Boolean.TRUE;
		}
		List<DictTree> trees = entityDao.get(DictTree.class, treeIds);
		for (DictTree tree : trees) {
			tree.setEnabled(enabled);
		}
		entityDao.saveOrUpdate(trees);
		return redirect("search", "info.save.success");
	}

	/**
	 * 打印预览功能列表
	 * 
	 * @return
	 */
	public String preview() {
		OqlBuilder<DictTree> query = OqlBuilder.from(DictTree.class, "tree");
		populateConditions(query);
		query.orderBy("tree.code asc");
		put("trees", entityDao.search(query));

		query.cleanOrders();
		query.select("max(length(tree.code)/2)");
		List<?> rs = entityDao.search(query);
		put("depth", rs.get(0));
		return forward();
	}

	@Override
	public String info() {
		Long entityId = getEntityId(getShortName());
		if (null == entityId) {
			logger.warn("cannot get paremeter {}Id or {}.id", getShortName(), getShortName());
		}
		DictTree tree = (DictTree) getModel(getEntityName(), entityId);
		put(getShortName(), tree);
		return forward();
	}

	protected PropertyExtractor getPropertyExtractor() {
		return new DictTreePropertyExtractor(getTextResource());
	}

	@Override
	protected String getEntityName() {
		return DictTree.class.getName();
	}

	protected Map<String, Object> getParams() {
		return ActionContext.getContext().getParameters();
	}

}
