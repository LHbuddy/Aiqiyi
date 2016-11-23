package com.jju.edu.aiqiyi.wode;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
    private RelativeLayout rl_play_download, relative_message_, relative_clear, relative_update;

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
        relative_clear = (RelativeLayout) findViewById(R.id.relative_clear);
        relative_update = (RelativeLayout) findViewById(R.id.relative_update);

        iv_setting_back.setOnClickListener(this);
        rl_user_manager.setOnClickListener(this);
        rl_play_download.setOnClickListener(this);
        relative_message_.setOnClickListener(this);
        relative_clear.setOnClickListener(this);
        relative_update.setOnClickListener(this);
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
            case R.id.relative_clear:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("提示");
                dialog.setMessage("清除缓存可能会降低流畅度，请三思啊(不会删除已离线的影片)");
                dialog.setCancelable(false);
                dialog.setPositiveButton("清除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SettingActivity.this, "已清除", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("取消", null);
                dialog.show();
                break;
            case R.id.relative_update:
                AlertDialog.Builder dialog_update = new AlertDialog.Builder(this);
                dialog_update.setTitle("新版本升级提醒");
                dialog_update.setMessage("更稳定、快速、多彩的功能和体验，立即点击升级！");
                dialog_update.setCancelable(false);
                dialog_update.setNegativeButton("立即升级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SettingActivity.this, "开始下载新版本...", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog_update.setPositiveButton("稍后再说", null);
                dialog_update.show();
                break;
            case R.id.rl_useful:
                startActivity(new Intent(SettingActivity.this, UsefulActivity.class));
                break;
        }
    }
}
