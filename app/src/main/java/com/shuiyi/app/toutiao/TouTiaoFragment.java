package com.shuiyi.app.toutiao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shuiyi.app.toutiao.adapter.TouTiaoAdapter;
import com.shuiyi.app.toutiao.bean.TouTiaoBean;
import com.shuiyi.app.toutiao.net.AsyncHttpUtil;
import com.shuiyi.app.toutiao.view.CustomListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by wang on 2016/6/7.
 */
public class TouTiaoFragment extends Fragment {
    private CustomListView listView;
    private TouTiaoAdapter ttAdapter = null;
    private ArrayList<TouTiaoBean> ttList = null;
    private int pageIndex = 1;
    private String typeid = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.toutiao, container, false);
        typeid = getArguments().getString("typeid");
        findView(view);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
//            ttList.clear();
//            pageIndex = 1;
//            TouTiaoJsonHttpResponseHandler tt = new TouTiaoJsonHttpResponseHandler() {
//                @Override
//                public void OnSuccess(ArrayList<TouTiaoBean> ttlist) {
//                    ttList.addAll(ttlist);
//                }
//            };
//            addData(tt);
        }
    }

    private void addData(JsonHttpResponseHandler jhrh) {
        AsyncHttpUtil ahu = new AsyncHttpUtil();
        RequestParams rp = new RequestParams();
        rp.add("page", String.valueOf(pageIndex));
        rp.add("typeid", typeid);
        ahu.get("http://toutiao.ishowyou.cc/Server/NewsHandler.ashx", rp, jhrh);
    }


    private void findView(View view) {
        listView = (CustomListView) view.findViewById(R.id.listView);
        ttList = new ArrayList<TouTiaoBean>();
        ttAdapter = new TouTiaoAdapter(getActivity(), ttList);
        JsonHttpResponseHandler jhrh = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<TouTiaoBean>>() {
                }.getType();
                ArrayList<TouTiaoBean> itemList = gson.fromJson(response.toString(), type);
                ttList.addAll(itemList);
                listView.setAdapter(ttAdapter);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_LONG).show();
            }
        };
        addData(jhrh);


        listView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ttList.clear();
                pageIndex = 1;
                listView.setCanLoadMore(true);
                JsonHttpResponseHandler jhrh = new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<TouTiaoBean>>() {
                        }.getType();
                        ArrayList<TouTiaoBean> itemList = gson.fromJson(response.toString(), type);
                        ttList.addAll(itemList);
                        listView.onRefreshComplete();
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_LONG).show();
                    }
                };
                addData(jhrh);
            }
        });

        listView.setOnLoadListener(new CustomListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageIndex++;
                JsonHttpResponseHandler jhrh = new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<TouTiaoBean>>() {
                        }.getType();
                        ArrayList<TouTiaoBean> itemList = gson.fromJson(response.toString(), type);
                        ttList.addAll(itemList);
                        listView.onLoadMoreComplete();
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_LONG).show();
                    }
                };
                addData(jhrh);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TouTiaoBean item = (TouTiaoBean) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), TouTiaoDetailActivity.class);
                intent.putExtra("id", item.getId());
                startActivity(intent);
            }
        });

    }
}
