package org.beangle.ems.config.service;

import java.util.List;

import org.beangle.ems.config.model.ClassPropertyConfig;

/**
 * ClassConfig服务类
 * 
 * @作者：钱剑林
 * @公司：上海彦致信息技术有限公司
 * @创建日期：2015年4月9日 上午10:36:51
 */
public interface ClassConfigService {

	/**
	 * 表单
	 */
	String FORM = "form";
	/**
	 * 列表
	 */
	String LIST = "list";

	/**
	 * 根据Class获得顶级配置
	 * 
	 * @param clazz 实体类
	 * @return 实体类的属性配置
	 * @author 钱剑林
	 * @createDate 2015年4月9日 上午10:37:55
	 */
	public List<ClassPropertyConfig> findConfig(Class<?> clazz);

	/**
	 * 根据Class和Code获得二级配置
	 * 
	 * @param clazz 实体类
	 * @param code 二级配置代码
	 * @return 实体类的属性配置
	 * @author 钱剑林
	 * @createDate 2015年4月9日 上午10:38:38
	 */
	public List<ClassPropertyConfig> findConfig(Class<?> clazz, String code);

	/**
	 * 保存顶级配置
	 * 
	 * @param clazz
	 * @param config
	 */
	public void saveConfig(Class<?> clazz, List<ClassPropertyConfig> list);

	/**
	 * 保存二级配置
	 * 
	 * @param clazz
	 * @param config
	 * @param code
	 */
	public void saveConfig(Class<?> clazz, List<ClassPropertyConfig> list, String code);

}
