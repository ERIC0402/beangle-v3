package org.beangle.website.system.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.Order;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.system.model.DictType;


/**
 * 字典类型
 * @author GOKU
 *
 */
public class DictTypeAction extends SecurityActionSupport {

	/**
	 * 查询语句
	 */
	protected OqlBuilder<DictType> getQueryBuilder() {
		OqlBuilder<DictType> oql = OqlBuilder.from(DictType.class,"dictType");
		populateConditions(oql);
		String order = get(Order.ORDER_STR);
		if(StringUtils.isEmpty(order)){
			order = "dictType.code";
		}
		oql.orderBy(order);
		oql.limit(getPageLimit());
		return oql;
	}
	
	/**
	 * 保存字典类型
	 */
	protected String saveAndForward(Entity<?> entity) {
		DictType dictType = (DictType) entity;
		//以下判断代码唯一
		Long id = getLong("dictType.id");
		String code = get("dictType.code");
		if(id == null){
			List<DictType> list = getDictTypes(code, null);
			if(list != null && list.size() > 0){
				put("dictType",dictType);
				addActionError("代码已存在");
				return forward("form");
			}
		}else{
			List<DictType> list = getDictTypes(code, id);
			if(list != null && list.size() > 0){
				put("dictType",dictType);
				addActionError("代码已存在");
				return forward("form");
			}
		}
		entityDao.saveOrUpdate(dictType);
		return redirect("search", "info.save.success");
	}
	
	/**
	 * 根据code获取不等于id的字典类型
	 * @param code
	 * @param id
	 * @return
	 */
	protected List<DictType> getDictTypes(String code,Long id){
		OqlBuilder<DictType> oql = OqlBuilder.from(DictType.class,"d");
		if(StringUtils.isNotEmpty(code)){
			oql.where("d.code =:code",code);
		}
		if(id != null){
			oql.where("d.id !=:did",id);
		}
		return entityDao.search(oql);
	}
	
	public String remove(){
		Long[] dictTypeIds = getEntityIds();
		boolean flag = false;
		for(int i=0;i<dictTypeIds.length;i++){
			DictType dictType = entityDao.get(DictType.class, dictTypeIds[i]);
			try {
				entityDao.remove(dictType);
			} catch (Exception e) {
				flag = true;
			}
		}
		if(flag){
			return redirect("search","删除成功、但包含字典数据的字典类型不做处理");
		}
		return redirect("search","info.remove.success");
	}
	
	@Override
	protected String getEntityName() {
		return DictType.class.getName();
	}
}
