/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

public class Plupload extends ClosingUIBean {

	private String value;
	private String url;
	private String dir;
	private String limit;
	private String width;
	private String height;
	private String multi;

	public Plupload(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		if (null == this.id) {
			generateIdIfEmpty();
		}
		if (url == null) {
			url = ServletActionContext.getServletContext().getContextPath() + "/common/file!download.action?file=" + value;
		}
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMulti() {
		return multi;
	}

	public void setMulti(String multi) {
		this.multi = multi;
	}

}
