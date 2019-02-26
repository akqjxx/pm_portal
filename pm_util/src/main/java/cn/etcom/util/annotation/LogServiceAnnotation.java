package cn.etcom.util.annotation;

import java.lang.annotation.*;

/**
 * @Description: 定义注解，拦截service
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface LogServiceAnnotation {
    String desc() default "" ;
}