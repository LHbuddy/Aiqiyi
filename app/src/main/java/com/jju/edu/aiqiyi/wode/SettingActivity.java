package com.jju.edu.aiqiyi.wode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jju.edu.aiqiyi.BaseActivity;
import com.jju.edu.aiqiyi.PageActivity;
import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.WoDeActivity;
import com.jju.edu.aiqiyi.wode.settings.UserManagerActivity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by 凌浩 on 2016/11/22.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rl_user_manager;
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

        rl_user_manager = (RelativeLayout) findViewById(R.id.rl_user_manager);
        login_out = (LinearLayout)findViewById(R.id.login_out) ;
        if (LoginActivity.islogin){
            login_out.setVisibility(View.VISIBLE);
        }else{
            login_out.setVisibility(View.GONE);
        }
        rl_user_manager.setOnClickListener(this);
        login_out.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_user_manager:
                startActivity(new Intent(SettingActivity.this, UserManagerActivity.class));
                break;
            case R.id.login_out:
                mShareAPI.deleteOauth(SettingActivity.this, SHARE_MEDIA.QQ, umdelAuthListener);

                break;
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
