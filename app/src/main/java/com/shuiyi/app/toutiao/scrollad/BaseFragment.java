package com.shuiyi.app.toutiao.scrollad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shuiyi.app.toutiao.net.AsyncHttpUtil;

public class BaseFragment extends Fragment {
	protected AlertLoadingDialog loadingDialog = null;
	protected boolean isCreated = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		loadingDialog = new AlertLoadingDialog(getActivity());
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		isCreated = true;
		super.onCreate(savedInstanceState);
	}

	protected void showLoading() {
		if (loadingDialog == null) {
			loadingDialog = new AlertLoadingDialog(getActivity());
			loadingDialog.show();
		} else {
			loadingDialog.show();
		}
	}

	protected void hideLoading() {
		if (loadingDialog != null) {
			loadingDialog.hide();
			loadingDialog = null;
			AsyncHttpUtil.getInstance().client.cancelAllRequests(true);
		}
	}

	/**
	 * 通过Class跳转界面 *
	 */
	protected void startActivity(Activity activity, Class<?> cls) {
		startActivity(activity, cls, null);
	}

	/**
	 * 含有Bundle通过Class跳转界面 *
	 */
	protected void startActivity(Activity activity, Class<?> cls, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(activity, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
		activity.overridePendingTransition(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
	}

	/**
	 * 含有Bundle通过Class跳转界面 *
	 */
	protected void startActivity(Activity activity, Class<?> cls,
								 Bundle bundle, int isanim) {
		Intent intent = new Intent();
		intent.setClass(activity, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
		activity.overridePendingTransition(isanim, isanim);
	}

}
