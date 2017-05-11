package org.beangle.website.system.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.beangle.ems.event.BusinessEvent;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.ems.security.User;
import org.beangle.ems.security.model.UserBean;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.web.util.RequestUtils;
import org.beangle.website.system.model.SystemLog;
import org.beangle.website.system.service.SystemLogService;
import org.beangle.website.system.utils.SystemLogConstant.SystemLogType;
import org.springframework.context.ApplicationEvent;
/**
 * 系统日志接口实现
 * @author GOKU
 *
 */
public class SystemLogServiceImpl extends BaseServiceImpl implements SystemLogService {

	public void addOperateForModule(String code, String menu, String content) {
		saveSystemLog(code, menu, content, SystemLogType.SYSTEM_LOG_TYPE_ADD);
	}

	public void updateOperateForModule(String code, String menu, String content) {
		saveSystemLog(code, menu, content, SystemLogType.SYSTEM_LOG_TYPE_UPDATE);
	}

	public void removeOperateForModule(String code, String menu, String content) {
		saveSystemLog(code, menu, content, SystemLogType.SYSTEM_LOG_TYPE_REMOVE);
	}
	
	protected void saveSystemLog(String code, String menu, String content, Integer type){
		SystemLog systemLog = new SystemLog();
		systemLog.setCode(code);
		systemLog.setMenu(menu);
		systemLog.setType(type);
		systemLog.setContent(content);
		User user = new UserBean();
		user.setId(SecurityUtils.getUserId());
		systemLog.setUser(user);
		systemLog.setTime(new Date());
		HttpServletRequest request = ServletActionContext.getRequest();
		if (null != request){
			systemLog.setIpAddr(RequestUtils.getIpAddr(request));
		}
		entityDao.saveOrUpdate(systemLog);
	}
	
	@Override
	public void publish(ApplicationEvent event) {
		super.publish(event);
	}
	
	public void publish(String description, String resource) {
		super.publish(new BusinessEvent(description, resource));
	}

}
