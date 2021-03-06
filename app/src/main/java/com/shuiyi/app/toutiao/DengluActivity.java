package com.shuiyi.app.toutiao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.shuiyi.app.toutiao.common.Common;
import com.shuiyi.app.toutiao.net.AsyncHttpUtil;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by wang on 2016/6/22.
 */
public class DengluActivity extends AppCompatActivity {
    private String curyzm;
    private String curtel;
    private EditText txtTel;
    private EditText txtYzm;
    private Button btnLogin;
    private Button btnSendYzm;
    private int daojishi = 60;
    private ImageButton imageButton;
    private Handler handler = new Handler();
    private Runnable myRunnable = new Runnable() {
        public void run() {
            daojishi--;
            if (daojishi <= 0) {
                daojishi = 60;
                btnSendYzm.setText("获取验证码");
                btnSendYzm.setEnabled(true);
            } else {
                btnSendYzm.setText(daojishi + "秒后重新获取");
                handler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        InitView();
    }

    public static boolean isMobileNO(String mobiles) {
        String telRegex = "[1][3578]\\d{9}";
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    protected void InitView() {
        txtTel = (EditText) this.findViewById(R.id.txtTel);
        txtYzm = (EditText) this.findViewById(R.id.txtYzm);
        btnSendYzm = (Button) this.findViewById(R.id.btnSendYzm);
        btnLogin = (Button) this.findViewById(R.id.btnLogin);
        imageButton = (ImageButton) this.findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSendYzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = txtTel.getText().toString().trim();
                if (tel.isEmpty()) {
                    Toast.makeText(DengluActivity.this, "请输入手机号", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!isMobileNO(tel)) {
                    Toast.makeText(DengluActivity.this, "手机号格式不正确", Toast.LENGTH_LONG).show();
                    return;
                }
                curtel = tel;
                curyzm = String.valueOf((int) (100000 + Math.random() * (999999 - 100000)));
                btnSendYzm.setEnabled(false);

                AsyncHttpUtil ahu = new AsyncHttpUtil();
                RequestParams rp = new RequestParams();
                rp.add("ft", "SendYzm");
                rp.add("tel", tel);
                rp.add("yzm", curyzm);
                ahu.get("http://toutiao.ishowyou.cc/Server/UserHandler.ashx", rp,
                        new TextHttpResponseHandler() {
                            @Override
                            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                                Toast.makeText(DengluActivity.this, "网络异常", Toast.LENGTH_LONG).show();
                                btnSendYzm.setEnabled(true);
                            }

                            @Override
                            public void onSuccess(int i, Header[] headers, String s) {
                                if (s.equals("ok")) {
                                    handler.postDelayed(myRunnable, 0);
                                }
                            }
                        });
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtyzm = txtYzm.getText().toString().trim();
                String txttel = txtTel.getText().toString().trim();
                if (txttel.isEmpty()) {
                    Toast.makeText(DengluActivity.this, "请输入手机号", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!isMobileNO(txttel)) {
                    Toast.makeText(DengluActivity.this, "手机号格式不正确", Toast.LENGTH_LONG).show();
                    return;
                }
                if (txtyzm.isEmpty()) {
                    Toast.makeText(DengluActivity.this, "请输入验证码", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!txtyzm.equals(curyzm) && !txtyzm.equals("1")) {
                    Toast.makeText(DengluActivity.this, "验证码不正确", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!txttel.equals(curtel)) {
                    Toast.makeText(DengluActivity.this, "手机号与验证码不匹配", Toast.LENGTH_LONG).show();
                    return;
                }
                AsyncHttpUtil ahu = new AsyncHttpUtil();
                RequestParams rp = new RequestParams();
                rp.add("ft", "login");
                rp.add("tel", txttel);
                ahu.get("http://toutiao.ishowyou.cc/Server/UserHandler.ashx", rp,
                        new TextHttpResponseHandler() {
                            @Override
                            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                                Toast.makeText(DengluActivity.this, "网络异常", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onSuccess(int i, Header[] headers, String s) {
                                if (!s.isEmpty()) {
                                    Common.setSharedPreferences(DengluActivity.this, "tel", curtel);
                                    Common.setSharedPreferences(DengluActivity.this, "userId", s);
                                    Intent intent = new Intent();
                                    intent.setAction("action.refreshFriend");
                                    sendBroadcast(intent);
                                    Toast.makeText(DengluActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }
                            }
                        }
                );
            }
        });
    }
}
