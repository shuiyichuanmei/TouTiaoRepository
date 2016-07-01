package com.shuiyi.app.toutiao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.shuiyi.app.toutiao.bean.ShangChengBean;
import com.shuiyi.app.toutiao.common.Common;
import com.shuiyi.app.toutiao.net.AsyncHttpUtil;
import com.shuiyi.app.toutiao.view.MyWebView;
import com.shuiyi.app.toutiao.view.ScrollInterface;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

@SuppressLint("JavascriptInterface")
public class ShangChengDetailActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private String news_url;
    private String spid;
    private String tel;
    private ImageButton backButton;
    private Button btnDuihuan;
    MyWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shangchengdetail);
        //setNeedBackGesture(true);//设置需要手势监听

        Intent intent1 = getIntent();
        spid = intent1.getStringExtra("id");
        news_url = "http://toutiao.ishowyou.cc/jifen/apps_detail.aspx?id=" + spid;
        tel = Common.getSharedPreferences(ShangChengDetailActivity.this, "tel");

        initView();
        initWebView();
    }

    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.ss_htmlprogessbar);
        progressBar.setVisibility(View.VISIBLE);
        backButton = (ImageButton) this.findViewById(R.id.imageButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnDuihuan = (Button) this.findViewById(R.id.btnDuihuan);
        btnDuihuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Common.isDenglu(ShangChengDetailActivity.this)) {
                    Intent intent = new Intent(ShangChengDetailActivity.this, DengluActivity.class);
                    startActivity(intent);
                    return;
                }
                AsyncHttpUtil ahu = new AsyncHttpUtil();
                RequestParams rp = new RequestParams();
                rp.add("ft", "duihuan");
                rp.add("tel", tel);
                rp.add("spid", spid);
                ahu.get("http://toutiao.ishowyou.cc/Server/JiFenOrderHandler.ashx", rp,
                        new TextHttpResponseHandler() {
                            @Override
                            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                                Toast.makeText(ShangChengDetailActivity.this, "网络异常", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onSuccess(int i, Header[] headers, String s) {
                                if (s.equals("ok")) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ShangChengDetailActivity.this);
                                    builder.setMessage("兑换成功,请尽快领取。\n可在礼物查询中查看兑换记录。");
                                    builder.setTitle("兑换信息");
                                    builder.setIcon(R.drawable.sc_liwu_icon);
                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    builder.create().show();
                                    Intent intent = new Intent();
                                    intent.setAction("action.refreshFriend");
                                    sendBroadcast(intent);
                                } else if (s.equals("shuliangbuzu")) {
                                    Toast.makeText(ShangChengDetailActivity.this, "商品数量不足", Toast.LENGTH_LONG).show();
                                    webView.reload();
                                    btnDuihuan.setText("商品数量不足");
                                    btnDuihuan.setTextColor(Color.parseColor("#999999"));
                                    btnDuihuan.setBackgroundResource(R.drawable.btn_false);
                                    btnDuihuan.setEnabled(false);
                                } else if (s.equals("jifenbuzu")) {
                                    Toast.makeText(ShangChengDetailActivity.this, "您的积分不足", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                );
            }
        });
    }

    private void initWebView() {
        webView = (MyWebView) findViewById(R.id.wb_details);
        if (!TextUtils.isEmpty(news_url)) {
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);//设置可以运行JS脚本
            //settings.setTextZoom(100);//设置页面的文本缩放百分比。默认值是100。
            settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
            settings.setUseWideViewPort(true); //打开页面时， 自适应屏幕??
            settings.setLoadWithOverviewMode(true);//缩小内容以适合屏幕宽度
            settings.setSupportZoom(false);// 用于设置webview放大
            settings.setBuiltInZoomControls(false);
            webView.setBackgroundResource(R.color.transparent);
            webView.setWebChromeClient(new MyWebChromeClient());//设置加载进度
            webView.setWebViewClient(new MyWebViewClient());
            new MyAsnycTask().execute(news_url);
        }
    }

    private class MyAsnycTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            String data = "";
            return data;
        }

        @Override
        protected void onPostExecute(String data) {
            webView.loadUrl(news_url);
        }
    }

    // 监听
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);

            AsyncHttpUtil ahu = new AsyncHttpUtil();
            RequestParams rp = new RequestParams();
            rp.add("ft", "get");
            rp.add("id", spid);
            ahu.get("http://toutiao.ishowyou.cc/Server/JiFenShangPinHandler.ashx", rp,
                    new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Gson gson = new Gson();
                            ShangChengBean item = gson.fromJson(response.toString(), new TypeToken<ShangChengBean>() {
                            }.getType());
                            btnDuihuan.setVisibility(View.VISIBLE);
                            if (item.getShuliang() <= 0) {
                                btnDuihuan.setText("商品数量不足");
                                btnDuihuan.setTextColor(Color.parseColor("#999999"));
                                btnDuihuan.setBackgroundResource(R.drawable.btn_false);
                                btnDuihuan.setEnabled(false);
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(ShangChengDetailActivity.this, "网络异常", Toast.LENGTH_LONG).show();
                        }
                    });
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            progressBar.setVisibility(View.GONE);
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress != 100) {
                progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
