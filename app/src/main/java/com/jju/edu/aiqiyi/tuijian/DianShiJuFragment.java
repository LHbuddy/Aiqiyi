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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jju.edu.aiqiyi.PlayerActivity;
import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.TuiJianActivity;
import com.jju.edu.aiqiyi.VideoActivity;
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
    private Button btn_more;
    private LinearLayout linear_dianshiju;
    private DisplayImageOptions options;
    private View view;
    private List<VideoUtil> olist = new ArrayList<VideoUtil>();
    private GridView gridView;
    private VideoGridAdapter dianshiju_adapter;
    private VideoUtil video_first;
    private String path_more;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dianshiju_fragment_layout, container, false);
        init_view();
        jsoup_();
        return view;
    }

    //抓取视频
    public void jsoup_() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("http://tv.sohu.com/drama/").get();
                    Element element_first = doc.getElementById("modC");
                    Elements elements_first = element_first.select("div.colL");
                    Element element_ = elements_first.first();
                    String name_first = element_.getElementsByTag("span").last().text();
                    String image_first = "http://" + element_.getElementsByTag("img").attr("src").split("//")[1];
                    String path_first = "http://" + element_.getElementsByTag("a").last().attr("href").split("//")[1];
                    video_first = new VideoUtil(path_first, image_first, name_first, "");

                    get_USdrama();
                    handler.sendEmptyMessage(123);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 抓取美剧
     */
    private void get_USdrama() {
        try {
            Document doc = Jsoup.connect("http://tv.sohu.com/drama/").get();
            Elements elements = doc.select(".con");
            Element element = elements.get(2);
            Elements elements_li = element.getElementsByTag("li");
            for (int i = 0; i < elements_li.size(); i++) {
                Element element_li = elements_li.get(i);
                String name = element_li.getElementsByTag("p").first().text();
                String image = "http://" + element_li.getElementsByTag("img").attr("src").split("//")[1];
                String desc = element_li.getElementsByTag("span").tagName("lisTx").text();
                String path = "http://" + element_li.getElementsByTag("p").first().getElementsByTag("a").first().attr("href").split("//")[1];
                VideoUtil video = new VideoUtil(path, image, name, desc);
                olist.add(video);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 123) {
                tv_dianshiju.setText(video_first.getVideo_name());
                Picasso.with(getContext()).load(video_first.getVideo_image()).placeholder(R.drawable.book_card_icon).resize(680, 400).into(img_dianshiju);
                dianshiju_adapter = new VideoGridAdapter(olist, getContext());
                gridView.setAdapter(dianshiju_adapter);

                gridView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return MotionEvent.ACTION_MOVE == event.getAction() ? true
                                : false;
                    }
                });
                gridView.setOnItemClickListener(listener);
                linear_dianshiju.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String path_dianshiju = video_first.getVideo_path();
                        Intent intent_dianshiju = new Intent(getActivity(), PlayerActivity.class);
                        intent_dianshiju.putExtra("path", path_dianshiju);
                        startActivity(intent_dianshiju);
                    }
                });

            }
        }
    };

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.gridview_dianshiju_USdrama:
                    String path_dianshiju = olist.get(position).getVideo_path();
                    Intent intent_dianshiju = new Intent(getActivity(), PlayerActivity.class);
                    intent_dianshiju.putExtra("path", path_dianshiju);
                    startActivity(intent_dianshiju);
                    break;
            }
        }
    };

    private void init_view() {
        tv_dianshiju = (TextView) view.findViewById(R.id.tv_dianshiju);
        img_dianshiju = (ImageView) view.findViewById(R.id.img_dianshiju);
        gridView = (GridView) view.findViewById(R.id.gridview_dianshiju_USdrama);
        linear_dianshiju = (LinearLayout) view.findViewById(R.id.linear_dianshiju);
        btn_more = (Button) view.findViewById(R.id.btn_dianshiju_quanqiu_more);
        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VideoActivity.class);
                path_more = "http://so.tv.sohu.com/list_p1101_p2_p3_p4_p5_p6_p7_p8_p9_p10_p11_p12_p13.html";
                intent.putExtra("path", path_more);
                startActivity(intent);
            }
        });
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
