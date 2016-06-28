package com.shuiyi.app.toutiao;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.content.Intent;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private TextView jifenUser;
    private String tel;
    private LinearLayout lotJifen;
    private LinearLayout myOrder;

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
        myOrder = (LinearLayout) getActivity().findViewById(R.id.myorder);
        jifenUser = (TextView) getActivity().findViewById(R.id.jifen_user);
        gridView = (GridView) getActivity().findViewById(R.id.gridview);
        scList = new ArrayList<ShangChengBean>();
        scAdapter = new ShangChengAdapter(getActivity(), scList);

        Drawable[] drawable = jifenUser.getCompoundDrawables();
        drawable[0].setBounds(0, 0, 50, 50);
        jifenUser.setCompoundDrawables(drawable[0], drawable[1], drawable[2], drawable[3]);


        AsyncHttpUtil ahu = new AsyncHttpUtil();
        RequestParams rp = new RequestParams();
        rp.add("ft", "get");
        ahu.get("http://toutiao.ishowyou.cc/Server/JiFenShangPinHandler.ashx", rp,
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
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShangChengBean item = (ShangChengBean) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), ShangChengDetailActivity.class);
                intent.putExtra("id", item.getId());
                startActivity(intent);
            }
        });
    }
}
