package org.beangle.website.system.action;

import java.util.List;

import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.Entity;
import org.beangle.website.system.model.DictType;
import org.beangle.website.system.model.SystemArgument;


/**
 * 系统参数
 * @author GOKU
 *
 */
public class SystemArgumentAction extends SecurityActionSupport {

	public String edit(){
		SystemArgument sa = entityDao.get(SystemArgument.class, 1L);
		if(sa == null){
			sa = new SystemArgument();
		}
		put("systemArgument",sa);
		return forward();
	}
	
	protected String saveAndForward(Entity<?> entity) {
		SystemArgument systemArgument = (SystemArgument) entity;
		entityDao.saveOrUpdate(systemArgument);
		return redirect("edit", "info.save.success");
	}
	
	
	@Override
	protected String getEntityName() {
		return SystemArgument.class.getName();
	}
}
