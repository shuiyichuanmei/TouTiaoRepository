package com.shuiyi.app.toutiao;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.text.method.Touch;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.zip.Inflater;

import static android.app.PendingIntent.getActivity;

/**
 * Created by wang on 2016/6/7.
 */
public class PingTaiFragment extends Fragment {
    private LinearLayout LayoutZghj, LayoutSjxnb, LayoutDongfang, LayoutDfkc;

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
        LayoutDongfang = (LinearLayout) getActivity().findViewById(R.id.lay_dongfang);
        LayoutDfkc = (LinearLayout) getActivity().findViewById(R.id.lay_dafu);
        LayoutZghj = (LinearLayout) getActivity().findViewById(R.id.lay_zghj);
        LayoutSjxnb = (LinearLayout) getActivity().findViewById(R.id.lay_sjxnb);

        LayoutDongfang.setOnClickListener(onClickListener);
        LayoutDfkc.setOnClickListener(onClickListener);
        LayoutZghj.setOnClickListener(onClickListener);
        LayoutSjxnb.setOnClickListener(onClickListener);
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lay_dongfang:
                    Intent intent = new Intent(getActivity(), PingTaiDetailActivity.class);
                    intent.putExtra("url", "http://dongfang.ishowyou.cc/index.aspx");
                    intent.putExtra("mingcheng", "东方快餐网上订餐");
                    startActivity(intent);
                    break;
                case R.id.lay_dafu:
                    Intent intent2 = new Intent(getActivity(), PingTaiDetailActivity.class);
                    intent2.putExtra("url", "http://www.ishowyou.cc/dfkc/index.aspx");
                    intent2.putExtra("mingcheng", "大福春艳在线外卖");
                    startActivity(intent2);
                    break;
                case R.id.lay_sjxnb:
                    Intent intent3 = new Intent(getActivity(), PingTaiDetailActivity.class);
                    intent3.putExtra("url", "http://www.ishowyou.cc/sjxnb/index.aspx");
                    intent3.putExtra("mingcheng", "四季鲜奶吧");
                    startActivity(intent3);
                    break;
                case R.id.lay_zghj:
                    Intent intent4 = new Intent(getActivity(), PingTaiDetailActivity.class);
                    intent4.putExtra("url", "http://www.ishowyou.cc/zghjbq/default.aspx");
                    intent4.putExtra("mingcheng", "宝清中国黄金");
                    startActivity(intent4);
                    break;
                default:
                    break;
            }
        }
    };
}
