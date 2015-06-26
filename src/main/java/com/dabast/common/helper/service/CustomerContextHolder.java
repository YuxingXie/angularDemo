package com.dabast.common.helper.service;

import org.springframework.util.Assert;

public class CustomerContextHolder {
	private static final ThreadLocal contextHolder = new ThreadLocal();

	public static void setCustomerType(String customerType) {
	   Assert.notNull(customerType, "customerType cannot be null");
	   contextHolder.set(customerType);
	}

	public static String getCustomerType() {
	   return (String) contextHolder.get();
	}

	public static void clearCustomerType() {
	   contextHolder.remove();
	}

}
