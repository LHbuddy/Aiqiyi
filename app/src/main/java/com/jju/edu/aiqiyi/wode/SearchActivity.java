package com.jju.edu.aiqiyi.wode;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.jju.edu.aiqiyi.BaseActivity;
import com.jju.edu.aiqiyi.JiaoYouActivity;
import com.jju.edu.aiqiyi.PlayerActivity;
import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.adapter.NewsAdapter;
import com.jju.edu.aiqiyi.util.NewsUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 凌浩 on 2016/11/17.
 */

public class SearchActivity extends BaseActivity {

    private List<NewsUtil> list = new ArrayList<NewsUtil>();
    private List<NewsUtil> list02 = new ArrayList<NewsUtil>();
    private NewsAdapter adapter;
    private NewsAdapter adapter02;
    private NewsUtil util;
    private NewsUtil util02;
    private GridView grid_view_news_search;
    private TextView text_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity_layout);
        text_button = (TextView) findViewById(R.id.text_button);
        grid_view_news_search = (GridView) findViewById(R.id.grid_view_news_search);
        text_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getmessage_01();
    }
    //信息爬取方法
    public void getmessage_01() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Document document = Jsoup.connect("http://tv.sohu.com/hothdtv/").get();
                    Elements elements = document.select("div.vNameIn");
                  //  Log.e("//////////",""+elements.size());
                    for (int i = 531; i < 541; i++) {
                        String name = elements.get(i).getElementsByTag("a").text();
                        String path = elements.get(i).getElementsByTag("a").attr("href");
                      //  Log.e("*********",""+path);
                        util = new NewsUtil();
                        util.setName(name);
                        util.setPath(path);
                        list.add(util);
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
                  //  Log.e("**********list_size",""+list.size());
                    adapter = new NewsAdapter(list,SearchActivity.this);
                    grid_view_news_search.setAdapter(adapter);
                    grid_view_news_search.setOnItemClickListener(listener);
                    grid_view_news_search.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return MotionEvent.ACTION_MOVE == event.getAction() ? true
                                    : false;
                        }
                    });

                    break;

            }
        }
    };
    //grid view 监听事件
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(SearchActivity.this,PlayerActivity.class);
            String info = "";
            switch (parent.getId()){
                case R.id.grid_view_news_search:
                    info = list.get(position).getPath();
                    break;
                case R.id.grid_view_news02:
                    break;
            }
            intent.putExtra("path",info);
            startActivity(intent);
        }
    };
}
