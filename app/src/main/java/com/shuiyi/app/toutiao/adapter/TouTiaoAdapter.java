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
    private  ArrayList<ImageBean> 	guideImgs;
    private  Context curcontext;
    private PictureFragment fragment ;
    private ImageCycleView mAdView;

    public TouTiaoAdapter(Context context, ArrayList<TouTiaoBean> beans) {
        super(context, beans);
        curcontext=context;
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

    private void addData() {
        guideImgs = new ArrayList<ImageBean>();
        for (int i = 0; i < 3; i++) {
            ImageBean imageBean = new ImageBean();
            switch (i) {
                case 0:
                    imageBean
                            .setImgurl("http://121.40.116.207/a-mj01/images/pa1_首页/u9.png");

                    break;
                case 1:
                    imageBean
                            .setImgurl("http://121.40.116.207/a-mj01/images/pa1_首页/u5.png");

                    break;
                case 2:
                    imageBean
                            .setImgurl("http://121.40.116.207/a-mj01/images/pa1_首页/u13.png");

                    break;

                default:
                    break;
            }

            guideImgs.add(imageBean);
        }

    }
    @Override
    protected View getExView(final int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = mInflater.inflate(R.layout.item_toutiao, parent, false);
//        }

        if(position==0){
            convertView = mInflater.inflate(R.layout.firstitem, parent, false);
            addData();
            mAdView = (ImageCycleView) ViewHolder.get(convertView,R.id.ad_view);
            mAdView.setImageResources(guideImgs, mAdCycleViewListener);

//            ImageView imgTitle = (ImageView) ViewHolder.get(convertView,
//                    R.id.imageView);
//            imgTitle.setImageResource(R.drawable.sc_top_img);
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

