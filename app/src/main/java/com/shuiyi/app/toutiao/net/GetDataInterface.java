package com.shuiyi.app.toutiao.net;

public interface GetDataInterface<T> {
	public void returnData(String code, T data);
	public void returnErrorData(String code, String errorString);
	public void returnErrorData();
}
