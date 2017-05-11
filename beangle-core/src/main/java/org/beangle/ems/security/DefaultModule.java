/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security;

import org.beangle.ems.security.nav.service.MenuServiceImpl;
import org.beangle.ems.security.restrict.service.CsvDataResolver;
import org.beangle.ems.security.restrict.service.IdentifierDataResolver;
import org.beangle.ems.security.restrict.service.OqlDataProvider;
import org.beangle.ems.security.service.AuthorityServiceImpl;
import org.beangle.ems.security.service.CacheableAuthorityManager;
import org.beangle.ems.security.service.DaoUserDetailServiceImpl;
import org.beangle.ems.security.service.UserServiceImpl;
import org.beangle.ems.security.service.impl.GroupServiceImpl;
import org.beangle.ems.security.session.service.CategoryProfileServiceImpl;
import org.beangle.security.core.session.impl.DbSessionRegistry;
import org.beangle.spring.bind.AbstractBindModule;

/**
 * 权限缺省服务配置
 * @author beangle
 * @version $Id: DefaultModule.java Jun 18, 2011 10:21:05 AM beangle $
 */
public class DefaultModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind("groupService", GroupServiceImpl.class);
		bind("userService", UserServiceImpl.class);
		bind("authorityService", AuthorityServiceImpl.class);
		bind("menuService", MenuServiceImpl.class);
		bind("userDetailService",DaoUserDetailServiceImpl.class);
		bind("authorityManager",CacheableAuthorityManager.class);
		bind(CategoryProfileServiceImpl.class).shortName();
		
		bind("sessionRegistry",DbSessionRegistry.class);
		bind(IdentifierDataResolver.class,CsvDataResolver.class,OqlDataProvider.class).shortName();
	}

}
