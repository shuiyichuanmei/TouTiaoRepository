package com.shuiyi.app.toutiao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.shuiyi.app.toutiao.Server.TouTiaoJsonHttpResponseHandler;
import com.shuiyi.app.toutiao.adapter.TouTiaoAdapter;
import com.shuiyi.app.toutiao.bean.TouTiaoBean;
import com.shuiyi.app.toutiao.net.AsyncHttpUtil;
import com.shuiyi.app.toutiao.view.CustomListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

/**
 * Created by wang on 2016/6/7.
 */
public class TouTiaoFragment extends Fragment {
    private ImageButton searchbtn;
    private CustomListView listView;
    private TouTiaoAdapter ttAdapter = null;
    private ArrayList<TouTiaoBean> ttList = null;
    private int pageIndex = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.toutiao, container, false);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {// 不在最前端界面显示


        } else {// 重新显示到最前端中
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        searchbtn = (ImageButton) getActivity().findViewById(R.id.imageButton);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    startActivity(intent);
                    //getActivity().overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                } catch (Exception ex) {
                    Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        findView();
    }

    private void findView() {

        listView = (CustomListView) getActivity().findViewById(R.id.listView);
        ttList = new ArrayList<TouTiaoBean>();
        ttAdapter = new TouTiaoAdapter(getActivity(), ttList);
        TouTiaoJsonHttpResponseHandler tt = new TouTiaoJsonHttpResponseHandler() {
            @Override
            public void OnSuccess(ArrayList<TouTiaoBean> ttlist) {
                ttList.addAll(ttlist);
                listView.setAdapter(ttAdapter);

            }
        };
        addData(tt);


        listView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ttList.clear();
                pageIndex = 1;
                //TouTiaoBean.TouTiaoJsonHttpResponseHandler tt= new TouTiaoBean.TouTiaoJsonHttpResponseHandler();

                TouTiaoJsonHttpResponseHandler tt = new TouTiaoJsonHttpResponseHandler() {
                    @Override
                    public void OnSuccess(ArrayList<TouTiaoBean> ttlist) {
                        ttList.addAll(ttlist);
                        listView.onRefreshComplete();
                    }
                };

                addData(tt);
            }
        });

        listView.setOnLoadListener(new CustomListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageIndex++;
                TouTiaoJsonHttpResponseHandler tt = new TouTiaoJsonHttpResponseHandler() {
                    @Override
                    public void OnSuccess(ArrayList<TouTiaoBean> ttlist) {
                        ttList.addAll(ttlist);
                        listView.onLoadMoreComplete();
                    }
                };
                addData(tt);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                System.out.println("position==" + position);
                System.out.println("id==" + id);
                TouTiaoBean item = (TouTiaoBean) parent.getItemAtPosition(position);
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
        ahu.get("http://toutiao.ishowyou.cc/TouTiaoHandler.ashx", rp, jhrh);
    }
}
