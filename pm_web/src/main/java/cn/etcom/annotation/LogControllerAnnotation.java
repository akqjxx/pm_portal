/**
 * 
 */
package cn.etcom.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.etcom.util.enums.ActionEnum;

/**
 * @author liujc
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogControllerAnnotation {
	// 定义注解参数
	String desc() default "";// 描述

	ActionEnum action() default ActionEnum.UNDEFINED;// 操作的类型

}
