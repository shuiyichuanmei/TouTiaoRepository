package com.shuiyi.app.toutiao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.ArrayList;


/**
 * Created by wang on 2016/6/7.
 */
@SuppressLint("JavascriptInterface")
public class TouTiaoDetail extends AppCompatActivity {
    private ProgressBar progressBar;
    private String news_url;
    private String news_title;
    private String news_source;
    private String news_date;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toutiaodetail);
        //setNeedBackGesture(true);//设置需要手势监听
        getData();
        initView();
        initWebView();
    }

    private void getData() {
        //news = (NewsEntity) getIntent().getSerializableExtra("news");
        news_url ="http://toutiao.ishowyou.cc/detail.aspx?id=9aeeb5e5-b0d4-47bf-b8e4-6b58c4dd8a88";
        news_title ="dfdfdf";
        news_source = "sdfsdfsdf";
        news_date ="dfsdfsdf";
    }

    private void initWebView() {
        webView = (WebView)findViewById(R.id.wb_details);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        if (!TextUtils.isEmpty(news_url)) {
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);//设置可以运行JS脚本
			settings.setTextZoom(300);//Sets the text zoom of the page in percent. The default is 100.
            settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			settings.setUseWideViewPort(true); //打开页面时， 自适应屏幕
			settings.setLoadWithOverviewMode(true);//打开页面时， 自适应屏幕
            settings.setSupportZoom(false);// 用于设置webview放大
            settings.setBuiltInZoomControls(false);
            webView.setBackgroundResource(R.color.transparent);
            // 添加js交互接口类，并起别名 imagelistner
            webView.addJavascriptInterface(new JavascriptInterface(getApplicationContext()),"imagelistner");
            webView.setWebChromeClient(new MyWebChromeClient());
            webView.setWebViewClient(new MyWebViewClient());
            new MyAsnycTask().execute(news_url, news_title, news_source + " " +news_date);
        }
    }

    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.ss_htmlprogessbar);
        progressBar.setVisibility(View.VISIBLE);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//    }

    private class MyAsnycTask extends AsyncTask<String, String,String>{

        @Override
        protected String doInBackground(String... urls) {
            String data="<div id=\"divneirong\" class=\"tuwen_content\">\n" +
                    "                    <p><br></p><p><img src=\"http://p3.pstatp.com/large/7210005eed0ff6b7752\" img_width=\"461\" img_height=\"303\" alt=\"比亚迪的一个数字堵住所有喷国产车的人的嘴\" onerror=\"javascript:errorimg.call(this);\"></p><p><img src=\"http://p3.pstatp.com/large/7210005eedbc878a6bf\" img_width=\"611\" img_height=\"408\" alt=\"比亚迪的一个数字堵住所有喷国产车的人的嘴\" onerror=\"javascript:errorimg.call(this);\"></p><p>“542”，对，就是这么一个简单的阿拉伯数字，可以堵住所有喷国产车的人的嘴，因为这个数字背后凝聚了一个巨大的变革。是中国汽车工业弯道超车的机遇，打破了传统的燃油发动机技术，以电池电力化解能源危机。</p><p><img src=\"http://p3.pstatp.com/large/778000311e3bd397c38\" img_width=\"520\" img_height=\"359\" alt=\"比亚迪的一个数字堵住所有喷国产车的人的嘴\" onerror=\"javascript:errorimg.call(this);\"></p><p>5：代表比亚迪车型百公里加速在5秒以内，打破豪车专属；</p><p>4：代表全时电四驱，比传统的燃油四驱更有劲，更安全；</p><p>2：百公里综合油耗2L以内，打破SUV“油老虎”的弊端。</p><p><img src=\"http://p3.pstatp.com/large/7780003133641cfd1a1\" img_width=\"728\" img_height=\"456\" alt=\"比亚迪的一个数字堵住所有喷国产车的人的嘴\" onerror=\"javascript:errorimg.call(this);\"></p><p><strong>世界上，还有第二个汽车品牌能做到这个的性能指数吗？</strong>所以，不要再喷国产车了，现在已经不是那个看外国人脸色的年代了。</p><p>之前，总是有人笑话吐槽比亚迪插电混动车型，现在再回过头来看看，哪个品牌没有在研发这样的车型？就连一向固执坚持做普锐斯的丰田也开始了插电混动的研发，你们还有什么资格吐槽国产车落后？</p><p><img src=\"http://p3.pstatp.com/large/778000311eafcab1ddb\" img_width=\"500\" img_height=\"280\" alt=\"比亚迪的一个数字堵住所有喷国产车的人的嘴\" onerror=\"javascript:errorimg.call(this);\"></p><p><img src=\"http://p3.pstatp.com/large/778000313032add097b\" img_width=\"597\" img_height=\"477\" alt=\"比亚迪的一个数字堵住所有喷国产车的人的嘴\" onerror=\"javascript:errorimg.call(this);\"></p><p><img src=\"http://p3.pstatp.com/large/778000311ee0a519628\" img_width=\"500\" img_height=\"277\" alt=\"比亚迪的一个数字堵住所有喷国产车的人的嘴\" onerror=\"javascript:errorimg.call(this);\"></p><p><img src=\"http://p3.pstatp.com/large/778000311ef4071082a\" img_width=\"500\" img_height=\"265\" alt=\"比亚迪的一个数字堵住所有喷国产车的人的嘴\" onerror=\"javascript:errorimg.call(this);\"></p><p><img src=\"http://p2.pstatp.com/large/7210005ee1c7c46cec2\" img_width=\"500\" img_height=\"280\" alt=\"比亚迪的一个数字堵住所有喷国产车的人的嘴\" onerror=\"javascript:errorimg.call(this);\"></p><p><img src=\"http://p3.pstatp.com/large/7780003128733c24624\" img_width=\"882\" img_height=\"445\" alt=\"比亚迪的一个数字堵住所有喷国产车的人的嘴\" onerror=\"javascript:errorimg.call(this);\"></p><p>双模混动，两种行驶模式（到现在还有好多人都不了解什么是双模），无论是纯电动还是油电混动，动力都巨大！还有人担心纯电动模式下的动力不行，其实，这是一种错误的认识，大功率高转速的电机带来的动力，绝对不低于燃油车发动机的动力！上图的动力参数就可以看出！</p><p><img src=\"http://p1.pstatp.com/large/7210005eea4fd4e4c65\" img_width=\"536\" img_height=\"266\" alt=\"比亚迪的一个数字堵住所有喷国产车的人的嘴\" onerror=\"javascript:errorimg.call(this);\"></p><p>“542”，是一个新时代的开始，同时也是一个旧时代的结束！</p><p>不管你们看不看好，比亚迪，他都在引领一个新技术的变革！</p><p><br></p></div>";//NewsDetailsService.getNewsDetails(urls[0],urls[1],urls[2]);
            return data;
        }

        @Override
        protected void onPostExecute(String data) {
            webView.loadDataWithBaseURL (null, data, "text/html", "utf-8",null);
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
            addImageClickListner();
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
            // TODO Auto-generated method stub
            if(newProgress != 100){
                progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
