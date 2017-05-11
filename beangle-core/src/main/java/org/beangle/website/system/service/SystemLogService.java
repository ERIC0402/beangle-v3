package org.beangle.website.system.service;

import org.springframework.context.ApplicationEvent;

/**
 * 系统日志接口
 * @author GOKU
 *
 */
public interface SystemLogService {

	/**
	 * 模块添加操作的日志
	 * @param code 模块标识
	 * @param menu 菜单名称
	 * @param content 操作内容
	 */
	public void addOperateForModule(String code,String menu,String content);

	/**
	 * 模块修改操作的日志
	 * @param code 模块标识
	 * @param menu 菜单名称
	 * @param content 操作内容
	 */
	public void updateOperateForModule(String code,String menu,String content);

	/**
	 * 模块删除操作的日志
	 * @param code 模块标识
	 * @param menu 菜单名称
	 * @param content 操作内容
	 */
	public void removeOperateForModule(String code,String menu,String content);
	/**
	 * 发布一个应用事件
	 * @param event
	 */
	public void publish(ApplicationEvent event) ;
	public void publish(String description, String resource);
}
