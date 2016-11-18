package com.jju.edu.aiqiyi.tuijian;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jju.edu.aiqiyi.PlayerActivity;
import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.adapter.VideoGridAdapter;
import com.jju.edu.aiqiyi.entity.VideoUtil;
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

public class GaoxiaoFragment extends Fragment {

    private List<VideoUtil> list = new ArrayList<VideoUtil>();
    private List<VideoUtil> list02 = new ArrayList<VideoUtil>();
    private List<VideoUtil> list03 = new ArrayList<VideoUtil>();
    private VideoUtil util;
    private VideoUtil util02;
    private VideoUtil util03;
    private VideoGridAdapter adapter;
    private VideoGridAdapter adapter02;
    private VideoGridAdapter adapter03;
    private GridView grid_view;
    private GridView grid_view02;
    private GridView grid_view03;
    private String img_head = "'";
    private String path_head = "";
    private String desc_head = "";
    private ImageView first_head_img;
    private TextView text_head;
    private TextView text_more01,text_more02,text_more03;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gaoxiao_fragment_layout,container,false);

        grid_view = (GridView) view.findViewById(R.id.grid_view);
        grid_view02 = (GridView) view.findViewById(R.id.grid_view02);
        grid_view03 = (GridView) view.findViewById(R.id.grid_view03);
        first_head_img = (ImageView) view.findViewById(R.id.first_head_img);
        text_head = (TextView) view.findViewById(R.id.text_head);
        text_more01 = (TextView) view.findViewById(R.id.text_more01);
        text_more02 = (TextView) view.findViewById(R.id.text_more02);

        text_more01.setOnClickListener(new myonclick());
        text_more02.setOnClickListener(new myonclick());

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
                    Document document = Jsoup.connect("http://tv.sohu.com/ugc/fun/").get();

                    Elements elements_ = document.select("div.fla1");
                    Element element_ = elements_.get(0);
                    img_head = "http:" + element_.getElementsByTag("img").attr("src");
                    path_head ="http:" + element_.getElementsByTag("a").attr("href");
                    desc_head = element_.getElementsByTag("p").text();

                    Elements elements = document.select("li[data-pb-other]");
                    for (int i = 0; i < 12; i++) {
                        util = new VideoUtil();
                        String img = elements.get(i).getElementsByTag("img").attr("src");
//                        Element element = elements.get(i);
//                        Elements elements2 = element.getElementsByTag("a");
//                        String name = elements2.get(0).text();
//                        String desc = elements2.get(2).text();
                        String name = elements.get(i).getElementsByTag("a").text();
                        String desc = elements.get(i).getElementsByTag("h3").text();
                        String path = elements.get(i).getElementsByTag("a").attr("href");
                        util.setVideo_image(img);
                        util.setVideo_name(name);
                        util.setVideo_desc("");
                        util.setVideo_path(path);
                        list.add(util);
                    }

                    for (int i = 12; i < 20; i++) {
                        util02 = new VideoUtil();
                        String img = elements.get(i).getElementsByTag("img").attr("src");
//                        Element element = elements.get(i);
//                        Elements elements2 = element.getElementsByTag("a");
//                        String name = elements2.get(0).text();
//                        String desc = elements2.get(2).text();
                        String name = elements.get(i).getElementsByTag("a").text();
                        String desc = elements.get(i).getElementsByTag("h3").text();
                        String path = elements.get(i).getElementsByTag("a").attr("href");
                        util02.setVideo_image(img);
                        util02.setVideo_name(name);
                        util02.setVideo_desc("");
                        util02.setVideo_path(path);
                        list02.add(util02);
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

                    first_head_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(),PlayerActivity.class);
                            intent.putExtra("path",path_head);
                            startActivity(intent);
                        }
                    });
                    grid_view.setOnItemClickListener(listener);


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
                    getmessage_02();
                    break;
                case 124:
                    grid_view03.setOnItemClickListener(listener);


                    adapter03 = new VideoGridAdapter(list03, getActivity());
                    grid_view03.setAdapter(adapter03);
                    //设置gridview无法滚动
                    grid_view03.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return MotionEvent.ACTION_MOVE == event.getAction() ? true
                                    : false;
                        }
                    });
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
                    Document document = Jsoup.connect("http://tv.sohu.com/ugc/fun/").get();
                    Elements elements = document.select(".col3");
                    for (int i = 11; i < elements.size(); i++) {
                        util03 = new VideoUtil();
                        String img = elements.get(i).getElementsByTag("img").attr("src");
                        Element element = elements.get(i);
                        Elements elements2 = element.getElementsByTag("a");
                        String name = elements2.get(2).text();
                        String desc = elements2.get(1).text();
                        String path = elements.get(i).getElementsByTag("a").attr("href");
                        util03.setVideo_image(img);
                        util03.setVideo_name(name);
                        util03.setVideo_desc("");
                        util03.setVideo_path(path);
                        list03.add(util03);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(124);
            }
        }.start();
    }


    //grid view 监听事件
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Toast.makeText(getActivity(),""+parent,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(),PlayerActivity.class);
            String info = "";
            switch (parent.getId()){
                case R.id.grid_view:
                    info = list.get(position).getVideo_path();
                    break;
                case R.id.grid_view02:
                    info = list02.get(position).getVideo_path();
                    break;
                case R.id.grid_view03:
                    info = list03.get(position).getVideo_path();
                    break;
            }
            intent.putExtra("path",info);
            startActivity(intent);

        }
    };
    //更多按钮监听事件
    class myonclick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();

        }
    }
}
