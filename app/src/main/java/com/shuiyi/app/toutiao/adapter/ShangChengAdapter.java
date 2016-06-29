package com.shuiyi.app.toutiao.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shuiyi.app.toutiao.R;
import com.shuiyi.app.toutiao.bean.KuaiCanBean;
import com.shuiyi.app.toutiao.bean.ShangChengBean;
import com.shuiyi.app.toutiao.common.DPImageOptions;

import java.util.ArrayList;

/**
 * Created by Hey on 2016/6/25.
 */
public class ShangChengAdapter extends MyBaseAdapter<ShangChengBean> {
    private DisplayImageOptions options = null;

    public ShangChengAdapter(Context context, ArrayList<ShangChengBean> beans) {
        super(context, beans);
        options = DPImageOptions.getDefaultOption(R.drawable.ic_stub,
                R.drawable.ic_stub, R.drawable.ic_stub, false);
    }

    @Override
    protected View getExView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_shangcheng, parent, false);

        }
        ImageView spimg = (ImageView) ViewHolder.get(convertView, R.id.sp_img);
        TextView spname = (TextView) ViewHolder.get(convertView, R.id.sp_name);
        TextView spjifen = (TextView) ViewHolder.get(convertView, R.id.sp_jifen);

        Drawable[] drawable = spjifen.getCompoundDrawables();
        drawable[0].setBounds(0, 0, 30, 30);
        spjifen.setCompoundDrawables(drawable[0], drawable[1], drawable[2], drawable[3]);

        spname.setText(beans.get(position).getMingcheng());
        spjifen.setText(String.valueOf(beans.get(position).getSpJifen()));
        ImageLoader.getInstance().displayImage(beans.get(position).getSpimg(), spimg, options);
        return convertView;
    }
}
