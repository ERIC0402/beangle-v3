package org.beangle.emsapp.security.action;

import org.beangle.ems.security.model.SubSystem;
import org.beangle.ems.web.action.SecurityActionSupport;
/**
 * 子系统管理
 * @author GOKU
 *
 */
public class SubSystemAction extends SecurityActionSupport {

	@Override
	protected String getEntityName() {
		return SubSystem.class.getName();
	}
}
