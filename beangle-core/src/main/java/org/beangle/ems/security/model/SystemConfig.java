package org.beangle.ems.security.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;

import org.beangle.model.pojo.LongIdTimeObject;
/**
 * 系统配置
 * @author GZW
 *
 */
@Entity(name="org.beangle.ems.security.model.SystemConfig")
public class SystemConfig extends LongIdTimeObject {

	/**
	 * 标题
	 */
	@Column(length=300)
	private String title;
	
	/**
	 * 标题图片
	 */
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(columnDefinition = "BLOB", length = 1048576)
	private byte[] headImage;

	/**
	 * 页脚信息
	 */
	@Column(length=900)
	private String footMsg;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public byte[] getHeadImage() {
		return headImage;
	}

	public void setHeadImage(byte[] headImage) {
		this.headImage = headImage;
	}

	public String getFootMsg() {
		return footMsg;
	}

	public void setFootMsg(String footMsg) {
		this.footMsg = footMsg;
	}
}
