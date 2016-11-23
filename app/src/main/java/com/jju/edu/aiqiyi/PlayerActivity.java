package com.jju.edu.aiqiyi;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.jju.edu.aiqiyi.sqlite.MySqliteOperation;
import com.jju.edu.aiqiyi.wode.LoginActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Calendar;

/**
 * Created by 凌浩 on 2016/11/16.
 */

public class PlayerActivity extends BaseActivity {

    private ImageView iv_video_title_back,iv_video_title_shoucang;
    private WebView webview;
    private String phone_path;
    private String path;
    private String img;
    private String name;
    private String desc;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_layout);

        uid = LoginActivity.uid_get;

        webview = (WebView) findViewById(R.id.webview);
        iv_video_title_back = (ImageView) findViewById(R.id.iv_video_title_back);
        iv_video_title_shoucang = (ImageView) findViewById(R.id.iv_video_title_shoucang);
        iv_video_title_back.setOnClickListener(new myonclick());
        iv_video_title_shoucang.setOnClickListener(new myonclick());

        // 设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.setVisibility(View.VISIBLE);
        webview.getSettings().setUseWideViewPort(true);

        path = getIntent().getStringExtra("path");
        if (path.split("//my").length==2){
            phone_path = "http://m" +  path.split("//my")[1];
        }else if (path.split("//").length==2){
            String[] path_phone = path.split("//");
            phone_path = "http://m."+path_phone[1];
        }else {
            phone_path = path;
        }


        //判断是否修改标题栏收藏图标
        if (MySqliteOperation.collect_exist(PageActivity.db,path,uid)){
            iv_video_title_shoucang.setBackgroundResource(R.drawable.ic_comment_favour_bt_selected);
        }else {
            iv_video_title_shoucang.setBackgroundResource(R.drawable.ic_comment_favour_bt);
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


        getmessage_();

    }

    //获取播放视频的信息
    public void getmessage_(){
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Document document = Jsoup.connect(path).get();
                    Elements elements = document.select("meta");
                   // Log.e("*************",""+elements.size());
                    img = elements.get(13).getElementsByTag("meta").attr("content");
                    name = elements.get(4).getElementsByTag("meta").attr("content");
                    desc = elements.get(2).getElementsByTag("meta").attr("content");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(123);
            }
        }.start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 123:
                    //数据库操作
                    if (MySqliteOperation.history_exist(PageActivity.db,path,uid)){
                    }else {
                        MySqliteOperation.history_add(PageActivity.db,img,name,desc,path,gettime(),uid);
                    }
                    break;

            }
        }
    };

    //获取当前时间
    public String gettime(){
        Calendar calendar = Calendar.getInstance();
        String time = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
        return time;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //图片按钮点击事件
    class myonclick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_video_title_back:
                    finish();
                    break;
                case R.id.iv_video_title_shoucang:
                    if (MySqliteOperation.collect_exist(PageActivity.db,path,uid)){
                        MySqliteOperation.collect_delete_one(PageActivity.db,path,uid);
                        Toast.makeText(PlayerActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
                        iv_video_title_shoucang.setBackgroundResource(R.drawable.ic_comment_favour_bt);
                    }else {
                        MySqliteOperation.collect_add(PageActivity.db,img,name,desc,path,uid);
                        Toast.makeText(PlayerActivity.this,"已添加到收藏夹",Toast.LENGTH_SHORT).show();
                        iv_video_title_shoucang.setBackgroundResource(R.drawable.ic_comment_favour_bt_selected);
                    }
                    break;
            }
        }
    }

}
