package com.shuiyi.app.toutiao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shuiyi.app.toutiao.common.Common;
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
    private ImageButton backButton;
    private Button btnGetJiFen;
    MyWebView webView;
    int daojishi = 3;
    private Handler handler = new Handler();
    private Runnable myRunnable = new Runnable() {
        public void run() {
            if (daojishi <= 0) {
                daojishi = 3;
                btnGetJiFen.setEnabled(true);
                btnGetJiFen.setText("领取" + jifen + "积分");
            } else {
                btnGetJiFen.setText("领取" + jifen + "积分(" + daojishi + ")");
                handler.postDelayed(this, 1000);
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
        tid = intent1.getStringExtra("id");
        news_url = "http://toutiao.ishowyou.cc/appdetail.aspx?id=" + tid;
        tel = Common.getSharedPreferences(TouTiaoDetailActivity.this, "tel");

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
                ahu.get("http://192.168.31.109:88/Server/JiFenHandler.ashx", rp, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        if (statusCode != 200) {
                            Toast.makeText(TouTiaoDetailActivity.this, "服务器无响应，请稍后重试。", Toast.LENGTH_LONG).show();
                            return;
                        }
                        try {
                            String success = response.getString("success");
                            String msg = response.getString("msg");
                            if (success.equals("true")) {
                                btnGetJiFen.setEnabled(false);
                                btnGetJiFen.setText("已领取");
                            } else {
                                Toast.makeText(TouTiaoDetailActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception ex) {
                        }
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
            //webView.addJavascriptInterface(new JavascriptInterface(getApplicationContext()),"imagelistner");// 添加js交互接口类，并起别名 imagelistner
            webView.setWebChromeClient(new MyWebChromeClient());//设置加载进度
            webView.setWebViewClient(new MyWebViewClient());
            new MyAsnycTask().execute(news_url);
        }
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//    }

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

    // 注入js函数监听
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
        webView.loadUrl("javascript:(function(){"
                + "var objs = document.getElementsByTagName(\"img\");"
                + "var imgurl=''; " + "for(var i=0;i<objs.length;i++)  " + "{"
                + "imgurl+=objs[i].src+',';"
                + "    objs[i].onclick=function()  " + "    {  "
                + "        window.imagelistner.openImage(imgurl);  "
                + "    }  " + "}" + "})()");
    }

    // js通信接口
    public class JavascriptInterface {


        private Context context;

        public JavascriptInterface(Context context) {
            this.context = context;
        }

        public void openImage(String img) {
            System.out.println(img);
            //
            String[] imgs = img.split(",");
            ArrayList<String> imgsUrl = new ArrayList<String>();
            for (String s : imgs) {
                imgsUrl.add(s);
                //Log.i("图片的URL>>>>>>>>>>>>>>>>>>>>>>>", s);
            }
            Intent intent = new Intent();
            intent.putStringArrayListExtra("infos", imgsUrl);
            //intent.setClass(context, ImageShowActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
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
            // html加载完成之后，添加监听图片的点击js函数
            //addImageClickListner();
            progressBar.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            AsyncHttpUtil ahu = new AsyncHttpUtil();
            RequestParams rp = new RequestParams();
            rp.add("ft", "get");
            rp.add("tid", tid);
            rp.add("type", "头条广告");
            rp.add("tel", tel);
            ahu.get("http://192.168.31.109:88/Server/JiFenHandler.ashx", rp, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    if (statusCode != 200) {
                        Toast.makeText(TouTiaoDetailActivity.this, "服务器无响应，请稍后重试。", Toast.LENGTH_LONG).show();
                        return;
                    }
                    try {
                        String success = response.getString("success");
                        if (success.equals("true")) {
                            jifen = response.getInt("jifennum");
                            jiFenId = response.getString("jifenid");
                            btnGetJiFen.setVisibility(View.VISIBLE);
                            handler.postDelayed(myRunnable, 0);
                        } else if (success.equals("chongfu")) {
                            btnGetJiFen.setVisibility(View.VISIBLE);
                            btnGetJiFen.setEnabled(false);
                            btnGetJiFen.setText("已领取");
                        }
                    } catch (Exception ex) {
                    }
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
