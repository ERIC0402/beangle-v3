/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 文本域
 * 
 * @author beangle
 * @version $Id: Textarea.java May 3, 2011 12:40:21 PM beangle $
 */
public class Textarea extends AbstractTextBean {

	protected String cols;
	protected String readonly;
	protected String rows;
	protected String wrap;

	public Textarea(ValueStack stack) {
		super(stack);
		if(StringUtils.isEmpty(rows)){
			rows = "3";
		}
		if(StringUtils.isEmpty(cols)){
			cols = "50";
		}
//		if(StringUtils.isEmpty(maxlength)){
//			maxlength = "1000";
//		}
	}

	@Override
	protected void evaluateParams() {
		super.evaluateParams();
		Form myform = findAncestor(Form.class);
		if(editable()){
			if (null != maxlength) myform.addCheck(id, ".maxl(" + maxlength + ")");
		}
	}

	public String getCols() {
		return cols;
	}

	public void setCols(String cols) {
		this.cols = cols;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getWrap() {
		return wrap;
	}

	public void setWrap(String wrap) {
		this.wrap = wrap;
	}

}
