package com.shuiyi.app.toutiao;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shuiyi.app.toutiao.adapter.JiFenOrderAdapter;
import com.shuiyi.app.toutiao.adapter.KuaiCanAdapter;
import com.shuiyi.app.toutiao.bean.JiFenOrderBean;
import com.shuiyi.app.toutiao.bean.KuaiCanBean;
import com.shuiyi.app.toutiao.common.Common;
import com.shuiyi.app.toutiao.net.AsyncHttpUtil;
import com.shuiyi.app.toutiao.view.CustomListView;

import org.apache.http.Header;
import org.json.JSONArray;

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
        ahu.get("http://toutiao.ishowyou.cc/Server/JiFenOrderHandler.ashx", rp, jhrh);
    }

    private void findView() {
        listView = (CustomListView) findViewById(R.id.list_item);
        jifenList = new ArrayList<JiFenOrderBean>();
        listAdapter = new JiFenOrderAdapter(JiFenOrderActivity.this, jifenList);
        JsonHttpResponseHandler kc = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Gson gson = new Gson();
                ArrayList<JiFenOrderBean> itemList = gson.fromJson(response.toString(), new TypeToken<ArrayList<JiFenOrderBean>>() {
                }.getType());
                if(itemList.size()<10){
                    listView.setCanLoadMore(false);
                }
                jifenList.addAll(itemList);
                listView.setAdapter(listAdapter);
            }
        };
        GetHttpData(kc);
        listView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                jifenList.clear();
                pageIndex = 1;
                JsonHttpResponseHandler kc = new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        Gson gson = new Gson();
                        ArrayList<JiFenOrderBean> itemList = gson.fromJson(response.toString(), new TypeToken<ArrayList<JiFenOrderBean>>() {
                        }.getType());
                        if(itemList.size()<10){
                            listView.setCanLoadMore(false);
                        }
                        jifenList.addAll(itemList);
                        listView.onRefreshComplete();
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
                        if(itemList.size()<10){
                            listView.setCanLoadMore(false);
                        }
                        jifenList.addAll(itemList);
                        listView.onLoadMoreComplete();
                    }
                };
                GetHttpData(kc);
            }
        });
    }
}
