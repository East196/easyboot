package com.github.east196.core.vo;

public class NameValuePair {
	private String name;// 请求名称
	private String value;// 请求值

	public NameValuePair(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
