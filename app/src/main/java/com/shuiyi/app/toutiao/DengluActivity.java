package com.shuiyi.app.toutiao;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shuiyi.app.toutiao.bean.TouTiaoBean;
import com.shuiyi.app.toutiao.net.AsyncHttpUtil;

import org.apache.http.Header;
import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by wang on 2016/6/22.
 */
public class DengluActivity extends AppCompatActivity {
    private int yzm;
    private EditText txtTel;
    private EditText txtYzm;
    private Button btnLogin;
    private Button btnSendYzm;
    private int daojishi = 5;
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
        txtYzm = (EditText) this.findViewById(R.id.txtYzm);
        btnSendYzm = (Button) this.findViewById(R.id.btnSendYzm);
        btnLogin = (Button) this.findViewById(R.id.btnLogin);

        btnSendYzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = txtTel.getText().toString();
                if (!isMobileNO(tel)) {
                    Toast.makeText(DengluActivity.this, "手机号格式不正确", Toast.LENGTH_LONG).show();
                    return;
                }
                btnSendYzm.setEnabled(false);
                yzm = (int) (100000 + Math.random() * (999999 - 100000));
                AsyncHttpUtil ahu = new AsyncHttpUtil();
                RequestParams rp = new RequestParams();
                rp.add("ft", "sms");
                rp.add("tel", tel);
                rp.add("yzm", String.valueOf(yzm));
                ahu.get("http://192.168.1.99:885/Server/UserHandler.ashx", rp, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        if (statusCode != 200) {
                            Toast.makeText(DengluActivity.this, "验证码发送失败，请稍后重试。", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }
}
