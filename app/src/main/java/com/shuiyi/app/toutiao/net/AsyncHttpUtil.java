package com.shuiyi.app.toutiao.net;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * 访问网络封装的工具类
 * 
 * @author admin
 * 
 */
public class AsyncHttpUtil {

	public AsyncHttpClient client = null;
	public static AsyncHttpUtil instance = null;

	public static AsyncHttpUtil getInstance() {
		instance = new AsyncHttpUtil();
		return instance;
	}

	public AsyncHttpUtil() {
		client = new AsyncHttpClient(); // 实例话对象
		client.setTimeout(60000); // 设置链接超时，如果不设置，默认为10s
		// client.addHeader("content-encoding", "gzip");
		// client.addHeader("Content-Type", "text/html;charset=UTF-8");
	}

	/**
	 * get 用一个完整url获取一个string对象
	 * 
	 * @param urlString
	 * @param res
	 */
	public void get(String urlString, AsyncHttpResponseHandler res) {
		client.get(urlString, res);
	}

	/**
	 * get url里面带参数
	 * 
	 * @param urlString
	 * @param params
	 * @param res
	 */
	public void get(String urlString, RequestParams params,
			AsyncHttpResponseHandler res) {
		client.get(urlString, params, res);
	}

	/**
	 * get 不带参数，获取json对象或者数组
	 * 
	 * @param urlString
	 * @param res
	 */
	public void get(String urlString, JsonHttpResponseHandler res) {
		client.get(urlString, res);
	}

	/**
	 * get 带参数，获取json对象或者数组
	 * 
	 * @param urlString
	 * @param params
	 * @param res
	 */
	public void get(String urlString, RequestParams params,
			JsonHttpResponseHandler res) {
		client.get(urlString, params, res);
	}

	/**
	 * get 下载数据使用，会返回byte数据
	 * 
	 * @param uString
	 * @param bHandler
	 */
	public void get(String uString, BinaryHttpResponseHandler bHandler) {
		client.get(uString, bHandler);
	}

	public void get(String uString, TextHttpResponseHandler responseHandler) {
		client.get(uString, responseHandler);
	}

	/**
	 * post 带参数，获取json对象或者数组
	 * 
	 * @param url
	 * @param params
	 * @param res
	 */
	public void post(String url, RequestParams params,
			JsonHttpResponseHandler res) {
		client.post(url, params, res);
	}

	public void post(String url, RequestParams params,
			TextHttpResponseHandler responseHandler) {
		client.post(url, params, responseHandler);
	}

	/**
	 * post url里面带参数
	 * 
	 * @param urlString
	 * @param params
	 * @param res
	 */
	public void post(String urlString, RequestParams params,
			AsyncHttpResponseHandler res) {
		client.post(urlString, params, res);
	}
/*
	public void getHttpData(final String url, RequestParams paramMap,
			final GetDataInterface<String> dataInterface) {
		LogUtil.Mylog(url + "?" + paramMap.toString());
		// paramMap.setContentEncoding("UTF-8");
		AsyncHttpUtil.getInstance().post(url, paramMap,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						if (statusCode == 200 && response != null) {
							if (response.optString("code").equals(
									Constant.RESULT_CODE_OK)) {
								String resultStr = response.optString("result");
								LogUtil.Mylog(resultStr);
								dataInterface.returnData(
										Constant.RESULT_CODE_OK, resultStr);
							} else if (response.optString("code").equals(
									Constant.RESULT_CODE_NOREFRESH)) {
								dataInterface.returnErrorData(
										Constant.RESULT_CODE_ERROR, "数据未更新!");
							} else {
								String resultStr = response.optString("result");
								dataInterface.returnErrorData(
										Constant.RESULT_CODE_ERROR, resultStr);
							}
						} else {
							dataInterface.returnErrorData(
									Constant.RESULT_CODE_ERROR, "获取数据失败!"
											+ "statusCode=" + statusCode);
						}
					}

					@Override
					public void onCancel() {
						dataInterface.returnErrorData();
						super.onCancel();
					}

					@Override
					public void onFinish() {
						dataInterface.returnErrorData();
						super.onFinish();
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Throwable throwable) {
						dataInterface.returnErrorData(
								Constant.RESULT_CODE_ERROR, "获取数据失败!"
										+ "statusCode=" + statusCode + ":");
						LogUtil.Mylog(responseString);
					}

				});
	}

	public void getHttpDataForGet(final String url, RequestParams paramMap,
			final GetDataInterface<String> dataInterface) {
		LogUtil.Mylog(url + "?" + paramMap.toString());
		// paramMap.setContentEncoding("UTF-8");
		AsyncHttpUtil.getInstance().get(url, paramMap,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						if (statusCode == 200 && response != null) {
							if (response.optString("code").equals(
									Constant.RESULT_CODE_OK)) {
								String resultStr = response.optString("result");
								LogUtil.Mylog(resultStr);
								dataInterface.returnData(
										Constant.RESULT_CODE_OK, resultStr);
							} else if (response.optString("code").equals(
									Constant.RESULT_CODE_NOREFRESH)) {
								dataInterface.returnErrorData(
										Constant.RESULT_CODE_ERROR, "数据未更新!");
							} else {
								String resultStr = response.optString("result");
								dataInterface.returnErrorData(
										Constant.RESULT_CODE_ERROR, resultStr);
							}
						} else {
							dataInterface.returnErrorData(
									Constant.RESULT_CODE_ERROR, "获取数据失败!"
											+ "statusCode=" + statusCode);
						}
					}

					@Override
					public void onCancel() {
						dataInterface.returnErrorData();
						super.onCancel();
					}

					@Override
					public void onFinish() {
						dataInterface.returnErrorData();
						super.onFinish();
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Throwable throwable) {
						dataInterface.returnErrorData(
								Constant.RESULT_CODE_ERROR, "获取数据失败!"
										+ "statusCode=" + statusCode + ":");
						LogUtil.Mylog(responseString);
					}

				});
	}
*/
}
