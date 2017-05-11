/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.spring.bind;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang.Validate;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.platform.util.FileUtils;
import org.beangle.softwareRegister.service.SecurityCheck;
import org.beangle.website.common.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.util.ClassUtils;

public class DefinitionBindRegistry implements BindRegistry {

	private static final Logger logger = LoggerFactory.getLogger(DefinitionBindRegistry.class);

	protected Map<String, Class<?>> nameTypes = CollectUtils.newHashMap();

	protected Map<Class<?>, List<String>> typeNames = CollectUtils.newHashMap();

	public DefinitionBindRegistry(BeanDefinitionRegistry registry) {
//		boolean con;
//		do{
//			con = activeSoftWare();
//			if(!con){
//				System.out.println("机器码："+SecurityCheck.getSequence());
//				Scanner cin = new Scanner(System.in);
//				System.out.println("请再次输入软件注册码:");
//				String licence = cin.nextLine();
//				//FileUtils.createAndWriteFile("/WEB-INF/classes/licences/beangle.licence", licence);
//				try {
//					FileUtil.writeFile(new File(FileUtil.FILE_PATH+"licences/beangle.licence"), licence);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}else{
//				break;
//			}
//		}while(true);
		
		for (String name : registry.getBeanDefinitionNames()) {
			BeanDefinition bd = registry.getBeanDefinition(name);
			if (bd.isAbstract()) continue;
			// find classname
			String className = bd.getBeanClassName();
			if (null == className) {
				String parentName = bd.getParentName();
				if (null == parentName) continue;
				else {
					BeanDefinition parentDef = registry.getBeanDefinition(parentName);
					className = parentDef.getBeanClassName();
				}
			}
			if (null == className) continue;

			try {
				Class<?> beanClass = ClassUtils.forName(className, ClassUtils.getDefaultClassLoader());
				if (FactoryBean.class.isAssignableFrom(beanClass)) {
					register(beanClass, "&" + name);
					PropertyValue pv = bd.getPropertyValues().getPropertyValue("target");
					if (null == pv) {
						Class<?> artifactClass = ((FactoryBean<?>) beanClass.newInstance()).getObjectType();
						if (null != artifactClass) register(artifactClass, name);
					} else {
						if (pv.getValue() instanceof BeanDefinitionHolder) {
							String nestedClassName = ((BeanDefinitionHolder) pv.getValue())
									.getBeanDefinition().getBeanClassName();
							if (null != nestedClassName) {
								register(
										ClassUtils.forName(nestedClassName,
												ClassUtils.getDefaultClassLoader()), name);
							}
						}
					}
				} else {
					register(beanClass, name);
				}
			} catch (Exception e) {
				logger.error("class not found", e);
				continue;
			}
		}
	}
	
	private boolean activeSoftWare(){
		boolean isActive = true;
		String licence = "";
		//String licenceDir = DefinitionBindRegistry.class.getClassLoader().getResource("").getPath();
		String filePath = FileUtil.FILE_PATH + "licences/beangle.licence";
		if (FileUtil.existsFile(filePath)) {
			List licences = FileUtil.getTextFileContent(filePath);
			if (licences.size() > 0) {
				licence = licences.get(0).toString();
			}
			
		} else {
			Scanner cin = new Scanner(System.in);
			System.out.println("请输入软件注册码：");
		    licence = cin.nextLine();
			//FileUtils.createAndWriteFile("/WEB-INF/classes/licences/beangle.licence", licence);
		    try {
				FileUtil.writeFile(new File(filePath), licence);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("注册码：" + licence);
		SecurityCheck.check();
		if (FileUtil.existsFile(FileUtil.FILE_PATH + "licences/failure.licence")) {
			System.out.println("您的注册码不正确，激活失败！");
			isActive = false;
		} else {
			System.out.println("激活成功，感谢您的购买！");
			isActive = true;
		}
		return isActive;
	}

	public List<String> getBeanNames(Class<?> type) {
		if (typeNames.containsKey(type)) { return typeNames.get(type); }
		List<String> names = CollectUtils.newArrayList();
		for (Map.Entry<String, Class<?>> entry : nameTypes.entrySet()) {
			if (type.isAssignableFrom(entry.getValue())) {
				names.add(entry.getKey());
			}
		}
		typeNames.put(type, names);
		return names;
	}

	public Class<?> getBeanType(String beanName) {
		return nameTypes.get(beanName);
	}

	public boolean contains(String beanName) {
		return nameTypes.containsKey(beanName);
	}

	public void register(Class<?> type, String name) {
		Validate.notNull(type, "name's class is null");
		nameTypes.put(name, type);
	}

}
