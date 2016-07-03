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
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shuiyi.app.toutiao.adapter.KuaiCanAdapter;
import com.shuiyi.app.toutiao.bean.KuaiCanBean;
import com.shuiyi.app.toutiao.net.AsyncHttpUtil;
import com.shuiyi.app.toutiao.view.CustomListView;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wang on 2016/6/7.
 */
public class KuaiCanFragment extends Fragment {
    private CustomListView listView;
    private KuaiCanAdapter kcAdapter = null;
    private ArrayList<KuaiCanBean> kcList = null;
    private int pageIndex = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.kuaican, container, false);

    }
    private void GetHttpData(JsonHttpResponseHandler jhrh) {
        AsyncHttpUtil ahu = new AsyncHttpUtil();
        RequestParams rp = new RequestParams();
        rp.add("page", String.valueOf(pageIndex));
        ahu.get("http://www.ishowyou.cc/qdkc/KuaicanHandler.ashx", rp, jhrh);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findView();
    }

    private void findView() {
        listView = (CustomListView) getActivity().findViewById(R.id.listView2);
        kcList = new ArrayList<KuaiCanBean>();
        kcAdapter = new KuaiCanAdapter(getActivity(), kcList);
        JsonHttpResponseHandler kc = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Gson gson = new Gson();
                ArrayList<KuaiCanBean> itemList= gson.fromJson(response.toString(),new TypeToken<ArrayList<KuaiCanBean>>(){}.getType());
                if (itemList.size() < 10) {
                    listView.setCanLoadMore(false);
                }
                kcList.addAll(itemList);
                listView.setAdapter(kcAdapter);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_LONG).show();
            }
        };
        GetHttpData(kc);
        listView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                kcList.clear();
                pageIndex = 1;
                listView.setCanLoadMore(true);
                JsonHttpResponseHandler kc = new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        Gson gson = new Gson();
                        ArrayList<KuaiCanBean> itemList= gson.fromJson(response.toString(),new TypeToken<ArrayList<KuaiCanBean>>(){}.getType());
                        if (itemList.size() < 10) {
                            listView.setCanLoadMore(false);
                        }
                        kcList.addAll(itemList);
                        listView.onRefreshComplete();
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_LONG).show();
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
                        ArrayList<KuaiCanBean> itemList= gson.fromJson(response.toString(),new TypeToken<ArrayList<KuaiCanBean>>(){}.getType());
                        if (itemList.size() < 10) {
                            listView.setCanLoadMore(false);
                        }
                        kcList.addAll(itemList);
                        listView.onLoadMoreComplete();
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_LONG).show();
                    }
                };
                GetHttpData(kc);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                KuaiCanBean item = (KuaiCanBean) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), KuaiCanDetailActivity.class);
                intent.putExtra("id", item.getId());
                intent.putExtra("mingcheng", item.getMingcheng());
                startActivity(intent);
            }
        });
    }
}

