package org.beangle.website.common;

import org.beangle.spring.bind.AbstractBindModule;
import org.beangle.spring.bind.Scope;
import org.beangle.website.common.action.DownloadAction;
import org.beangle.website.common.action.ServiceFlowViewAction;
import org.beangle.website.system.service.impl.DictDataServiceImpl;

public class CommonDefaultModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind(DownloadAction.class,ServiceFlowViewAction.class).in(Scope.SINGLETON);

		bind("dictDataService", DictDataServiceImpl.class);
	}

}
