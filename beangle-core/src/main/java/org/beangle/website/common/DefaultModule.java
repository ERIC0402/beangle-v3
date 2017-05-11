package org.beangle.website.common;

import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;
import org.beangle.website.common.action.FileAction;


/**
 * 
 * @author GOKU
 *
 */
public class DefaultModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		// TODO Auto-generated method stub
		bind(FileAction.class).in(Scope.PROTOTYPE);
	}
}
