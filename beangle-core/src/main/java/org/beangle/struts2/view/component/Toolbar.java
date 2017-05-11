/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.util.ValueStack;

import freemarker.template.SimpleNumber;

public class Toolbar extends ClosingUIBean {

	private String title;

	public Toolbar(ValueStack stack) {
		super(stack);
	}

	public void evaluateParams() {
		generateIdIfEmpty();
		if (null != title) {
			title = getText(title);
			SimpleNumber id  = (SimpleNumber) getParameters().get("entityId");
			if(id != null){
				if(id.getAsNumber().longValue() == 0){
					title = "新建&nbsp;" + title;
				}else{
					title = "修改&nbsp;" + title;
				}
			}
		}
	}

	public String getTitle() {
		//return title;
		if(StringUtils.isNotEmpty(title) && title.indexOf("+") >= 0){
			String subtitle = title.substring(0, title.indexOf("+"));
			return getText(subtitle) + getText(title.substring(title.indexOf("+")+1,title.length()));
		}
		return getText(title);
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
