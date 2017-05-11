package org.beangle.ems.config.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CProperty {

	/**
	 * 标题
	 * 
	 * @return
	 */
	String label();

	/**
	 * 是否可编辑
	 * 
	 * @return
	 */
	boolean editable() default true;

	/**
	 * 列表是否显示
	 * 
	 * @return
	 */
	boolean listable() default false;

	/**
	 * 排序
	 * 
	 * @return
	 */
	int px() default 0;

	/**
	 * 是否必填
	 * 
	 * @return
	 */
	boolean required() default false;

}
