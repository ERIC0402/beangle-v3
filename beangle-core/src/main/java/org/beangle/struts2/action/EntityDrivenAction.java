/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.action;

import static org.beangle.web.util.RequestUtils.encodeAttachName;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.exception.MyException;
import org.beangle.commons.lang.StrUtils;
import org.beangle.model.Entity;
import org.beangle.model.entity.Model;
import org.beangle.model.entity.types.EntityType;
import org.beangle.model.pojo.TimeEntity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.EntityQuery;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.TransferListener;
import org.beangle.model.transfer.csv.CsvItemReader;
import org.beangle.model.transfer.excel.ExcelItemReader;
import org.beangle.model.transfer.exporter.Context;
import org.beangle.model.transfer.exporter.DefaultPropertyExtractor;
import org.beangle.model.transfer.exporter.Exporter;
import org.beangle.model.transfer.exporter.PropertyExtractor;
import org.beangle.model.transfer.importer.DefaultEntityImporter;
import org.beangle.model.transfer.importer.EntityImporter;
import org.beangle.model.transfer.importer.listener.ImporterForeignerListener;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.model.transfer.io.TransferFormats;
import org.beangle.model.util.EntityUtils;
import org.beangle.struts2.helper.ExportHelper;
import org.beangle.struts2.helper.Params;
import org.beangle.struts2.helper.QueryHelper;

import com.opensymphony.xwork2.util.ClassLoaderUtil;

@SuppressWarnings("deprecation")
public class EntityDrivenAction extends BaseAction {

	protected String entityName;
	protected ItemImporterListener importerListener;

	public ItemImporterListener getImporterListener() {
		return importerListener;
	}

	/**
	 * 主页面
	 * 
	 * @return
	 */
	public String index() throws Exception {
		indexSetting();
		return forward();
	}

	/**
	 * 查找标准
	 * 
	 * @return
	 */
	public String search() {
		put(getShortName() + "s", search(getQueryBuilder()));
		return forward();
	}

	protected Collection<?> getExportDatas() {
		return search(getQueryBuilder().limit(null));
	}

	/**
	 * 修改标准
	 * 
	 * @return
	 */
	public String edit() {
		Long entityId = getEntityId(getShortName());
		Entity<?> entity = null;
		if (null == entityId) {
			entity = populateEntity();
		} else {
			entity = getModel(getEntityName(), entityId);
		}
		put(getShortName(), entity);
		editSetting(entity);
		return forward();
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String remove() throws Exception {
		Long entityId = getEntityId(getShortName());
		Collection<?> entities = null;
		if (null == entityId) {
			entities = getModels(getEntityName(), getEntityIds(getShortName()));
		} else {
			Entity<?> entity = getModel(getEntityName(), entityId);
			entities = Collections.singletonList(entity);
		}
		return removeAndForward(entities);
	}

	/**
	 * 保存修改后的标准
	 * 
	 * @return
	 */
	public String save() throws Exception {
		return saveAndForward(populateEntity());
	}

	protected Entity<?> populateEntity() {
		return populateEntity(getEntityName(), getShortName());
	}

	protected Long getEntityId(String shortName) {
		Long entityId = getLong(shortName + ".id");
		if (null == entityId) {
			entityId = getLong(shortName + "Id");
		}
		if (null == entityId) {
			entityId = getLong(shortName + "id");
		}
		if (null == entityId) {
			entityId = getLong("id");
		}
		return entityId;
	}

	/**
	 * Get entity's id shortname.id[],shortname.ids,shortnameIds
	 * 
	 * @param <T>
	 * @param shortName
	 * @param clazz
	 * @return
	 */
	protected <T> T[] getEntityIds(String shortName, Class<T> clazz) {
		T[] datas = Params.getAll(shortName + ".id", clazz);
		if (null == datas) {
			String datastring = Params.get(shortName + ".ids");
			if (null == datastring)
				datastring = Params.get(shortName + "Ids");
			if (null != datastring) {
				return Params.converter.convert(StringUtils.split(datastring, ","), clazz);
			}
		}
		return datas;
	}

	protected Long[] getEntityIds(String shortName) {
		return getEntityIds(shortName, Long.class);
	}

	protected Long[] getEntityIds() {
		return getEntityIds(getShortName(), Long.class);
	}

	protected <T> void foo(Class<T> a) {
	}

	protected Entity<?> populateEntity(String entityName, String shortName) {
		Long entityId = getEntityId(shortName);
		Entity<?> entity = null;
		if (null == entityId) {
			entity = (Entity<?>) populate(entityName, shortName);
		} else {
			entity = getModel(entityName, entityId);
			populate(Params.sub(shortName), entity, entityName);
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	protected <T> T populateEntity(Class<T> entityClass, String shortName) {
		EntityType type = null;
		if (entityClass.isInterface()) {
			type = Model.getEntityType(entityClass.getName());
		} else {
			type = Model.getEntityType(entityClass);
		}
		return (T) populateEntity(type.getEntityName(), shortName);
	}

	protected Entity<?> getEntity() {
		return getEntity(getEntityName(), getShortName());
	}

	protected Entity<?> getEntity(String entityName, String name) {
		Long entityId = getEntityId(name);
		Entity<?> entity = null;
		try {
			EntityType type = Model.getEntityType(entityName);
			if (null == entityId) {
				entity = (Entity<?>) populate(type.newInstance(), type.getEntityName(), name);
			} else {
				entity = getModel(entityName, entityId);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	protected <T> T getEntity(Class<T> entityClass, String shortName) {
		EntityType type = null;
		if (entityClass.isInterface()) {
			type = Model.getEntityType(entityClass.getName());
		} else {
			type = Model.getEntityType(entityClass);
		}
		return (T) getEntity(type.getEntityName(), shortName);
	}

	protected <T> T getEntity(Class<T> entityClass) {
		return getEntity(entityClass, EntityUtils.getCommandName(entityClass.getName()));
	}

	/**
	 * 查看信息
	 * 
	 * @return
	 */
	public String info() throws Exception {
		Long entityId = getEntityId(getShortName());
		if (null == entityId) {
			logger.warn("cannot get paremeter {}Id or {}.id", getShortName(), getShortName());
		}
		Entity<?> entity = getModel(getEntityName(), entityId);
		put(getShortName(), entity);
		return forward();
	}

	protected void indexSetting() {

	}

	protected void editSetting(Entity<?> entity) {

	}

	/**
	 * 保存对象
	 * 
	 * @param entity
	 * @return
	 */
	protected String saveAndForward(Entity<?> entity) {
		try {
			if (entity instanceof TimeEntity<?>) {
				TimeEntity<?> timeEntity = (TimeEntity<?>) entity;
				if (!timeEntity.isPersisted()) {
					timeEntity.setCreatedAt(new Date());
				}
				timeEntity.setUpdatedAt(new Date());
			}
			saveOrUpdate(entity);
			return redirect("search", "info.save.success");
		} catch (MyException e) {
			logger.error("saveAndForwad failure", e);
			return redirect("search", e.getMessage());
		} catch (Exception e) {
			logger.info("saveAndForwad failure", e);
			return redirect("search", "info.save.failure");
		}

	}

	protected String removeAndForward(Collection<?> entities) {
		try {
			remove(entities);
		} catch (Exception e) {
			logger.info("removeAndForwad failure", e);
			return redirect("search", "info.delete.failure");
		}
		return redirect("search", "info.remove.success");
	}

	protected QueryBuilder<?> getQueryBuilder() {
		OqlBuilder<?> builder = OqlBuilder.from(getEntityName(), getShortName());
		populateConditions(builder);
		QueryHelper.populateIds(builder, getShortName() + ".id");
		builder.orderBy(getOrderString()).limit(getPageLimit());
		return builder;
	}

	/**
	 * 获取排序(无参数，默认按照ID倒序排列)
	 * 
	 * @return
	 */
	protected String getOrderString() {
		return getOrderString(getDefaultOrderString());
	}

	protected String getDefaultOrderString() {
		return null;
	}

	/**
	 * 获取排序
	 * 
	 * @param defaultOrder
	 *            默认排序字段
	 * @return
	 */
	protected String getOrderString(String defaultOrder) {
		String orderBy = get(Order.ORDER_STR);
		if (StringUtils.isEmpty(orderBy)) {
			orderBy = defaultOrder;
		} else {
		}
		// if (StringUtils.isEmpty(orderBy)) {
		// orderBy = getShortName() + ".id desc";
		// }
		return orderBy;
	}

	/**
	 * @deprecated
	 * @return
	 */
	protected EntityQuery buildQuery() {
		EntityQuery query = new EntityQuery(getEntityName(), getShortName());
		populateConditions(query);
		query.addOrder(Order.parse(get(Order.ORDER_STR))).setLimit(getPageLimit());
		return query;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	protected String getEntityName() {
		if (null == entityName) {
			throw new RuntimeException("entityName not set for :" + getClass().getName());
		}
		return entityName;
	}

	public String getEntityName2() {
		return getEntityName();
	}

	protected String getShortName() {
		String name = getEntityName();
		if (StringUtils.isNotEmpty(name))
			return EntityUtils.getCommandName(name);
		else
			return null;
	}

	protected Entity<?> getModel(String entityName, Serializable id) {
		return (Entity<?>) entityDao.get(entityName, id);
	}

	@SuppressWarnings("rawtypes")
	protected List getModels(String entityName, Long[] ids) {
		return entityDao.get(entityName, "id", (Object[]) ids);
	}

	protected <T> List<T> getModels(Class<T> modelClass, Long[] ids) {
		return entityDao.get(modelClass, "id", (Object[]) ids);
	}

	public String exportDataSelect() {
		String format = get("format");
		String fileName = get("fileName");
		String template = get("template");
		put("format", format);
		put("fileName", fileName);
		put("template", template);
		String properties = get("propertiesExport");
		if (StringUtils.isNotEmpty(properties)) {
			String[] props = StringUtils.split(properties, ",");
			List<String> keys = CollectUtils.newArrayList();
			Map<String, String> titles = CollectUtils.newHashMap();
			for (String prop : props) {
				String key = StringUtils.substringBefore(prop, ":");
				System.out.println(key);
				String value = getTextInternal(StringUtils.substringAfter(prop, ":"));
				keys.add(key);
				titles.put(key, value);
			}
			put("keys", keys);
			put("titles", titles);
		}
		return forward("/template/exportDataSelect");
	}

	/**
	 * 导出数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public String export() throws Exception {
		String format = getExportFormat();
		String fileName = getExportFileName();
		String template = get("template");

		// 配置导出上下文
		Context context = new Context();
		context.put("format", format);
		context.put("exportFile", fileName);
		context.put("fileName", fileName);
		context.put("template", template);
		String properties = get("propertiesExport");
		if (null != properties) {
			String[] props = StringUtils.split(properties, ",");
			List<String> keys = CollectUtils.newArrayList();
			List<String> titles = CollectUtils.newArrayList();
			for (String prop : props) {
				keys.add(StringUtils.substringBefore(prop, ":"));
				titles.add(getTextInternal(StringUtils.substringAfter(prop, ":")));
			}
			context.put(Context.KEYS, StrUtils.join(keys, ","));
			context.put(Context.TITLES, StrUtils.join(titles, ","));
		} else {
			context.put(Context.KEYS, get("keys"));
			context.put(Context.TITLES, get("titles"));
		}
		context.put(Context.EXTRACTOR, getPropertyExtractor());

		final Exporter exporter = buildExporter(context);

		// configExporter(exporter, context);
		// 进行输出
		ExportThread thread = new ExportThread();
		thread.setEntityDao(entityDao);
		thread.setQuery(getQueryBuilder());
		thread.setExporter(exporter);
		thread.setContext(context);
		thread.start();
		getSession().put("export_thread", thread);
		return forward("/template/export");
	}

	public String exportStatus() {
		ExportThread thread = (ExportThread) getSession().get("export_thread");
		put("thread", thread);
		return forward("/template/exportStatus");
	}

	private String getExportFileName() {
		String fileName = get("fileName");
		if (StringUtils.isEmpty(fileName)) {
			fileName = "exportResult";
		}
		return fileName;
	}

	private String getExportFormat() {
		String format = get("format");
		if (StringUtils.isBlank(format)) {
			format = TransferFormats.XLS;
		}
		return format;
	}

	public String downLoadExport() {
		ExportThread thread = (ExportThread) getSession().get("export_thread");
		File file = new File(thread.getFilePath());
		HttpServletResponse response = ServletActionContext.getResponse();
		String fileName = thread.getFileName();
		String format = thread.getFormat();
		if (format.equals(TransferFormats.XLS)) {
			response.setContentType("application/vnd.ms-excel;charset=GBK");
		} else {
			response.setContentType("application/x-msdownload");
		}
		response.setHeader("Content-Disposition",
				"attachment;filename=" + encodeAttachName(ServletActionContext.getRequest(), fileName + "." + format));
		try {
			FileInputStream fis = new FileInputStream(file);
			ServletOutputStream sos = response.getOutputStream();
			IOUtils.copy(fis, sos);
			IOUtils.closeQuietly(fis);
			sos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		getSession().remove("export_thread");
		return null;
	}

	protected PropertyExtractor getPropertyExtractor() {
		return new DefaultPropertyExtractor(getTextResource());
	}

	protected URL getResource(String name) {
		URL url = ClassLoaderUtil.getResource(name, getClass());
		if (url == null) {
			logger.error("Cannot load template {}", name);
		}
		return url;
	}

	protected Exporter buildExporter(Context context) {
		return ExportHelper.buildExporter(context);
	}

	protected void configExporter(Exporter exporter, Context context) {
		configExportContext(context);
	}

	/**
	 * @deprecated use configExporter
	 * @param context
	 */
	protected void configExportContext(Context context) {
		context.put("items", getExportDatas());
	}

	public String importForm() {
		// return forward("/components/importData/form");
		return forward();
	}

	/**
	 * 构建实体导入者
	 * 
	 * @return
	 */
	protected EntityImporter buildEntityImporter() {
		if (null == getEntityName()) {
			return buildEntityImporter("importFile", null);
		} else {
			return buildEntityImporter("importFile", Model.getEntityType(getEntityName()).getEntityClass());
		}
	}

	/**
	 * 用于构建单个实体类的导入构造器
	 * 
	 * @param clazz
	 * @return
	 */
	protected EntityImporter buildEntityImporter(Class<?> clazz) {
		return buildEntityImporter("importFile", clazz);
	}

	/**
	 * 构建实体导入者
	 * 
	 * @param upload
	 * @param clazz
	 * @return
	 */
	protected EntityImporter buildEntityImporter(String upload, Class<?> clazz) {
		try {
			File file = get(upload, File.class);
			if (null == file) {
				logger.error("cannot get upload file {}.", upload);
				return null;
			}
			String fileName = get(upload + "FileName");
			InputStream is = new FileInputStream(file);
			if (fileName.endsWith(".xls")) {
				EntityImporter importer = (clazz == null) ? new DefaultEntityImporter() : new DefaultEntityImporter(clazz);
				importer.setReader(new ExcelItemReader(is, 1));
				put("importer", importer);
				return importer;
			} else {
				LineNumberReader reader = new LineNumberReader(new InputStreamReader(is));
				if (null == reader.readLine()) {
					reader.close();
					return null;
				}
				reader.reset();
				EntityImporter importer = (clazz == null) ? new DefaultEntityImporter() : new DefaultEntityImporter(clazz);
				importer.setReader(new CsvItemReader(reader));
				return importer;
			}
		} catch (MyException e) {
			logger.error("saveAndForwad failure", e);
			return null;
		} catch (Exception e) {
			logger.error("error", e);
			return null;
		}
	}

	/**
	 * 导入信息
	 * 
	 * @return
	 */
	public String importData() {
		EntityImporter importer = buildEntityImporter();
		if (null == importer) {
			return redirect("importForm", "请上传一个正确的XLS文件!");
		}
		configImporter(importer);
		EntityImporterThread thread = new EntityImporterThread();
		thread.setEntityDao(entityDao);
		thread.setImporter(importer);
		thread.start();
		getSession().put("import_data_thread", thread);
		return forward("/template/importData");
	}

	public String importDataStatus() {
		EntityImporterThread thread = (EntityImporterThread) getSession().get("import_data_thread");
		put("result", thread.getTransferResult());
		return forward("/template/importDataStatus");
	}

	public String importDataResult() {
		EntityImporterThread thread = (EntityImporterThread) getSession().get("import_data_thread");
		put("result", thread.getTransferResult());
		getSession().remove("import_data_thread");
		return forward("/template/importDataResult");
	}

	protected void configImporter(EntityImporter importer) {
		for (final TransferListener il : getImporterListeners()) {
			importer.addListener(il);
		}
	}

	protected List<? extends TransferListener> getImporterListeners() {
		if (importerListener == null) {
			importerListener = getImporterListener();
		}
		if (importerListener == null) {
			importerListener = new ImporterForeignerListener(entityDao);
		}
		return Collections.singletonList(importerListener);
	}

	/**
	 * 根据传入的ids查找数据
	 * 
	 * @param <T>
	 * @param ids
	 *            页面参数
	 * @param clazz
	 *            要查询的实体
	 * @return
	 */
	protected <T> List<T> findEntityByIds(Class<T> clazz, String ids) {
		// 获取选中ID，并组建Long类型数组
		Long[] id = StrUtils.splitToLong(get(ids));
		// 查询
		List<T> datas = entityDao.get(clazz, id);
		return datas;
	}

	protected <T> List<T> getAllEntity(Class<T> clazz, String shortName) {
		return getAllEntity(clazz, shortName, null);
	}

	protected <T> List<T> getAllEntity(Class<T> clazz, String shortName, GetAllEntityInvoker<T> invoker) {
		List<T> list = new ArrayList<T>();
		String[] jtcypxs = getAll(shortName, String.class);
		if (jtcypxs == null) {
			return list;
		}
		for (String px : jtcypxs) {
			T t = populateEntity(clazz, shortName + px);
			if (invoker != null) {
				invoker.doSth(t);
			}
			list.add(t);
		}
		return list;
	}

	public interface GetAllEntityInvoker<T> {
		public void doSth(T t);
	}

	protected Class<?> getEntityClass() {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(getEntityName());
		} catch (ClassNotFoundException e) {
		}
		return clazz;
	}
}
