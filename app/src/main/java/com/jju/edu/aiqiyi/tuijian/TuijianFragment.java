package com.jju.edu.aiqiyi.tuijian;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jju.edu.aiqiyi.PlayerActivity;
import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.VideoActivity;
import com.jju.edu.aiqiyi.adapter.VideoGridAdapter;
import com.jju.edu.aiqiyi.entity.VideoUtil;
import com.jju.edu.aiqiyi.util.AdvertUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.squareup.picasso.Picasso;

import org.apache.http.params.HttpParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static android.util.Log.e;

/**
 * Created by Administrator on 2016/11/14.
 */

public class TuijianFragment extends Fragment {

    //广告轮播
    private ViewPager viewPager;
    private List<AdvertUtil> list = new ArrayList<AdvertUtil>();
    private AdvertUtil util;
    private List<ImageView> image_list = new ArrayList<ImageView>();

    //图片加载配置
    private DisplayImageOptions options;
    //ViewPager是否正在触摸
    private boolean isStop = false;
    //ViewPager的滑动次数
    private int index = 0;

    //今日资讯
    private List<VideoUtil> jinri_list = new ArrayList<VideoUtil>();
    private VideoUtil videoUtil_jinri;
    private Button btn_tuijian_jinri_more;
    private TextView btn_tuijian_jinri_1, btn_tuijian_jinri_2, btn_tuijian_jinri_3;
    private LinearLayout ll_tuijian_jinri_1, ll_tuijian_jinri_2, ll_tuijian_jinri_3;

    //电视剧
    private VideoUtil videoUtil_dianshiju;
    private List<VideoUtil> dianshiju_list = new ArrayList<VideoUtil>();
    private Button btn_tuijian_dianshiju_more;
    private GridView gv_tuijian_dianshiju;
    private VideoGridAdapter adapter_dianshiju;
    //综艺节目
    private VideoUtil videoUtil_zongyi;
    private List<VideoUtil> zongyi_list = new ArrayList<VideoUtil>();
    private Button btn_tuijian_zongyi_more;
    private GridView gv_tuijian_zongyi;
    private VideoGridAdapter adapter_zongyi;
    //动漫
    private VideoUtil videoUtil_dongman;
    private List<VideoUtil> dongman_list = new ArrayList<VideoUtil>();
    private Button btn_tuijian_dongman_more;
    private GridView gv_tuijian_dongman;
    private VideoGridAdapter adapter_dongman;
    //电影
    private VideoUtil videoUtil_dianying;
    private List<VideoUtil> dianying_list = new ArrayList<VideoUtil>();
    private Button btn_tuijian_dianying_more;
    private GridView gv_tuijian_dianying;
    private VideoGridAdapter adapter_dianying;
    //搞笑
    private VideoUtil videoUtil_gaoxiao;
    private List<VideoUtil> gaoxiao_list = new ArrayList<VideoUtil>();
    private Button btn_tuijian_gaoxiao_more;
    private GridView gv_tuijian_gaoxiao;
    private VideoGridAdapter adapter_gaoxiao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tuijian_fragment_layout, container, false);
        //初始化ViewPager
        viewPager = (ViewPager) view.findViewById(R.id.page);
        //初始化今日资讯
        btn_tuijian_jinri_more = (Button) view.findViewById(R.id.btn_tuijian_jinri_more);
        btn_tuijian_jinri_1 = (TextView) view.findViewById(R.id.btn_tuijian_jinri_1);
        btn_tuijian_jinri_2 = (TextView) view.findViewById(R.id.btn_tuijian_jinri_2);
        btn_tuijian_jinri_3 = (TextView) view.findViewById(R.id.btn_tuijian_jinri_3);
        ll_tuijian_jinri_1 = (LinearLayout) view.findViewById(R.id.ll_tuijian_jinri_1);
        ll_tuijian_jinri_2 = (LinearLayout) view.findViewById(R.id.ll_tuijian_jinri_2);
        ll_tuijian_jinri_3 = (LinearLayout) view.findViewById(R.id.ll_tuijian_jinri_3);
        //获取资源
        http_image();

        //初始化电视剧
        btn_tuijian_dianshiju_more = (Button) view.findViewById(R.id.btn_tuijian_dianshiju_more);
        gv_tuijian_dianshiju = (GridView) view.findViewById(R.id.gv_tuijian_dianshiju);
        btn_tuijian_dianshiju_more.setOnClickListener(onClickMoreInfo);

        //初始化综艺
        btn_tuijian_zongyi_more = (Button) view.findViewById(R.id.btn_tuijian_zongyi_more);
        gv_tuijian_zongyi = (GridView) view.findViewById(R.id.gv_tuijian_zongyi);
        btn_tuijian_zongyi_more.setOnClickListener(onClickMoreInfo);

        //初始化动漫
        btn_tuijian_dongman_more = (Button) view.findViewById(R.id.btn_tuijian_dongman_more);
        gv_tuijian_dongman = (GridView) view.findViewById(R.id.gv_tuijian_dongman);
        btn_tuijian_dongman_more.setOnClickListener(onClickMoreInfo);

        //初始化电影
        btn_tuijian_dianying_more = (Button) view.findViewById(R.id.btn_tuijian_dianying_more);
        gv_tuijian_dianying = (GridView) view.findViewById(R.id.gv_tuijian_dianying);

        //初始化搞笑
        btn_tuijian_gaoxiao_more = (Button) view.findViewById(R.id.btn_tuijian_gaoxiao_more);
        gv_tuijian_gaoxiao = (GridView) view.findViewById(R.id.gv_tuijian_gaoxiao);
        btn_tuijian_gaoxiao_more.setOnClickListener(onClickMoreInfo);

        return view;
    }

    private View.OnClickListener onClickMoreInfo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_tuijian_dongman_more:
                    Intent intent_dongman_more = new Intent(getActivity(), VideoActivity.class);
                    String path_dongman = "http://so.tv.sohu.com/list_p1115_p2_p3_p4_p5_p6_p7_p8_p9_p10_p11_p12_p13.html?lcode=AAAAXI_-1SzNcuUrjz4IX80Fsov7PkQ-_9HcfhP5CqXXjp0R8XfYZkr4y7lONiSf97pAsyIIUrQWfRQxqQVNGmCbWAMSePteYrBsqLZ_EWa-JV8681rfKbWwtZkwmTubY1aVEA..cf6&lqd=10051";
                    intent_dongman_more.putExtra("path", path_dongman);
                    startActivity(intent_dongman_more);
                    break;
                case R.id.btn_tuijian_dianshiju_more:
                    Intent intent_dianshiju_more = new Intent(getActivity(), VideoActivity.class);
                    String path_dianshiju = "http://so.tv.sohu.com/list_p1101_p2_p3_p4_p5_p6_p7_p8_p9_p10_p11_p12_p13.html";
                    intent_dianshiju_more.putExtra("path", path_dianshiju);
                    startActivity(intent_dianshiju_more);
                    break;
                case R.id.btn_tuijian_zongyi_more:
                    Intent intent_zongyi_more = new Intent(getActivity(), VideoActivity.class);
                    String path_zongyi = "http://so.tv.sohu.com/list_p1106_p2_p3_p4_p5_p6_p7_p8_p9_p10_p11_p12_p13.html";
                    intent_zongyi_more.putExtra("path", path_zongyi);
                    startActivity(intent_zongyi_more);
                    break;
                case R.id.btn_tuijian_gaoxiao_more:
                    Intent intent_gaoxiao_more = new Intent(getActivity(), VideoActivity.class);
                    String path_gaoxiao = "http://so.tv.sohu.com/list_p1133_p2_p3_p4_p5_p6_p7_p8_p9_p10_p11_p12_p13.html";
                    intent_gaoxiao_more.putExtra("path", path_gaoxiao);
                    startActivity(intent_gaoxiao_more);
                    break;
            }
        }
    };

    private void initView() {

        //配置文件初始化
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
                getActivity()).denyCacheImageMultipleSizesInMemory()
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

    public void image_thread() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    while (!isStop) {
                        try {
                            sleep(4000);
                            index++;
                            handler.sendEmptyMessage(124);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        private float x, y;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = event.getX();
                    y = event.getY();
                    isStop = true;

                    break;
                case MotionEvent.ACTION_UP:
                    isStop = false;
                    if (Math.abs(event.getX() - x) < 3
                            && Math.abs(event.getY() - y) < 3) {
                    } else {
                        if (event.getX() - x > 0) {
                            index--;
                        } else if (event.getX() - x < 0) {
                            index++;
                        }
                    }
                    viewPager.setCurrentItem(index % image_list.size());
                    break;
            }
            return false;
        }
    };

//    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
//        @Override
//        public void onPageScrollStateChanged(int arg0) {
//        }
//        @Override
//        public void onPageScrolled(int arg0, float arg1, int arg2) {
//        }
//        @Override
//        public void onPageSelected(int arg0) {
//            setViewPager();
//            System.out.println("show----------" + index);
////            text.setText(infos[index]);
////            for (TextView textView : oTextViews) {
////                textView.setBackgroundResource(R.drawable.tv_shape);
////            }
////            oTextViews.get(index)
////                    .setBackgroundResource(R.drawable.tv_shape_red);
//        }
//    };

    public void http_image() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    //获取搜狐主页
                    Document document = Jsoup.connect("http://tv.sohu.com/").get();
                    //获取搜狐主页的推广信息
                    Elements elements = document.select("div.focusimgs");
                    for (int i = 0; i < elements.size(); i++) {
                        util = new AdvertUtil();
                        String s1 = elements.get(i).getElementsByTag("a").attr("href");
                        String s2 = elements.get(i).getElementsByTag("img").attr("src");
                        util.setImg_path(s2);
                        util.setVideo_path(s1);
                        list.add(util);
                    }
                    Elements elements_jinri = document.getElementsByClass("w265");

                    //获取今日资讯信息
                    Element element_jinri = elements_jinri.get(0);
                    //获取今日资讯更多
                    Elements elements_more = element_jinri.getElementsByTag("h5");
                    String jinri_more_name = elements_more.text();
                    String jinri_more_path = "http:" + element_jinri.getElementsByTag("a").attr("href");
                    videoUtil_jinri = new VideoUtil();
                    videoUtil_jinri.setVideo_name(jinri_more_name);
                    videoUtil_jinri.setVideo_path(jinri_more_path);
                    jinri_list.add(videoUtil_jinri);
                    //获取今日资讯
                    Elements elements_li = element_jinri.getElementsByTag("li");
                    for (int i = 0; i < 3; i++) {
                        videoUtil_jinri = new VideoUtil();
                        String jinri_li_name = elements_li.get(i).getElementsByTag("a").text();
                        String jinri_li_path = "http:" + elements_li.get(i).getElementsByTag("a").attr("href");
                        videoUtil_jinri.setVideo_name(jinri_li_name);
                        videoUtil_jinri.setVideo_path(jinri_li_path);
                        jinri_list.add(videoUtil_jinri);
                    }

                    //获取视频
                    Elements elements_video = document.select("div.con");
                    //获取电视剧信息
                    Element element_dianshiju = elements_video.get(4);
                    //获取电视剧内容
                    Elements elements_dianshiju_1 = element_dianshiju.getElementsByTag("ul");
                    for (int i = 0; i < 2; i++) {
                        Element element_dianshiju_info = elements_dianshiju_1.get(i);
                        Elements elements_dianshiju_info = element_dianshiju_info.getElementsByTag("li");
                        for (int j = 0; j < 2; j++) {
                            Element e_1 = elements_dianshiju_info.get(j);
                            String video_path = "http:" + e_1.getElementsByTag("a").first().attr("href");
//                            Document document1 = Jsoup.connect(video_path).get();

                            String image_path = "http:" + e_1.getElementsByTag("img").attr("lazysrc");
                            String video_name = e_1.getElementsByTag("p").get(0).text();
                            String video_desc = e_1.getElementsByTag("span").get(1).text() + "--" +
                                    e_1.getElementsByTag("p").get(1).text();
                            videoUtil_dianshiju = new VideoUtil();
                            videoUtil_dianshiju.setVideo_path(video_path);
                            videoUtil_dianshiju.setVideo_name(video_name);
                            videoUtil_dianshiju.setVideo_image(image_path);
                            videoUtil_dianshiju.setVideo_desc(video_desc);
                            dianshiju_list.add(videoUtil_dianshiju);
                        }
                    }

                    //获取综艺节目
                    Element element_zongyi = elements_video.get(10);
                    //获取综艺节目内容
                    Elements elements_zongyi_1 = element_zongyi.getElementsByTag("ul");
                    for (int i = 0; i < 2; i++) {
                        Element element_zongyi_info = elements_zongyi_1.get(i);
                        Elements elements_zongyi_info = element_zongyi_info.getElementsByTag("li");
                        for (int j = 0; j < 2; j++) {
                            Element e_1 = elements_zongyi_info.get(j);
                            String video_path = "http:" + e_1.getElementsByTag("a").first().attr("href");
                            String image_path = "http:" + e_1.getElementsByTag("img").attr("lazysrc");
                            String video_name = e_1.getElementsByTag("p").get(0).text();
                            String video_desc = e_1.getElementsByTag("span").get(1).text() + "--" +
                                    e_1.getElementsByTag("p").get(1).text();
                            videoUtil_zongyi = new VideoUtil();
                            videoUtil_zongyi.setVideo_path(video_path);
                            videoUtil_zongyi.setVideo_name(video_name);
                            videoUtil_zongyi.setVideo_image(image_path);
                            videoUtil_zongyi.setVideo_desc(video_desc);
                            zongyi_list.add(videoUtil_zongyi);
                        }
                    }

                    //获取动漫
                    Element element_dongman = elements_video.get(7);
                    //获取动漫内容
                    Elements elements_dongman_1 = element_dongman.getElementsByTag("ul");
                    for (int i = 0; i < 2; i++) {
                        Element element_dongman_info = elements_dongman_1.get(i);
                        Elements elements_dongman_info = element_dongman_info.getElementsByTag("li");
                        for (int j = 0; j < 2; j++) {
                            Element e_1 = elements_dongman_info.get(j);
                            String video_path = "http:" + e_1.getElementsByTag("a").first().attr("href");
                            String image_path = "http:" + e_1.getElementsByTag("img").attr("lazysrc");
                            String video_name = e_1.getElementsByTag("p").get(0).text();
                            String video_desc = e_1.getElementsByTag("span").get(1).text() + "--" +
                                    e_1.getElementsByTag("p").get(1).text();
                            videoUtil_dongman = new VideoUtil();
                            videoUtil_dongman.setVideo_path(video_path);
                            videoUtil_dongman.setVideo_name(video_name);
                            videoUtil_dongman.setVideo_image(image_path);
                            videoUtil_dongman.setVideo_desc(video_desc);
                            dongman_list.add(videoUtil_dongman);
                        }
                    }

                    //获取电影
                    Element element_dianying = elements_video.get(12);
                    //获取电影内容
                    Elements elements_dianying_1 = element_dianying.getElementsByTag("ul");
                    for (int i = 0; i < 4; i++) {
                        Element element_dianying_info = elements_dianying_1.get(i);
                        Elements elements_dianying_info = element_dianying_info.getElementsByTag("li");
                        for (int j = 0; j < 2; j++) {
                            Element e_1 = elements_dianying_info.get(j);
                            //"http:" +
                            String video_path = e_1.getElementsByTag("a").first().attr("href");
                            String image_path = e_1.getElementsByTag("img").attr("lazysrc");

                            String video_name = e_1.getElementsByTag("p").get(0).text();
                            //e_1.getElementsByTag("a").get(1).text() + "--" +
                            String video_desc = e_1.getElementsByTag("p").get(1).text();
                            videoUtil_dianying = new VideoUtil();
                            videoUtil_dianying.setVideo_path(video_path);
                            videoUtil_dianying.setVideo_name(video_name);
                            videoUtil_dianying.setVideo_image(image_path);
                            videoUtil_dianying.setVideo_desc(video_desc);
                            dianying_list.add(videoUtil_dianying);
                        }
                    }

                    //搞笑
                    Elements elements_gaoxiao_ = document.select("div.weBox");
                    Element element_gaoxiao_ = elements_gaoxiao_.get(1);
                    Element element_gaoxiao = element_gaoxiao_.getElementsByTag("ul").get(1);
                    Elements elements_gaoxiao = element_gaoxiao.getElementsByTag("li");
                    for (int i = 1; i < 5; i++) {
                        Element gaoxiao = elements_gaoxiao.get(i);
                        String image_path = "http:" + gaoxiao.getElementsByTag("img").attr("lazysrc");
                        String video_path = "http:" + gaoxiao.getElementsByTag("a").first().attr("href");
                        String video_name = gaoxiao.getElementsByTag("a").get(1).text();
                        String video_desc = gaoxiao.getElementsByTag("a").get(2).text();
                        videoUtil_gaoxiao = new VideoUtil();
                        videoUtil_gaoxiao.setVideo_image(image_path);
                        videoUtil_gaoxiao.setVideo_path(video_path);
                        videoUtil_gaoxiao.setVideo_name(video_name);
                        videoUtil_gaoxiao.setVideo_desc(video_desc);
                        gaoxiao_list.add(videoUtil_gaoxiao);
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
                    operation_();
                    break;
                case 124:
                    viewPager.setCurrentItem(index % image_list.size());
                    break;
            }
        }
    };

    //根据图片路径加载图片，并添加给ViewPager,设置ViewPager
    public void operation_() {
        initView();
        for (int i = 0; i < list.size(); i++) {
            ImageView imageview = new ImageView(getContext());
            String uri_path = "http:" + list.get(i).getImg_path();
            Uri uri = Uri.parse(uri_path);
//            ImageLoader.getInstance().displayImage(uri, imageview, options);
            Picasso.with(getContext()).load(uri).placeholder(R.drawable.phone_variety_focus_cover_default_bg)
                    .resize(0, 185).into(imageview);
            image_list.add(imageview);
        }

        //给ViewPager添加适配器
        viewPager.setAdapter(pagerAdapter);
        //设置ViewPager的显示
        viewPager.setCurrentItem(0);
//        viewPager.setOnPageChangeListener(listener);
        //设置图片自动轮播
        image_thread();
        //设置ViewPager的触摸监听
        viewPager.setOnTouchListener(onTouchListener);
        //ViewPager设置监听事件

        //给今日资讯添加内容
        btn_tuijian_jinri_more.setText(jinri_list.get(0).getVideo_name());
        btn_tuijian_jinri_1.setText(jinri_list.get(1).getVideo_name());
        btn_tuijian_jinri_2.setText(jinri_list.get(2).getVideo_name());
        btn_tuijian_jinri_3.setText(jinri_list.get(3).getVideo_name());
        //设置今日资讯的点击事件
        btn_tuijian_jinri_more.setOnClickListener(jinriOnClick);
        ll_tuijian_jinri_1.setOnClickListener(jinriOnClick);
        ll_tuijian_jinri_2.setOnClickListener(jinriOnClick);
        ll_tuijian_jinri_3.setOnClickListener(jinriOnClick);

        //电视剧
        gv_tuijian_dianshiju.setOnTouchListener(gridViewOnTouch);
        adapter_dianshiju = new VideoGridAdapter(dianshiju_list, getActivity());
        gv_tuijian_dianshiju.setAdapter(adapter_dianshiju);
        gv_tuijian_dianshiju.setOnItemClickListener(gridViewOnItemClick);

        //综艺
        gv_tuijian_zongyi.setOnTouchListener(gridViewOnTouch);
        adapter_zongyi = new VideoGridAdapter(zongyi_list, getActivity());
        gv_tuijian_zongyi.setAdapter(adapter_zongyi);
        gv_tuijian_zongyi.setOnItemClickListener(gridViewOnItemClick);

        //动漫
        gv_tuijian_dongman.setOnTouchListener(gridViewOnTouch);
        adapter_dongman = new VideoGridAdapter(dongman_list, getActivity());
        gv_tuijian_dongman.setAdapter(adapter_dongman);
        gv_tuijian_dongman.setOnItemClickListener(gridViewOnItemClick);

        //电影
        gv_tuijian_dianying.setOnTouchListener(gridViewOnTouch);
        adapter_dianying = new VideoGridAdapter(dianying_list, getActivity());
        gv_tuijian_dianying.setAdapter(adapter_dianying);
        gv_tuijian_dianying.setOnItemClickListener(gridViewOnItemClick);

        //搞笑
        gv_tuijian_gaoxiao.setOnTouchListener(gridViewOnTouch);
        adapter_gaoxiao = new VideoGridAdapter(gaoxiao_list, getActivity());
        gv_tuijian_gaoxiao.setAdapter(adapter_gaoxiao);
        gv_tuijian_gaoxiao.setOnItemClickListener(gridViewOnItemClick);
    }

    //GridView的点击事件
    private AdapterView.OnItemClickListener gridViewOnItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            switch (adapterView.getId()) {
                case R.id.gv_tuijian_dianshiju:
                    String path_dianshiju = dianshiju_list.get(i).getVideo_path();
                    Intent intent_dianshiju = new Intent(getActivity(), PlayerActivity.class);
                    intent_dianshiju.putExtra("path", path_dianshiju);
                    startActivity(intent_dianshiju);
                    break;
                case R.id.gv_tuijian_zongyi:
                    String path_zongyi = zongyi_list.get(i).getVideo_path();
                    Intent intent_zongyi = new Intent(getActivity(), PlayerActivity.class);
                    intent_zongyi.putExtra("path", path_zongyi);
                    startActivity(intent_zongyi);
                    break;
                case R.id.gv_tuijian_dongman:
                    String path_dongman = dongman_list.get(i).getVideo_path();
                    Intent intent_dongman = new Intent(getActivity(), PlayerActivity.class);
                    intent_dongman.putExtra("path", path_dongman);
                    startActivity(intent_dongman);
                    break;
                case R.id.gv_tuijian_dianying:
                    String path_dianying = dianying_list.get(i).getVideo_path();
                    Intent intent_dianying = new Intent(getActivity(), PlayerActivity.class);
                    intent_dianying.putExtra("path", path_dianying);
                    startActivity(intent_dianying);
                    break;
                case R.id.gv_tuijian_gaoxiao:
                    String path_gaoxiao = gaoxiao_list.get(i).getVideo_path();
                    Intent intent_gaoxiao = new Intent(getActivity(), PlayerActivity.class);
                    intent_gaoxiao.putExtra("path", path_gaoxiao);
                    startActivity(intent_gaoxiao);
                    break;
            }
        }
    };

    //GridView的触摸事件--禁止GridView的触摸事件
    private View.OnTouchListener gridViewOnTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return MotionEvent.ACTION_MOVE == motionEvent.getAction() ? true : false;
        }
    };

    //定义今日资讯的点击事件对象
    private View.OnClickListener jinriOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_tuijian_jinri_more:  //今日资讯--更多
                    Intent intent1 = new Intent(getActivity(), PlayerActivity.class);
                    intent1.putExtra("path", jinri_list.get(0).getVideo_path());
                    startActivity(intent1);
                    break;
                case R.id.ll_tuijian_jinri_1:  //今日资讯--第一个
                    Intent intent2 = new Intent(getActivity(), PlayerActivity.class);
                    intent2.putExtra("path", jinri_list.get(1).getVideo_path());
                    startActivity(intent2);
                    break;
                case R.id.ll_tuijian_jinri_2:  //今日资讯--第二个
                    Intent intent3 = new Intent(getActivity(), PlayerActivity.class);
                    intent3.putExtra("path", jinri_list.get(2).getVideo_path());
                    startActivity(intent3);
                    break;
                case R.id.ll_tuijian_jinri_3:  //今日资讯--第二个
                    Intent intent4 = new Intent(getActivity(), PlayerActivity.class);
                    intent4.putExtra("path", jinri_list.get(3).getVideo_path());
                    startActivity(intent4);
                    break;
            }
        }
    };

    //页面轮播适配器
    private PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return image_list.size() * 100;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (image_list.size() == 0) {
                position = 3;
            }
            if (position < 0) {
                position = Math.abs(position);
            }
            position %= image_list.size();
            ImageView imageView = image_list.get(position);
            //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
            ViewParent vp = imageView.getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(imageView);
            }
            container.addView(imageView);
            //给ImageView设置点击事件
            final int image_position = position;
            image_list.get(position).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_image = new Intent(getActivity(), PlayerActivity.class);
                    intent_image.putExtra("path", list.get(image_position).getVideo_path());
                    startActivity(intent_image);
                }
            });

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            if (Math.abs(position)>image_list.size()){
//                position %= image_list.size();
//            }
//            container.removeView(image_list.get(position));
        }
    };

}
