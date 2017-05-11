/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.test.spring;

import org.beangle.model.persist.EntityDao;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * 基础服务的测试用例
 * 
 * @author beangle 2005-9-8
 */
@ContextConfiguration("classpath:applicationContext-test.xml")
public class SpringTestCase extends AbstractTestNGSpringContextTests {

	public EntityDao entityDao;

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
