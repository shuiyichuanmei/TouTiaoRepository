package com.shuiyi.app.toutiao;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shuiyi.app.toutiao.adapter.KuaiCanAdapter;
import com.shuiyi.app.toutiao.adapter.ShangChengAdapter;
import com.shuiyi.app.toutiao.bean.KuaiCanBean;
import com.shuiyi.app.toutiao.bean.ShangChengBean;
import com.shuiyi.app.toutiao.net.AsyncHttpUtil;
import com.shuiyi.app.toutiao.view.CustomListView;

import org.apache.http.Header;
import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Hey on 2016/6/25.
 */
public class ShangChengFragment extends Fragment {
    private GridView gridView;
    private ShangChengAdapter scAdapter = null;
    private ArrayList<ShangChengBean> scList = null;
    private int pageIndex = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shangcheng, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findView();
    }

    private void findView() {
        gridView = (GridView) getActivity().findViewById(R.id.gridview);
        scList = new ArrayList<ShangChengBean>();
        scAdapter = new ShangChengAdapter(getActivity(), scList);

        AsyncHttpUtil ahu = new AsyncHttpUtil();
        RequestParams rp = new RequestParams();
        rp.add("ft", "get");
        ahu.get("http://192.168.31.109:88/Server/JiFenShangPinHandler.ashx", rp,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        Gson gson = new Gson();
                        ArrayList<ShangChengBean> itemList = gson.fromJson(response.toString(), new TypeToken<ArrayList<ShangChengBean>>() {
                        }.getType());
                        scList.addAll(itemList);
                        gridView.setAdapter(scAdapter);
                    }
                });
    }
}