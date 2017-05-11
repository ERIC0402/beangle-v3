package org.beangle.website.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdObject;


/**
 * 系统参数
 * @author GOKU
 *
 */
@Entity(name="org.beangle.website.system.model.SystemArgument")
public class SystemArgument extends LongIdObject {

	/**邮件服务器域名*/
	@Size(max=64)
	@NotNull
	@Column(length=64)
	private String yjfwqym;
	
	/**登录邮件服务器用户名*/
	@Size(max=64)
	@NotNull@Column(length=64)
	private String yjfwqyhm;
	
	/**登录邮件服务器密码*/
	@Size(max=64)
	@NotNull
	@Column(length=64)
	private String yjfwqmm;
	
	/**邮件发送人Email*/
	@Size(max=64)
	@NotNull
	@Column(length=64)
	private String yjfsr;

	public String getYjfwqym() {
		return yjfwqym;
	}

	public void setYjfwqym(String yjfwqym) {
		this.yjfwqym = yjfwqym;
	}

	public String getYjfwqyhm() {
		return yjfwqyhm;
	}

	public void setYjfwqyhm(String yjfwqyhm) {
		this.yjfwqyhm = yjfwqyhm;
	}

	public String getYjfwqmm() {
		return yjfwqmm;
	}

	public void setYjfwqmm(String yjfwqmm) {
		this.yjfwqmm = yjfwqmm;
	}

	public String getYjfsr() {
		return yjfsr;
	}

	public void setYjfsr(String yjfsr) {
		this.yjfsr = yjfsr;
	}
	
}
