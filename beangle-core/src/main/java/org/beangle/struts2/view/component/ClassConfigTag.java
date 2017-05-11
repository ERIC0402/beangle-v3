package org.beangle.struts2.view.component;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beangle.ems.config.model.ClassPropertyConfig;

import com.opensymphony.xwork2.util.ValueStack;

public class ClassConfigTag extends ClosingUIBean {

	private List<ClassPropertyConfig> configs;

	public List<ClassPropertyConfig> getConfigs() {
		return configs;
	}

	public void setConfigs(List<ClassPropertyConfig> configs) {
		this.configs = configs;
	}

	public ClassConfigTag(ValueStack stack) {
		super(stack);
	}

	/**
	 * 根据标签的name获取属性配置
	 * @param bean
	 * @return
	 * @author 钱剑林
	 * @createDate 2015年4月9日 上午10:53:59
	 */
	public ClassPropertyConfig getConfig(UIBean bean) {
		String name = bean.getName();
		//如果没有配置则返回空
		if (configs == null || StringUtils.isEmpty(name)) {
			return null;
		}
		name = name.substring(name.indexOf(".") + 1);
		if (name.endsWith(".id")) {
			name = name.substring(0, name.length() - 3);
		}
		for (ClassPropertyConfig cpc : configs) {
			if (name.equals(cpc.getPropertyName())) {
				// bean.setLabel(cpc.getLabel());
				bean.setRequired(cpc.getRequired().toString());
				return cpc;
			}
		}
		// 如果找不到配置文件，则返回一个不可见配置
		return new ClassPropertyConfig();
	}

}
