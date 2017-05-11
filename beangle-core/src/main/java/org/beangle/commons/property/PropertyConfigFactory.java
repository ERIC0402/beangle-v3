/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.property;

import java.util.Properties;

/**
 * 配置工厂
 * 
 * @author beangle
 */
public interface PropertyConfigFactory {

	public PropertyConfig getConfig();

	public void reload();

	public void addConfigProvider(Provider provider);

	public interface Provider {
		public Properties getConfig();
	}

	public Object getConfig(String key, String defaultValue);
}
