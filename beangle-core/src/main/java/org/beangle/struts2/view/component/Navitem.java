/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.ems.web.tags.component.Guard;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author beangle
 * @version $Id: Navitem.java Apr 20, 2011 8:46:40 AM beangle $
 */
public class Navitem extends UIBean {

	private String title;
	private String href;

	private String onclick;
	private String target;
	private boolean selected = false;

	public Navitem(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		if(this.target == null){
			this.target = findAncestor(Navmenu.class).getTarget();
		}
		this.href = render(this.href);
		this.selected = findAncestor(Navmenu.class).isSelected(this.href);
		if (null == onclick) {
			if (null != target) {
				onclick = StrUtils.concat("return bg.Go(this,'", target, "',true)");
				target = null;
			} else {
				onclick = "return bg.Go(this,'main',true)";
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

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
