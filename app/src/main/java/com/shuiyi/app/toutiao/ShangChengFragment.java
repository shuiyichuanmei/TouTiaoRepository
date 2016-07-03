package com.shuiyi.app.toutiao;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Handler;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.shuiyi.app.toutiao.adapter.KuaiCanAdapter;
import com.shuiyi.app.toutiao.adapter.ShangChengAdapter;
import com.shuiyi.app.toutiao.bean.KuaiCanBean;
import com.shuiyi.app.toutiao.bean.ShangChengBean;
import com.shuiyi.app.toutiao.common.Common;
import com.shuiyi.app.toutiao.net.AsyncHttpUtil;
import com.shuiyi.app.toutiao.view.CustomListView;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

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
    private int daojishi = 0;
    private Handler handler = new Handler();
    private Runnable myRunnable = new Runnable() {
        public void run() {
            daojishi--;
            if (daojishi <= 0) {
                daojishi = 0;
            } else {
                handler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shangcheng, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findView();
        InitJifen();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshFriend");
        getActivity().registerReceiver(mRefreshBroadcastReceiver, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("action.refreshShangPin");
        getActivity().registerReceiver(mRefreshBroadcastReceiver, intentFilter2);
    }

    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refreshFriend")) {
                InitJifen();
            } else if (action.equals("action.refreshShangPin")) {
                scList.clear();
                AsyncHttpUtil ahu = new AsyncHttpUtil();
                RequestParams rp = new RequestParams();
                rp.add("ft", "GetList");
                ahu.get("http://toutiao.ishowyou.cc/Server/JiFenShangPinHandler.ashx", rp,
                        new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                                Gson gson = new Gson();
                                ArrayList<ShangChengBean> itemList = gson.fromJson(response.toString(), new TypeToken<ArrayList<ShangChengBean>>() {
                                }.getType());
                                scList.addAll(itemList);
                                scAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        }
    };

    private void InitJifen() {
        jifenUser.setCompoundDrawables(null, null, null, null);
        if (Common.isDenglu(getActivity())) {
            tel = Common.getSharedPreferences(getActivity(), "tel");
            AsyncHttpUtil ahu = new AsyncHttpUtil();
            RequestParams rp = new RequestParams();
            rp.add("ft", "GetJifen");
            rp.add("tel", tel);
            ahu.get("http://toutiao.ishowyou.cc/Server/UserHandler.ashx", rp, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess(int i, Header[] headers, String s) {
                    jifenUser.setText(s);
                    Drawable img_on, img_off;
                    Resources res = getResources();
                    img_off = res.getDrawable(R.drawable.icon_jifen_num);
                    img_off.setBounds(0, 0, 30, 30);
                    jifenUser.setCompoundDrawables(img_off, null, null, null);
                }
            });
        } else {
            jifenUser.setText("立即登录查看");
            jifenUser.setCompoundDrawables(null, null, null, null);
        }
    }

    private void findView() {
        myOrder = (LinearLayout) getActivity().findViewById(R.id.myorder);
        jifenUser = (TextView) getActivity().findViewById(R.id.jifen_user);
        gridView = (GridView) getActivity().findViewById(R.id.gridview);
        scList = new ArrayList<ShangChengBean>();
        scAdapter = new ShangChengAdapter(getActivity(), scList);
        lotJifen = (LinearLayout) getActivity().findViewById(R.id.lotJifen);
        lotJifen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Common.isDenglu(getActivity())) {
                    Intent intent = new Intent(getActivity(), DengluActivity.class);
                    startActivity(intent);
                } else {
                    handler.removeCallbacks(myRunnable);
                    daojishi++;
                    if (daojishi >= 7) {
                        daojishi = 0;
                        Common.removeSharedPreferences(getActivity(), "tel");
                        Common.removeSharedPreferences(getActivity(), "userId");
                        InitJifen();
                    } else {

                        handler.postDelayed(myRunnable, 1000);
                    }
                }
            }
        });
        myOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Common.isDenglu(getActivity())) {
                    Intent intent = new Intent(getActivity(), DengluActivity.class);
                    startActivity(intent);
                    return;
                }
                Intent intent = new Intent(getActivity(), JiFenOrderActivity.class);
                startActivity(intent);
            }
        });


        AsyncHttpUtil ahu = new AsyncHttpUtil();
        RequestParams rp = new RequestParams();
        rp.add("ft", "GetList");
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

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_LONG).show();
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
