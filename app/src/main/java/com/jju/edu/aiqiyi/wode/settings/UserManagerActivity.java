package com.jju.edu.aiqiyi.wode.settings;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.WoDeActivity;
import com.jju.edu.aiqiyi.wode.LoginActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by Administrator on 2016/11/22.
 */

public class UserManagerActivity extends Activity {

    private ImageView img_back;
    public static ImageView info_img;
    public static TextView info_name,info_gender,info_where;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.userinfolayout);

        img_back  = (ImageView)findViewById(R.id.iv_info_back);
        info_img = (ImageView)findViewById(R.id.info_icon);
        info_name = (TextView)findViewById(R.id.info_nickname);
        info_gender = (TextView)findViewById(R.id.info_gender);
        info_where  = (TextView)findViewById(R.id.info_provice);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        info_name.setText("昵称:"+LoginActivity.name_get);
        info_gender.setText("性别:"+LoginActivity.gender_get);
        info_where.setText("所在地:"+LoginActivity.where_get);
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
                UserManagerActivity.this).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(configuration);

        ImageLoader.getInstance().displayImage(LoginActivity.img_get, info_img);

    }
}
