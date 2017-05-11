/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import com.opensymphony.xwork2.util.ValueStack;

public class Field extends ClosingUIBean {

	public Field(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		if (null != label) {
			label = getText(label);
		}
	}
	
	@Override
	public String getParameterString() {
		return super.getParameterString() + (getId() == null ? "" : " id=" + getId());
	}

}
