package com.squirrel.revenge.utils;

import org.springframework.context.ApplicationContext;

public class SquirrelRevengeUtils {

	private static SquirrelRevengeUtils instance;
	private ApplicationContext ctx;
	
	public static SquirrelRevengeUtils getInstance() {
		if (instance == null) {
			System.out.println("SquirrelRevengeUtils not initialized!!!");
			return null;
		} else {
			return instance;
		}
	}
	
	public SquirrelRevengeUtils(ApplicationContext ctx) {
		this.ctx = ctx;
		instance = this;
	}
	
	public Object getBean(String name) {
		return ctx.getBean(name);
	}
}
