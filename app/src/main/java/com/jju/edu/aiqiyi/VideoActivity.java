package com.jju.edu.aiqiyi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jju.edu.aiqiyi.adapter.VideoGridAdapter;
import com.jju.edu.aiqiyi.entity.VideoUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/17.
 */

public class VideoActivity extends Activity {

    private TextView tv_video_title_center;
    private ImageView iv_video_title_search;
    private GridView gv_video;

    //图片加载配置
    private DisplayImageOptions options;

    //加载路径
    private String path =
            "http://so.tv.sohu.com/list_p1101_p20_p3_u5185_u5730_p40_p5_p6_p77_p80_p9_2d1_p10_p11_p12_p13.html";

    //一次加载的数量
    private int count = 0;
    private VideoUtil videoUtil;
    private List<VideoUtil> list = new ArrayList<VideoUtil>();
    private VideoGridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.video_layout);

        initView();
        http_();
    }

    private void http_() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(path).get();
                    Elements e = doc.getElementsByTag("li");
                    for (int i = 18; i < e.size(); i++) {
                        videoUtil = new VideoUtil();
                        Element element = e.get(i);
                        String path = element.getElementsByTag("a").get(2).attr("href");
                        String image_path = element.getElementsByTag("img").attr("src");
                        String video_name = element.getElementsByTag("a").get(2).attr("title");
                        String video_desc = element.select("p.actor").text();
                        Document document = Jsoup.connect(path).get();
                        Elements e_1 = document.getElementsByTag("a");
                        Elements elements_1 = e_1.select("[location=play]");
                        String video_path = elements_1.get(0).attr("href");
                        videoUtil.setVideo_name(video_name);
                        videoUtil.setVideo_image(image_path);
                        videoUtil.setVideo_path(video_path);
                        videoUtil.setVideo_desc(video_desc);
                        list.add(videoUtil);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(234);
            }
        }.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 234:
                    loadInfo();
                    break;
            }
        }
    };

    //加载信息
    private void loadInfo() {
        adapter = new VideoGridAdapter(list,VideoActivity.this);
        gv_video.setAdapter(adapter);
        gv_video.setOnItemClickListener(gridViewOnItemClick);
    }

    private AdapterView.OnItemClickListener gridViewOnItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String path = list.get(i).getVideo_path();
            Intent intent_play = new Intent(VideoActivity.this, PlayerActivity.class);
            intent_play.putExtra("path", path);
            startActivity(intent_play);
        }
    };

    private void initView() {
        tv_video_title_center = (TextView) findViewById(R.id.tv_video_title_center);
        iv_video_title_search = (ImageView) findViewById(R.id.iv_video_title_search);
        gv_video = (GridView) findViewById(R.id.gv_video);

        //配置文件初始化
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
                VideoActivity.this).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(100 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCache(new WeakMemoryCache()).build();
        ImageLoader.getInstance().init(configuration);

        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnLoading(R.drawable.phone_variety_focus_cover_default_bg)
                .showImageOnFail(R.drawable.phone_variety_focus_cover_default_bg)
                .showImageForEmptyUri(R.drawable.phone_variety_focus_cover_default_bg)
                .displayer(new RoundedBitmapDisplayer(0)).build();
    }


}
