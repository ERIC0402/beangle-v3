package org.beangle.commons.lang;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

public class ClassScan {

	public static List<Class> scan(String packAge, Class<?> annotation) {
		return scan(packAge, annotation, null);
	}
	
	public static  List<Class> scan(String packAge, ClassScanInvoker invoker) {
		return scan(packAge, null, invoker);
	}

	public static  List<Class> scan(String packAge, Class<?> annotation,  ClassScanInvoker invoker) {
		List<Class> list = new ArrayList<Class>();
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		String ENTITY_CLASS_RESOURCE_PATTERN = "/**/*.class";
		try {
			String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(packAge) + ENTITY_CLASS_RESOURCE_PATTERN;
			Resource[] resources = resourcePatternResolver.getResources(pattern);
			MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
			for (Resource resource : resources) {
				if (resource.isReadable()) {
					MetadataReader reader = readerFactory.getMetadataReader(resource);
					String className = reader.getClassMetadata().getClassName();
					Class<?> cls = Class.forName(className);
					if(invoker == null  || invoker.accept(cls)){
						list.add(cls);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public interface ClassScanInvoker {

		public boolean accept(Class<?> cls);
	}

}
