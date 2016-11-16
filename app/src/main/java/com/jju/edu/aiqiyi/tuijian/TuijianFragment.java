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
import android.widget.ImageView;

import com.jju.edu.aiqiyi.R;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tuijian_fragment_layout, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.page);
        http_();

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

    public void http_() {
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
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
//        viewPager.setOnPageChangeListener(listener);
        image_thread();
        viewPager.setOnTouchListener(onTouchListener);
    }

    //页面轮播适配器
    private PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return image_list.size()*100;
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
