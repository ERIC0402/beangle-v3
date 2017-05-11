/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author beangle
 * @version $Id: AbstractTextBean.java May 3, 2011 12:46:06 PM beangle $
 */
public abstract class AbstractTextBean extends UIBean {
	protected String comment;
	protected Object value = "";
	protected String check;
	protected String maxlength = "100";

	public AbstractTextBean(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		Form myform = findAncestor(Form.class);
		if (null == this.id)
			generateIdIfEmpty();
		if (label == null)
			label = name;
		if (null != label)
			label = getText(label);
		if (null != title) {
			title = getText(title);
		} else {
			title = label;
		}
		if (editable()) {
			if (StringUtils.isNotEmpty(getComment())) {
				// myform.addCheck(id, ".comment('" + getComment() + "')");
			}
			if ("true".equals(required))
				myform.addCheck(id, ".required()");
			if (null != check)
				myform.addCheck(id, "." + check);
		}
	}

	public String getName() {
		return name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public Object getValue() {
		return (String) value;
	}

	public void setValue(Object value) {
		this.value = String.valueOf(value);
	}

	public String getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

}
