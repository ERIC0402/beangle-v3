package org.beangle.ems.config.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.ems.config.model.CProperty;
import org.beangle.ems.config.model.CPropertys;
import org.beangle.ems.config.model.ClassConfig;
import org.beangle.ems.config.model.ClassPropertyConfig;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.model.query.builder.OqlBuilder;

/**
 * ClassConfig服务类的实现类
 * 
 * @作者：钱剑林
 * @公司：上海彦致信息技术有限公司
 * @创建日期：2015年4月9日 上午10:50:28
 */
public class ClassConfigServiceImpl extends BaseServiceImpl implements ClassConfigService {

	@Override
	public List<ClassPropertyConfig> findConfig(Class<?> clazz) {
		return findConfig(clazz, null);
	}

	@Override
	public List<ClassPropertyConfig> findConfig(Class<?> clazz, String code) {
		ClassConfig cc = findConfigFromDB(clazz, code);
		if (cc == null) {
			// 如果没有配置，创建一个空配置
			cc = new ClassConfig();
			cc.setClassName(getEntityName(clazz));
			cc.setCode(code);
		}
		List<ClassPropertyConfig> list = getClassPropertyConfigList(clazz, code, cc.getConfig());
		Collections.sort(list);
		return list;
	}

	/**
	 * 从数据库中查询配置
	 * 
	 * @param clazz
	 * @param code
	 * @return
	 * @author 钱剑林
	 * @createDate 2015年4月9日 上午10:46:15
	 */

	public ClassConfig findConfigFromDB(String className, String code) {
		OqlBuilder<ClassConfig> query = OqlBuilder.from(ClassConfig.class, "o");
		query.where("o.className = :className", className);
		if (StringUtils.isEmpty(code)) {
			query.where("o.code is null");
		} else {
			query.where("o.code = :code", code);
		}
		List<ClassConfig> cclist = entityDao.search(query);
		ClassConfig cc = null;
		if (!cclist.isEmpty()) {
			cc = cclist.get(0);
			// 删除重复记录
			if (cclist.size() > 1) {
				cclist.remove(0);
				entityDao.remove(cclist);
			}
		}
		return cc;
	}

	public ClassConfig findConfigFromDB(Class<?> clazz, String code) {
		return findConfigFromDB(getEntityName(clazz), code);
	}

	private String getEntityName(Class<?> clazz) {
		Entity entity = clazz.getAnnotation(Entity.class);
		String className = clazz.getName();
		if (entity != null) {
			className = entity.name();
		}
		return className;
	}

	/**
	 * 将数据库里存储的config转换为List<ClassPropertyConfig>
	 * 
	 * @param clazz
	 * @param code
	 * @param config
	 * @return
	 * @author 钱剑林
	 * @createDate 2015年4月9日 上午10:47:04
	 */
	@SuppressWarnings("unchecked")
	private List<ClassPropertyConfig> getClassPropertyConfigList(Class<?> clazz, String code, String config) {
		List<ClassPropertyConfig> cpclist = new ArrayList<ClassPropertyConfig>();
		if (StringUtils.isNotEmpty(config)) {
			try {
				JSONArray jsons = JSONArray.fromObject(config);
				for (Object o : jsons) {
					JSONObject json = (JSONObject) o;
					ClassPropertyConfig cpc = (ClassPropertyConfig) JSONObject.toBean(json, ClassPropertyConfig.class);
					cpclist.add(cpc);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 顶级配置检测类更改
		if (code == null) {
			List<ClassPropertyConfig> clist = getClassPropertyConfigList(clazz, new ArrayList<ClassPropertyConfig>(),
					new HashMap<Class<?>, String>(), "");
			cpclist = new ArrayList<ClassPropertyConfig>(CollectionUtils.intersection(cpclist, clist));
			Set<ClassPropertyConfig> set = new HashSet<ClassPropertyConfig>();
			set.addAll(cpclist);
			set.addAll(clist);
			cpclist = new ArrayList<ClassPropertyConfig>(set);
		}
		// 加载默认配置
		if (cpclist.isEmpty() && code != null) {
			cpclist = getClassPropertyConfigList(clazz, new ArrayList<ClassPropertyConfig>(), new HashMap<Class<?>, String>(), "");
			if (LIST.equals(code)) {
				for (ClassPropertyConfig cpc : cpclist) {
					cpc.setReadable(cpc.getListable());
				}
			}
		}
		return cpclist;
	}

	/**
	 * 递归查询可配置属性
	 * 
	 * @param clazz
	 * @param list
	 * @param map
	 * @param propertyNamePerfix
	 * @return
	 * @author 钱剑林
	 * @createDate 2015年4月9日 上午10:49:43
	 */
	private List<ClassPropertyConfig> getClassPropertyConfigList(Class<?> clazz, List<ClassPropertyConfig> list, Map<Class<?>, String> map,
			String propertyNamePerfix) {
		if (map.get(clazz) != null) {
			return list;
		}
		map.put(clazz, "1");
		CPropertys cps = clazz.getAnnotation(CPropertys.class);
		if (cps != null) {
			String propertiestr = cps.properties();
			int px = 1;
			if (StringUtils.isNotEmpty(propertiestr)) {
				String[] properties = propertiestr.split(";");
				for (String property : properties) {
					if (StringUtils.isNotEmpty(property)) {
						String[] ss = property.split(",");
						if (ss.length >= 2) {
							ClassPropertyConfig cpc = new ClassPropertyConfig();
							cpc.setPropertyName(propertyNamePerfix + ss[0]);
							cpc.setLabel(ss[1]);
							cpc.setPx(px++);
							try {
								if (ss.length > 2) {
									if ("1".equals(ss[2])) {
										cpc.setReadable(true);
									}
									if (ss.length > 3) {
										cpc.setEditable(true);
									}
								}
							} catch (Exception e) {
							}
							list.add(cpc);
						}
					}
				}
			}
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				CProperty cp = field.getAnnotation(CProperty.class);
				Class<?> propertyClass = field.getType();
				ClassPropertyConfig cpc = new ClassPropertyConfig();
				cpc.setPropertyName(propertyNamePerfix + field.getName());
				if (cp != null) {
					cpc.setLabel(cp.label());
					cpc.setRequired(cp.required());
					cpc.setListable(cp.listable());
					if (cpc.getPx() == 0) {
						cpc.setPx(px++);
					}
					list.add(cpc);
				}
//				if (LongIdObject.class.isAssignableFrom(propertyClass)) {
//					getClassPropertyConfigList(propertyClass, list, map, cpc.getPropertyName() + ".");
//				}
			}
		}
		if (clazz.getSuperclass() != null) {
			getClassPropertyConfigList(clazz.getSuperclass(), list, map, propertyNamePerfix);
		}
		return list;
	}

	public void saveConfig(Class<?> clazz, List<ClassPropertyConfig> list) {
		saveConfig(clazz, list, null);
	}

	public void saveConfig(Class<?> clazz, List<ClassPropertyConfig> list, String code) {
		try {
			//取消不可编辑项的必填限制
			Iterator<ClassPropertyConfig> itor = list.iterator();
			while (itor.hasNext()) {
				ClassPropertyConfig cpc = itor.next();
				if(!cpc.getEditable()){
					cpc.setRequired(false);
				}
				if(!cpc.getReadable()){
					cpc.setEditable(false);
					cpc.setRequired(false);
				}
			}
			ClassConfig cc = findConfigFromDB(clazz, code);
			if (cc == null) {
				cc = new ClassConfig();
				cc.setClassName(getEntityName(clazz));
				cc.setCode(code);
			}
			String configStr = JSONArray.fromObject(list).toString();
			cc.setConfig(configStr);
			entityDao.saveOrUpdate(cc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
