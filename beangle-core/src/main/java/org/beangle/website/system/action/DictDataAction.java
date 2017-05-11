package org.beangle.website.system.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.Order;
import org.beangle.ems.security.Category;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.model.DictType;


/**
 * 字典数据
 * @author GOKU
 *
 */
public class DictDataAction extends SecurityActionSupport {

	@Override
	protected String getEntityName() {
		return DictData.class.getName();
	}
	
	protected void indexSetting() {
		 putAllEnabledDictTypes();
	}
	
	/**
	 * 把有效的字典类型放入页面
	 */
	protected void putAllEnabledDictTypes(){
		OqlBuilder<DictType> oql = OqlBuilder.from(DictType.class,"d");
		oql.where("d.enabled=1");
		oql.orderBy("d.name");
		put("dictTypes",entityDao.search(oql));
	}
	
	/**
	 * 查询语句
	 */
	protected OqlBuilder<DictData> getQueryBuilder() {
		OqlBuilder<DictData> oql = OqlBuilder.from(DictData.class,"dictData");
		populateConditions(oql);
		String order = get(Order.ORDER_STR);
		if(StringUtils.isEmpty(order)){
			order = "dictData.code";
		}
		oql.orderBy(order);
		oql.limit(getPageLimit());
		return oql;
	}
	
	protected void editSetting(Entity<?> entity) {
		DictData dictData = (DictData) entity;
		put("dictData",dictData);
		putAllEnabledDictTypes();
	}
	
	/**
	 * 保存字典类型
	 */
	protected String saveAndForward(Entity<?> entity) {
		DictData dictData = (DictData) entity;
		//以下判断代码唯一
		Long id = getLong("dictData.id");
		String code = get("dictData.code");
		if(id == null){
			List<DictData> list = getDictDatas(code, null);
			if(list != null && list.size() > 0){
				put("dictData",dictData);
				putAllEnabledDictTypes();
				return forward("form","code.exist");
			}
		}else{
			List<DictData> list = getDictDatas(code, id);
			if(list != null && list.size() > 0){
				put("dictData",dictData);
				putAllEnabledDictTypes();
				return forward("form","code.exist");
			}
		}
		entityDao.saveOrUpdate(dictData);
		return redirect("search", "info.save.success");
	}
	
	/**
	 * 根据code获取不等于id的字典类型
	 * @param code
	 * @param id
	 * @return
	 */
	protected List<DictData> getDictDatas(String code,Long id){
		OqlBuilder<DictData> oql = OqlBuilder.from(DictData.class,"d");
		if(StringUtils.isNotEmpty(code)){
			oql.where("d.code =:code",code);
		}
		if(id != null){
			oql.where("d.id !=:did",id);
		}
		return entityDao.search(oql);
	}
	
	public String remove(){
		Long[] dictDataIds = getEntityIds();
		boolean flag = false;
		for(int i=0;i<dictDataIds.length;i++){
			DictData dictData = entityDao.get(DictData.class, dictDataIds[i]);
			try {
				entityDao.remove(dictData);
			} catch (Exception e) {
				flag = true;
			}
		}
		if(flag){
			return redirect("search","删除成功、但已使用的字典数据不做处理");
		}
		return redirect("search","info.remove.success");
	}
}
