package com.shuiyi.app.toutiao;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.qrcode.encoder.QRCode;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shuiyi.app.toutiao.adapter.JiFenOrderAdapter;
import com.shuiyi.app.toutiao.adapter.KuaiCanAdapter;
import com.shuiyi.app.toutiao.bean.JiFenOrderBean;
import com.shuiyi.app.toutiao.bean.KuaiCanBean;
import com.shuiyi.app.toutiao.bean.ShangChengBean;
import com.shuiyi.app.toutiao.common.Common;
import com.shuiyi.app.toutiao.common.QRCodeUtil;
import com.shuiyi.app.toutiao.net.AsyncHttpUtil;
import com.shuiyi.app.toutiao.view.CustomListView;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Hey on 2016/6/28.
 */
public class JiFenOrderActivity extends AppCompatActivity {
    private CustomListView listView;
    private JiFenOrderAdapter listAdapter = null;
    private ArrayList<JiFenOrderBean> jifenList = null;
    private int pageIndex = 1;
    private String tel;
    private ImageButton imageButton;
    private LinearLayout line;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jifenorder);
        tel = Common.getSharedPreferences(this, "tel");
        findView();
    }

    private void GetHttpData(JsonHttpResponseHandler jhrh) {
        AsyncHttpUtil ahu = new AsyncHttpUtil();
        RequestParams rp = new RequestParams();
        rp.add("page", String.valueOf(pageIndex));
        rp.add("tel", tel);
        rp.add("ft", "GetList");
        ahu.get("http://toutiao.ishowyou.cc/Server/JiFenOrderHandler.ashx", rp, jhrh);
    }

    private void findView() {
        line = (LinearLayout) findViewById(R.id.line);
        listView = (CustomListView) findViewById(R.id.list_item);
        jifenList = new ArrayList<JiFenOrderBean>();
        listAdapter = new JiFenOrderAdapter(JiFenOrderActivity.this, jifenList);
        imageButton = (ImageButton) this.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        JsonHttpResponseHandler kc = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Gson gson = new Gson();
                ArrayList<JiFenOrderBean> itemList = gson.fromJson(response.toString(), new TypeToken<ArrayList<JiFenOrderBean>>() {
                }.getType());
                if (itemList.size() != 0) {
                    if (itemList.size() < 10) {
                        listView.setCanLoadMore(false);
                    }
                    jifenList.addAll(itemList);
                    listView.setAdapter(listAdapter);
                } else {
                    line.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(JiFenOrderActivity.this, "网络异常", Toast.LENGTH_LONG).show();
            }
        };
        GetHttpData(kc);
        listView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                jifenList.clear();
                pageIndex = 1;
                listView.setCanLoadMore(true);
                JsonHttpResponseHandler kc = new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        Gson gson = new Gson();
                        ArrayList<JiFenOrderBean> itemList = gson.fromJson(response.toString(), new TypeToken<ArrayList<JiFenOrderBean>>() {
                        }.getType());
                        if (itemList.size() < 10) {
                            listView.setCanLoadMore(false);
                        }
                        jifenList.addAll(itemList);
                        listView.onRefreshComplete();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Toast.makeText(JiFenOrderActivity.this, "网络异常", Toast.LENGTH_LONG).show();
                    }
                };
                GetHttpData(kc);
            }
        });
        listView.setOnLoadListener(new CustomListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageIndex++;
                JsonHttpResponseHandler kc = new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        Gson gson = new Gson();
                        ArrayList<JiFenOrderBean> itemList = gson.fromJson(response.toString(), new TypeToken<ArrayList<JiFenOrderBean>>() {
                        }.getType());
                        if (itemList.size() < 10) {
                            listView.setCanLoadMore(false);
                        }
                        jifenList.addAll(itemList);
                        listView.onLoadMoreComplete();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Toast.makeText(JiFenOrderActivity.this, "网络异常", Toast.LENGTH_LONG).show();
                    }
                };
                GetHttpData(kc);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JiFenOrderBean item = (JiFenOrderBean) parent.getItemAtPosition(position);
                String url = "http://toutiao.ishowyou.cc/jifen/houtai/OrderErweimaYanzheng.aspx?oid=" + item.getId();
                Bitmap bitmap = QRCodeUtil.createQRImage(url, 300, 300);
                View root = JiFenOrderActivity.this.getLayoutInflater().inflate(R.layout.ordererweima, null);
                ImageView imgerweima = (ImageView) root.findViewById(R.id.imgerweima);
                imgerweima.setImageBitmap(bitmap);

                new AlertDialog.Builder(JiFenOrderActivity.this)
                        .setView(root)
                        .setTitle(item.getMingcheng())
                        .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });
    }
}
