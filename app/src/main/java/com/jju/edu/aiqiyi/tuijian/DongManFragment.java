package com.jju.edu.aiqiyi.tuijian;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jju.edu.aiqiyi.PlayerActivity;
import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.VideoActivity;
import com.jju.edu.aiqiyi.adapter.VideoGridAdapter;
import com.jju.edu.aiqiyi.entity.MovieUtil;
import com.jju.edu.aiqiyi.entity.VideoUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.squareup.picasso.Picasso;

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

public class DongManFragment extends Fragment {
    private ImageView img_dongman;
    private TextView tv_dongman;
    private LinearLayout linear_dongman;
    private GridView gridView;
    private GridView grid_view01;
    private GridView grid_view02;
    private List<VideoUtil> olist = new ArrayList<VideoUtil>();
    private List<VideoUtil> olist_ = new ArrayList<VideoUtil>();
    private List<VideoUtil> list01 = new ArrayList<VideoUtil>();
    private List<VideoUtil> list02 = new ArrayList<VideoUtil>();
    private View view;
    private ListView m_list;
    private DisplayImageOptions options;
    private VideoGridAdapter dongman_adapter;
    private VideoGridAdapter adapter01;
    private VideoGridAdapter adapter02;
    private VideoGridAdapter adapter03;
    private VideoGridAdapter adapter04;
    private TextView text_more01,text_more02,text_more03;

    public View onCreateView(LayoutInflater inflater,ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dongman_fragment_layout, container, false);


        init_view();
        jsoup_();

        return view;



    }

    public void jsoup_() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("http://tv.sohu.com/comic/").get();
                    Elements elements = doc.select("#modA");
                    Elements li = elements.select("li.lisi");
                    Log.e("size",""+li.size());
                    for(int i=0;i<li.size();i++){
                        String video_path = "http:"+li.get(i).select("a").first().attr("href");
                        Log.e("video_path",video_path);
                        String video_image = "http:"+li.get(i).select("img").attr("lazysrc");
                        Log.e("video_image",video_image);
                        String video_name = li.get(i).select("img").attr("alt");
                        Log.e("video_name",video_name);
                        String time = li.get(i).select("span.lisTx").text();
                        Log.e("time",time);
                        VideoUtil util = new VideoUtil(video_path,  video_image, video_name,  time);
                        olist.add(util);

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


                handler.sendEmptyMessage(123);
            }

        }.start();
    }

    public void getData(){
        new Thread() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("http://tv.sohu.com/comic/").get();
                    Elements elements = doc.select("#modB");
                    Elements li = elements.select("li.lisi");
                    Log.e("size",""+li.size());
                    for(int i=1;i<7;i++){
                        String video_path = "http:"+li.get(i).select("a").first().attr("href");
                        Log.e("video_path",video_path);
                        String video_image = "http:"+li.get(i).select("img").attr("lazysrc");
                        Log.e("video_image",video_image);
                        String video_name = li.get(i).select("p.p_bt").text();
                        Log.e("video_name",video_name);
                        String time = li.get(i).select("span.lisTx").text();
                        Log.e("time",time);
                        VideoUtil util = new VideoUtil(video_path,  video_image, video_name,  time);
                        list01.add(util);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                handler.sendEmptyMessage(124);
            }

        }.start();


    }
    public void getData2(){
        new Thread() {
            @Override
            public void run() {
                for(int i=9;i<18;i++){
                    list02.add(olist.get(i));
                }
                handler.sendEmptyMessage(125);
            }

        }.start();


    }
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 123:
                tv_dongman.setText(olist.get(0).getVideo_name());
                Picasso.with(getContext()).load(olist.get(0).getVideo_image()).placeholder(R.drawable.book_card_icon).resize(680, 350).into(img_dongman);
                for(int i=1;i<7;i++){
                    olist_.add(olist.get(i));
                }
                dongman_adapter = new VideoGridAdapter(olist_, getContext());
                gridView.setAdapter(dongman_adapter);
                    getData();
                gridView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return MotionEvent.ACTION_MOVE == event.getAction() ? true
                                : false;
                    }
                });
                gridView.setOnItemClickListener(listener);
                linear_dongman.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent_dongmna = new Intent(getActivity(), PlayerActivity.class);
                       //intent_dongmna.putExtra("path", path_dianshiju);
                        startActivity(intent_dongmna);
                    }
                });

                    break;
                case 124:
                    adapter01 = new VideoGridAdapter(list01, getActivity());
                    grid_view01.setAdapter(adapter01);
                    getData2();
                    grid_view01.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return MotionEvent.ACTION_MOVE == event.getAction() ? true
                                    : false;
                        }
                    });
                    grid_view01.setOnItemClickListener(listener);
                case 125:
                    adapter02 = new VideoGridAdapter(list02, getActivity());
                    grid_view02.setAdapter(adapter02);
                    grid_view02.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return MotionEvent.ACTION_MOVE == event.getAction() ? true
                                    : false;
                        }
                    });
                    grid_view02.setOnItemClickListener(listener);
            }
        }
    };

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.gridview_dianshiju:
                    String path_dianshiju = olist.get(position).getVideo_path();
                    Intent intent_dianshiju = new Intent(getActivity(), PlayerActivity.class);
                    intent_dianshiju.putExtra("path", path_dianshiju);
                    startActivity(intent_dianshiju);
                    break;
            }
        }
    };

    private void init_view() {
        tv_dongman = (TextView) view.findViewById(R.id.tv_dongman);
        img_dongman = (ImageView) view.findViewById(R.id.img_dongman);
        gridView = (GridView) view.findViewById(R.id.gridview_dongman);
        linear_dongman = (LinearLayout) view.findViewById(R.id.linear_dongman);
        grid_view01 = (GridView) view.findViewById(R.id.dm_grid_view01);
        grid_view02 = (GridView) view.findViewById(R.id.dm_grid_view02);


        text_more01 = (TextView) view.findViewById(R.id.dm_text_more01);
//        text_more02 = (TextView) view.findViewById(R.id.dm_text_more02);
       text_more01.setOnClickListener( listenter_more);
//        text_more02.setOnClickListener(new ZongYiFragment.myonclick());
//        text_more03.setOnClickListener(new ZongYiFragment.myonclick());

        //配置文件初始化
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
                getActivity()).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(100 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(configuration);

        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnLoading(R.drawable.phone_variety_focus_cover_default_bg)
                .showImageOnFail(R.drawable.phone_variety_focus_cover_default_bg)
                .showImageForEmptyUri(R.drawable.phone_variety_focus_cover_default_bg)
                .displayer(new RoundedBitmapDisplayer(0)).build();

    }

   private View.OnClickListener listenter_more = new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           String path = "http://so.tv.sohu.com/list_p1115_p2_p3_p4_p5_p6_p7_p8_p9_p10_p11_p12_p13.html?lcode=AAAAXI_-1SzNcuUrjz4IX80Fsov7PkQ-_9HcfhP5CqXXjp0R8XfYZkr4y7lONiSf97pAsyIIUrQWfRQxqQVNGmCbWAMSePteYrBsqLZ_EWa-JV8681rfKbWwtZkwmTubY1aVEA..cf6&lqd=10051";
           Intent intent = new Intent(getActivity(), VideoActivity.class);
           intent.putExtra("path",path);
           startActivity(intent);

       }
   };

}
