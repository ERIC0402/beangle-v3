/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer.exporter;

import org.beangle.model.transfer.Transfer;
import org.beangle.model.transfer.io.Writer;

/**
 * 数据导出转换器
 * 
 * @author beangle
 */
public interface Exporter extends Transfer {

	public void setContext(Context context);

	public Context getContext();

	public void setWriter(Writer writer);

	public Writer getWriter();
}
