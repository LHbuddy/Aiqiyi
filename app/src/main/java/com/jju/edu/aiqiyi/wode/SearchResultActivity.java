package com.jju.edu.aiqiyi.wode;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jju.edu.aiqiyi.R;

/**
 * Created by Administrator on 2016/11/20.
 */

public class SearchResultActivity extends Activity {
    private WebView search_webview;
    private String path = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_result_layout);
        search_webview = (WebView) findViewById(R.id.search_webview);

        path = getIntent().getStringExtra("path");

        // 设置WebView属性，能够执行Javascript脚本
        search_webview.getSettings().setJavaScriptEnabled(true);
        search_webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        search_webview.setVisibility(View.VISIBLE);
        search_webview.getSettings().setUseWideViewPort(true);

        search_webview.loadUrl(path);
        search_webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            if (search_webview.canGoBack()){
                search_webview.goBack();// 返回前一个页面
            }else {
                SearchResultActivity.this.finish();
            }
        }
        return true;
    }
}
