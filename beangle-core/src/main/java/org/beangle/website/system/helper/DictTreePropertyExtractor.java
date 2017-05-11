/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.website.system.helper;

import org.beangle.commons.text.TextResource;
import org.beangle.ems.security.nav.Menu;
import org.beangle.model.transfer.exporter.DefaultPropertyExtractor;
import org.beangle.website.system.model.DictTree;

/**
 * @author beangle
 * @version $Id: ResourcePropertyExtractor.java Jul 17, 2011 9:41:20 PM beangle $
 */
public class DictTreePropertyExtractor extends DefaultPropertyExtractor {

	public DictTreePropertyExtractor() {
		super();
	}

	public DictTreePropertyExtractor(TextResource textResource) {
		super(textResource);
	}

	public Object getPropertyValue(Object target, String property) throws Exception {
		DictTree tree = (DictTree) target;
		if ("enabled".equals(property)) {
			return tree.isEnabled() ? "有效" : "无效";
		} else{
			return super.getPropertyValue(target, property);
		}
	}

}
