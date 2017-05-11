/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer.io;

/**
 * 数据读取类
 * 
 * @author beangle
 */
public interface Reader {

	/**
	 * 读取数据
	 * 
	 * @return
	 */
	public Object read();

	/**
	 * 返回读取类型的格式
	 * 
	 * @return
	 */
	public String getFormat();

	public int getTotal();
}
