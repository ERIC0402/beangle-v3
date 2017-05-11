package org.beangle.emsapp.security.helper;

import org.beangle.commons.text.TextResource;
import org.beangle.ems.security.model.ServiceFlow;
import org.beangle.model.transfer.exporter.DefaultPropertyExtractor;

public class ServiceFlowPropertyExtractor extends DefaultPropertyExtractor{
	
	public ServiceFlowPropertyExtractor(){
		super();
	}
	
	public ServiceFlowPropertyExtractor(TextResource textResource){
		super(textResource);
	}
	
	public Object getPropertyValue(Object target, String property) throws Exception {
		ServiceFlow flow = (ServiceFlow) target;
		if ("enabled".equals(property)) {
			return flow.isEnabled() ? "有效" : "无效";
		} else{
			return super.getPropertyValue(target, property);
		}
	}

}
