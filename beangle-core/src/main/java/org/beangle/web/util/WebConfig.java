package org.beangle.web.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class WebConfig {
	
	public static final String IDSTAR_OPEN = "idstar.open";
	public static final String INDEX_URL = "index.url";

	private static Properties props = new Properties();
	static {
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("system.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public WebConfig() {
	}

	public static String get(String key) {
		return props.getProperty(key);
	}

	public static void set(String key, String value) {
		props.setProperty(key, value);
	}
}
