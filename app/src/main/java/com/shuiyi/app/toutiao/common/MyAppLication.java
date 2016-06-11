package com.shuiyi.app.toutiao.common;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

public class MyAppLication extends Application {
	
	private static MyAppLication instance;

	public void onCreate() {
		super.onCreate();
		instance = this;
		initImageLoader(getApplicationContext());
	}

	public static MyAppLication getInstance() {
		return instance;
	}

	public void initImageLoader(Context context) {
		// 缓存文件的目录
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				"QinXin/QinXinDaImageCache");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory().diskCacheFileCount(100)
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024)
				// 50 Mb
				.memoryCacheSize(3 * 1024 * 1024)
				// 内存缓存的最大值
				.diskCache(new UnlimitedDiscCache(cacheDir))
				// 自定义缓存路径
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		ImageLoader.getInstance().init(config);
	}
}
