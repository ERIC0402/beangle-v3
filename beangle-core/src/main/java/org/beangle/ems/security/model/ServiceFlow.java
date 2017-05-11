package org.beangle.ems.security.model;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.model.pojo.HierarchyEntity;
import org.beangle.model.pojo.LongIdTimeObject;
import org.hibernate.annotations.Loader;

/**
 * 业务流
 * @author LPJ
 *
 */
@Entity(name = "org.beangle.ems.security.model.ServiceFlow")
public class ServiceFlow extends LongIdTimeObject implements HierarchyEntity<ServiceFlow> ,Comparable<ServiceFlow>{
	
	/**
	 * 编号
	 */
	@NotNull
	@Size(max = 32)
	@Column(unique = true,length = 32)
	private String code;
	
	/**
	 * 标题
	 */
	@NotNull
	@Size(max = 150)
	@Column(length = 150)
	private String title;
	
	/**
	 * 上级节点
	 */
	private ServiceFlow parent;
	
	/**
	 * 入口
	 */
	@Column(length = 200)
	private String entry;
	
	/**
	 * 是否有效
	 */
	private boolean enabled = true;
	
	/**
	 * 描述
	 */
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(columnDefinition = "CLOB")
	private String describe;
	
	/**
	 * 子节点
	 */
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	@OrderBy("code")
	private List<ServiceFlow> children = CollectUtils.newArrayList();
	
	public ServiceFlow(){
		super();
	}
	
	public ServiceFlow(Long id){
		super(id);
	}
	
	/**
	 * 不同级的菜单按照他们固有的级联顺序排序.
	 */
	public int compareTo(ServiceFlow other) {
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

	public List<ServiceFlow> getChildren() {
		return children;
	}

	public void setChildren(List<ServiceFlow> children) {
		this.children = children;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ServiceFlow getParent() {
		return parent;
	}

	public void setParent(ServiceFlow parent) {
		this.parent = parent;
	}
	
	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getDescription() {
		return StrUtils.concat("[", code, "]", title);
	}

	@Override
	public String toString() {
		return getDescription();
	}
	

}
