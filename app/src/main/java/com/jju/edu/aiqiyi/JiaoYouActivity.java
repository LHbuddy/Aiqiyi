package com.jju.edu.aiqiyi;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.GridView;

import com.jju.edu.aiqiyi.adapter.NewsAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 凌浩 on 2016/11/14.
 */

public class JiaoYouActivity extends BaseActivity{

    private List<String> list = new ArrayList<String>();
    private List<String> list02 = new ArrayList<String>();
    private NewsAdapter adapter;
    private NewsAdapter adapter02;
    private GridView grid_view_news;
    private GridView grid_view_news02;
    private String[] video = new String[]{"电视剧","电影","动漫","综艺","搞笑","新闻"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiaoyou_layout);
        grid_view_news = (GridView) findViewById(R.id.grid_view_news);
        grid_view_news02 = (GridView) findViewById(R.id.grid_view_news02);

        getmessage_01();

    }
    //信息爬取方法
    public void getmessage_01() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Document document = Jsoup.connect("http://tv.sohu.com/news/").get();
                    Elements elements = document.select(".ni");
                    Log.e("//////////",""+elements.size());
                    for (int i = 0; i < elements.size(); i++) {
                        String name = elements.get(i).getElementsByTag("a").text();

                        Log.e("*********",""+name);
                        list.add(name);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(124);
            }
        }.start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 124:
                    Log.e("**********list_size",""+list.size());
                    adapter = new NewsAdapter(list,JiaoYouActivity.this);
                    grid_view_news.setAdapter(adapter);

                    init();

                break;

            }
        }
    };

    public void init(){
        for (int i = 0;i<video.length;i++){
            list02.add(video[i]);
        }
        adapter02 = new NewsAdapter(list02,JiaoYouActivity.this);
        grid_view_news02.setAdapter(adapter02);

    }
}
