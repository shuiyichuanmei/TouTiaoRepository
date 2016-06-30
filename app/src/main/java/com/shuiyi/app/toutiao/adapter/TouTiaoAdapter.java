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
    //下面的声明都是后添的
    private int resource;//资源信息
    private LayoutInflater mLayoutInflater;//填充布局
    private int currentType;//当前布局
    private final int TYPE_COUNT = 2;//2种布局
    private final int FIRST_TYPE = 0;//第一个布局
    private final int OTHERS_TYPE = 1;//其他布局


    public TouTiaoAdapter(Context context, ArrayList<TouTiaoBean> beans) {
        super(context, beans);
        options = DPImageOptions.getDefaultOption(R.drawable.ic_stub,
                R.drawable.ic_stub, R.drawable.ic_stub, false);
        //mLayoutInflater后添的
        mLayoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    protected View getExView(final int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_toutiao, parent, false);
        }


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

//下面都是更改item布局的
    @Override
    public int getCount() {
        if (beans == null) {
            return 0;
        } else {
            return (beans.size() + 1);
        }

    }

    @Override
    public Object getItem(int position) {
        if (beans == null) {
            return null;
        } else {
            if (position > 0) {
                return beans.get(position - 1);
            } else {
                return beans.get(position + 1);
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return FIRST_TYPE;
        } else {
            return OTHERS_TYPE;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View firstItemView = null;
        View othersItemView = null;

        //获取到当前位置所对应的Type
        currentType = getItemViewType(position);
        System.out.println("type=" + currentType);
        if (currentType == FIRST_TYPE) {
            firstItemView = convertView;
            FirstItemViewHolder firstItemViewHolder = null;
            if (firstItemView == null) {
                System.out.println("firstItemView==null ");
                firstItemView = mLayoutInflater.inflate(R.layout.firstitem, null);
                firstItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("=====click first item=======");
                    }
                });
                firstItemViewHolder = new FirstItemViewHolder();
                firstItemViewHolder.imageView = (ImageView) firstItemView.findViewById(R.id.imageView);
                firstItemView.setTag(firstItemViewHolder);

            } else {
                System.out.println("firstItemView!=null ");
                System.out.println("111 getClass=" + firstItemView.getTag().getClass().toString());
                firstItemViewHolder = (FirstItemViewHolder) firstItemView.getTag();
            }

            if (firstItemViewHolder.imageView != null) {
                firstItemViewHolder.imageView.setImageResource(R.drawable.sc_top_img);
            }

            convertView = firstItemView;

        } else {
            othersItemView = convertView;
            OthersViewHolder othersViewHolder = null;
            if (othersItemView == null) {
                System.out.println("othersItemView==null ");
                othersItemView = mLayoutInflater.inflate(R.layout.item_toutiao, null);
                othersViewHolder = new OthersViewHolder();
                othersViewHolder.textView = (TextView) othersItemView.findViewById(R.id.mingcheng);
                othersItemView.setTag(othersViewHolder);
            } else {
                System.out.println("othersItemView!=null ");
                System.out.println("222 getClass=" + othersItemView.getTag().getClass().toString());
                othersViewHolder = (OthersViewHolder) othersItemView.getTag();
            }

            if (beans != null) {
                if (othersViewHolder.textView != null) {
                    othersViewHolder.textView.setText((String) (beans.get(position - 1).getTitleImg()));
                }

            }

            convertView = othersItemView;

        }

        return convertView;
    }


    //第一个Item的ViewHolder
    private class FirstItemViewHolder {
        ImageView imageView;
    }

    //除第一个Item以外其余Item的ViewHolder
    private class OthersViewHolder {
        TextView textView;

    }


}

