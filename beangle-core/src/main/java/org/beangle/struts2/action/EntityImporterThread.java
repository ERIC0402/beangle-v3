package org.beangle.struts2.action;

import java.io.Serializable;

import org.beangle.model.persist.EntityDao;
import org.beangle.model.transfer.TransferListener;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.importer.EntityImporter;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class EntityImporterThread extends Thread implements Serializable {
	
	private static Logger log = LoggerFactory.getLogger(EntityImporterThread.class);

	private EntityImporter importer;
	private TransferResult transferResult;
	private EntityDao entityDao;

	public void setImporter(EntityImporter importer) {
		this.importer = importer;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@Override
	public void run() {
		try {
			SessionFactory sessionFactory = entityDao.getSessionFactory();
			Session session = SessionFactoryUtils.getSession(sessionFactory, true);
			TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
			transferResult = new TransferResult();
			importer.transfer(transferResult);
			for (TransferListener listener : importer.getListeners()) {
				if (listener instanceof ItemImporterListener) {
					ItemImporterListener iil = (ItemImporterListener) listener;
					transferResult.setCount(iil.getCount());
					transferResult.setUcount(iil.getUcount());
					break;
				}
			}
			session.close();
			transferResult.setStatus("导入结束");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			transferResult.setStatus("导入失败");
		}
		super.run();
	}

	public TransferResult getTransferResult() {
		return transferResult;
	}

}