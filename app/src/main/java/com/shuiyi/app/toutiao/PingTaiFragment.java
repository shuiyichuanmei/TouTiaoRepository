package com.shuiyi.app.toutiao;

import android.app.Activity;
import android.app.Fragment;
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
    private LinearLayout DongFang;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.kuaican, container, false);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findView();
    }
    private void findView() {
        DongFang=(LinearLayout) getActivity().findViewById(R.id.dongfang);
        DongFang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PingTaiDetailActivity.class);
//                startActivity(intent);
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
