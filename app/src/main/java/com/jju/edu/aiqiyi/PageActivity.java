package com.jju.edu.aiqiyi;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.jju.edu.aiqiyi.sqlite.MySqliteOpenHelper;
import com.jju.edu.aiqiyi.wode.LoginActivity;
import com.jju.edu.aiqiyi.wode.SettingActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by 凌浩 on 2016/11/14.
 */

public class PageActivity extends BaseActivity{

    private TabHost tab_host;
    private ImageView image1, image2, image3, image4,image5;
    private TextView text1, text2, text3, text4,text5;
    public static LocalActivityManager manager;
    private MySqliteOpenHelper mySqliteOpenHelper;
    public static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_layout);

        mySqliteOpenHelper = new MySqliteOpenHelper(PageActivity.this,"message.db",null,1);
        db = mySqliteOpenHelper.getReadableDatabase();


        tab_host = (TabHost) findViewById(R.id.table_host);

        //设置tabhost
        manager = new LocalActivityManager(PageActivity.this, true);
        manager.dispatchCreate(savedInstanceState);
        tab_host.setup(manager);

        Intent intent1 = new Intent(PageActivity.this, TuiJianActivity.class);
        View view1 = getLayoutInflater().inflate(R.layout.buttom_layout, null);
        image1 = (ImageView) view1.findViewById(R.id.img);
        text1 = (TextView) view1.findViewById(R.id.text);
        image1.setBackgroundResource(R.drawable.phone_navi_recom_selected);
        text1.setText("推荐");
        text1.setTextColor(Color.GREEN);
        TabHost.TabSpec spec1 = tab_host.newTabSpec("onespec").setIndicator(view1).setContent(intent1);
        tab_host.addTab(spec1);

        Intent intent2 = new Intent(PageActivity.this, DaoHangActivity.class);
        View view2 = getLayoutInflater().inflate(R.layout.buttom_layout, null);
        image2 = (ImageView) view2.findViewById(R.id.img);
        text2 = (TextView) view2.findViewById(R.id.text);
        image2.setBackgroundResource(R.drawable.phone_navi_cate);
        text2.setText("导航");
        text2.setTextColor(Color.GRAY);
        TabHost.TabSpec spec2 = tab_host.newTabSpec("twospec").setIndicator(view2).setContent(intent2);
        tab_host.addTab(spec2);

        Intent intent3 = new Intent(PageActivity.this, HuiYuanActivity.class);
        View view3 = getLayoutInflater().inflate(R.layout.buttom_layout, null);
        image3 = (ImageView) view3.findViewById(R.id.img);
        text3 = (TextView) view3.findViewById(R.id.text);
        image3.setBackgroundResource(R.drawable.phone_navi_vip);
        text3.setText("会员");
        text3.setTextColor(Color.GRAY);
        TabHost.TabSpec spec3 = tab_host.newTabSpec("threespec").setIndicator(view3).setContent(intent3);
        tab_host.addTab(spec3);

        Intent intent4 = new Intent(PageActivity.this, WoDeActivity.class);
        View view4 = getLayoutInflater().inflate(R.layout.buttom_layout, null);
        image4 = (ImageView) view4.findViewById(R.id.img);
        text4 = (TextView) view4.findViewById(R.id.text);
        image4.setBackgroundResource(R.drawable.phone_navi_my);
        text4.setText("我的");
        text4.setTextColor(Color.GRAY);
        TabHost.TabSpec spec4 = tab_host.newTabSpec("fourspec").setIndicator(view4).setContent(intent4);
        tab_host.addTab(spec4);

        Intent intent5 = new Intent(PageActivity.this, JiaoYouActivity.class);
        View view5 = getLayoutInflater().inflate(R.layout.buttom_layout, null);
        image5 = (ImageView) view5.findViewById(R.id.img);
        text5 = (TextView) view5.findViewById(R.id.text);
        image5.setBackgroundResource(R.drawable.phone_navi_friend);
        text5.setText("发现");
        text5.setTextColor(Color.GRAY);
        TabHost.TabSpec spec5 = tab_host.newTabSpec("fivespec").setIndicator(view5).setContent(intent5);
        tab_host.addTab(spec5);

        //设置默认界面
        tab_host.setCurrentTabByTag("onespec");

        tab_host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("onespec")) {
                    image1.setBackgroundResource(R.drawable.phone_navi_recom_selected);
                    text1.setTextColor(Color.GREEN);
                    image2.setBackgroundResource(R.drawable.phone_navi_cate);
                    text2.setTextColor(Color.GRAY);
                    image3.setBackgroundResource(R.drawable.phone_navi_vip);
                    text3.setTextColor(Color.GRAY);
                    image4.setBackgroundResource(R.drawable.phone_navi_my);
                    text4.setTextColor(Color.GRAY);
                    image5.setBackgroundResource(R.drawable.phone_navi_friend);
                    text5.setTextColor(Color.GRAY);
                } else if (tabId.equals("twospec")) {
                    image1.setBackgroundResource(R.drawable.phone_navi_recom);
                    text1.setTextColor(Color.GRAY);
                    image2.setBackgroundResource(R.drawable.phone_navi_cate_selected);
                    text2.setTextColor(Color.GREEN);
                    image3.setBackgroundResource(R.drawable.phone_navi_vip);
                    text3.setTextColor(Color.GRAY);
                    image4.setBackgroundResource(R.drawable.phone_navi_my);
                    text4.setTextColor(Color.GRAY);
                    image5.setBackgroundResource(R.drawable.phone_navi_friend);
                    text5.setTextColor(Color.GRAY);
                } else if (tabId.equals("threespec")) {
                    image1.setBackgroundResource(R.drawable.phone_navi_recom);
                    text1.setTextColor(Color.GRAY);
                    image2.setBackgroundResource(R.drawable.phone_navi_cate);
                    text2.setTextColor(Color.GRAY);
                    image3.setBackgroundResource(R.drawable.phone_navi_vip_selected);
                    text3.setTextColor(Color.GREEN);
                    image4.setBackgroundResource(R.drawable.phone_navi_my);
                    text4.setTextColor(Color.GRAY);
                    image5.setBackgroundResource(R.drawable.phone_navi_friend);
                    text5.setTextColor(Color.GRAY);
                } else if (tabId.equals("fourspec")) {
                    image1.setBackgroundResource(R.drawable.phone_navi_recom);
                    text1.setTextColor(Color.GRAY);
                    image2.setBackgroundResource(R.drawable.phone_navi_cate);
                    text2.setTextColor(Color.GRAY);
                    image3.setBackgroundResource(R.drawable.phone_navi_vip);
                    text3.setTextColor(Color.GRAY);
                    image4.setBackgroundResource(R.drawable.phone_navi_my_selected);
                    text4.setTextColor(Color.GREEN);
                    image5.setBackgroundResource(R.drawable.phone_navi_friend);
                    text5.setTextColor(Color.GRAY);
                }else if (tabId.equals("fivespec")) {
                    image1.setBackgroundResource(R.drawable.phone_navi_recom);
                    text1.setTextColor(Color.GRAY);
                    image2.setBackgroundResource(R.drawable.phone_navi_cate);
                    text2.setTextColor(Color.GRAY);
                    image3.setBackgroundResource(R.drawable.phone_navi_vip);
                    text3.setTextColor(Color.GRAY);
                    image4.setBackgroundResource(R.drawable.phone_navi_my);
                    text4.setTextColor(Color.GRAY);
                    image5.setBackgroundResource(R.drawable.phone_navi_friend_selected);
                    text5.setTextColor(Color.GREEN);
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);

    }
    @Override
    protected void onResume() {
        super.onResume();
        manager.dispatchResume();

        if (LoginActivity.islogin ){
            ImageLoader.getInstance().displayImage(LoginActivity.img_get,WoDeActivity.user_img);
            WoDeActivity.user_name.setText(LoginActivity.name_get);
            WoDeActivity.user_desc.setText("尊敬的VIP会员 "+LoginActivity.name_get+" 欢迎你！");
        }else {
            LoginActivity.islogin = false;
            if (WoDeActivity.user_img != null) {
                WoDeActivity.user_img.setImageResource(R.drawable.phone_my_main_icon_avatar);
                WoDeActivity.user_name.setText("未登录");
                WoDeActivity.user_desc.setText("登陆后可享受更多云服务");
            }
        }
    }
}
