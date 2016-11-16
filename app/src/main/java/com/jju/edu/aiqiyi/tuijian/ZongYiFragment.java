package com.jju.edu.aiqiyi.tuijian;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.entity.VideoUtil;
import com.jju.edu.aiqiyi.util.AdvertUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */

public class ZongYiFragment extends Fragment {

    private List<VideoUtil> list = new ArrayList<VideoUtil>();
    private VideoUtil util;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.dreamvoice_fragment_layout,container,false);
        getmessage_();
        return view;
    }
    //信息爬取方法
    public void getmessage_(){
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Document document = Jsoup.connect("http://tv.sohu.com/").get();
                    Elements elements = document.select("div.colR");
                    Log.e("//////////",""+elements.size());
                    for (int i = 0; i < elements.size(); i++) {
                        util = new VideoUtil();
                        String img = elements.get(i).getElementsByTag("img").attr("src");
                        String name = elements.get(i).getElementsByTag("a").text();
                        String desc = elements.get(i).getElementsByTag("p").text();
                        String path = elements.get(i).getElementsByTag("a").attr("href");
                        Log.e("222222", "" + img+ "******" + name+ "******" + desc+ "******" + path);


                        list.add(util);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(123);
            }
        }.start();

    }
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 123:
                    break;
            }
        }
    };


}
