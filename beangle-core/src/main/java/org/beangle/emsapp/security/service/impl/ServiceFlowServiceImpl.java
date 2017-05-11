package org.beangle.emsapp.security.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.model.ServiceFlow;
import org.beangle.emsapp.security.service.ServiceFlowService;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.OqlBuilder;

public class ServiceFlowServiceImpl extends BaseServiceImpl implements ServiceFlowService{

	public void move(ServiceFlow flow, ServiceFlow parent, int indexno) {
		if (ObjectUtils.equals(flow.getParent(), parent)) {
			if (NumberUtils.toInt(flow.getIndexno()) != indexno) {
				shiftCode(flow, parent, indexno);
			}
		} else {
			if (null != flow.getParent()) {
				flow.getParent().getChildren().remove(flow);
			}
			flow.setParent(parent);
			shiftCode(flow, parent, indexno);
		}
		
	}
	
	private void shiftCode(ServiceFlow flow, ServiceFlow newParent, int indexno) {
		List<ServiceFlow> sibling = null;
		if (null != newParent) {
			sibling = newParent.getChildren();
		}else {
			sibling = CollectUtils.newArrayList();
			OqlBuilder<ServiceFlow> query = OqlBuilder.from(ServiceFlow.class);
			query.where("parent is null");
			sibling = entityDao.search(query);
		}
		Collections.sort(sibling);
		sibling.remove(flow);
		indexno--;
		if (indexno > sibling.size()) {
			indexno = sibling.size();
		}
		sibling.add(indexno, flow);
		int nolength = String.valueOf(sibling.size()).length();
		Set<ServiceFlow> menus = CollectUtils.newHashSet();
		for (int seqno = 1; seqno <= sibling.size(); seqno++) {
			ServiceFlow one = sibling.get(seqno - 1);
			generateCode(one, StringUtils.leftPad(String.valueOf(seqno), nolength, '0'), menus);
		}
		entityDao.saveOrUpdate(menus);
	}

	private void generateCode(ServiceFlow flow, String indexno, Set<ServiceFlow> flows) {
		flows.add(flow);
		if (null != indexno) {
			((ServiceFlow) flow).generateCode(indexno);
		} else {
			((ServiceFlow) flow).generateCode();
		}
		if (null != flow.getChildren()) {
			for (ServiceFlow m : flow.getChildren()) {
				generateCode(m, null, flows);
			}
		}
	}

	public List<ServiceFlow> findServiceFlowByCode(String code) {
		
			OqlBuilder<ServiceFlow> query = OqlBuilder.from(ServiceFlow.class,"d");
			query.where("d.code like '" + code + "%'");
			query.where("d.enabled=1");
			query.orderBy("d.code");
			return entityDao.search(query);
		
	}

	public Map<String, ServiceFlow> getServiceFlowMap(String code) {
		List<ServiceFlow> list = findServiceFlowByCode(code);
		Map<String, ServiceFlow> map = new HashMap<String, ServiceFlow>(list==null?0:list.size());
		for(ServiceFlow serviceFlow : list){
			map.put(serviceFlow.getTitle(), serviceFlow);
		}
		return map;
	}

}
