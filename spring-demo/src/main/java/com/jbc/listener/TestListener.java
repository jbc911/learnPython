package com.jbc.listener;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class TestListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("监听初始化");
		ServletContext context = sce.getServletContext();
		Enumeration<String> names = context.getInitParameterNames();
		while (names.hasMoreElements()) {
			String nextElement = names.nextElement();
			System.out.println(nextElement + "-" + context.getInitParameter(nextElement));
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("监听销毁");
	}

}
