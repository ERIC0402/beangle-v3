/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import com.opensymphony.xwork2.util.ValueStack;

public class Captcha extends AbstractTextBean {

	public Captcha(ValueStack stack) {
		super(stack);
		if(label == null){
			label = "验证码";
		}
		if(required == null){
			required = "true";
		}
	}

}
