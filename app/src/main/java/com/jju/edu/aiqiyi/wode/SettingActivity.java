package com.jju.edu.aiqiyi.wode;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jju.edu.aiqiyi.BaseActivity;
import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.wode.settings.MessagePush;
import com.jju.edu.aiqiyi.wode.settings.PlayAndDownloadActivity;
import com.jju.edu.aiqiyi.wode.settings.PluginActivity;
import com.jju.edu.aiqiyi.wode.settings.UsefulActivity;
import com.jju.edu.aiqiyi.wode.settings.UserManagerActivity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by 凌浩 on 2016/11/22.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_setting_back;
    private RelativeLayout rl_user_manager;
    private RelativeLayout rl_useful;
    private RelativeLayout rl_plugin;
    private RelativeLayout rl_play_download, relative_message_, relative_clear, relative_update;
    private LinearLayout login_out;
    private UMShareAPI mShareAPI = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

        initView();
        mShareAPI = UMShareAPI.get( this );
    }

    private void initView() {
        iv_setting_back = (ImageView) findViewById(R.id.iv_setting_back);
        rl_user_manager = (RelativeLayout) findViewById(R.id.rl_user_manager);
        rl_play_download = (RelativeLayout) findViewById(R.id.rl_play_download);
        rl_useful = (RelativeLayout) findViewById(R.id.rl_useful);
        relative_message_ = (RelativeLayout) findViewById(R.id.relative_message_);
        relative_clear = (RelativeLayout) findViewById(R.id.relative_clear);
        relative_update = (RelativeLayout) findViewById(R.id.relative_update);
        rl_plugin = (RelativeLayout) findViewById(R.id.rl_plugin);

        iv_setting_back.setOnClickListener(this);
        rl_user_manager.setOnClickListener(this);
        rl_play_download.setOnClickListener(this);
        relative_message_.setOnClickListener(this);
        relative_clear.setOnClickListener(this);
        relative_update.setOnClickListener(this);
        rl_useful.setOnClickListener(this);
        rl_plugin.setOnClickListener(this);

        login_out = (LinearLayout)findViewById(R.id.login_out) ;
        if (LoginActivity.islogin){
            login_out.setVisibility(View.VISIBLE);
        }else{
            login_out.setVisibility(View.GONE);
        }
        login_out.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_setting_back:
                finish();
                break;
            case R.id.rl_user_manager:
                if(LoginActivity.islogin) {
                    startActivity(new Intent(SettingActivity.this, UserManagerActivity.class));
                }else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(SettingActivity.this);
                    dialog.setTitle("提示");
                    dialog.setMessage("您还没有登录！");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }
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
            case R.id.rl_plugin:
                startActivity(new Intent(SettingActivity.this,PluginActivity.class));
                break;
            case R.id.login_out:
                mShareAPI.deleteOauth(SettingActivity.this, SHARE_MEDIA.QQ, umdelAuthListener);
            default:
                break;
        }
    }

    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 123) {
                login_out.setVisibility(View.GONE);
                LoginActivity.islogin = false;
                LoginActivity.img_get = "";
                LoginActivity.name_get = "";
                LoginActivity.uid_get= "";
                LoginActivity.gender_get = "";
                LoginActivity.where_get="";
            }
        }
    };
    /** delauth callback interface**/
    private UMAuthListener umdelAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            //    Log.e("#####","退出成功");
            //    Toast.makeText(getApplicationContext(), "delete Authorize succeed", Toast.LENGTH_SHORT).show();

            new Thread(){
                @Override
                public void run() {
                    super.run();
                    Message msg = new Message();
                    msg.what =123;

                    handler.sendMessage(msg);
                }
            }.start();
        }
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            //       Toast.makeText( getApplicationContext(), "delete Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            //       Toast.makeText( getApplicationContext(), "delete Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //    Log.e("kakaoxx", "requestCode="+requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }
}

