package com.jju.edu.aiqiyi.tuijian;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jju.edu.aiqiyi.PlayerActivity;
import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.adapter.VideoGridAdapter;
import com.jju.edu.aiqiyi.entity.VideoUtil;
import com.jju.edu.aiqiyi.util.AdvertUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */

public class ZongYiFragment extends Fragment {

    private List<VideoUtil> list = new ArrayList<VideoUtil>();
    private List<VideoUtil> list02 = new ArrayList<VideoUtil>();
    private VideoUtil util;
    private VideoUtil util02;
    private VideoGridAdapter adapter;
    private VideoGridAdapter adapter02;
    private GridView grid_view;
    private GridView grid_view02;
    private String img_head = "'";
    private String path_head = "";
    private String desc_head = "";
    private ImageView first_head_img;
    private TextView text_head;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dreamvoice_fragment_layout, container, false);
        grid_view = (GridView) view.findViewById(R.id.grid_view);
        grid_view02 = (GridView) view.findViewById(R.id.grid_view02);
        first_head_img = (ImageView) view.findViewById(R.id.first_head_img);
        text_head = (TextView) view.findViewById(R.id.text_head);

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
                getActivity()).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(configuration);

        getmessage_();
        return view;
    }

    //信息爬取方法
    public void getmessage_() {
        new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    Document document = Jsoup.connect("http://tv.sohu.com/show/").get();

                    Elements elements_ = document.select(".colL");
                  //  Log.e("^^^^^^^^^", "" + elements_.size());
                    Element element_ = elements_.get(0);
                    img_head = "http:" + element_.getElementsByTag("img").attr("data-original");
                    path_head = element_.getElementsByTag("a").attr("href");
                    desc_head = element_.getElementsByTag("a").text();

                    Elements elements = document.select(".lisi");
                    // Log.e("//////////",""+elements.size());
                    for (int i = 0; i < 4; i++) {
                        util = new VideoUtil();
                        String img = elements.get(i).getElementsByTag("img").attr("data-original");
                        Element element = elements.get(i);
                        Elements elements2 = element.getElementsByTag("p");
                        String name = elements2.get(0).text();
                        String desc = elements2.get(1).text();
                        String path = elements.get(i).getElementsByTag("a").attr("href");
                       // Log.e("222222", "" + img + "******" + name + "******" + desc + "******" + path);

                        util.setVideo_image("http:" + img);
                        util.setVideo_name(name);
                        util.setVideo_desc(desc);
                        util.setVideo_path(path);

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
                 //   Log.e("1111111", "" + list.size());
                    ImageLoader.getInstance().displayImage(img_head, first_head_img);
                    text_head.setText(desc_head);
                    adapter = new VideoGridAdapter(list, getActivity());
                    grid_view.setAdapter(adapter);
                    //设置gridview无法滚动
                    grid_view.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return MotionEvent.ACTION_MOVE == event.getAction() ? true
                                    : false;
                        }
                    });
                    getmessage_02();

                    first_head_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(),PlayerActivity.class);
                            intent.putExtra("path","http://tv.sohu.com/20161115/n473212012.shtml");
                            startActivity(intent);
                        }
                    });
                    break;
                case 124:
                    //   Log.e("1111111", "" + list.size());
                    adapter02 = new VideoGridAdapter(list02, getActivity());
                    grid_view02.setAdapter(adapter02);
                    //设置gridview无法滚动
                    grid_view02.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return MotionEvent.ACTION_MOVE == event.getAction() ? true
                                    : false;
                        }
                    });
                   // getmessage_02();
                    break;
            }
        }
    };
    //信息爬取方法
    public void getmessage_02() {
        new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    Document document = Jsoup.connect("http://tv.sohu.com/show/").get();
                    Elements elements = document.select(".cbox");
                   //  Log.e("//////////",""+elements.size());
                    for (int i = 0; i < 4; i++) {
                        util02 = new VideoUtil();
                        String img = elements.get(i).getElementsByTag("img").attr("data-original");
                        Element element = elements.get(i);
                        Elements elements2 = element.getElementsByTag("a");
                        String name = elements2.get(0).text();
                        String desc = elements2.get(2).text();
                        String path = elements.get(i).getElementsByTag("a").attr("href");
                    //    Log.e("222222", "" + img + "******" + name + "******" + desc + "******" + path);

                        util02.setVideo_image("http:" + img);
                        util02.setVideo_name(name);
                        util02.setVideo_desc(desc);
                        util02.setVideo_path(path);

                        list02.add(util02);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(124);
            }
        }.start();
    }



}
