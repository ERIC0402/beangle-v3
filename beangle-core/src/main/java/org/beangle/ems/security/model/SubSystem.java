package org.beangle.ems.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdObject;

/**
 * 子系统
 * @author GOKU
 * 
 */
@Entity(name = "org.beangle.ems.security.model.SubSystem")
public class SubSystem extends LongIdObject {

	/**
	 * 名称
	 */
	@Size(max = 300)
	@Column(length = 300)
	private String name;

	/**
	 * 域名
	 */
	@Size(max = 300)
	@Column(length = 300)
	private String domain;

	/**
	 * 根路径
	 */
	@Size(max = 300)
	@Column(length = 300)
	private String contextPath;

	/**
	 * appid
	 */
	@Size(max = 300)
	@Column(length = 300)
	private String appid;

	/**
	 * 密钥
	 */
	@Size(max = 300)
	@Column(length = 300)
	private String pass;

	/**
	 * 地址
	 */
	@Size(max = 300)
	@Column(length = 300)
	private String url;
	/**
	 * 是否启用
	 */
	private Boolean enabled;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
