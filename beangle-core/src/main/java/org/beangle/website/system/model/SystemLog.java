package org.beangle.website.system.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;
/**
 * 系统日志
 * @author GOKU
 *
 */
@Entity(name="org.beangle.website.system.model.SystemLog")
public class SystemLog extends LongIdObject {

	/**
	 * 模块标识(用常量传值)
	 */
	@Size(max=20)
	@Column(length=20)
	private String code;

	/**
	 * 模块名称（XXX->XXX）
	 */
	@Size(max=150)
	@Column(length=150)
	private String menu;

	/**
	 * 操作类型（0：删除；1：新建；2：修改）
	 */
	private Integer type;

	/**
	 * 操作内容
	 */
	@Size(max=300)
	@Column(length=300)
	private String content;

	/**
	 * 操作人
	 */
	private User user;

	/**
	 * 操作时间
	 */
	private Date time;

	/**
	 * 操作人IP
	 */
	@Size(max=150)
	@Column(length=150)
	private String ipAddr;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
}
