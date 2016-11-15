package com.jju.edu.aiqiyi.tuijian;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jju.edu.aiqiyi.R;
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

public class TuijianFragment extends Fragment {

    private List<AdvertUtil> list=new ArrayList<AdvertUtil>();
    private AdvertUtil util;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tuijian_fragment_layout,container,false);

        //配置文件初始化
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
                getActivity()).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(100 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(configuration);

        http_();
        return view;
    }
    public void http_() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Document document = Jsoup.connect("http://tv.sohu.com/").get();
                    //Elements elements = document.select(".focus").select(".rel");
                    Elements elements = document.select("div.focusimgs");
                    Log.e("111111", elements.size() + "'");
                    //Log.e("111111", document.text() + "'");
                    for (int i=0;i<elements.size();i++){
                        String s1 = elements.get(i).getElementsByTag("a").attr("href");
                        String s2 = elements.get(i).getElementsByTag("img").attr("src");
                        Log.e("222222",""+s1+"******"+s2);
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
            if (msg.what == 123) {
                operation_();
            }
        }
    };

    public void operation_(){
        for (int i=0;i<list.size();i++){

        }


    }

    //页面轮播适配器
    private PagerAdapter adapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    };

}
