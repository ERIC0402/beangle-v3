package org.beangle.struts2.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.transfer.Transfer;
import org.beangle.model.transfer.TransferListener;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.exporter.Context;
import org.beangle.model.transfer.exporter.Exporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class ExportThread extends Thread implements Serializable {

	private EntityDao entityDao;
	private QueryBuilder<?> query;
	private String status;
	private int total;
	private int num;
	private Exporter exporter;
	private Context context;
	private String format;
	private String fileName;
	private String filePath;

	@Override
	public void run() {
		try {
			fileName = context.get("fileName").toString();
			format = context.get("format").toString();
			// 开启Session
			status = "开始导出";
			// 输出到临时文件
			Properties props = System.getProperties();
			String tmpdir = props.getProperty("java.io.tmpdir");
			File file = new File(tmpdir + "/" + UUID.randomUUID() + "." + format);
			FileOutputStream fos = new FileOutputStream(file);
			filePath = file.getAbsolutePath();
			exporter.getWriter().setOutputStream(fos);

			SessionFactory sessionFactory = entityDao.getSessionFactory();
			Session session = SessionFactoryUtils.getSession(sessionFactory, true);
			TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
			status = "请稍候，正在准备导出数据...";
			List<?> datas = entityDao.search(query.limit(null));
			total = datas.size();
			context.put("items", datas);
			exporter.setContext(context);
			status = "导出数据到Excel文件";
			exporter.addListener(new TransferListener() {

				@Override
				public void setTransfer(Transfer transfer) {

				}

				@Override
				public void onStart(TransferResult tr) {

				}

				@Override
				public void onItemStart(TransferResult tr) {

				}

				@Override
				public void onItemFinish(TransferResult tr) {
					num++;
					// System.out.println(num);
				}

				@Override
				public void onFinish(TransferResult tr) {

				}
			});
			exporter.transfer(new TransferResult());
			session.close();
			status = "下载文件";
		} catch (Exception e) {
			status = "导出失败";
			e.printStackTrace();
		}
		super.run();
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	public QueryBuilder<?> getQuery() {
		return query;
	}

	public void setQuery(QueryBuilder<?> query) {
		this.query = query;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Exporter getExporter() {
		return exporter;
	}

	public void setExporter(Exporter exporter) {
		this.exporter = exporter;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String getFormat() {
		return format;
	}
}
