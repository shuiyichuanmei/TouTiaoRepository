package com.shuiyi.app.toutiao.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuiyi.app.toutiao.MainActivity;
import com.shuiyi.app.toutiao.R;
import com.shuiyi.app.toutiao.bean.ImageBean;
import com.shuiyi.app.toutiao.bean.TouTiaoBean;
import com.shuiyi.app.toutiao.common.DPImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shuiyi.app.toutiao.scrollad.ImageCycleView;
import com.shuiyi.app.toutiao.scrollad.PictureFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2016/6/5.
 */
public class TouTiaoAdapter extends MyBaseAdapter<TouTiaoBean> {
    private DisplayImageOptions options = null;
    private Context curcontext;
    private PictureFragment fragment;
    private ImageCycleView mAdView;

    public TouTiaoAdapter(Context context, ArrayList<TouTiaoBean> beans) {
        super(context, beans);
        curcontext = context;
        options = DPImageOptions.getDefaultOption(R.drawable.ic_stub,
                R.drawable.ic_stub, R.drawable.ic_stub, false);


    }

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
        @Override
        public void onImageClick(int position, View imageView) {
            // Intent intent = new Intent(getActivity(),
            // ViewPagerExampleActivity.class);
            // intent.putExtra("strings", mImageUrl);
            // intent.putExtra("index", position);
            // startActivity(intent);
            // getActivity().overridePendingTransition(
            // android.R.anim.slide_in_left,
            // android.R.anim.slide_out_right);
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            ImageLoader.getInstance().displayImage(
                    imageURL,
                    imageView,
                    DPImageOptions.getDefaultOption(R.drawable.ic_stub,
                            R.drawable.ic_stub, R.drawable.ic_stub, false));// 此处本人使用了ImageLoader对图片进行加装！
        }
    };

    @Override
    protected View getExView(final int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = mInflater.inflate(R.layout.item_toutiao, parent, false);
//        }
        String type = beans.get(position).getTypeId();
        if (type.equals("广告")) {
            convertView = mInflater.inflate(R.layout.firstitem, parent, false);
            String imgurl = beans.get(position).getTitleImg();
            String[] imgs = imgurl.toString().split("&");
            ArrayList<ImageBean> guideImgs = new ArrayList<ImageBean>();
            for (int i = 0; i < imgs.length; i++) {
                ImageBean imageBean = new ImageBean();
                imageBean.setImgurl(imgs[i]);
                guideImgs.add(imageBean);
            }
            mAdView = (ImageCycleView) ViewHolder.get(convertView, R.id.ad_view);
            mAdView.setImageResources(guideImgs, mAdCycleViewListener);

//            ImageView imgTitle = (ImageView) ViewHolder.get(convertView,
//                    R.id.imageView);
//            imgTitle.setImageResource(R.drawable.sc_top_img);
            return convertView;
        } else {
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

