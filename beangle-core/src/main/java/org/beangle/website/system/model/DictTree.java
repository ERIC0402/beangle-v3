package org.beangle.website.system.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.model.pojo.HierarchyEntity;
import org.beangle.model.pojo.LongIdObject;

/**
 * 字典树
 * @author Donhko
 *
 */
@Entity(name="org.beangle.website.system.model.DictTree")
public class DictTree extends LongIdObject implements HierarchyEntity<DictTree>, Comparable<DictTree>{

	/**
	 * 标识，用于程序判断用，如：TMFL
	 */
	@Size(max = 32)
	@Column(unique = true,length=32)
	private String dm;
	/**
	 * 编码
	 * 此字段值将自动生成，如：1.1
	 */
	@NotNull
	@Size(max = 32)
	@Column(unique = true,length=32)
	private String code;

	/**
	 * 名称
	 */
	@NotNull
	@Size(max = 100)
	@Column(length=100)
	private String name;

	/**
	 * 备注
	 */
	@Size(max = 300)
	@Column(length=300)
	private String remark;

	/**
	 * 是否有效
	 */
	@NotNull
	private boolean enabled = true;

	/**
	 * 父节点
	 */
	private DictTree parent;
	
	private Date createTime;
	private Date updateTime;

	/**
	 * 子节点
	 */
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	@OrderBy("code")
	private List<DictTree> children = CollectUtils.newArrayList();

	public DictTree() {
		super();
	}

	public DictTree(Long id) {
		super(id);
	}

	/**
	 * 不同级的菜单按照他们固有的级联顺序排序.
	 */
	public int compareTo(DictTree other) {
		return getCode().compareTo(other.getCode());
	}

	public void generateCode(String indexno) {
		if (null == parent) {
			this.code = indexno;
		} else {
			this.code = StrUtils.concat(parent.getCode(), ".", indexno);
		}
	}

	public void generateCode() {
		if (null != parent) {
			this.code = StrUtils.concat(parent.getCode(), ".", getIndexno());
		}
	}

	public String getIndexno() {
		String indexno = StringUtils.substringAfterLast(code, ".");
		if (StringUtils.isEmpty(indexno)) {
			indexno = code;
		}
		return indexno;
	}

	public int getDepth() {
		return (null == parent) ? 1 : parent.getDepth() + 1;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getDescription() {
		return StrUtils.concat("[", code, "]", name);
	}

	@Override
	public String toString() {
		return getDescription();
	}

	public DictTree getParent() {
		return parent;
	}

	public void setParent(DictTree parent) {
		this.parent = parent;
	}

	public String getDm() {
		return dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	public List<DictTree> getChildren() {
		return children;
	}

	public void setChildren(List<DictTree> children) {
		this.children = children;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
