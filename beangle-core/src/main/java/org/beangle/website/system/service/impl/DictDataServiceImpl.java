package org.beangle.website.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.model.DictDataUtils;
import org.beangle.website.system.model.DictType;
import org.beangle.website.system.service.DictDataService;
import org.codehaus.plexus.util.StringUtils;

public class DictDataServiceImpl implements DictDataService {
	
	private EntityDao entityDao;
	
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	public List<DictData> findDictData(Long typeid) {
		return findDictData(typeid, false);
	}


	public List<DictData> findDictData(String code) {
		OqlBuilder<DictData> oql = OqlBuilder.from(DictData.class, "dd");
		oql.where("dd.enabled = true");
		oql.where("dd.dictType.code = :code", code);
		oql.orderBy("dd.code");
		oql.cacheable();
		return entityDao.search(oql);
	}

	public List<DictData> findDictData(Long typeid, boolean all) {
		OqlBuilder<DictData> oql = OqlBuilder.from(DictData.class, "dd");
		if(!all){
			oql.where("dd.enabled = true");
		}
		oql.where("dd.dictType.id = :typeid", typeid);
		oql.orderBy("dd.code");
		return entityDao.search(oql);
	}

	public DictData saveDictData(Long typeid, String name) {
		DictType dictType = entityDao.get(DictType.class, typeid);
		Long count = entityDao.count(DictData.class, "dictType.id", typeid);
		String code = count.toString();
		if(code.length() < 0){
			code ="0"+code;
		}
		code = dictType.getCode() + "_" + code;
		DictData dd = new DictData();
		dd.setCode(code);
		dd.setName(name);
		dd.setDictType(dictType);
		entityDao.saveOrUpdate(dd);
		return dd;
	}

	public Map<String, DictData> getDictDataMap(Long typeid) {
		List<DictData> list = findDictData(typeid, true);
		Map<String, DictData> map = new HashMap<String, DictData>(list.size());
		for(DictData dd : list){
			map.put(dd.getName(), dd);
		}
		return map;
	}
	
	public Map<String, DictData> getDictDataMapByCode(String code) {
		List<DictData> list = findDictData(code);
		Map<String, DictData> map = new HashMap<String, DictData>(list.size());
		for(DictData dd : list){
			map.put(dd.getName(), dd);
		}
		return map;
	}

	public DictData getXbBySfzh(String sfzh) {
		if(sfzh == null || sfzh.length() < 2){
			return null;
		}
		String code = DictDataUtils.GENDER_01_MALE;
		String last2 = sfzh.substring(sfzh.length() -2, sfzh.length() -1);
		Integer last2i = Integer.parseInt(last2);
		if(last2i % 2 == 0){
			code = DictDataUtils.GENDER_02_FEMALE;
		}
		DictData xb = getDictData(code);
		return xb;
	}

	/**
	 * 根据字典数据编码获得一个数据字典
	 * @param code
	 * @return
	 */
	public DictData getDictData(String code) {
		if(StringUtils.isEmpty(code)){
			return new DictData();
		}
		OqlBuilder<DictData> query = OqlBuilder.from(DictData.class, "d");
		query.where("d.enabled=1");
		query.where("d.code=:code", code);
		query.cacheable(true);
		List<DictData> list = entityDao.search(query);
		if(list.isEmpty()){
			return new DictData();
		}else{
			return list.get(0);
		}
	}
}
