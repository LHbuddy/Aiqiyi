package com.jju.edu.aiqiyi;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

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

    private static final String[] titles = new String[]{"推荐","梦想的声音","订阅","电视剧","电影","搞笑"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tuijian_layout);
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
        tabLayout.setTabTextColors(Color.BLACK,Color.argb(255,16,225,37));
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
}
