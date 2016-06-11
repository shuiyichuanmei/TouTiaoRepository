package com.shuiyi.app.toutiao.common;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class DPImageOptions {

	/**
	 * @param OnLoading
	 * @param EmptyUri
	 * @param OnFail
	 * @param isRounded
	 * @return
	 */
	public static DisplayImageOptions getDefaultOption(int OnLoading,
			int EmptyUri, int OnFail, boolean isRounded) {
		DisplayImageOptions options = null;
		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
				.showImageOnLoading(OnLoading).showImageForEmptyUri(EmptyUri)
				.showImageOnFail(OnFail).cacheInMemory(false).cacheOnDisk(true)
				.considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565);
		if (isRounded) {
			options = builder.displayer(new RoundedBitmapDisplayer(10)).build();
		} else {
			options = builder.build();
		}
		return options;
	}

	public static DisplayImageOptions getRoundeOption(int OnLoading,
			int EmptyUri, int OnFail) {
		DisplayImageOptions options = null;
		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
				.showImageOnLoading(OnLoading).showImageForEmptyUri(EmptyUri)
				.showImageOnFail(OnFail).cacheInMemory(false).cacheOnDisk(true)
				.considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new RoundedBitmapDisplayer(90));
		options = builder.build();
		return options;
	}

	/**
	 * @param OnLoading
	 * @param EmptyUri
	 * @param OnFail
	 * @param isRounded
	 * @param cacheInMemory
	 *            是否缓存到内存
	 */
	public static DisplayImageOptions getDefaultOption(int OnLoading,
			int EmptyUri, int OnFail, boolean isRounded, boolean cacheInMemory) {
		DisplayImageOptions options = null;
		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
				.showImageOnLoading(OnLoading).showImageForEmptyUri(EmptyUri)
				.showImageOnFail(OnFail).cacheInMemory(cacheInMemory)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565);
		if (isRounded) {
			options = builder.displayer(new RoundedBitmapDisplayer(10)).build();
		} else {
			options = builder.build();
		}
		return options;
	}
}
