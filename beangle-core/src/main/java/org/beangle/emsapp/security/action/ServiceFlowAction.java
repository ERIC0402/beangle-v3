package org.beangle.emsapp.security.action;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.model.ServiceFlow;
import org.beangle.emsapp.security.helper.ServiceFlowPropertyExtractor;
import org.beangle.emsapp.security.service.ServiceFlowService;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.exporter.PropertyExtractor;
import org.beangle.model.util.HierarchyEntityUtil;
import org.beangle.website.common.action.BaseCommonAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * 业务流配置
 * @author LPJ
 *
 */
public class ServiceFlowAction extends BaseCommonAction{
	
	private ServiceFlowService serviceFlowService;

	public void setServiceFlowService(ServiceFlowService serviceFlowService) {
		this.serviceFlowService = serviceFlowService;
	}

	@Override
	protected String getEntityName(){
		return ServiceFlow.class.getName();
	}
	
	protected void indexSetting(){
		
	}
	
	protected void editSetting(Entity<?> entity) {
		ServiceFlow flow = (ServiceFlow) entity;
		List<ServiceFlow> folders = CollectUtils.newArrayList();
		OqlBuilder<ServiceFlow> query = OqlBuilder.from(ServiceFlow.class, "t");
		query.where("enabled = 1");
		query.orderBy("code");
		folders = entityDao.search(query);
		folders.removeAll(HierarchyEntityUtil.getFamily(flow));
		put("parents", folders);
	}

	@Override
	protected String removeAndForward(Collection<?> entities) {
		return super.removeAndForward(entities);
	}

	protected String saveAndForward(Entity<?> entity) {
		ServiceFlow flow = (ServiceFlow) entity;
		try {
			Long newParentId = getLong("parent.id");
			int indexno = getInteger("indexno");
			ServiceFlow parent = null;
			if (null != newParentId) {
				parent = entityDao.get(ServiceFlow.class, newParentId);
			}
			serviceFlowService.move(flow, parent, indexno);
			entityDao.saveOrUpdate(flow);
		} catch (Exception e) {
			e.printStackTrace();
			return forward(ERROR);
		}
		return redirect("search", "info.save.success");
	}

	/**
	 * 禁用或激活一个或多个模块
	 * 
	 * @return
	 */
	public String activate() {
		Long[] flowIds = getEntityIds(getShortName());
		Boolean enabled = getBoolean("isActivate");
		if (null == enabled) {
			enabled = Boolean.TRUE;
		}
		List<ServiceFlow> flows = entityDao.get(ServiceFlow.class, flowIds);
		for (ServiceFlow flow : flows) {
			flow.setEnabled(enabled);
		}
		entityDao.saveOrUpdate(flows);
		return redirect("search", "info.save.success");
	}

	/**
	 * 打印预览功能列表
	 * 
	 * @return
	 */
	public String preview() {
		OqlBuilder<ServiceFlow> query = OqlBuilder.from(ServiceFlow.class, "flow");
		populateConditions(query);
		query.orderBy("flow.code asc");
		put("flows", entityDao.search(query));

		query.cleanOrders();
		query.select("max(length(flow.code)/2)");
		List<?> rs = entityDao.search(query);
		put("depth", rs.get(0));
		return forward();
	}

	@Override
	public String info() {
		Long entityId = getEntityId(getShortName());
		if (null == entityId) {
			logger.warn("cannot get paremeter {}Id or {}.id", getShortName(), getShortName());
		}
		ServiceFlow flow = (ServiceFlow) getModel(getEntityName(), entityId);
		put(getShortName(), flow);
		return forward();
	}
	
	protected Map<String, Object> getParams() {
		return ActionContext.getContext().getParameters();
	}
	
	protected PropertyExtractor getPropertyExtractor() {
		return new ServiceFlowPropertyExtractor(getTextResource());
	}
	
}
