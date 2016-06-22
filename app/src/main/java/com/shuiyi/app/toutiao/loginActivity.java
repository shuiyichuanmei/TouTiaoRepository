package com.shuiyi.app.toutiao;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shuiyi.app.toutiao.common.Common;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wang on 2016/6/22.
 */
public class LoginActivity extends AppCompatActivity {
    private int yzm;
    private EditText txtTel;
    private EditText txtYzm;
    private Button btnLogin;
    private Button btnSendYzm;
    private int daojishi = 3;
    private Handler handler = new Handler();
    private Runnable myRunnable = new Runnable() {
        public void run() {
            daojishi--;
            if (daojishi <= 0) {
                daojishi = 3;
                btnSendYzm.setText("获取验证码");
                btnSendYzm.setEnabled(true);
            } else {
                btnSendYzm.setText(daojishi + "后");
                handler.postDelayed(this, 1000);
            }
        }
    };
    private Handler handlerSms = new Handler();
    private Runnable myRunnableSms = new Runnable() {
        public void run() {
            String tel = txtTel.getText().toString();
            //Common.SendSms(tel, "SMS_5016100", "{\"code\":\"" + yzm + "\"}");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        InitView();
        System.out.println("-----------------------sdfdsf-");

    }

    /**
     * 验证手机格式
     */
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
                    Toast.makeText(LoginActivity.this, "手机号格式不正确", Toast.LENGTH_LONG).show();
                    return;
                }
                btnSendYzm.setEnabled(false);
                yzm = (int) (100000 + Math.random() * (999999 - 100000));
                //handlerSms.postDelayed(myRunnableSms, 0);
                new Thread(myRunnableSms).start();
                //handler.postDelayed(myRunnable, 0);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }
}
