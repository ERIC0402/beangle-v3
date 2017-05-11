package org.beangle.emsapp.system.action;

import org.apache.commons.lang.StringUtils;
import org.beangle.ems.security.model.SystemConfig;
import org.beangle.model.Entity;
import org.beangle.website.common.action.FileAction;

public class SystemConfigAction extends FileAction {

	@Override
	protected String getEntityName() {
		// TODO Auto-generated method stub
		return SystemConfig.class.getName();
	}
	
	@Override
	protected String getShortName() {
		// TODO Auto-generated method stub
		return "sc";
	}
	
	@Override
	public String index() throws Exception {
		SystemConfig sc = entityDao.get(SystemConfig.class, 1L);
		if(sc == null){
			sc = new SystemConfig();
		}
		put("sc",sc);
		put("time",System.currentTimeMillis());
		return forward("form");
	}
	
	@Override
	protected String saveAndForward(Entity<?> entity) {
		SystemConfig sc = (SystemConfig) entity;
		try {
			String iconPath = get("iconPath");
			if(StringUtils.isNotEmpty(iconPath)){
				sc.setHeadImage(saveIcon(iconPath));
				delete(iconPath);
			}
			entityDao.saveOrUpdate(sc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return redirect("index", "保存成功");
	}
}
