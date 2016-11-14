package com.jju.edu.aiqiyi;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.jju.edu.aiqiyi.tuijian.DianShiJuFragment;
import com.jju.edu.aiqiyi.tuijian.DingyueFragment;
import com.jju.edu.aiqiyi.tuijian.DreamVoiceFragment;
import com.jju.edu.aiqiyi.tuijian.GaoxiaoFragment;
import com.jju.edu.aiqiyi.tuijian.MovieFragment;
import com.jju.edu.aiqiyi.tuijian.TuijianFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 凌浩 on 2016/11/14.
 */

public class TuiJianActivity extends BaseFragmentActivity{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private List<Fragment> fragments;

    private Fragment tuijian_fragment;
    private Fragment dreamvoice_fragment;
    private Fragment dingyue_fragment;
    private Fragment dianshiju_fragment;
    private Fragment movie_fragment;
    private Fragment gaoxiao_fragment;

    private ImageView search,history,outline,more;
    private LinearLayout ll_search;
    private PopupWindow popupWindow;

    private static final String[] titles = new String[]{"推荐","梦想的声音","订阅","电视剧","电影","搞笑"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tuijian_layout);

        search = (ImageView) findViewById(R.id.search);
        history = (ImageView) findViewById(R.id.history);
        outline = (ImageView) findViewById(R.id.outline);
        more = (ImageView) findViewById(R.id.more);
        ll_search = (LinearLayout) findViewById(R.id.ll_search);

        //设置监听
        search.setOnClickListener(new myclick());
        history.setOnClickListener(new myclick());
        outline.setOnClickListener(new myclick());
        more.setOnClickListener(new myclick());
        ll_search.setOnClickListener(new myclick());

        initView();
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.container);
        fragments = new ArrayList<>();

        tuijian_fragment = new TuijianFragment();
        dreamvoice_fragment = new DreamVoiceFragment();
        dingyue_fragment = new DingyueFragment();
        dianshiju_fragment = new DianShiJuFragment();
        movie_fragment = new MovieFragment();
        gaoxiao_fragment = new GaoxiaoFragment();
        fragments.add(tuijian_fragment);
        fragments.add(dreamvoice_fragment);
        fragments.add(dingyue_fragment);
        fragments.add(dianshiju_fragment);
        fragments.add(movie_fragment);
        fragments.add(gaoxiao_fragment);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.argb(255,16,225,37));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    //标题栏监听事件
    class myclick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.search:
                    Toast.makeText(TuiJianActivity.this,"",Toast.LENGTH_SHORT).show();
                break;
                case R.id.history:
                    Toast.makeText(TuiJianActivity.this,"",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.outline:
                    Toast.makeText(TuiJianActivity.this,"",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.more:
                    View view = getLayoutInflater().inflate(R.layout.popupwindow_layout, null);
                    popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    //设置背景
                    popupWindow.setBackgroundDrawable(new ColorDrawable());
                    //设置为可点击
                    popupWindow.setFocusable(true);
                    popupWindow.setContentView(view);
                    //设置出现在当前点击控件的正下方
                    popupWindow.showAsDropDown(more);
                    break;
                case R.id.ll_search:
                    Toast.makeText(TuiJianActivity.this,"",Toast.LENGTH_SHORT).show();
                    break;

            }

        }
    }
}
