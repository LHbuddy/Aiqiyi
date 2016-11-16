package com.jju.edu.aiqiyi.tuijian;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.entity.VideoUtil;
import com.jju.edu.aiqiyi.util.AdvertUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */

public class TuijianFragment extends Fragment {

    private ViewPager viewPager;

    private List<AdvertUtil> list = new ArrayList<AdvertUtil>();
    private AdvertUtil util;
    private List<ImageView> image_list = new ArrayList<ImageView>();

    private DisplayImageOptions options;

    private boolean isStop = false;
    private int index = 0;

    //今日资讯
    private List<VideoUtil> jinri_list = new ArrayList<VideoUtil>();
    private VideoUtil videoUtil_jinri;
    private Button btn_tuijian_jinri_more;
    private TextView btn_tuijian_jinri_1, btn_tuijian_jinri_2,btn_tuijian_jinri_3;
    private LinearLayout ll_tuijian_jinri_1, ll_tuijian_jinri_2,ll_tuijian_jinri_3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tuijian_fragment_layout, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.page);
        btn_tuijian_jinri_more = (Button) view.findViewById(R.id.btn_tuijian_jinri_more);
        btn_tuijian_jinri_1 = (TextView) view.findViewById(R.id.btn_tuijian_jinri_1);
        btn_tuijian_jinri_2 = (TextView) view.findViewById(R.id.btn_tuijian_jinri_2);
        btn_tuijian_jinri_3 = (TextView) view.findViewById(R.id.btn_tuijian_jinri_3);
        ll_tuijian_jinri_1 = (LinearLayout) view.findViewById(R.id.ll_tuijian_jinri_1);
        ll_tuijian_jinri_2 = (LinearLayout) view.findViewById(R.id.ll_tuijian_jinri_2);
        ll_tuijian_jinri_3 = (LinearLayout) view.findViewById(R.id.ll_tuijian_jinri_3);
        http_image();

        return view;
    }

    private void initView() {
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
                    Document document = Jsoup.connect("http://tv.sohu.com/").get();
                    Elements elements = document.select("div.focusimgs");
                    for (int i = 0; i < elements.size(); i++) {
                        util = new AdvertUtil();
                        String s1 = elements.get(i).getElementsByTag("a").attr("href");
                        String s2 = elements.get(i).getElementsByTag("img").attr("src");
//                        Log.e("222222", "" + s1 + "******" + s2);
                        util.setImg_path(s2);
                        util.setVideo_path(s1);
                        list.add(util);
                    }
                    Elements elements_jinri = document.getElementsByClass("w265");

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
                        String jinri_li_path ="http:" + elements_li.get(i).getElementsByTag("a").attr("href");
                        videoUtil_jinri.setVideo_name(jinri_li_name);
                        videoUtil_jinri.setVideo_path(jinri_li_path);
                        jinri_list.add(videoUtil_jinri);
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

    public void operation_() {
        initView();
        for (int i = 0; i < list.size(); i++) {
            ImageView imageview = new ImageView(getContext());
            String uri = "http:" + list.get(i).getImg_path();
            ImageLoader.getInstance().displayImage(uri, imageview, options);
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
    }

    //定义一个点击事件对象
    private View.OnClickListener jinriOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_tuijian_jinri_more:  //今日资讯--更多
                         Toast.makeText(getActivity(), jinri_list.get(0).getVideo_path(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ll_tuijian_jinri_1:  //今日资讯--第一个
                    Toast.makeText(getActivity(), jinri_list.get(1).getVideo_path(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ll_tuijian_jinri_2:  //今日资讯--第二个
                    Toast.makeText(getActivity(), jinri_list.get(2).getVideo_path(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ll_tuijian_jinri_3:  //今日资讯--第二个
                    Toast.makeText(getActivity(), jinri_list.get(3).getVideo_path(), Toast.LENGTH_SHORT).show();
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
