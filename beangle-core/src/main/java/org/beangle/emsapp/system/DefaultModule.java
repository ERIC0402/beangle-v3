/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.system;

import org.beangle.ems.web.action.LogoutAction;
import org.beangle.emsapp.business.action.LogAction;
import org.beangle.emsapp.business.action.RuleAction;
import org.beangle.emsapp.business.action.RuleParamAction;
import org.beangle.emsapp.dictionary.action.CodeAction;
import org.beangle.emsapp.dictionary.action.CodeMetaAction;
import org.beangle.emsapp.dictionary.action.CodeScriptAction;
import org.beangle.emsapp.portal.action.HomeAction;
import org.beangle.emsapp.portal.action.LoginAction;
import org.beangle.emsapp.system.action.ClassConfigAction;
import org.beangle.emsapp.system.action.FileAction;
import org.beangle.emsapp.system.action.InfoAction;
import org.beangle.emsapp.system.action.PropertyAction;
import org.beangle.emsapp.system.action.SystemConfigAction;
import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;

public final class DefaultModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		// property
		bind(FileAction.class, InfoAction.class, PropertyAction.class, ClassConfigAction.class).in(Scope.PROTOTYPE);
		// home
		bind(LoginAction.class, LogoutAction.class, HomeAction.class).in(Scope.PROTOTYPE);

		bind(LogAction.class, RuleAction.class, RuleParamAction.class).in(Scope.PROTOTYPE);

		bind(CodeAction.class, CodeMetaAction.class, CodeScriptAction.class).in(Scope.PROTOTYPE);

		bind(SystemConfigAction.class).in(Scope.PROTOTYPE);
	}

}
