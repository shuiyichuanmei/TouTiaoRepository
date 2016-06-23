package com.shuiyi.app.toutiao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shuiyi.app.toutiao.common.Common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2016/6/22.
 */
public class XinWenFragment extends Fragment {

    private ImageButton searchbtn;
    private ImageButton btnShoucang;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        initdata();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    private View contentview;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentview == null) {
            contentview = initview(inflater);
        }
        return contentview;
    }

    private FragmentManager fragmentManager;

    private List<Fragment> xinwen_framentlist;

    //初始化数据
    private void initdata() {
        xinwen_framentlist = new ArrayList<>();
        TouTiaoFragment toutiao = new TouTiaoFragment();
        Bundle bundletoutiao = new Bundle();
        bundletoutiao.putString("xinwendaohang", "头条");
        toutiao.setArguments(bundletoutiao);
        xinwen_framentlist.add(toutiao);

        TouTiaoFragment yule = new TouTiaoFragment();
        Bundle bundleyule = new Bundle();
        bundleyule.putString("xinwendaohang", "娱乐");
        yule.setArguments(bundleyule);
        xinwen_framentlist.add(yule);

        TouTiaoFragment tiyu = new TouTiaoFragment();
        Bundle bundletiyu = new Bundle();
        bundletiyu.putString("xinwendaohang", "体育");
        tiyu.setArguments(bundletiyu);
        xinwen_framentlist.add(tiyu);

    }


    //初始化控件
    private View initview(LayoutInflater inflater) {
        //System.out.println(getFragmentManager().getFragments().size()+"    ------------------------------------------");

        View view = inflater.inflate(R.layout.xinwenfragment, null, false);


        searchbtn = (ImageButton) view.findViewById(R.id.imageButton);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Common.isDenglu(getActivity()))
                {
                    Intent intent = new Intent(getActivity(), DengluActivity.class);
                    startActivity(intent);
                    return;
                }
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        btnShoucang = (ImageButton) view.findViewById(R.id.btnShoucang);
        btnShoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DengluActivity.class);
                startActivity(intent);
            }
        });

        //新闻导航栏控件
        final RadioGroup xinwen_Rradio = (RadioGroup) view.findViewById(R.id.xinwen_radiogroup);
        final ViewPager xinwen_viewpage = (ViewPager) view.findViewById(R.id.xinwen_viewpager);
        final HorizontalScrollView xinwen_scrollView = (HorizontalScrollView) view.findViewById(R.id.xinwen_scroll);
        final TextView xinwen_indicator = (TextView) view.findViewById(R.id.xinwen_indicator);
        //新闻页面的adapter
        XinWenFragmentAdapter fragmentAdapter = new XinWenFragmentAdapter(getActivity().getSupportFragmentManager());
        xinwen_viewpage.setAdapter(fragmentAdapter);
        xinwen_viewpage.setOffscreenPageLimit(2);
        xinwen_Rradio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //选中的RadioButton播放动画
                ScaleAnimation sAnim = new ScaleAnimation(1, 1.1f, 1, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                sAnim.setDuration(500);
                sAnim.setFillAfter(true);
                //遍历所有的RadioButton
                for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton radioBtn = (RadioButton) group.getChildAt(i);
                    if (radioBtn.isChecked()) {
                        radioBtn.startAnimation(sAnim);
                    } else {
                        radioBtn.clearAnimation();
                    }
                }
                switch (checkedId) {
                    case R.id.xinwen_rb1:
                        xinwen_viewpage.setCurrentItem(0);
                        break;
                    case R.id.xinwen_rb2:
                        xinwen_viewpage.setCurrentItem(1);
                        break;
                    case R.id.xinwen_rb3:
                        xinwen_viewpage.setCurrentItem(2);
                        break;
                }
            }
        });

        xinwen_viewpage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                // 获取对应位置的RadioButton
                RadioButton radioBtn = (RadioButton) xinwen_Rradio.getChildAt(arg0);
                // 设置对应位置的RadioButton为选中的状态
                radioBtn.setChecked(true);
                /* 滚动HorizontalScrollView使选中的RadioButton处于屏幕中间位置 */
                //获取屏幕信息
                DisplayMetrics outMetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
                //获取每一个RadioButton的宽度
                int radioBtnPiexls = radioBtn.getWidth();
                //计算滚动距离
                int distance = (int) ((arg0 + 0.5) * radioBtnPiexls - outMetrics.widthPixels / 2);
                //滚动
                xinwen_scrollView.scrollTo(distance, 0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                /* 伴随着ViewPager的滑动，滚动指示条TextView */
                // 获取TextView在其父容器LinearLayout中的布局参数
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) xinwen_indicator
                        .getLayoutParams();
                params.leftMargin = (int) ((arg0 + arg1) * params.width);
                xinwen_indicator.setLayoutParams(params);
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

        return view;
    }


    //新闻viewpager的填充类
    class XinWenFragmentAdapter extends FragmentPagerAdapter {
        public XinWenFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return xinwen_framentlist.get(arg0);
        }

        @Override
        public int getCount() {
            return xinwen_framentlist.size();
        }

    }

}
