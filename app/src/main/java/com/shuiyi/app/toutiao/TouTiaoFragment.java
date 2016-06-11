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

import com.shuiyi.app.toutiao.adapter.TouTiaoAdapter;
import com.shuiyi.app.toutiao.bean.TouTiaoBean;
import com.shuiyi.app.toutiao.net.AsyncHttpUtil;
import com.shuiyi.app.toutiao.view.CustomListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wang on 2016/6/7.
 */
public class TouTiaoFragment extends Fragment {
    private ImageButton searchbtn;
    private CustomListView listView;
    private TouTiaoAdapter ttAdapter = null;
    private ArrayList<TouTiaoBean> ttList = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.toutiao, container, false);
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

        addData("diyici");


        listView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ttList.clear();
                addData("shuaxin");
            }
        });

        listView.setOnLoadListener(new CustomListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                addData("gengduo");
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                System.out.println("position==" + position);
                System.out.println("id==" + id);
                Intent intent = new Intent(getActivity(),TouTiaoDetail.class);
                startActivity(intent);
            }
        });

    }

    private void addData(final String flag) {
        AsyncHttpUtil ahu = new AsyncHttpUtil();
        JsonHttpResponseHandler res = new JsonHttpResponseHandler();
        RequestParams rp = new RequestParams();
        rp.add("page", "1");
        ahu.get("http://toutiao.ishowyou.cc/TouTiaoHandler.ashx", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i = 0; i < response.length(); i++) {
                    TouTiaoBean tt = new TouTiaoBean();
                    try {
                        JSONObject jo = response.optJSONObject(i);
                        tt.setId(jo.getString("id"));
                        tt.setCreateDate(jo.getString("createDate"));
                        tt.setJianJie(jo.getString("jianJie"));
                        tt.setTitleImg("http://toutiao.ishowyou.cc/upload/" + jo.getString("titleImg"));
                        tt.setTypeId(jo.getString("typeId"));
                        tt.setZuoZhe(jo.getString("zuoZhe"));
                        tt.setBiaoTi(jo.getString("biaoTi"));
                    } catch (Exception ex) {
                    }
                    ttList.add(tt);
                }
                if (flag == "diyici") {
                    listView.setAdapter(ttAdapter);
                } else if (flag == "shuaxin") {
                    listView.onRefreshComplete();
                } else if (flag == "gengduo") {
                    listView.onLoadMoreComplete();
                }
            }
        });
    }
}
