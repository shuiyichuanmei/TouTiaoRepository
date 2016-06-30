package com.shuiyi.app.toutiao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuiyi.app.toutiao.R;
import com.shuiyi.app.toutiao.bean.TouTiaoBean;
import com.shuiyi.app.toutiao.common.DPImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2016/6/5.
 */
public class TouTiaoAdapter extends MyBaseAdapter<TouTiaoBean> {
    private DisplayImageOptions options = null;

    public TouTiaoAdapter(Context context, ArrayList<TouTiaoBean> beans) {
        super(context, beans);
        options = DPImageOptions.getDefaultOption(R.drawable.ic_stub,
                R.drawable.ic_stub, R.drawable.ic_stub, false);
    }

    @Override
    protected View getExView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_toutiao, parent, false);
        }

        if(position==0){
            convertView = mInflater.inflate(R.layout.firstitem, parent, false);
            ImageView imgTitle = (ImageView) ViewHolder.get(convertView,
                    R.id.imageView);
            imgTitle.setImageResource(R.drawable.sc_top_img);
            return convertView;
        }else{
            convertView = mInflater.inflate(R.layout.item_toutiao, parent, false);
            ImageView imgTitle = (ImageView) ViewHolder.get(convertView,
                    R.id.imgTitle);
            TextView labBiaoTi = (TextView) ViewHolder.get(convertView,
                    R.id.labBiaoTi);
            TextView labZuoZhe = (TextView) ViewHolder.get(convertView,
                    R.id.labZuoZhe);
            TextView labCreateDate = (TextView) ViewHolder.get(convertView,
                    R.id.labCreateDate);


            labBiaoTi.setText(beans.get(position).getBiaoTi());
            labZuoZhe.setText(beans.get(position).getZuoZhe());
            labCreateDate.setText(beans.get(position).getCreateDate());
            ImageLoader.getInstance().displayImage(beans.get(position).getTitleImg(), imgTitle, options);
            return convertView;
        }
    }
}

