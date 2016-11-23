package com.jju.edu.aiqiyi.wode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.Toast;
import com.jju.edu.aiqiyi.R;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by 凌浩 on 2016/11/22.
 */

public class LoginActivity extends Activity implements TextWatcher{
    private UMShareAPI mShareAPI = null;
    private SHARE_MEDIA platform = null;
    public static String img_get = "";
    public static String name_get = "";
    public static String uid_get= "";
    private ImageView login_back;
    private ImageView qq_img_login;
    private Button button_login;
    private EditText et_username,et_password;
    private SlidingDrawer sliding;
    private ImageView handle_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        mShareAPI = UMShareAPI.get( LoginActivity.this );

        init_view();

        sliding = (SlidingDrawer) findViewById(R.id.sliding);
        handle_img = (ImageView) findViewById(R.id.handle_img);
        sliding.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                handle_img.setBackgroundResource(R.drawable.player_common_expand_normal);
            }
        });
        sliding.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                handle_img.setBackgroundResource(R.drawable.player_common_collapse_normal);
            }
        });


    }

    public void init_view(){
        login_back = (ImageView) findViewById(R.id.login_back);
        qq_img_login = (ImageView) findViewById(R.id.qq_img_login);
        button_login = (Button) findViewById(R.id.button_login);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_username.addTextChangedListener(LoginActivity.this);
        et_password.addTextChangedListener(LoginActivity.this);
        login_back.setOnClickListener(new myonclick());
        qq_img_login.setOnClickListener(new myonclick());
        button_login.setOnClickListener(new myonclick());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    private UMAuthListener umAuthListener = new UMAuthListener() {

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            img_get = map.get("profile_image_url");
            name_get = map.get("screen_name");
            uid_get = map.get("uid");
            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {

        }
    };


    /**
     * 文本框字符串改变事件
     * */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
    @Override
    public void afterTextChanged(Editable s) {
        if (et_username.getText().toString().length()>0&&et_password.getText().toString().length()>0){
            button_login.setBackgroundColor(getResources().getColor(R.color.result_points));
        }else {
            button_login.setBackgroundColor(getResources().getColor(R.color.result_minor_text));
        }
    }

    //按钮监听事件
    class myonclick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.qq_img_login:
                    Toast.makeText(LoginActivity.this,"功能暂未开启...",Toast.LENGTH_SHORT).show();
                    platform = SHARE_MEDIA.QQ;
                    mShareAPI.getPlatformInfo(LoginActivity.this, platform, umAuthListener);
                    break;
                case R.id.button_login:
                    Toast.makeText(LoginActivity.this,"功能暂未开启...",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.login_back:
                    finish();
                    break;

            }

        }
    }
}
