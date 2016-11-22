package com.jju.edu.aiqiyi.wode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
            Log.e("**********",""+map.toString());
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {

        }
    };
}
