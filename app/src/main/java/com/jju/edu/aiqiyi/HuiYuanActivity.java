package com.jju.edu.aiqiyi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.jju.edu.aiqiyi.vipuser.MyVipFragment;
import com.jju.edu.aiqiyi.vipuser.VipGameFragment;
import com.jju.edu.aiqiyi.vipuser.VipTechnologyFragment;
import com.jju.edu.aiqiyi.vipuser.VipRecordFragment;
import com.jju.edu.aiqiyi.wode.SearchActivity;
import com.jju.edu.aiqiyi.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 凌浩 on 2016/11/14.
 */

public class HuiYuanActivity extends BaseFragmentActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private List<Fragment> vip_fragments;

    private Fragment myvip_fragment;
    private Fragment viphome_fragment;
    private Fragment vipmovie_fragment;
    private Fragment viprecord_fragment;

    private ImageView search, plus;
    private PopupWindow popupWindow;

    private LinearLayout pop_ll_upload;
    private LinearLayout pop_ll_scan;

    private static final String[] vip_titles = new String[]{"  VIP体育  ", "  VIP游戏  ", "  VIP科技  ", "  VIP纪录片  "};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.huiyuan_layout);

        search = (ImageView) findViewById(R.id.search);
        plus = (ImageView) findViewById(R.id.plus);
        search.setOnClickListener(new myonclick());
        plus.setOnClickListener(new myonclick());

    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.container);
        vip_fragments = new ArrayList<>();

        myvip_fragment = new MyVipFragment();
        viphome_fragment = new VipGameFragment();
        vipmovie_fragment = new VipTechnologyFragment();
        viprecord_fragment = new VipRecordFragment();
        vip_fragments.add(myvip_fragment);
        vip_fragments.add(viphome_fragment);
        vip_fragments.add(vipmovie_fragment);
        vip_fragments.add(viprecord_fragment);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.argb(255, 16, 225, 37));
        tabLayout.setTabTextColors(Color.BLACK, Color.argb(255, 16, 225, 37));
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

    class myonclick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.search:
                    startActivity(new Intent(HuiYuanActivity.this, SearchActivity.class));
                    break;
                case R.id.plus:
                    View view = getLayoutInflater().inflate(R.layout.popupwindow_layout, null);
                    pop_ll_upload = (LinearLayout) view.findViewById(R.id.pop_ll_upload);
                    pop_ll_scan = (LinearLayout) view.findViewById(R.id.pop_ll_scan);
                    popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    //设置背景
                    popupWindow.setBackgroundDrawable(new ColorDrawable());
                    //设置为可点击
                    popupWindow.setFocusable(true);
                    popupWindow.setContentView(view);
                    //设置出现在当前点击控件的正下方
                    popupWindow.showAsDropDown(plus);
                    pop_ll_upload.setOnClickListener(popOnClick);
                    pop_ll_scan.setOnClickListener(popOnClick);
                    break;

            }
        }
    }

    /**
     * PopWindow的点击事件
     */
    private View.OnClickListener popOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.pop_ll_upload:
                    Toast.makeText(HuiYuanActivity.this, "该功能尚未实现！", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.pop_ll_scan:
                    Intent intent_scan = new Intent(HuiYuanActivity.this,CaptureActivity.class);
                    startActivity(intent_scan);
//                    startActivityForResult(new Intent(TuiJianActivity.this, CaptureActivity.class), 0);
                    break;
            }
        }
    };
    /**
     * 双击退出操作
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(HuiYuanActivity.this);
            dialog.setTitle("提示");
            dialog.setMessage("确定退出么？");
            dialog.setNegativeButton("再看看", null);
            dialog.setPositiveButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dialog.show();
        }
        return true;
    }
}
