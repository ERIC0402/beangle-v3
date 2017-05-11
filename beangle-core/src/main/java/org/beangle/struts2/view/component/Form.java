/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;

import com.opensymphony.xwork2.util.ValueStack;

public class Form extends ClosingUIBean {

	protected String action;
	protected String target;

	protected String onsubmit;
	/** Boolean */
	protected String validate;
	private String title;

	private Map<String, StringBuilder> checks = CollectUtils.newHashMap();

	private StringBuilder validity;
	private StringBuilder validateStr;
	private String notable;

	public Form(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		if (null == name && null == id) {
			generateIdIfEmpty();
			name = id;
		} else if (null == id) {
			id = name;
		}
		action = render(action);
		if (null != title){
			title = getText(title);
		}
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOnsubmit() {
		return onsubmit;
	}

	public void setOnsubmit(String onsubmit) {
		this.onsubmit = onsubmit;
	}

	public String getValidate() {
		if (null == validate) {
			if (!checks.isEmpty()){
				validate = "true";
			}else {
				validate = "false";
			}
		}
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	public void addCheck(String id, String check) {
		StringBuilder sb = checks.get(id);
		if (null == sb) {
			sb = new StringBuilder(20);
			checks.put(id, sb);
		}
		if (!check.startsWith(".")) {
			check = "." + check;
		}
		sb.append(check);
	}

	public void addCheck(String check) {
		if (null == validity)
			validity = new StringBuilder();
		validity.append(check);
	}

	public String getValidity() {
		if (null == validity){
			validity = new StringBuilder();
		}
		StringBuilder sb = new StringBuilder(validity);
		for (Map.Entry<String, StringBuilder> check : checks.entrySet()) {
			sb.append("jQuery('#").append(StringUtils.replace(check.getKey(), ".", "\\\\.")).append("')").append(check.getValue()).append(";\n");
		}
		return sb.toString();
	}

	public String getValidateStr() {
		StringBuilder sb = validateStr;
		if (null == sb) {
			sb = validateStr = new StringBuilder();
			for (Map.Entry<String, StringBuilder> check : checks.entrySet()) {
				sb.append("$('#").append(StringUtils.replace(check.getKey(), ".", "\\\\.")).append("').rules(\"add\", {").append(check.getValue())
						.append("});\n");
			}
		}
		return sb.toString();
	}

	public String getValidator() {
		if (null == validity)
			validity = new StringBuilder();
		StringBuilder sb = new StringBuilder(validity);
		for (Map.Entry<String, StringBuilder> check : checks.entrySet()) {
			sb.append(",'#").append(check.getKey()).append("':{").append(check.getValue()).append("}");
		}
		return sb.toString();
	}

	public Map<String, StringBuilder> getChecks() {
		return checks;
	}

	public String getNotable() {
		return notable;
	}

	public void setNotable(String notable) {
		this.notable = notable;
	}

	protected void addCheck(UIBean bean) {
		if ("true".equals(bean.getRequired()))
			addCheck(bean.getId(), ".required()");
		if (null != bean.getCheck())
			addCheck(bean.getCheck(), check);
	}

}
