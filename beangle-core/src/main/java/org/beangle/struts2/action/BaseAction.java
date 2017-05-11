/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.Validate;
import org.apache.struts2.ServletActionContext;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.meta.SystemVersion;
import org.beangle.commons.property.PropertyConfig;
import org.beangle.commons.property.PropertyConfigFactory;
import org.beangle.commons.text.TextResource;
import org.beangle.ems.security.model.SystemConfig;
import org.beangle.model.Entity;
import org.beangle.model.entity.Model;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.helper.ContextHelper;
import org.beangle.struts2.helper.Params;
import org.beangle.struts2.helper.PopulateHelper;
import org.beangle.struts2.helper.QueryHelper;
import org.beangle.web.util.CookieUtils;
import org.beangle.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;

public class BaseAction extends DispatchAction {

	protected static final Logger logger = LoggerFactory.getLogger(BaseAction.class);

	@Resource(name = "entityDao")
	protected EntityDao entityDao;

	protected SystemVersion systemVersion;

	public SystemVersion getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(SystemVersion systemVersion) {
		this.systemVersion = systemVersion;
	}

	protected String getRemoteAddr() {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (null == request) {
			return null;
		}
		return RequestUtils.getIpAddr(request);
	}

	protected TextResource getTextResource() {
		return new ActionTextResource(this, this);
	}

	protected void put(String key, Object value) {
		ContextHelper.put(key, value);
	}

	protected Object[] getAll(String paramName) {
		return Params.getAll(paramName);
	}

	protected <T> T[] getAll(String paramName, Class<T> clazz) {
		return Params.getAll(paramName, clazz);
	}

	protected String get(String paramName) {
		return Params.get(paramName);
	}

	protected Object getAttribute(String name) {
		return ActionContext.getContext().getContextMap().get(name);
	}

	protected <T> T get(String name, Class<T> clazz) {
		return Params.get(name, clazz);
	}

	protected Boolean getBoolean(String name) {
		return Params.getBoolean(name);
	}

	protected boolean getBool(String name) {
		return Params.getBool(name);
	}

	protected java.sql.Date getDate(String name) {
		return Params.getDate(name);
	}

	protected Date getDateTime(String name) {
		return Params.getDateTime(name);
	}

	protected Float getFloat(String name) {
		return Params.getFloat(name);
	}

	protected Integer getInteger(String name) {
		return Params.getInteger(name);
	}

	protected Long getLong(String name) {
		return Params.getLong(name);
	}

	// populate------------------------------------------------------------------

	/**
	 * 将request中的参数设置到clazz对应的bean。
	 * 
	 * @param request
	 * @param clazz
	 * @param title
	 * @return
	 */
	protected <T> T populate(Class<T> clazz, String shortName) {
		return PopulateHelper.populate(clazz, shortName);
	}

	protected void populate(Object obj, String shortName) {
		if (obj == null) {
			return;
		}
		Model.populate(Params.sub(shortName), obj);
	}

	protected Object populate(String entityName) {
		return PopulateHelper.populate(entityName);
	}

	protected Object populate(Class<?> clazz) {
		return PopulateHelper.populate(clazz);
	}

	protected Object populate(String entityName, String shortName) {
		return PopulateHelper.populate(entityName, shortName);
	}

	protected Object populate(Object obj, String entityName, String shortName) {
		return PopulateHelper.populate(obj, entityName, shortName);
	}

	protected void populate(Map<String, Object> params, Entity<?> entity, String entityName) {
		Validate.notNull(entity, "Cannot populate to null.");
		Model.getPopulator().populate(entity, entityName, params);
	}

	protected void populate(Map<String, Object> params, Entity<?> entity) {
		Validate.notNull(entity, "Cannot populate to null.");
		Model.populate(params, entity);
	}

	// query------------------------------------------------------
	protected int getPageNo() {
		return QueryHelper.getPageNo();
	}

	protected int getPageSize() {
		return QueryHelper.getPageSize();
	}

	/**
	 * 从request的参数或者cookie中(参数优先)取得分页信息
	 * 
	 * @param request
	 * @return
	 */
	protected PageLimit getPageLimit() {
		return QueryHelper.getPageLimit();
	}

	protected void populateConditions(OqlBuilder<?> builder) {
		QueryHelper.populateConditions(builder);
	}

	protected void populateConditions(OqlBuilder<?> builder, String exclusiveAttrNames) {
		QueryHelper.populateConditions(builder, exclusiveAttrNames);
	}

	protected <T> List<T> populateList(Class<T> clazz, String shortName) {
		String[] ids = getAll(shortName, String.class);
		List<T> list = new ArrayList<T>();
		for (String id : ids) {
			T t = populate(clazz, shortName + id);
			list.add(t);
		}
		return list;
	}

	// CURD----------------------------------------
	protected void remove(Collection<?> list) {
		entityDao.remove(list);
	}

	protected void remove(Object obj) {
		entityDao.remove(obj);
	}

	protected void saveOrUpdate(Collection<?> list) {
		if (list == null || list.isEmpty()) {
			return;
		}
		entityDao.saveOrUpdate(list);
	}

	protected void saveOrUpdate(Object obj) {
		entityDao.saveOrUpdate(obj);
	}

	protected <T> T get(Class<T> clazz, Long id) {
		return entityDao.get(clazz, id);
	}

	@SuppressWarnings("rawtypes")
	protected List search(QueryBuilder<?> query) {
		return entityDao.search(query);
	}

	protected EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	protected String getCookieValue(String cookieName) {
		return CookieUtils.getCookieValue(ServletActionContext.getRequest(), cookieName);
	}

	protected void addCookie(String name, String value, String path, int age) {
		try {
			CookieUtils.addCookie(ServletActionContext.getRequest(), ServletActionContext.getResponse(), name, value, path, age);
		} catch (Exception e) {
			logger.error("setCookie error", e);
		}
	}

	protected void addCookie(String name, String value, int age) {
		try {
			CookieUtils.addCookie(ServletActionContext.getRequest(), ServletActionContext.getResponse(), name, value, age);
		} catch (Exception e) {
			logger.error("setCookie error", e);
		}
	}

	protected void deleteCookie(String name) {
		CookieUtils.deleteCookieByName(ServletActionContext.getRequest(), ServletActionContext.getResponse(), name);
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected Map<String, Object> getSession() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		if (session.get("id") == null) {
			session.put("id", getRequest().getSession().getId());
		}
		return session;
	}

	// 以下为获取系统参数
	protected PropertyConfigFactory configFactory;

	public void setConfigFactory(PropertyConfigFactory configFactory) {
		put("systemConfig", configFactory.getConfig());
		this.configFactory = configFactory;
	}

	public PropertyConfigFactory getConfigFactory() {
		if (configFactory == null) {
			ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getRequest().getSession().getServletContext());
			configFactory = (PropertyConfigFactory) ctx.getBean(PropertyConfigFactory.class);
		}
		return configFactory;
	}

	public PropertyConfig getSystemConfig() {
		return getConfigFactory().getConfig();
	}

	/**
	 * 获取系统参数
	 * 
	 * @param clazz
	 *            参数类型
	 * @param name
	 *            参数名称
	 * @return 参数
	 */
	protected Object getSystemParameters(Class<?> clazz, String name) {
		return getSystemConfig().get(clazz, name);
	}

	public SystemConfig getSc() {
		SystemConfig sc = entityDao.get(SystemConfig.class, 1L);
		return sc;
	}

}
