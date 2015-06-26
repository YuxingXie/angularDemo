package com.dabast.common.helper.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

@Component
public class ProjectContext implements ServletContextAware, ApplicationContextAware {

	private static ApplicationContext applicationContext;
	private static ServletContext servletContext;
//	private static ThreadLocal<SysUser> local = new ThreadLocal<SysUser>();
//
//	public static void setLoginUser(SysUser user) {
//		local.set(user);
//	}
//
//	public static SysUser getLoginUser() {
//		return local.get();
//	}


	@Override
	public void setServletContext(ServletContext sc) {
		ProjectContext.servletContext = sc;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ProjectContext.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}

	private static void checkApplicationContext() {
		// To change body of created methods use File | Settings | File
		// Templates.
		if (applicationContext == null) {
			throw new IllegalStateException("applicatinContext未注入");
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}
}
