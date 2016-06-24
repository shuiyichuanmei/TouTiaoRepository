package com.shuiyi.app.toutiao;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.zip.Inflater;

import static android.app.PendingIntent.getActivity;

/**
 * Created by wang on 2016/6/7.
 */
public class PingTaiFragment extends Fragment {
    private LinearLayout DongFang, DaFu, ZGHJ, SJXNB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pingtai, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findView();
    }

    private void findView() {
        DongFang = (LinearLayout) getActivity().findViewById(R.id.dongfang);
        DongFang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PingTaiDetailActivity.class);
                intent.putExtra("url", "http://dongfang.ishowyou.cc/index.aspx");//这块直接写要打开的url
                intent.putExtra("mingcheng", "东方快餐网上订餐");
                startActivity(intent);
            }
        });
        DaFu = (LinearLayout) getActivity().findViewById(R.id.dafu);
        DaFu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PingTaiDetailActivity.class);
                intent.putExtra("url", "http://www.ishowyou.cc/dfkc/index.aspx");//这块直接写要打开的url
                intent.putExtra("mingcheng", "大福春艳在线外卖");
                startActivity(intent);
            }
        });
        SJXNB = (LinearLayout) getActivity().findViewById(R.id.sjxnb);
        SJXNB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PingTaiDetailActivity.class);
                intent.putExtra("url", "http://www.ishowyou.cc/sjxnb/index.aspx");//这块直接写要打开的url
                intent.putExtra("mingcheng", "四季鲜奶吧");
                startActivity(intent);
            }
        });
        ZGHJ = (LinearLayout) getActivity().findViewById(R.id.zghj);
        ZGHJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PingTaiDetailActivity.class);
                intent.putExtra("url", "http://www.ishowyou.cc/zghjbq/default.aspx");//这块直接写要打开的url
                intent.putExtra("mingcheng", "宝清中国黄金");
                startActivity(intent);
            }
        });
    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pingtai);
//        DongFang = (LinearLayout) this.findViewById(R.id.dongfang);
//        DongFang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), PingTaiDetailActivity.class);
//                startActivity(intent);
//            }
//        });
//
//    }


}
