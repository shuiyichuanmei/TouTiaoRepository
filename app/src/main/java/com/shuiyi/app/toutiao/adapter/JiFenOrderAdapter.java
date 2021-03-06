package com.shuiyi.app.toutiao.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shuiyi.app.toutiao.R;
import com.shuiyi.app.toutiao.bean.JiFenOrderBean;
import com.shuiyi.app.toutiao.bean.KuaiCanBean;
import com.shuiyi.app.toutiao.common.DPImageOptions;

import java.util.ArrayList;

/**
 * Created by Hey on 2016/6/28.
 */
public class JiFenOrderAdapter extends MyBaseAdapter<JiFenOrderBean> {
    private DisplayImageOptions options = null;

    public JiFenOrderAdapter(Context context, ArrayList<JiFenOrderBean> beans) {
        super(context, beans);
        options = DPImageOptions.getDefaultOption(R.drawable.ic_stub,
                R.drawable.ic_stub, R.drawable.ic_stub, false);
    }


    @Override
    protected View getExView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_jifenorder, parent, false);
        }
        ImageView imgurl = (ImageView) ViewHolder.get(convertView, R.id.imageurl);
        TextView mingcheng = (TextView) ViewHolder.get(convertView, R.id.mingcheng);
        TextView txtstatus = (TextView) ViewHolder.get(convertView, R.id.status);
        TextView dhText = (TextView) ViewHolder.get(convertView, R.id.num_text);
        TextView tmText = (TextView) ViewHolder.get(convertView, R.id.timedate);

        String status = beans.get(position).getStatus();
        if (status != null && status.equals("未兑换")) {
            txtstatus.setBackgroundResource(R.color.DCDCDC);
            txtstatus.setTextColor(Color.parseColor("#999999"));
        } else {
            txtstatus.setBackgroundResource(R.color.white);
            txtstatus.setTextColor(Color.parseColor("#333333"));
        }

        tmText.setText(beans.get(position).getCreatedate());
        mingcheng.setText(beans.get(position).getMingcheng());
        dhText.setText(String.valueOf(beans.get(position).getSpJifen()));
        txtstatus.setText(beans.get(position).getStatus());
        ImageLoader.getInstance().displayImage(beans.get(position).getSpimg(), imgurl, options);
        return convertView;
    }

}
