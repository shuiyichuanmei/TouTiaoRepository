package com.shuiyi.app.toutiao;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shuiyi.app.toutiao.bean.JiFenOrderBean;
import com.shuiyi.app.toutiao.common.Common;
import com.shuiyi.app.toutiao.common.QRCodeUtil;
import com.shuiyi.app.toutiao.net.AsyncHttpUtil;
import com.shuiyi.app.toutiao.view.MyWebView;
import com.shuiyi.app.toutiao.view.ScrollInterface;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

@SuppressLint("JavascriptInterface")
public class TouTiaoDetailActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private String news_url;
    private int jifen;
    private String jiFenId;
    private String tid;
    private String tel;
    private ImageButton backButton, fenxiangButton;
    private Button btnGetJiFen;
    private LinearLayout linearLayout;
    private Dialog alertDialog;
    private String title;
    private String fxUrl;
    MyWebView webView;
    int daojishi = 5;
    private Handler handler = new Handler();
    private Runnable myRunnable = new Runnable() {
        public void run() {
            if (daojishi <= 0) {
                daojishi = 5;
                btnGetJiFen.setEnabled(true);
                btnGetJiFen.setText("领取" + jifen + "礼券");
                btnGetJiFen.setBackgroundResource(R.drawable.denglu_btn_click);
                btnGetJiFen.setTextColor(Color.parseColor("#ffffff"));
            } else {
                btnGetJiFen.setText(daojishi + "秒后可领取" + jifen + "礼券");
                handler.postDelayed(this, 1000);
                btnGetJiFen.setTextColor(Color.parseColor("#999999"));
                btnGetJiFen.setBackgroundResource(R.drawable.btn_false);
            }
            daojishi--;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toutiaodetail);
        //setNeedBackGesture(true);//设置需要手势监听

        Intent intent1 = getIntent();
        title = intent1.getStringExtra("title");
        tid = intent1.getStringExtra("id");
        news_url = "http://toutiao.ishowyou.cc/appdetail.aspx?id=" + tid;
        fxUrl= "http://toutiao.ishowyou.cc/WebDetail.aspx?id=" + tid;
        tel = Common.getSharedPreferences(TouTiaoDetailActivity.this, "tel");

        initView();
        initWebView();
    }

    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.ss_htmlprogessbar);
        progressBar.setVisibility(View.VISIBLE);
        backButton = (ImageButton) this.findViewById(R.id.imageButton);
        linearLayout = (LinearLayout) this.findViewById(R.id.first);
        fenxiangButton = (ImageButton) this.findViewById(R.id.imageButton2);

        fenxiangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View root = TouTiaoDetailActivity.this.getLayoutInflater().inflate(R.layout.send_wx, null);
                Button pyBtn = (Button) root.findViewById(R.id.pyBtn);
                pyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Common.WXFenxiang(TouTiaoDetailActivity.this, fxUrl, title, "会话");

                    }
                });
                Button pyqBtn = (Button) root.findViewById(R.id.pyqBtn);
                Resources res = getResources();
                Drawable img_on, img_off;
                img_off = res.getDrawable(R.drawable.fx_pyq_icon);
                img_off.setBounds(0, 0, 120, 120);
                pyqBtn.setCompoundDrawables(null, img_off, null, null);
                img_on = res.getDrawable(R.drawable.fx_wxpy_icon);
                img_on.setBounds(0, 0, 120, 120);
                pyBtn.setCompoundDrawables(null, img_on, null, null);
                pyqBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Common.WXFenxiang(TouTiaoDetailActivity.this, fxUrl, title, "朋友圈");
                    }
                });
                Button falesBtn = (Button) root.findViewById(R.id.falesBtn);
                falesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });


                alertDialog = new AlertDialog.Builder(TouTiaoDetailActivity.this)
                        .setView(root).create();
                Window w = alertDialog.getWindow();
                WindowManager.LayoutParams lp = w.getAttributes();
                w.setGravity(Gravity.BOTTOM);
                alertDialog.show();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnGetJiFen = (Button) this.findViewById(R.id.btnGetJiFen);
        btnGetJiFen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Common.isDenglu(TouTiaoDetailActivity.this)) {
                    Intent intent = new Intent(TouTiaoDetailActivity.this, DengluActivity.class);
                    startActivity(intent);
                    return;
                }
                tel = Common.getSharedPreferences(TouTiaoDetailActivity.this, "tel");
                AsyncHttpUtil ahu = new AsyncHttpUtil();
                RequestParams rp = new RequestParams();
                rp.add("ft", "add");
                rp.add("tel", tel);
                rp.add("type", "头条广告");
                rp.add("jfid", jiFenId);
                ahu.get("http://toutiao.ishowyou.cc/Server/JiFenHandler.ashx", rp, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            String success = response.getString("success");
                            String msg = response.getString("msg");
                            if (success.equals("true")) {
                                btnGetJiFen.setEnabled(false);
                                btnGetJiFen.setText("已领取" + jifen + "礼券");
                                btnGetJiFen.setTextColor(Color.parseColor("#999999"));
                                btnGetJiFen.setBackgroundResource(R.drawable.btn_false);
                                Intent intent = new Intent();
                                intent.setAction("action.refreshFriend");
                                sendBroadcast(intent);
                                Toast.makeText(TouTiaoDetailActivity.this, "恭喜您获得了" + jifen + "礼券", Toast.LENGTH_LONG).show();

                            } else if (success.equals("chongfu")) {
                                btnGetJiFen.setEnabled(false);
                                btnGetJiFen.setText("您之前领取过该条信息的礼券");
                                btnGetJiFen.setTextColor(Color.parseColor("#999999"));
                                btnGetJiFen.setBackgroundResource(R.drawable.btn_false);
                                Toast.makeText(TouTiaoDetailActivity.this, "您之前领取过该条信息的礼券", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception ex) {
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Toast.makeText(TouTiaoDetailActivity.this, "网络异常", Toast.LENGTH_LONG).show();
                    }
                });
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
        public void onPageFinished(final WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            AsyncHttpUtil ahu = new AsyncHttpUtil();
            RequestParams rp = new RequestParams();
            rp.add("ft", "get");
            rp.add("tid", tid);
            rp.add("type", "头条广告");
            rp.add("tel", tel);
            ahu.get("http://toutiao.ishowyou.cc/Server/JiFenHandler.ashx", rp, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        String success = response.getString("success");
                        if (success.equals("true")) {
                            jifen = response.getInt("jifennum");
                            jiFenId = response.getString("jifenid");
                            btnGetJiFen.setVisibility(View.VISIBLE);
                            handler.postDelayed(myRunnable, 0);
                        } else if (success.equals("chongfu")) {
                            jifen = response.getInt("jifennum");
                            jiFenId = response.getString("jifenid");
                            btnGetJiFen.setVisibility(View.VISIBLE);
                            btnGetJiFen.setEnabled(false);
                            btnGetJiFen.setText("已领取" + jifen + "礼券");
                            btnGetJiFen.setTextColor(Color.parseColor("#999999"));
                            btnGetJiFen.setBackgroundResource(R.drawable.btn_false);

                        }
                    } catch (Exception ex) {
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Toast.makeText(TouTiaoDetailActivity.this, "网络异常", Toast.LENGTH_LONG).show();
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
