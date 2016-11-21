package com.jju.edu.aiqiyi;

import android.app.AlertDialog;
import android.app.Dialog;
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

import com.jju.edu.aiqiyi.tuijian.DianShiJuFragment;
import com.jju.edu.aiqiyi.tuijian.DongManFragment;
import com.jju.edu.aiqiyi.tuijian.ZongYiFragment;
import com.jju.edu.aiqiyi.tuijian.GaoxiaoFragment;
import com.jju.edu.aiqiyi.tuijian.MovieFragment;
import com.jju.edu.aiqiyi.tuijian.TuijianFragment;
import com.jju.edu.aiqiyi.wode.LocalVideoActivity;
import com.jju.edu.aiqiyi.wode.PlayHistoryActivity;
import com.jju.edu.aiqiyi.wode.SearchActivity;
import com.jju.edu.aiqiyi.wode.SearchResultActivity;
import com.jju.edu.aiqiyi.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 凌浩 on 2016/11/14.
 */

public class TuiJianActivity extends BaseFragmentActivity {

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

    private ImageView search, history, outline, more;
    private LinearLayout ll_search;
    private PopupWindow popupWindow;

    private static final String[] titles = new String[]{"推荐", "综艺", "动漫", "电视剧", "电影", "搞笑"};
    private LinearLayout pop_ll_upload;
    private LinearLayout pop_ll_scan;

    public static String scan_info = "";

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

        if (scan_info.equals("")){

        }else {
            Intent intent_search_result = new Intent(TuiJianActivity.this,SearchResultActivity.class);
            intent_search_result.putExtra("path",scan_info);
            startActivity(intent_search_result);
        }
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.container);
        fragments = new ArrayList<>();

        tuijian_fragment = new TuijianFragment();
        dreamvoice_fragment = new ZongYiFragment();
        dingyue_fragment = new DongManFragment();
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
    class myclick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.search:  //搜索
                    startActivity(new Intent(TuiJianActivity.this, SearchActivity.class));
                    break;
                case R.id.history:  //播放记录
                    startActivity(new Intent(TuiJianActivity.this, PlayHistoryActivity.class));
                    break;
                case R.id.outline:   //本地视频
                    startActivity(new Intent(TuiJianActivity.this, LocalVideoActivity.class));
                    break;
                case R.id.more:  //pop_window
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
                    popupWindow.showAsDropDown(more);
                    pop_ll_upload.setOnClickListener(popOnClick);
                    pop_ll_scan.setOnClickListener(popOnClick);
                    break;
                case R.id.ll_search:
                    Toast.makeText(TuiJianActivity.this, "", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private View.OnClickListener popOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.pop_ll_upload:
                    Toast.makeText(TuiJianActivity.this, "该功能尚未实现！", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.pop_ll_scan:
                    Intent intent_scan = new Intent(TuiJianActivity.this,CaptureActivity.class);
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
            AlertDialog.Builder dialog = new AlertDialog.Builder(TuiJianActivity.this);
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
