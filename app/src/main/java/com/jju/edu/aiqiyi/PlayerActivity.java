package com.jju.edu.aiqiyi;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by 凌浩 on 2016/11/16.
 */

public class PlayerActivity extends BaseActivity {
    private WebView webview;
    private String phone_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_layout);

        webview = (WebView) findViewById(R.id.webview);

        // 设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);

        webview.setVisibility(View.VISIBLE);
        webview.getSettings().setUseWideViewPort(true);

        String path = getIntent().getStringExtra("path");
        if (path.split("//my").length==2){
            phone_path = "http://m" +  path.split("//my")[1];
        }else if (path.split("//").length==2){
            String[] path_phone = path.split("//");
            phone_path = "http://m."+path_phone[1];
        }else {
            phone_path = path;
        }

        webview.loadUrl(phone_path);
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

    }
}
