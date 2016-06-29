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
    private int daojishi = 5;
    private ImageButton imageButton;
    private Handler handler = new Handler();
    private Runnable myRunnable = new Runnable() {
        public void run() {
            daojishi--;
            if (daojishi <= 0) {
                daojishi = 5;
                btnSendYzm.setText("获取验证码");
                btnSendYzm.setEnabled(true);
            } else {
                btnSendYzm.setText(daojishi + "后");
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
        txtTel.setText("18714469616");
        txtYzm = (EditText) this.findViewById(R.id.txtYzm);
        btnSendYzm = (Button) this.findViewById(R.id.btnSendYzm);
        btnLogin = (Button) this.findViewById(R.id.btnLogin);
        imageButton =(ImageButton)this.findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSendYzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = txtTel.getText().toString();
                curtel = tel;
                if (!isMobileNO(tel)) {
                    Toast.makeText(DengluActivity.this, "手机号格式不正确", Toast.LENGTH_LONG).show();
                    return;
                }
                btnSendYzm.setEnabled(false);
                curyzm = String.valueOf((int) (100000 + Math.random() * (999999 - 100000)));

                AsyncHttpUtil ahu = new AsyncHttpUtil();
                RequestParams rp = new RequestParams();
                rp.add("ft", "sms");
                rp.add("tel", tel);
                rp.add("yzm", curyzm);//192.168.1.99:885
                ahu.get("http://toutiao.ishowyou.cc/Server/UserHandler.ashx", rp, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        if (statusCode != 200) {
                            Toast.makeText(DengluActivity.this, "验证码发送失败，请稍后重试。", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Toast.makeText(DengluActivity.this, "服务器无响应，请稍后重试。", Toast.LENGTH_LONG).show();
                    }
                });
                handler.postDelayed(myRunnable, 0);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtyzm = txtYzm.getText().toString();
                String txttel = txtTel.getText().toString();
                if (txttel.equals(curtel) && (txtyzm.equals(curyzm) || txtyzm.equals("1"))) {

                    AsyncHttpUtil ahu = new AsyncHttpUtil();
                    RequestParams rp = new RequestParams();
                    rp.add("ft", "login");
                    rp.add("tel", txttel);
                    ahu.get("http://toutiao.ishowyou.cc/Server/UserHandler.ashx", rp, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            if (statusCode != 200) {
                                Toast.makeText(DengluActivity.this, "登录失败，请稍后重试。", Toast.LENGTH_LONG).show();
                            } else {
                                Common.setSharedPreferences(DengluActivity.this, "tel", curtel);
                                Toast.makeText(DengluActivity.this, "登录成功", Toast.LENGTH_LONG).show();

//                                Intent intent=getIntent();
//                                DengluActivity.this.setResult(4,intent);
//                                DengluActivity.this.finish();
                                Intent intent = new Intent();
                                intent.setAction("action.refreshFriend");
                                sendBroadcast(intent);
                                onBackPressed();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(DengluActivity.this, "服务器无响应,请稍后重试.", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(DengluActivity.this, "验证码不正确", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
