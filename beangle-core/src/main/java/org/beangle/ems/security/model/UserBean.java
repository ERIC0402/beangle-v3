/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.config.model.CProperty;
import org.beangle.ems.config.model.CPropertys;
import org.beangle.ems.security.Category;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.User;
import org.beangle.ems.security.restrict.RestrictionHolder;
import org.beangle.ems.security.restrict.UserRestriction;
import org.beangle.model.pojo.LongIdTimeObject;
import org.beangle.model.util.EntityUtils;

/**
 * 系统中所有用户的账号、权限、状态信息.
 * 
 * @author dell,beangle 2005-9-26
 */
@Entity(name = "org.beangle.ems.security.User")
@Inheritance(strategy = InheritanceType.JOINED)
@CPropertys(name = "用户信息")
public class UserBean extends LongIdTimeObject implements User, RestrictionHolder<UserRestriction> {
	private static final long serialVersionUID = -3625902334772342380L;

	/** 名称 */
	@Size(max = 40)
	@NotNull
	@Column(unique = true)
	protected String name;

	/** 用户密文 */
	@Size(max = 100)
	@NotNull
	@CProperty(label = "密码")
	private String password;

	/** 用户姓名 */
	@NotNull
	@Size(max = 50)
	@CProperty(label = "姓名", listable = true, required = true)
	private String fullname;

	/** 手机 */
	@CProperty(label = "手机", listable = true)
	@Size(max = 20)
	@Column(length = 20)
	private String phone;

	/** 用户联系email */
	@CProperty(label = "EMAIL", listable = true)
	@Column(length = 100)
	private String mail;

	/** 对应用户组 */
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<GroupMember> groups = CollectUtils.newHashSet();

	/** 创建人 */
	private User creator;

	/** 种类 */
	@ManyToMany
	protected Set<Category> categories = CollectUtils.newHashSet();;

	/** 缺省类别 */
	@NotNull
	private Category defaultCategory;

	/**
	 * 账户生效时间
	 */
	@NotNull
	protected Date effectiveAt;

	/**
	 * 账户失效时间
	 */
	protected Date invalidAt;

	/**
	 * 密码失效时间
	 */
	protected Date passwordExpiredAt;

	/** 备注 */
	@CProperty(label = "备注")
	protected String remark;

	@NotNull
	@CProperty(label = "状态", listable = true)
	protected boolean enabled;

	/** 访问限制 */
	@OneToMany(mappedBy = "holder", cascade = CascadeType.ALL)
	protected Set<UserRestriction> restrictions = CollectUtils.newHashSet();

	public UserBean() {
		super();
	}

	public UserBean(Long id) {
		setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isCategory(Long categoryId) {
		for (final Category category : categories) {
			if (category.getId().equals(categoryId))
				return true;
		}
		return false;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Category getDefaultCategory() {
		return defaultCategory;
	}

	public void setDefaultCategory(Category defaultCategory) {
		this.defaultCategory = defaultCategory;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 是否账户过期
	 */
	public boolean isAccountExpired() {
		return EntityUtils.isExpired(this);
	}

	/**
	 * 是否密码过期
	 */
	public boolean isPasswordExpired() {
		return (null != passwordExpiredAt && new Date().after(passwordExpiredAt));
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<GroupMember> getGroups() {
		return groups;
	}

	public void setGroups(Set<GroupMember> groups) {
		this.groups = groups;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Set<UserRestriction> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(Set<UserRestriction> restrictions) {
		this.restrictions = restrictions;
	}

	public Date getEffectiveAt() {
		return effectiveAt;
	}

	public void setEffectiveAt(Date effectiveAt) {
		this.effectiveAt = effectiveAt;
	}

	public Date getInvalidAt() {
		return invalidAt;
	}

	public void setInvalidAt(Date invalidAt) {
		this.invalidAt = invalidAt;
	}

	public Date getPasswordExpiredAt() {
		return passwordExpiredAt;
	}

	public void setPasswordExpiredAt(Date passwordExpiredAt) {
		this.passwordExpiredAt = passwordExpiredAt;
	}

	public String getDescription() {
		return fullname + "（" + name + "）";
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("password", this.password).append("name", this.getName()).toString();
	}
}
