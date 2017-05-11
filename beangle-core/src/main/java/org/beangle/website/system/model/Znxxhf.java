package org.beangle.website.system.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;

/**
 * 站内消息回复
 * @author GOKU
 *
 */
@Entity(name="org.beangle.website.system.model.Znxxhf")
public class Znxxhf extends LongIdObject {

	/**
	 * 站内消息
	 */
	private Znxx znxx;

	/**
	 * 回复人
	 */
	private User user;

	/**
	 * 回复内容
	 */
	@Size(max=3000)
	@Column(length=3000)
	private String replyContent;

	/**
	 * 回复时间
	 */
	private Date time;

	public Znxx getZnxx() {
		return znxx;
	}

	public void setZnxx(Znxx znxx) {
		this.znxx = znxx;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
