package org.beangle.test.junit;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * junit测试类基本服务类
 * @author GZW
 *
 */
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-context.xml"})
public class BaseJunit4Test {
	
}

