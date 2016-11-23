package com.jju.edu.aiqiyi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jju.edu.aiqiyi.wode.CollectActivity;
import com.jju.edu.aiqiyi.wode.LocalVideoActivity;
import com.jju.edu.aiqiyi.wode.LoginActivity;
import com.jju.edu.aiqiyi.wode.PlayHistoryActivity;
import com.jju.edu.aiqiyi.wode.SearchActivity;
import com.jju.edu.aiqiyi.wode.WallPaperActivity;
import com.jju.edu.aiqiyi.wode.XiaoXIActivity;
import com.jju.edu.aiqiyi.wode.SettingActivity;
import com.jju.edu.aiqiyi.wode.settings.YuEActivity;
import com.jju.edu.aiqiyi.zxing.activity.CaptureActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by 凌浩 on 2016/11/14.
 */

public class WoDeActivity extends BaseActivity{

    private ImageView search,plus;
    private PopupWindow popupWindow;
    private LinearLayout denglu,kaitongvip,xiaoxi,lixianguankan,bofangjilu,shoucang,shangchuan,shebei,
            yue,pifu,bangzhu,diqu,shezhi;

    private LinearLayout pop_ll_upload;
    private LinearLayout pop_ll_scan;

    public static ImageView user_img;
    public static  TextView user_name,user_desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wode_layout);

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
                WoDeActivity.this).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(configuration);

        initview();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("$$$$$$$$$$$$$$$$$$",LoginActivity.img_get+""+LoginActivity.name_get);
        if (LoginActivity.img_get.equals("")){
        }else {
            ImageLoader.getInstance().displayImage(LoginActivity.img_get,user_img);
            user_name.setText(LoginActivity.name_get);
            user_desc.setText("尊敬的VIP会员 "+LoginActivity.name_get+" 欢迎你！");
        }
    }

    //控件初始化
    public void initview(){

        user_img = (ImageView) findViewById(R.id.user_img);
        user_name = (TextView) findViewById(R.id.user_name);
        user_desc = (TextView) findViewById(R.id.user_desc);

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
                case R.id.denglu:
                    startActivity(new Intent(WoDeActivity.this, LoginActivity.class));
                    break;
                case R.id.lixianguankan:
                    startActivity(new Intent(WoDeActivity.this, LocalVideoActivity.class));
                    break;
                case R.id.bofangjilu:
                    startActivity(new Intent(WoDeActivity.this, PlayHistoryActivity.class));
                    break;
                case R.id.shoucang:
                    startActivity(new Intent(WoDeActivity.this, CollectActivity.class));
                    break;
                case R.id.pifu:
                    startActivity(new Intent(WoDeActivity.this, WallPaperActivity.class));
                    break;
                case R.id.xiaoxi:
                    startActivity(new Intent(WoDeActivity.this,XiaoXIActivity.class));
                    break;
                case R.id.shezhi:
                    startActivity(new Intent(WoDeActivity.this, SettingActivity.class));
                    break;
                case R.id.yue:
                    startActivity(new Intent(WoDeActivity.this, YuEActivity.class));
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
                    Toast.makeText(WoDeActivity.this, "该功能尚未实现！", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.pop_ll_scan:
                    Intent intent_scan = new Intent(WoDeActivity.this,CaptureActivity.class);
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
            AlertDialog.Builder dialog = new AlertDialog.Builder(WoDeActivity.this);
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
