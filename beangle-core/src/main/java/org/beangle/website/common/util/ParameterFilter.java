package org.beangle.website.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ParameterFilter {

	public final static Map<String, String> sqlmap = new HashMap<String, String>();

	public final static Map<String, String> htmlmap = new HashMap<String, String>();

	static {

		sqlmap.put("'", "");

		sqlmap.put("%", "");
		
		sqlmap.put("--", "");
		
		sqlmap.put("\"", "");
		
		sqlmap.put(":", "");
		

	}

	static {

		htmlmap.put("<", "&lt;");

		htmlmap.put(">", "&gt;");

		htmlmap.put("\"", "&quot;");

		htmlmap.put("?", "&thetasym;");

		htmlmap.put("'", "&acute;");
		
		htmlmap.put("%", "");
		
		htmlmap.put("&", "");
		

	}

	public static String sqlStringfilter(String sqlstr) {

		if (sqlstr == null) {

			return null;

		}

		String sql = sqlstr;//sqlstr.replaceAll("\\s", sqlstr);

		Set<String> mapkey = sqlmap.keySet();

		for (String string : mapkey) {

			sql = sql.replace(string, sqlmap.get(string));

		}

		return sql;

	}

	public static String htmlStringfilter(String htmlstr) {

		if (htmlstr == null) {

			return null;

		}

		String html = htmlstr.replaceAll("\\s", "");

		Set<String> mapkey = htmlmap.keySet();

		for (String string : mapkey) {

			html = html.replace(string, htmlmap.get(string));

		}

		return html;

	}

}
