package com.dabast.common.web.taglib;

import javax.servlet.jsp.tagext.BodyTagSupport;

public class SetTag extends BodyTagSupport{
	private String entityClass;
	private String name;
	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}
	public void setName(String name) {
		this.name = name;
	}
}
