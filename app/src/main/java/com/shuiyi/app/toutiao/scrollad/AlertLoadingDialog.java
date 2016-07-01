package com.shuiyi.app.toutiao.scrollad;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuiyi.app.toutiao.R;

public class AlertLoadingDialog {
	private Dialog loadingDialog = null;
	private TextView tipTextView = null;
	private AnimationDrawable animationDrawable = null;

	public AlertLoadingDialog setMessage(String msg) {
		if (tipTextView != null) {
			tipTextView.setText(msg);// 设置加载信息
		}
		return this;

	}

	public AlertLoadingDialog(Context context) {
		if (loadingDialog == null) {
			View v = View.inflate(context, R.layout.view_alertprogressdialog,
					null);// 得到加载view
			LinearLayout layout = (LinearLayout) v.findViewById(R.id.linear);// 加载布局
			// main.xml中的ImageView
			ImageView spaceshipImage = (ImageView) v
					.findViewById(R.id.loading_iv);
			animationDrawable = (AnimationDrawable) spaceshipImage
					.getBackground();
			tipTextView = (TextView) v.findViewById(R.id.message);// 提示文字
			loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
			loadingDialog.setCanceledOnTouchOutside(false);
			loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		}

	}

	public AlertLoadingDialog setCancelable(boolean cancel) {
		if (loadingDialog != null) {
			loadingDialog.setCancelable(cancel);// 不可以用“返回键”取消
		}
		return this;
	}

	public void show() {
		if (loadingDialog != null && animationDrawable != null) {
			animationDrawable.start();
			loadingDialog.show();
		}
	}

	public void showLoadingCanNotCancelable() {
		if (loadingDialog != null && animationDrawable != null) {
			animationDrawable.start();
			loadingDialog.setCancelable(false);
			loadingDialog.show();
		}
	}

	public void hide() {
		if (loadingDialog != null && animationDrawable != null) {
			animationDrawable.stop();
			loadingDialog.cancel();
		}
	}

}
