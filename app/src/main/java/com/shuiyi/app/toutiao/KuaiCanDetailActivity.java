package com.shuiyi.app.toutiao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hey on 2016/6/19.
 */
@SuppressLint("JavascriptInterface")
public class KuaiCanDetailActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView textView;
    private String kcs_url;
    WebView webView;
    private ImageButton imageButton;
    private int nbb = 2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kuaicandetail);

        //setNeedBackGesture(true);//设置需要手势监听
        Intent intent1 = getIntent();
        kcs_url = "http://www.ishowyou.cc/qdkc/Telduan/apps_WaiMaiDetail.aspx?spid=" + intent1.getStringExtra("id");
        initView();
        initWebView();
        textView.setText(intent1.getStringExtra("mingcheng"));
    }


    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.ss_htmlprogessbar);
        progressBar.setVisibility(View.VISIBLE);
        textView = (TextView) this.findViewById(R.id.txtTitle);
        imageButton = (ImageButton) this.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initWebView() {
        webView = (WebView) findViewById(R.id.wb_details);
        if (!TextUtils.isEmpty(kcs_url)) {
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);//设置可以运行JS脚本
            //settings.setTextZoom(100);//设置页面的文本缩放百分比。默认值是100。
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            settings.setUseWideViewPort(true); //打开页面时， 自适应屏幕??
            settings.setLoadWithOverviewMode(true);//缩小内容以适合屏幕宽度
            settings.setSupportZoom(false);// 用于设置webview放大
            settings.setBuiltInZoomControls(false);
            webView.setBackgroundResource(R.color.transparent);
            webView.setWebChromeClient(new MyWebChromeClient());//设置加载进度
            webView.setWebViewClient(new MyWebViewClient());
            new MyAsnycTask().execute(kcs_url);

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
            webView.loadUrl(kcs_url);
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //调用拨号程序
            if (url.startsWith("mailto:") || url.startsWith("geo:") || url.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            } else {
                return super.shouldOverrideUrlLoading(view, url);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
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
