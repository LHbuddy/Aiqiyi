package com.jju.edu.aiqiyi.wode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jju.edu.aiqiyi.BaseActivity;
import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.wode.settings.MessagePush;
import com.jju.edu.aiqiyi.wode.settings.PlayAndDownloadActivity;
import com.jju.edu.aiqiyi.wode.settings.UsefulActivity;
import com.jju.edu.aiqiyi.wode.settings.UserManagerActivity;

/**
 * Created by 凌浩 on 2016/11/22.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_setting_back;
    private RelativeLayout rl_user_manager;
    private RelativeLayout rl_useful;
    private RelativeLayout rl_play_download, relative_message_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

        initView();
    }

    private void initView() {
        iv_setting_back = (ImageView) findViewById(R.id.iv_setting_back);
        rl_user_manager = (RelativeLayout) findViewById(R.id.rl_user_manager);
        rl_play_download = (RelativeLayout) findViewById(R.id.rl_play_download);
        rl_useful = (RelativeLayout) findViewById(R.id.rl_useful);
        relative_message_ = (RelativeLayout) findViewById(R.id.relative_message_);

        iv_setting_back.setOnClickListener(this);
        rl_user_manager.setOnClickListener(this);
        rl_play_download.setOnClickListener(this);
        relative_message_.setOnClickListener(this);
        rl_useful.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_setting_back:
                finish();
                break;
            case R.id.rl_user_manager:
                startActivity(new Intent(SettingActivity.this, UserManagerActivity.class));
                break;
            case R.id.rl_play_download:
                startActivity(new Intent(SettingActivity.this, PlayAndDownloadActivity.class));
                break;
            case R.id.relative_message_:
                startActivity(new Intent(SettingActivity.this, MessagePush.class));
                break;
            case R.id.rl_useful:
                startActivity(new Intent(SettingActivity.this, UsefulActivity.class));
                break;
        }
    }
}
