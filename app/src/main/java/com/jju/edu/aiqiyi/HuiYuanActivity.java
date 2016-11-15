package com.jju.edu.aiqiyi;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.jju.edu.aiqiyi.vipuser.MyVipFragment;
import com.jju.edu.aiqiyi.vipuser.VipHomeFragment;
import com.jju.edu.aiqiyi.vipuser.VipMovieFragment;
import com.jju.edu.aiqiyi.vipuser.VipRecordFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 凌浩 on 2016/11/14.
 */

public class HuiYuanActivity extends BaseFragmentActivity{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private List<Fragment> vip_fragments;

    private Fragment myvip_fragment;
    private Fragment viphome_fragment;
    private Fragment vipmovie_fragment;
    private Fragment viprecord_fragment;

    private static final String[] vip_titles = new String[]{"我的VIP会员","会员俱乐部","VIP会员","VIP纪录片"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tuijian_layout);

        initView();
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.container);
        vip_fragments = new ArrayList<>();

        myvip_fragment = new MyVipFragment();
        viphome_fragment = new VipHomeFragment();
        vipmovie_fragment = new VipMovieFragment();
        viprecord_fragment = new VipRecordFragment();
        vip_fragments.add(myvip_fragment);
        vip_fragments.add(viphome_fragment);
        vip_fragments.add(vipmovie_fragment);
        vip_fragments.add(viprecord_fragment);
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
            return vip_fragments.get(position);
        }

        @Override
        public int getCount() {
            return vip_titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return vip_titles[position];
        }
    }
}
