package com.shuiyi.app.toutiao.scrollad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shuiyi.app.toutiao.bean.ImageBean;
import com.shuiyi.app.toutiao.common.DPImageOptions;
import com.shuiyi.app.toutiao.scrollad.ImageCycleView.ImageCycleViewListener;
import com.shuiyi.app.toutiao.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


public class PictureFragment extends BaseFragment {
	private ImageCycleView mAdView;
	private ArrayList<ImageBean> mImageUrl;

	public PictureFragment() {
	}

	public PictureFragment(ArrayList<ImageBean> mImageUrl) {
		this.mImageUrl = mImageUrl;
	}

	public void setData(ArrayList<ImageBean> mImageUrl) {
		this.mImageUrl = mImageUrl;
		if (mAdView != null && mImageUrl != null) {
			mAdView.setImageResources(mImageUrl, mAdCycleViewListener);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_viewpager,
				container, false);// 关联布局文件
		findView(rootView);

		return rootView;
	}

	private void findView(View rootView) {
		mAdView = (ImageCycleView) rootView.findViewById(R.id.ad_view);
		mAdView.setImageResources(mImageUrl, mAdCycleViewListener);
	}

	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {
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
	public void onDestroy() {
		mAdView.pushImageCycle();
		super.onDestroy();
	}

	@Override
	public void onStart() {
		mAdView.startImageCycle();
		super.onStart();
	}

	@Override
	public void onPause() {
		mAdView.pushImageCycle();
		super.onPause();
	}

}
