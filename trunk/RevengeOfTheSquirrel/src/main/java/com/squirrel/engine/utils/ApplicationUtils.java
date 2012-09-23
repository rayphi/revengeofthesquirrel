package com.squirrel.engine.utils;

import org.springframework.context.ApplicationContext;

/**
 * 
 * @author Shane
 *
 */
public class ApplicationUtils {

	private static ApplicationUtils instance;
	private ApplicationContext ctx;
	
	public static ApplicationUtils getInstance() {
		if (instance == null) {
			System.out.println("SquirrelRevengeUtils not initialized!!!");
			return null;
		} else {
			return instance;
		}
	}
	
	public ApplicationUtils(ApplicationContext ctx) {
		this.ctx = ctx;
		instance = this;
	}
	
	public Object getBean(String name) {
		return ctx.getBean(name);
	}
}
