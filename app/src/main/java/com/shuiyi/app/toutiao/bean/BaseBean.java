package com.shuiyi.app.toutiao.bean;

import java.io.Serializable;

public class BaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String id = "";
	protected String islock = "0";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIslock() {
		return islock;
	}

	public void setIslock(String islock) {
		this.islock = islock;
	}

}
