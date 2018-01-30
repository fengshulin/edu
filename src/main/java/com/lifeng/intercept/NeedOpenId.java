package com.lifeng.intercept;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 微信拦截器注解
 * 
 * @author solin
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedOpenId {
	boolean needOpenId() default false;

	boolean subscribeFlag() default false;

	boolean oauth2Flag() default false;

}
