package org.beangle.security.idstar.util;

import java.io.File;

import org.beangle.web.util.WebConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wiscom.is.IdentityFactory;
import com.wiscom.is.IdentityManager;

public class IdstarUtils {
	
	public static Logger log = LoggerFactory.getLogger(IdstarUtils.class);
	private static IdentityManager im;
	
	public static IdentityManager getIdentityManager() {
		if(!"1".equals(WebConfig.get(WebConfig.IDSTAR_OPEN))){
			return null;
		}
		if(im == null){
			try {
				log.info("IdstarUtils.getIdentityManager start");
				String is_config = IdstarUtils.class.getClassLoader().getResource("client.properties").getPath();
				is_config = new File(is_config).getAbsolutePath();
				IdentityFactory factory = IdentityFactory.createFactory(is_config);
				im = factory.getIdentityManager();
				log.info("IdstarUtils.getIdentityManager end");
			} catch (Exception e) {
				return null;
			}
		}
		return im;
	}
}
