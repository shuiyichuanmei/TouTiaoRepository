package com.shuiyi.app.toutiao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by wang on 2016/6/24.
 */
public class MyWebView extends WebView {

    ScrollInterface mt;
    public MyWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public MyWebView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //Log.e("hhah",""+l+" "+t+" "+oldl+" "+oldt);
        mt.onSChanged(l, t, oldl, oldt);
    }


    public void setOnCustomScroolChangeListener(ScrollInterface t){
        this.mt=t;
    }
}


