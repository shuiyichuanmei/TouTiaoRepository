package com.shuiyi.app.toutiao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public abstract class MyBaseAdapter<T> extends BaseAdapter {
	protected ArrayList<T> beans;
	protected LayoutInflater mInflater;
	protected Context context;

	public MyBaseAdapter(Context context, ArrayList<T> beans) {
		this.beans = beans;
		this.context = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(ArrayList<T> beans) {
		this.beans = beans;
	}

	@Override
	public int getCount() {
		return beans.size();
	}

	@Override
	public Object getItem(int position) {
		return beans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (position == getCount() - 1) {
			onReachBottom();
		}
		return getExView(position, convertView, parent);
	}

	protected abstract View getExView(int position, View convertView,
			ViewGroup parent);

	/**
	 * 滑动到底部的事件
	 */
	protected void onReachBottom() {
	};

}
