/**
 * ContextLoader.java 11:55:24 AM Feb 7, 2012
 *
 * Copyright(c) 2000-2012 HC360.COM, All Rights Reserved.
 */
package org.cc.web.context;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.cc.core.common.ScanUtils;
import org.cc.ioc.IocContext;
import org.cc.web.WebConfig;
import org.cc.web.WebException;
import org.cc.web.WebMethod;
import org.cc.web.annotation.Controller;
import org.cc.web.annotation.RequestMapping;
import org.cc.web.annotation.ResponseBody;

/**
 * 初始化web环境
 * 
 * @author dixingxing
 * @date Feb 7, 2012
 */
public class ContextListener implements ServletContextListener {
	private static final Logger LOG = Logger.getLogger(ContextListener.class);

	public void contextDestroyed(ServletContextEvent arg0) {
		LOG.debug("销毁servletContext!");
	}

	public void contextInitialized(ServletContextEvent arg0) {
		LOG.debug("初始化servletContext!");
		ScanUtils helper = new ScanUtils(true, true, null);

		Set<Class<?>> calssList = helper.getPackageAllClasses(WebConfig.getControllerLocation(), true);
		
		try {
			for (Class<?> clazz : calssList) {
				if (clazz.isAnnotationPresent(Controller.class)) {
					Object o  = IocContext.get(clazz);
					if(o == null) {
						o = clazz.newInstance();
					}
					addMappings(o);
				}
			}
		} catch (Exception e) {
			throw new WebException("解析controller时出错", e);
		}
	}

	/**
	 * 增加映射关系到WebContext
	 * 
	 * @param clazz
	 * @return
	 */
	private static List<WebMethod> addMappings(Object handler) {
		Class<?> clazz = handler.getClass();
		List<WebMethod> list = new ArrayList<WebMethod>();
		Method[] methods = clazz.getDeclaredMethods();

		String[] urlPathMain = null;
		// 是否定义了请求的路径
		if (clazz.isAnnotationPresent(RequestMapping.class)) {
			RequestMapping rm = clazz.getAnnotation(RequestMapping.class);
			urlPathMain = rm.value();
		}
		
		for (Method m : methods) {
			if (m.isAnnotationPresent(RequestMapping.class)) {
				RequestMapping rm = m.getAnnotation(RequestMapping.class);
				WebMethod webMethod = new WebMethod();
				webMethod.setHandler(handler);
				webMethod.setMethod(m);
				webMethod.setUrlPathMain(urlPathMain);
				webMethod.setUrlPath(rm.value());
				webMethod.setRequestMethod(rm.method());
				webMethod.setResponseBody(m.isAnnotationPresent(ResponseBody.class));
				LOG.debug("初始化url映射 - " + clazz.getName() + "."
						+ m.getName() + ":" + webMethod);
				WebContext.addMapping(webMethod);
			}
		}

		return list;
	}

}