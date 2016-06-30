package com.shuiyi.app.toutiao.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Window;
import android.widget.TextView;

import com.shuiyi.app.toutiao.R;

public class BaseActivity extends FragmentActivity {
    protected BaseActivity ctx = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.ctx = this;
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;

    }
    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


    protected MyAppLication myAppLication() {

        MyAppLication myApplication = (MyAppLication) getApplication();

        return myApplication;
    }

    protected int windowWidth() {
        DisplayMetrics dm = getResources().getDisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        return dm.widthPixels;
    }

    protected int windowHeight() {
        DisplayMetrics dm = getResources().getDisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        return dm.heightPixels;
    }

    protected void backDialog(final Activity activity) {
        new AlertDialog.Builder(this)
                .setTitle("确认放弃本轮检测？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    protected void shouyeDialog(final Activity activity) {
        new AlertDialog.Builder(this)
                .setTitle("确认放弃本轮检测？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    protected void ToastDialog(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();
    }


    protected void startActivity(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    protected void startActivity(Class<?> cls) {
        startActivity(cls, null, -1);
    }


    protected void startActivity(Class<?> cls, int anim) {
        startActivity(cls, null, anim);
    }


    protected void startActivity(Class<?> cls, Bundle bundle, int anim) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if (anim == -1) {
            overridePendingTransition(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
        } else {
            overridePendingTransition(0, 0);
        }
    }

    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
//		overridePendingTransition(android.R.anim.slide_in_left,
//				android.R.anim.slide_out_right);
    }


    protected void startActivityForResult(Class<?> cls, int code) {

        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivityForResult(intent, code);

    }

    protected TextView setTitle(String str) {
        TextView titleText = (TextView) findViewById(R.id.title);

        titleText.setText(str);
        return titleText;
    }

/*
	protected TextView setTopFenLei() {
		TextView btnBack = (TextView) findViewById(R.id.top_fenlei);
		btnBack.setVisibility(View.VISIBLE);
		return btnBack;
	}

	protected TextView setTopInput() {
		TextView btnBack = (TextView) findViewById(R.id.top_input);
		btnBack.setVisibility(View.VISIBLE);
		return btnBack;
	}
	protected EditText setTopInputEdit() {
		EditText btnBack = (EditText) findViewById(R.id.top_edinput);
		btnBack.setVisibility(View.VISIBLE);
		return btnBack;
	}

	protected TextView setTopRightStr(String str) {
		TextView btnBack = (TextView) findViewById(R.id.top_rignttv);
		btnBack.setVisibility(View.VISIBLE);
		btnBack.setText(str);
		return btnBack;
	}

	protected ImageView setTopRightImg() {
		ImageView btnBack = (ImageView) findViewById(R.id.top_rigntimg);
		btnBack.setVisibility(View.VISIBLE);
		return btnBack;
	}

	protected ImageView setTopRightImg1() {
		ImageView btnBack = (ImageView) findViewById(R.id.top_rigntimg1);
		btnBack.setVisibility(View.VISIBLE);
		return btnBack;
	}

	protected ImageView setTopRightImg2() {
		ImageView btnBack = (ImageView) findViewById(R.id.top_rigntimg2);
		btnBack.setVisibility(View.VISIBLE);
		return btnBack;
	}

	protected void setTopTitle(String title) {
		TextView imageView = (TextView) findViewById(R.id.top_title);
		if (null != imageView) {
			imageView.setText(title);
		}
	}
	*/
}
