package org.beangle.struts2.view;

import static org.apache.struts2.util.TextProviderHelper.getText;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.page.Page;
import org.beangle.struts2.view.component.ActionUrlRender;
import org.beangle.struts2.view.component.Captcha;
import org.beangle.struts2.view.component.Component;
import org.beangle.struts2.view.component245.Anchor;
import org.beangle.struts2.view.component245.Checkbox;
import org.beangle.struts2.view.component245.Checkboxes;
import org.beangle.struts2.view.component245.Css;
import org.beangle.struts2.view.component245.Date;
import org.beangle.struts2.view.component245.Dialog;
import org.beangle.struts2.view.component245.Div;
import org.beangle.struts2.view.component245.Email;
import org.beangle.struts2.view.component245.Field;
import org.beangle.struts2.view.component245.Foot;
import org.beangle.struts2.view.component245.Form;
import org.beangle.struts2.view.component245.Formfoot;
import org.beangle.struts2.view.component245.Grid;
import org.beangle.struts2.view.component245.Head;
import org.beangle.struts2.view.component245.Iframe;
import org.beangle.struts2.view.component245.Messages;
import org.beangle.struts2.view.component245.Module;
import org.beangle.struts2.view.component245.Navitem;
import org.beangle.struts2.view.component245.Navmenu;
import org.beangle.struts2.view.component245.Pagebar;
import org.beangle.struts2.view.component245.Password;
import org.beangle.struts2.view.component245.Radio;
import org.beangle.struts2.view.component245.Radios;
import org.beangle.struts2.view.component245.RedirectParams;
import org.beangle.struts2.view.component245.Reset;
import org.beangle.struts2.view.component245.Select;
import org.beangle.struts2.view.component245.Select2;
import org.beangle.struts2.view.component245.Startend;
import org.beangle.struts2.view.component245.Submit;
import org.beangle.struts2.view.component245.Tab;
import org.beangle.struts2.view.component245.Tabs;
import org.beangle.struts2.view.component245.Textarea;
import org.beangle.struts2.view.component245.Textfield;
import org.beangle.struts2.view.component245.Textfields;
import org.beangle.struts2.view.component245.Toolbar;
import org.beangle.struts2.view.template.Theme;

import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ValueStack;

public class BeangleTagLibrary245 extends BeangleTagLibrary {

	private ActionUrlRender render;

	public BeangleTagLibrary245() {
		super();
	}

	public BeangleTagLibrary245(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		super(stack, req, res);
		theme.setUi(getUitheme());
		theme.setUibase(req.getContextPath());
		this.stack.getContext().put(Theme.THEME, theme);
	}

	public Object getModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		BeangleTagLibrary245 models = new BeangleTagLibrary245(stack, req, res);
		models.render = this.render;
		return models;
	}

	protected String getUitheme() {
		String uitheme = req.getParameter("ui.theme");
		if (null == uitheme) {
			HttpSession session = req.getSession(false);
			if (null != session && null != session.getAttribute("ui.theme")) {
				uitheme = (String) session.getAttribute("ui.theme");
			}
		}
		if (null == uitheme) {
			uitheme = "default";
		}
		return uitheme;
	}

	@Inject
	public void setRender(ActionUrlRender render) {
		this.render = render;
	}

	public String url(String url) {
		if(StringUtils.isNotEmpty(url) && url.startsWith("/")){
			url = ServletActionContext.getRequest().getContextPath() + url;
		}
		return render.render(req.getRequestURI(), url);
	}

	public java.util.Date getNow() {
		return new java.util.Date();
	}

	/**
	 * query string and form control
	 * 
	 * @return
	 */
	public String getParamstring() {
		StringWriter sw = new StringWriter();
		Enumeration<?> em = req.getParameterNames();
		while (em.hasMoreElements()) {
			String attr = (String) em.nextElement();
			String value = req.getParameter(attr);
			if (attr.equals("x-requested-with")) {
				continue;
			}
			sw.write(attr);
			sw.write('=');
			try {
				StringEscapeUtils.escapeJavaScript(sw, value);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (em.hasMoreElements()) {
				sw.write('&');
			}
		}
		return sw.toString();
	}

	public boolean isPage(Object data) {
		return data instanceof Page<?>;
	}

	public String text(String name) {
		return getText(name, name, Collections.emptyList(), stack, false);
	}

	public String text(String name, Object arg0) {
		return getText(name, name, Collections.singletonList(arg0), stack, false);
	}

	public String text(String name, Object arg0, Object arg1) {
		return getText(name, name, CollectUtils.newArrayList(arg0, arg1), stack, false);
	}

	public TagModel getHead() {
		return get(Head.class);
	}

	public TagModel getDialog() {
		return get(Dialog.class);
	}

	public TagModel getCss() {
		return get(Css.class);
	}

	public TagModel getIframe() {
		return get(Iframe.class);
	}

	public TagModel getFoot() {
		return get(Foot.class);
	}

	public TagModel getForm() {
		return get(Form.class);
	}

	public TagModel getFormfoot() {
		return get(Formfoot.class);
	}

	public TagModel getSubmit() {
		return get(Submit.class);
	}

	public TagModel getReset() {
		return get(Reset.class);
	}

	public TagModel getToolbar() {
		return get(Toolbar.class);
	}

	public TagModel getTabs() {
		return get(Tabs.class);
	}

	public TagModel getTab() {
		return get(Tab.class);
	}

	public TagModel getGrid() {
		return get(Grid.class);
	}

	public TagModel getGridbar() {
		return get(Grid.Bar.class);
	}

	public TagModel getGridfilter() {
		return get(Grid.Filter.class);
	}

	public TagModel getRow() {
		return get(Grid.Row.class);
	}

	public TagModel getCol() {
		TagModel model = models.get(Grid.Col.class);
		if (null == model) {
			// just for performance
			model = new TagModel(stack) {
				@Override
				protected Component getBean() {
					return new Grid.Col(stack);
				}
			};
			models.put(Grid.Col.class, model);
		}
		return model;
	}

	public TagModel getBoxcol() {
		return get(Grid.Boxcol.class);
	}

	public TagModel getPagebar() {
		return get(Pagebar.class);
	}

	public TagModel getPassword() {
		return get(Password.class);
	}

	public TagModel getA() {
		TagModel model = models.get(Anchor.class);
		if (null == model) {
			model = new TagModel(stack) {
				protected Component getBean() {
					return new Anchor(stack);
				}
			};
			models.put(Anchor.class, model);
		}
		return model;
	}

	public TagModel getRedirectParams() {
		return get(RedirectParams.class);
	}

	public TagModel getMessages() {
		return get(Messages.class);
	}

	public TagModel getTextfield() {
		return get(Textfield.class);
	}

	public TagModel getCaptcha() {
		return get(Captcha.class);
	}

	public TagModel getEmail() {
		return get(Email.class);
	}

	public TagModel getTextarea() {
		return get(Textarea.class);
	}

	public TagModel getField() {
		return get(Field.class);
	}

	public TagModel getTextfields() {
		return get(Textfields.class);
	}

	public TagModel getDatepicker() {
		return get(Date.class);
	}

	public TagModel getDiv() {
		return get(Div.class);
	}

	public TagModel getSelect() {
		return get(Select.class);
	}

	public TagModel getSelect2() {
		return get(Select2.class);
	}

	public TagModel getModule() {
		return get(Module.class);
	}

	public TagModel getNavmenu() {
		return get(Navmenu.class);
	}

	public TagModel getNavitem() {
		return get(Navitem.class);
	}

	public TagModel getRadio() {
		return get(Radio.class);
	}

	public TagModel getRadios() {
		return get(Radios.class);
	}

	public TagModel getStartend() {
		return get(Startend.class);
	}
	
	public TagModel getCheckbox(){
		return get(Checkbox.class);
	}
	
	public TagModel getCheckboxes(){
		return get(Checkboxes.class);
	}
	
}
