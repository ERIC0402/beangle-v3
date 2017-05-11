package org.beangle.website.system.service;

import java.util.List;
import java.util.Map;

import org.beangle.website.system.model.DictData;

public interface DictDataService {

	/**
	 * 查找数据字典类型
	 * 
	 * @param typeid
	 * @return
	 */
	public List<DictData> findDictData(Long typeid);

	/**
	 * 查找数据字典类型
	 * 
	 * @param typeid
	 * @return
	 */
	public List<DictData> findDictData(String code);

	/**
	 * 查找数据字典类型
	 * 
	 * @param typeid
	 * @return
	 */
	public Map<String, DictData> getDictDataMap(Long typeid);

	/**
	 * 根据code查找数据字典类型
	 * 
	 * @param code
	 * @return
	 */
	public Map<String, DictData> getDictDataMapByCode(String code);

	/**
	 * 添加数据字典类型
	 * 
	 * @param typeid
	 * @param name
	 * @return
	 */
	public DictData saveDictData(Long typeid, String name);

	/**
	 * 根据身份证号码获得性别数据字典
	 * 
	 * @param sfzh
	 * @return
	 */
	public DictData getXbBySfzh(String sfzh);

	/**
	 * 根据字典数据编码获得一个数据字典
	 * 
	 * @param code
	 * @return
	 */
	public DictData getDictData(String code);

}
