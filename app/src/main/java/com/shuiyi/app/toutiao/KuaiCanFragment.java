package com.shuiyi.app.toutiao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shuiyi.app.toutiao.Server.KuaiCanJsonHttpResponseHandler;
import com.shuiyi.app.toutiao.Server.TouTiaoJsonHttpResponseHandler;
import com.shuiyi.app.toutiao.adapter.KuaiCanAdapter;
import com.shuiyi.app.toutiao.bean.KuaiCanBean;
import com.shuiyi.app.toutiao.bean.TouTiaoBean;
import com.shuiyi.app.toutiao.net.AsyncHttpUtil;
import com.shuiyi.app.toutiao.view.CustomListView;

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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {// 不在最前端界面显示


        } else {// 重新显示到最前端中
//            kcList.clear();
//            pageIndex = 1;
//            KuaiCanJsonHttpResponseHandler tt = new KuaiCanJsonHttpResponseHandler() {
//                @Override
//                public void OnSuccess(ArrayList<KuaiCanBean> kclist) {
//                    kcList.addAll(kclist);
//                }
//            };
//            addData(kc);
        }
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
        KuaiCanJsonHttpResponseHandler kc = new KuaiCanJsonHttpResponseHandler() {
            @Override
            public void OnSuccess(ArrayList<KuaiCanBean> kclist) {
                kcList.addAll(kclist);
                listView.setAdapter(kcAdapter);
            }
        };
        addData(kc);
        listView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                kcList.clear();
                pageIndex = 1;
                //KuaiCanBean.KuaiCanJsonHttpResponseHandler kc= new KuaiCanBean.KuaiCanJsonHttpResponseHandler();

                KuaiCanJsonHttpResponseHandler kc = new KuaiCanJsonHttpResponseHandler() {
                    @Override
                    public void OnSuccess(ArrayList<KuaiCanBean> kclist) {
                        kcList.addAll(kclist);
                        listView.onRefreshComplete();
                    }
                };
                addData(kc);
            }
        });
        listView.setOnLoadListener(new CustomListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageIndex++;
                KuaiCanJsonHttpResponseHandler kc = new KuaiCanJsonHttpResponseHandler() {
                    @Override
                    public void OnSuccess(ArrayList<KuaiCanBean> kclist) {
                        kcList.addAll(kclist);
                        listView.onLoadMoreComplete();
                    }
                };
                addData(kc);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                System.out.println("position==" + position);
                System.out.println("id==" + id);
                KuaiCanBean item = (KuaiCanBean) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), TouTiaoDetail.class);
                intent.putExtra("id", item.getId());
                startActivity(intent);
            }
        });
    }


    private void addData(JsonHttpResponseHandler jhrh) {
        AsyncHttpUtil ahu = new AsyncHttpUtil();
        RequestParams rp = new RequestParams();
        rp.add("page", String.valueOf(pageIndex));
        ahu.get("http://www.ishowyou.cc/qdkc/KuaicanHandler.ashx", rp, jhrh);
    }
}

