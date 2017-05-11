/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.page.Page;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.model.Entity;
import org.beangle.model.query.Query;
import org.beangle.model.query.QueryBuilder;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * dao 查询辅助类
 * 
 * @作者：高照伟
 * @公司：上海彦致信息技术有限公司
 * @创建日期：2015年1月15日 上午11:41:50
 */
public interface EntityDao {

	/**
	 * 查询指定id的对象
	 * 
	 * @param clazz
	 *            类型
	 * @param id
	 *            唯一标识
	 * @return null
	 */
	public <T> T get(Class<T> clazz, Serializable id);

	/**
	 * 依据实体名加载对象
	 * 
	 * @param entityName
	 *            实体名
	 * @param id
	 *            唯一标识
	 * @return
	 */
	public <T> T get(String entityName, Serializable id);

	/**
	 * 列举给定实体的所有实例
	 * 
	 * @param entity
	 * @return
	 */
	public <T> List<T> getAll(Class<T> entity);

	/**
	 * 根据属性列举实体
	 * 
	 * @param entityClass
	 * @param keyName
	 * @param values
	 * @return
	 */
	public <T> List<T> get(Class<T> entityClass, Long... values);

	/**
	 * 根据属性列举实体
	 * 
	 * @param entityClass
	 * @param keyName
	 * @param values
	 * @return
	 */
	public <T> List<T> get(Class<T> entityClass, Object... values);

	/**
	 * 根据属性列举实体
	 * 
	 * @param entityClass
	 * @param keyName
	 * @param values
	 * @return
	 */
	public <T> List<T> get(Class<T> entityClass, Collection<?> values);

	/**
	 * 根据属性列举实体
	 * 
	 * @param entityClass
	 * @param keyName
	 * @param values
	 * @return
	 */
	public <T> List<T> get(Class<T> entityClass, String keyName, Object... values);

	public <T> T getEntity(Class<T> entityClass, String keyName, Object... values);

	/**
	 * 根据属性列举实体
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param keyName
	 * @param values
	 * @return
	 */
	public <T> List<T> get(Class<T> entityClass, String keyName, Collection<?> values);

	/**
	 * @param <T>
	 * @param entityClass
	 * @param attrs
	 * @param values
	 * @return
	 */
	public <T> List<T> get(Class<T> entityClass, String[] attrs, Object... values);

	/**
	 * @param <T>
	 * @param entityClass
	 * @param parameterMap
	 * @return
	 */
	public <T> List<T> get(Class<T> entityClass, Map<String, Object> parameterMap);

	/**
	 * 根据属性列举实体
	 * 
	 * @param entityClass
	 * @param keyName
	 * @param values
	 * @return
	 */
	public <T> List<T> get(String entityName, String keyName, Object... values);

	/**
	 * 执行查询
	 * 
	 * @param query
	 * @return
	 */
	public <T> List<T> search(Query<T> query);

	/**
	 * 查询hql语句
	 * 
	 * @param <T>
	 * @param builder
	 * @return
	 */
	public <T> List<T> search(QueryBuilder<T> builder);

	/**
	 * 查询hql语句
	 * 
	 * @param <T>
	 * @param builder
	 * @return
	 */
	public <T> T uniqueResult(QueryBuilder<T> builder);

	/**
	 * 命名查询
	 * 
	 * @param queryName
	 * @param params
	 * @param cacheable
	 * @return
	 */
	public <T> List<T> searchNamedQuery(final String queryName, final Map<String, Object> params);

	/**
	 * 命名查询
	 * 
	 * @param queryName
	 * @param params
	 * @param cacheable
	 * @return
	 */
	public <T> List<T> searchNamedQuery(final String queryName, final Object... params);

	/**
	 * 支持缓存的命名查询
	 * 
	 * @param queryName
	 * @param params
	 * @param cacheable
	 * @return
	 */
	public <T> List<T> searchNamedQuery(String queryName, Map<String, Object> params, boolean cacheable);

	/**
	 * 直接查询
	 * 
	 * @param hql
	 * @return
	 */
	public <T> List<T> searchHQLQuery(final String hql);

	/**
	 * HQL查询
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public <T> List<T> searchHQLQuery(final String hql, final Map<String, Object> params);

	/**
	 * HQL查询
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public <T> List<T> searchHQLQuery(final String hql, final Object... params);

	/**
	 * HQL查询
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public Object getHQLQuery(final String hql, final Object... params);

	/**
	 * 支持缓存的HQL查询
	 * 
	 * @param queryName
	 * @param params
	 * @param cacheable
	 * @return
	 */
	public <T> List<T> searchHQLQuery(String hql, final Map<String, Object> params, boolean cacheable);

	/**
	 * 分页命名查询
	 * 
	 * @param queryName
	 * @param params
	 * @param limit
	 * @return
	 */
	public <T> Page<T> paginateNamedQuery(final String queryName, final Map<String, Object> params, PageLimit limit);

	/**
	 * 分页HQL查询
	 * 
	 * @param hql
	 * @param params
	 * @param limit
	 * @return
	 */
	public <T> Page<T> paginateHQLQuery(final String hql, final Map<String, Object> params, PageLimit limit);

	/**
	 * 执行HQL 进行更新或者删除
	 * 
	 * @param queryStr
	 * @param argument
	 * @return
	 */
	public int executeUpdateHql(String hql, Object... arguments);

	public int executeUpdateSql(String sql, Object... arguments);

	/**
	 * 重复执行单个hql语句
	 * 
	 * @param queryStr
	 * @param arguments
	 * @return
	 */
	public int executeUpdateHqlRepeatly(String queryStr, List<Object[]> arguments);

	/**
	 * 执行HQL 进行更新或者删除
	 * 
	 * @param queryStr
	 * @param argument
	 * @return
	 */
	public int executeUpdateHql(String hql, Map<String, Object> parameterMap);

	/**
	 * 执行命名语句进行更新或者删除
	 * 
	 * @param queryStr
	 * @param argument
	 * @return
	 */
	public int executeUpdateNamedQuery(String queryName, Map<String, Object> parameterMap);

	/**
	 * 执行命名语句进行更新或者删除
	 * 
	 * @param queryStr
	 * @param argument
	 * @return
	 */
	public int executeUpdateNamedQuery(String queryName, Object... arguments);

	/**
	 * 保存或更新单个或多个实体.
	 */
	public void saveOrUpdate(Object... entities);

	/**
	 * 保存单个或多个实体.
	 */
	public void save(Object... entities);

	/**
	 * Save Collection
	 * 
	 * @param entities
	 */
	public void saveOrUpdate(Collection<?> entities);

	/**
	 * 按照实体名称，保存单个或多个实体.
	 * 
	 * @param entityName
	 * @param obj
	 */
	public void saveOrUpdate(String entityName, Object... entities);

	/**
	 * Save collection of given entity name.
	 * 
	 * @param entityName
	 * @param entities
	 */
	public void saveOrUpdate(String entityName, Collection<?> entities);

	/**
	 * Update entity's property value describe in upateParams where attr in
	 * values.
	 * 
	 * @param entityClass
	 * @param attr
	 * @param values
	 * @param updateParams
	 * @return
	 */
	public int update(Class<?> entityClass, String attr, Object[] values, Map<String, Object> updateParams);

	/**
	 * Update entity set argumentName=argumentValue where attr in values.
	 * 
	 * @param entityClass
	 * @param attr
	 * @param values
	 * @param argumentName
	 * @param argumentValue
	 * @return
	 */
	public int update(Class<?> entityClass, String attr, Object[] values, String[] argumentName, Object[] argumentValue);

	/**
	 * 删除单个对象
	 * 
	 * @param entity
	 */
	public void remove(Object... entities);

	/**
	 * 删除集合内的所有对象
	 * 
	 * @param entities
	 */
	public void remove(Collection<?> entities);

	/**
	 * 批量删除对象
	 * 
	 * @param entityClass
	 *            (对象对应的类)
	 * @param keyName
	 *            (得到对象的key)
	 * @param values
	 *            (要修改的values的值集合)
	 * @return 是否删除成功
	 */
	public boolean remove(Class<?> entityClass, String attr, Object... values);

	/**
	 * 批量删除对象
	 * 
	 * @param entityClass
	 *            (对象对应的类)
	 * @param attr
	 *            (得到对象的key)
	 * @param values
	 *            (要修改的ids的值集合)
	 * @return 是否删除成功
	 */
	public boolean remove(Class<?> entityClass, String attr, Collection<?> values);

	/**
	 * 批量删除对象
	 * 
	 * @param entityClass
	 * @param parameterMap
	 *            (取得对象的key的name和value对应的Map)
	 * @return 是否删除成功
	 */
	public boolean remove(Class<?> entityClass, Map<String, Object> parameterMap);

	// Blob and Clob
	public Blob createBlob(InputStream inputStream, int length);

	public Blob createBlob(InputStream inputStream);

	public Clob createClob(String str);

	// 容器相关
	public void evict(Object entity);

	/**
	 * Initialize entity whenever session close or open
	 * 
	 * @param <T>
	 * @param entity
	 * @return
	 */
	public <T> T initialize(T entity);

	/**
	 * 刷新操作
	 * 
	 * @param entity
	 * @author 高照伟
	 * @createDate 2015年1月15日 上午9:54:30
	 */
	public void refresh(Object entity);

	/**
	 * 根据条件统计数据数量
	 * 
	 * @param entityName
	 *            实体类名
	 * @param keyName
	 *            字段名
	 * @param value
	 *            值
	 * @return 数量
	 * @author 高照伟
	 * @createDate 2015年1月15日 上午9:54:44
	 */
	public long count(String entityName, String keyName, Object value);

	/**
	 * 根据条件统计数据数量
	 * 
	 * @param entityClass
	 *            实体类class
	 * @param keyName
	 *            字段名
	 * @param value
	 *            值
	 * @return 数量
	 * @author 高照伟
	 * @createDate 2015年1月15日 上午9:55:37
	 */
	public long count(Class<?> entityClass, String keyName, Object value);

	/**
	 * 根据多条件统计某一属性数据数量
	 * 
	 * @param entityClass
	 *            实体类class
	 * @param attrs
	 *            属性名数组
	 * @param values
	 *            值数组
	 * @param countAttr
	 *            统计属性
	 * @return 数量
	 * @author 高照伟
	 * @createDate 2015年1月15日 上午9:56:34
	 */
	public long count(Class<?> entityClass, String[] attrs, Object[] values, String countAttr);

	/**
	 * 是否存在指定属性名和值得对象
	 * 
	 * @param entityClass
	 *            实体类class
	 * @param attr
	 *            属性
	 * @param value
	 *            值
	 * @return true：存在；false：不存在
	 * @author 高照伟
	 * @createDate 2015年1月15日 上午9:57:48
	 */
	public boolean exist(Class<?> entityClass, String attr, Object value);

	/**
	 * 是否存在指定属性名和值得对象
	 * 
	 * @param entityName
	 *            实体类class
	 * @param attr
	 *            属性
	 * @param value
	 *            值
	 * @return true：存在；false：不存在
	 * @author 高照伟
	 * @createDate 2015年1月15日 上午9:59:23
	 */
	public boolean exist(String entityName, String attr, Object value);

	/**
	 * 是否存在多条件的对象
	 * 
	 * @param entity
	 *            实体类class
	 * @param attrs
	 *            属性名数组
	 * @param values
	 *            值数组
	 * @return true：存在；false：不存在
	 * @author 高照伟
	 * @createDate 2015年1月15日 上午10:00:19
	 */
	public boolean exist(Class<?> entity, String[] attrs, Object[] values);

	/**
	 * 是否存在id主键的记录
	 * 
	 * @param entityName
	 *            实体类名
	 * @param id
	 *            id主键
	 * @param params
	 *            条件参数
	 * @return true：存在；false：不存在
	 * @author 高照伟
	 * @createDate 2015年1月15日 上午10:04:18
	 */
	public boolean duplicate(String entityName, Serializable id, Map<String, Object> params);

	/**
	 * 是否存在id主键的记录
	 * 
	 * @param clazz
	 *            实体类class
	 * @param id
	 *            id主键
	 * @param codeName
	 *            属性名
	 * @param codeValue
	 *            值
	 * @return true：存在；false：不存在
	 * @author 高照伟
	 * @createDate 2015年1月15日 上午10:05:28
	 */
	public boolean duplicate(Class<? extends Entity<?>> clazz, Serializable id, String codeName, Object codeValue);

	/**
	 * 在同一个session保存、删除
	 * 
	 * @param builder
	 */
	public void execute(Operation... opts);

	/**
	 * 执行一个操作构建者提供的一系列操作
	 * 
	 * @param builder
	 */
	public void execute(Operation.Builder builder);

	/**
	 * 执行sql语句查询
	 * 
	 * @param sql
	 *            sql语句
	 * @param params
	 *            参数
	 * @return 查询结果
	 * @author 高照伟
	 * @createDate 2015年1月15日 上午9:51:08
	 */
	public List<?> searchSqlQuery(String sql, Object... params);

	/**
	 * 执行sql语句查询
	 * 
	 * @param sql
	 *            sql语句
	 * @return 查询结果
	 * @author 高照伟
	 * @createDate 2015年1月15日 上午9:51:42
	 */
	public List<?> searchSqlQuery(String sql);

	/**
	 * 根据ID查询实体
	 * 
	 * @param cls
	 *            实体类class
	 * @param id
	 *            Long类型Id
	 * @return
	 * @author 高照伟
	 * @createDate 2015年1月15日 上午9:52:48
	 */
	public <T> T load(Class<T> cls, Long id);

	/**
	 * 获取持久层访问模板化hibernateTemplate
	 * 
	 * @return 持久层访问模板化
	 * @author 高照伟
	 * @createDate 2015年1月15日 上午9:53:45
	 */
	public HibernateTemplate getTemplate();

	public SessionFactory getSessionFactory();
}
