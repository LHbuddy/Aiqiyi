package com.jju.edu.aiqiyi.wode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jju.edu.aiqiyi.R;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by 凌浩 on 2016/11/22.
 */

public class LoginActivity extends Activity{
    private UMShareAPI mShareAPI = null;
    private SHARE_MEDIA platform = null;
    public static String img_get = "";
    public static String name_get = "";
    public static String uid_get= "";
    public  static  String gender_get = "";
    public static String where_get="";
    public static boolean islogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        mShareAPI = UMShareAPI.get( LoginActivity.this );

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    public void qq(View view){
        platform = SHARE_MEDIA.QQ;
        mShareAPI.getPlatformInfo(LoginActivity.this, platform, umAuthListener);
    }
    private UMAuthListener umAuthListener = new UMAuthListener() {

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
        //    Log.e("#####",map.toString());
            if(map.toString()!=null){
                islogin =true;
            }
            img_get = map.get("profile_image_url");
            name_get = map.get("screen_name");
            uid_get = map.get("uid");
            gender_get = map.get("gender");
            where_get = map.get("province") + map.get("city");
        //    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
            finish();

        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {

        }
    };
}
