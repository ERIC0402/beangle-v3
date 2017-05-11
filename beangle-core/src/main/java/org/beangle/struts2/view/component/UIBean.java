/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.io.Writer;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.TextProviderHelper;
import org.beangle.ems.config.model.ClassPropertyConfig;
import org.beangle.struts2.view.template.TemplateEngine;
import org.beangle.struts2.view.template.Theme;
import org.beangle.struts2.view.template.ThemeStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author beangle
 */
public abstract class UIBean extends Component {
	final protected static transient Random RANDOM = new Random();

	protected Logger log = LoggerFactory.getLogger(UIBean.class);

	protected String id;

	protected String name;
	protected String label;
	protected String title;
	protected String required = "false";

	protected Theme theme;
	protected Theme defaultTheme;
	private ClassPropertyConfig config;
	protected String check;
	protected Object text;
	protected Object value;

	public Object getText() {
		if(text != null){
			return text;
		}else{
			return value;
		}
	}

	public void setText(String text) {
		this.text = text;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public ClassPropertyConfig getConfig() {
		return config;
	}

	public void setConfig(ClassPropertyConfig config) {
		this.config = config;
	}

	public static Random getRandom() {
		return RANDOM;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public UIBean(ValueStack stack) {
		super(stack);
	}

	protected void evaluateParams() {
	}

	public boolean start(Writer writer) {
		setConfig();
		return true;
	}

	@Override
	public boolean end(Writer writer, String body) {
		evaluateParams();
		try {
			mergeTemplate(writer);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	protected void setConfig() {
		ClassConfigTag cctag = findAncestor(ClassConfigTag.class);
		if (cctag != null) {
			setConfig(cctag.getConfig(this));
		}
	}

	protected void mergeTemplate(Writer writer) throws Exception {
		if (config == null || config.getReadable()) {
			TemplateEngine engine = getContainer().getInstance(TemplateEngine.class);
			if (config != null && !config.getEditable()) {
				Theme infoTheme = getTheme();
				if(!infoTheme.getName().endsWith("info")){
					if(infoTheme.getName().indexOf("/") > 0){
						setTheme(infoTheme.getName() + "info");
					}else{
						setTheme("info");
					}
				}
			}
			engine.render(getTheme().getTemplatePath(getClass(), engine.getSuffix()), stack, writer, this);
		} else {
			log.info(this.name + " is hidden");
		}
	}

	/**
	 * 将所有额外参数链接起来
	 * 
	 * @return 空格开始 空格相隔的参数字符串
	 */
	public String getParameterString() {
		StringBuilder sb = new StringBuilder(parameters.size() * 10);
		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			String key = entry.getKey();
			if ("cssClass".equals(key))
				key = "class";
			sb.append(" ").append(key).append("=\"").append(entry.getValue().toString()).append("\"");
		}
		return sb.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	protected Theme getTheme() {
		if (null == theme) {
			ThemeStack themestack = (ThemeStack) stack.getContext().get(Theme.THEME_STACK);
			if (null != themestack) {
				theme = themestack.peek();
			}
			if (theme == null) {
				theme = defaultTheme;
			}
			if (null == theme) {
				theme = (Theme) stack.getContext().get(Theme.THEME);
			}
			return theme;
		} else {
			return theme;
		}
	}

	public void setTheme(String theme) {
		this.theme = new Theme(theme);
	}

	/**
	 * 获得对应的国际化信息
	 * 
	 * @param text
	 * @return 当第一个字符不是字母或者不包含.或者包含空格的均返回原有字符串
	 */
	protected String getText(String text) {
		return getText(text, text);
	}

	protected String getText(String text, String defaultText) {
		if (StringUtils.isEmpty(text))
			return defaultText;
		if (!CharUtils.isAsciiAlpha(text.charAt(0)))
			return defaultText;
		if (-1 == text.indexOf('.') || -1 < text.indexOf(' ')) {
			return defaultText;
		} else {
			return TextProviderHelper.getText(text, defaultText, Collections.emptyList(), stack, false);
		}
	}

	protected String getRequestURI() {
		HttpServletRequest req = (HttpServletRequest) stack.getContext().get(ServletActionContext.HTTP_REQUEST);
		return req.getRequestURI();
	}

	protected Object getValue(Object obj, String property) {
		stack.push(obj);
		try {
			return stack.findValue(property);
		} finally {
			stack.pop();
		}
	}

	protected Container getContainer() {
		return (Container) stack.getContext().get(ActionContext.CONTAINER);
	}

	protected String render(String uri) {
		if (StringUtils.isNotEmpty(uri) && uri.startsWith("/")) {
			uri = ServletActionContext.getRequest().getContextPath() + uri;
		}
		uri = getContainer().getInstance(ActionUrlRender.class).render(getRequestURI(), uri);
		if (uri.indexOf("?") < 0) {
			uri += "?";
		}
		uri += "&t=" + System.currentTimeMillis();
		return uri;
	}

	protected void generateIdIfEmpty() {
		if (StringUtils.isEmpty(this.id)) {
			int nextInt = RANDOM.nextInt();
			nextInt = (nextInt == Integer.MIN_VALUE) ? Integer.MAX_VALUE : Math.abs(nextInt);
			this.id = Theme.getTemplateName(getClass()) + String.valueOf(nextInt);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		// return label;
		if (StringUtils.isNotEmpty(label) && label.indexOf("+") >= 0) {
			String subtitle = label.substring(0, label.indexOf("+"));
			return getText(subtitle) + getText(label.substring(label.indexOf("+") + 1, label.length()));
		}
		return getText(label);
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public Theme getDefaultTheme() {
		return defaultTheme;
	}

	public void setDefaultTheme(Theme defaultTheme) {
		this.defaultTheme = defaultTheme;
	}

	protected boolean editable() {
		if(config != null && !config.getEditable()){
			return false;
		}
		return true;
	}

}
