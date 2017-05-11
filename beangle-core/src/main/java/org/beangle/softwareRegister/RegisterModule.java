package org.beangle.softwareRegister;

import org.beangle.softwareRegister.action.ActiveAction;
import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;

/**
 * 
 * @author GOKU
 * 
 */
public class RegisterModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind(ActiveAction.class).in(Scope.PROTOTYPE);
	}

}
