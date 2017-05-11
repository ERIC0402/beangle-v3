package org.beangle.website.system;

import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;
import org.beangle.website.common.action.FileAction;
import org.beangle.website.system.action.DictDataAction;
import org.beangle.website.system.action.DictTreeAction;
import org.beangle.website.system.action.DictTypeAction;
import org.beangle.website.system.action.IndexAction;
import org.beangle.website.system.action.SelectUserAction;
import org.beangle.website.system.action.SystemArgumentAction;
import org.beangle.website.system.action.ZntzAction;
import org.beangle.website.system.action.ZnxxAction;
import org.beangle.website.system.action.ZnxxhfAction;
import org.beangle.website.system.service.impl.DictDataServiceImpl;
import org.beangle.website.system.service.impl.DictTreeServiceImpl;
import org.beangle.website.system.service.impl.SystemLogServiceImpl;


/**
 * 
 * @author GOKU
 *
 */
public class DefaultModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind(IndexAction.class,DictTypeAction.class,DictDataAction.class, DictTreeAction.class,SystemArgumentAction.class,
				ZntzAction.class,ZnxxAction.class,SelectUserAction.class,ZnxxhfAction.class).in(Scope.PROTOTYPE);
		bind(FileAction.class).in(Scope.PROTOTYPE);
		
		bind("systemLogService", SystemLogServiceImpl.class);
		bind("dictTreeService", DictTreeServiceImpl.class);
	}

}
