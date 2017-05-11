/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.template;

import java.util.Stack;

/**
 * ui主体栈
 * @author beangle
 * @version $Id: ThemeStack.java Jul 28, 2011 12:04:52 PM beangle $
 */
public class ThemeStack {

	private Stack<Theme> themes=new Stack<Theme>();

	public Theme push(Theme item) {
		return themes.push(item);
	}

	public Theme pop() {
		return themes.pop();
	}

	public Theme peek() {
		return themes.peek();
	}

	public boolean isEmpty() {
		return themes.isEmpty();
	}
	
}
