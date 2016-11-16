package com.jju.edu.aiqiyi.tuijian;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.TuiJianActivity;
import com.jju.edu.aiqiyi.adapter.VideoGridAdapter;
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
import java.util.Map;

/**
 * Created by Administrator on 2016/11/14.
 */

public class DianShiJuFragment extends Fragment {
    private ImageView img_dianshiju;
    private TextView tv_dianshiju;
    private DisplayImageOptions options;
    private View view;
    private List<VideoUtil> olist = new ArrayList<VideoUtil>();
    private GridView gridView;
    private VideoGridAdapter dianshiju_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dianshiju_fragment_layout, container, false);


        init_view();
        jsoup_();


        return view;
    }

    public void jsoup_() {
        new Thread() {
            @Override
            public void run() {

                try {
                    Document doc = Jsoup.connect("http://tv.sohu.com/drama/").get();
                    Elements elements = doc.select("div.colR");
                    Element element = elements.get(0);
                    Elements elements1 = element.getElementsByTag("li");

                    Log.i("TAG", "elements  " + elements.size());
                    for (int i = 0; i < elements1.size(); i++) {
                        Element element1 = elements1.get(i);
                        Log.i("TAG", "element  " + element1.toString());

                        String name = element1.getElementsByTag("a").get(1).text();
                        String a = element1.getElementsByTag("img").first().attr("src");
                        String image = "http:" + a;
                        Log.i("TAG", "image" + image);

                        VideoUtil util = new VideoUtil("", image, name, "");
                        olist.add(util);
                    }

                    handler.sendEmptyMessage(123);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 123) {
                //  Log.i("TAG", olist.get(0).getVideo_image());
                tv_dianshiju.setText(olist.get(0).getVideo_name());
                Picasso.with(getContext()).load(olist.get(0).getVideo_image()).placeholder(R.drawable.book_card_icon).resize(680, 400).into(img_dianshiju);
                dianshiju_adapter = new VideoGridAdapter(olist, getContext());
                Log.i("TAG", "olist" + olist.size());
                gridView.setAdapter(dianshiju_adapter);

            }
        }
    };

    private void init_view() {
        tv_dianshiju = (TextView) view.findViewById(R.id.tv_dianshiju);
        img_dianshiju = (ImageView) view.findViewById(R.id.img_dianshiju);
        gridView = (GridView) view.findViewById(R.id.gridview_dianshiju);

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


}
