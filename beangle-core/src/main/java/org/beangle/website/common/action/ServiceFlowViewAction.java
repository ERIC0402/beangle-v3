package org.beangle.website.common.action;

import org.beangle.ems.security.model.ServiceFlow;
import org.beangle.model.query.builder.OqlBuilder;

public class ServiceFlowViewAction extends BaseCommonAction{
	
	@Override
	protected void indexSetting() {
		Long sfId = getLong("sfId");
		if(sfId != null){
			put("sf",entityDao.get(ServiceFlow.class, sfId));
		}else{
//			String hql = "from org.beangle.ems.security.model.ServiceFlow SF where SF.parent is null order by SF.code";
//			entityDao.searchHQLQuery(hql);
			OqlBuilder<ServiceFlow> query = OqlBuilder.from(ServiceFlow.class,"flow");
			query.where("flow.parent is null");
			query.orderBy("flow.code");
			put("sfs",entityDao.search(query));
		}
	}

}
