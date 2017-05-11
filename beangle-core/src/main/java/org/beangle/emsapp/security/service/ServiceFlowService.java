package org.beangle.emsapp.security.service;

import java.util.List;
import java.util.Map;

import org.beangle.ems.security.model.ServiceFlow;

public interface ServiceFlowService {
	
	/**
	 * 移动菜单到指定的位置
	 * 
	 * @param flow
	 * @param location
	 *            新的位置
	 * @param indexno
	 *            新位置的顺序号
	 */
	public void move(ServiceFlow flow, ServiceFlow parent, int indexno);
	
	/**
	 * 根据code查找业务流
	 * @param code
	 * @return
	 */
	public List<ServiceFlow> findServiceFlowByCode(String code);
	
	/**
	 * 根据code查找业务流map
	 * @param code
	 * @return
	 */
	public Map<String, ServiceFlow> getServiceFlowMap(String code);

}
