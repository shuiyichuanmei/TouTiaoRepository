package com.shuiyi.app.toutiao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shuiyi.app.toutiao.R;
import com.shuiyi.app.toutiao.bean.KuaiCanBean;
import com.shuiyi.app.toutiao.bean.TouTiaoBean;
import com.shuiyi.app.toutiao.common.DPImageOptions;

import java.util.ArrayList;

/**
 * Created by Hey on 2016/6/18.
 */
public class KuaiCanAdapter extends MyBaseAdapter<KuaiCanBean> {
    private DisplayImageOptions options = null;

    public KuaiCanAdapter(Context context, ArrayList<KuaiCanBean> beans) {
        super(context, beans);
        options = DPImageOptions.getDefaultOption(R.drawable.ic_stub,
                R.drawable.ic_stub, R.drawable.ic_stub, false);
    }

    @Override
    protected View getExView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_kuaican, parent, false);
        }
        ImageView imgurl = (ImageView) ViewHolder.get(convertView, R.id.imgUrl);
        TextView biaoti = (TextView) ViewHolder.get(convertView, R.id.biaoti);
        TextView xfText = (TextView) ViewHolder.get(convertView, R.id.xfText);
        TextView txText = (TextView) ViewHolder.get(convertView, R.id.txText);


        biaoti.setText(beans.get(position).getMingcheng());
        txText.setText(beans.get(position).getSongcantixing());
        xfText.setText(beans.get(position).getCankao());
        ImageLoader.getInstance().displayImage(beans.get(position).getImgxiao(), imgurl, options);
        return convertView;
    }

}
