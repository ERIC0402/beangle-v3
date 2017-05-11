package org.beangle.ems.config.model;

import org.apache.commons.lang.StringUtils;

public class ClassPropertyConfig implements Comparable<ClassPropertyConfig> {

	/**
	 * 属性名
	 */
	private String propertyName;
	/**
	 * 标题
	 */
	private String label;
	/**
	 * 新标题
	 */
	public String newLabel;
	/**
	 * 是否可见
	 */
	private Boolean readable = false;
	/**
	 * 是否可编辑
	 */
	private Boolean editable = false;
	/**
	 * 是否必填
	 */
	private Boolean required = false;
	/**
	 * 是否列表可见
	 */
	private Boolean listable = false;
	/**
	 * 排序
	 */
	private Integer px = 0;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getPx() {
		return px;
	}

	public void setPx(Integer px) {
		this.px = px;
	}

	public boolean equals(Object o) {
		if (o instanceof ClassPropertyConfig) {
			ClassPropertyConfig config = (ClassPropertyConfig) o;
			return StringUtils.equals(this.propertyName, config.propertyName);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return propertyName.hashCode();
	}

	public Boolean getReadable() {
		return readable;
	}

	public void setReadable(Boolean readable) {
		this.readable = readable;
	}

	public String getNewLabel() {
		if (newLabel == null) {
			newLabel = label;
		}
		return newLabel;
	}

	public void setNewLabel(String newLabel) {
		this.newLabel = newLabel;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public int compareTo(ClassPropertyConfig o) {
		return px.compareTo(o.px);
	}

	public Boolean getListable() {
		return listable;
	}

	public void setListable(Boolean listable) {
		this.listable = listable;
	}

}
