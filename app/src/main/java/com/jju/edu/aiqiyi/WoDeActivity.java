package com.jju.edu.aiqiyi;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.jju.edu.aiqiyi.wode.SearchActivity;

/**
 * Created by 凌浩 on 2016/11/14.
 */

public class WoDeActivity extends BaseActivity{
    private ImageView search,plus;
    private PopupWindow popupWindow;
    private LinearLayout denglu,kaitongvip,xiaoxi,lixianguankan,bofangjilu,shoucang,shangchuan,shebei,
            yue,pifu,bangzhu,diqu,shezhi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wode_layout);
        initview();
    }
    //控件初始化
    public void initview(){

        denglu = (LinearLayout) findViewById(R.id.denglu);
        kaitongvip = (LinearLayout) findViewById(R.id.kaitongvip);
        xiaoxi = (LinearLayout) findViewById(R.id.xiaoxi);
        lixianguankan = (LinearLayout) findViewById(R.id.lixianguankan);
        bofangjilu = (LinearLayout) findViewById(R.id.bofangjilu);
        shoucang = (LinearLayout) findViewById(R.id.shoucang);
        shangchuan = (LinearLayout) findViewById(R.id.shangchuan);
        shebei = (LinearLayout) findViewById(R.id.shebei);
        yue = (LinearLayout) findViewById(R.id.yue);
        pifu = (LinearLayout) findViewById(R.id.pifu);
        bangzhu = (LinearLayout) findViewById(R.id.bangzhu);
        diqu = (LinearLayout) findViewById(R.id.diqu);
        shezhi = (LinearLayout) findViewById(R.id.shezhi);

        denglu.setOnClickListener(new myclick());
        kaitongvip.setOnClickListener(new myclick());
        xiaoxi.setOnClickListener(new myclick());
        lixianguankan.setOnClickListener(new myclick());
        bofangjilu.setOnClickListener(new myclick());
        shoucang.setOnClickListener(new myclick());
        shangchuan.setOnClickListener(new myclick());
        shebei.setOnClickListener(new myclick());
        yue.setOnClickListener(new myclick());
        pifu.setOnClickListener(new myclick());
        bangzhu.setOnClickListener(new myclick());
        diqu.setOnClickListener(new myclick());
        shezhi.setOnClickListener(new myclick());

        search = (ImageView) findViewById(R.id.search);
        plus = (ImageView) findViewById(R.id.plus);
        //开始监听
        search.setOnClickListener(new myclick());
        plus.setOnClickListener(new myclick());

    }
    //标题按钮监听事件
    class myclick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.search:
                    startActivity(new Intent(WoDeActivity.this, SearchActivity.class));
                    break;
                case R.id.plus:
                    View view = getLayoutInflater().inflate(R.layout.popupwindow_layout, null);
                    popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    //设置背景
                    popupWindow.setBackgroundDrawable(new ColorDrawable());
                    //设置为可点击
                    popupWindow.setFocusable(true);
                    popupWindow.setContentView(view);
                    //设置出现在当前点击控件的正下方
                    popupWindow.showAsDropDown(plus);
                    break;
                case R.id.denglu:
                    Toast.makeText(WoDeActivity.this,"",Toast.LENGTH_SHORT).show();
                    break;

            }

        }
    }

}
