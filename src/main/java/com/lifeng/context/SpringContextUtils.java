package com.lifeng.context;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringContextUtils {

	private SpringContextUtils() {
	}

	public static class SpringApplicactionContext implements ApplicationContextAware {
		Logger logger = LoggerFactory.getLogger(SpringContextUtils.class);
		private static ApplicationContext context;

		public static ApplicationContext getContext() {
			return context;
		}

		@Override
		public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
			context = applicationContext;
		}
	}

	/**
	 * 获取spring上下文
	 * 
	 * @return
	 */
	public static ApplicationContext getCurrentContext() {
		return SpringApplicactionContext.getContext();
	}

	/**
	 * 根据web请求获取spring上下文
	 * 
	 * @param request
	 * @return
	 */
	public static ApplicationContext getApplicationContext(HttpServletRequest request) {
		ServletContext context = request.getSession().getServletContext();
		return WebApplicationContextUtils.getRequiredWebApplicationContext(context);
	}
}
