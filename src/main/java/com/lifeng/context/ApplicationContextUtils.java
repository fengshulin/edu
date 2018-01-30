package com.lifeng.context;

import org.springframework.context.ApplicationContext;

/**
 * 存放spring上下文
 * @author solin
 *
 */
public class ApplicationContextUtils {
	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		ApplicationContextUtils.applicationContext = applicationContext;
	}

}
