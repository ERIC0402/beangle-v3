package org.beangle.emsapp.system.action;

import java.util.List;

import org.beangle.ems.config.model.ClassPropertyConfig;
import org.beangle.ems.config.service.ClassConfigService;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.springframework.util.Assert;

public class ClassConfigAction extends SecurityActionSupport {

	private ClassConfigService classConfigService;

	public void setClassConfigService(ClassConfigService classConfigService) {
		this.classConfigService = classConfigService;
	}

	@Override
	public String index() throws Exception {
		String className = get("entityName");
		Assert.notNull(className);
		Class<?> clazz = Class.forName(className);
		List<ClassPropertyConfig> globalConfig = classConfigService.findConfig(clazz);
		List<ClassPropertyConfig> formConfig = classConfigService.findConfig(clazz, ClassConfigService.FORM);
		List<ClassPropertyConfig> listConfig = classConfigService.findConfig(clazz, ClassConfigService.LIST);
		put("globalConfig", globalConfig);
		put("formConfig", formConfig);
		put("listConfig", listConfig);
		return forward();
	}

	public String result() {
		return forward();
	}

	@Override
	public String save() throws Exception {
		String className = get("entityName");
		Assert.notNull(className);
		Class<?> clazz = Class.forName(className);
		List<ClassPropertyConfig> globalConfig = populateList(ClassPropertyConfig.class, "globalConfig");
		List<ClassPropertyConfig> formConfig = populateList(ClassPropertyConfig.class, "formConfig");
		List<ClassPropertyConfig> listConfig = populateList(ClassPropertyConfig.class, "listConfig");
		classConfigService.saveConfig(clazz, globalConfig);
		classConfigService.saveConfig(clazz, formConfig, ClassConfigService.FORM);
		classConfigService.saveConfig(clazz, listConfig, ClassConfigService.LIST);
		return redirect("result", "info.save.success");
	}

	public String code() {
		try {
			String className = get("entityName");
			String code = get("code");
			Class<?> clazz = Class.forName(className);
			List<ClassPropertyConfig> config = classConfigService.findConfig(clazz, code);
			put("config", config);
		} catch (Exception e) {
		}
		return forward();
	}

	public String codejson() {
		code();
		return forward();
	}

	public String saveCode() {
		try {
			String className = get("entityName");
			String code = get("code");
			Class<?> clazz = Class.forName(className);
			List<ClassPropertyConfig> config = populateList(ClassPropertyConfig.class, "classConfig");
			classConfigService.saveConfig(clazz, config, code);
		} catch (Exception e) {
		}
		return redirect("result", "info.save.success");
	}

}
