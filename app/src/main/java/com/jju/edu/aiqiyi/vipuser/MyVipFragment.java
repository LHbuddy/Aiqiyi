package com.jju.edu.aiqiyi.vipuser;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jju.edu.aiqiyi.PlayerActivity;
import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.adapter.SportsAdapter;
import com.jju.edu.aiqiyi.entity.VideoUtil;
import com.jju.edu.aiqiyi.util.SportUtil;
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

public class MyVipFragment extends Fragment {

    private String img_head = "'";
    private String path_head = "";
    private String desc_head = "";
    private ImageView first_head_img;
    private TextView text_head;
    private List<SportUtil> list = new ArrayList<SportUtil>();
    private SportUtil util;
    private SportsAdapter adapter;
    private ListView sport_list_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myvip_vipuser_fragment_layout,container,false);

        first_head_img = (ImageView) view.findViewById(R.id.first_head_img);
        text_head = (TextView) view.findViewById(R.id.text_head);
        sport_list_view = (ListView) view.findViewById(R.id.sport_list_view);

        first_head_img.setOnClickListener(new myonclick());

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
                    Document document = Jsoup.connect("http://tv.sohu.com/sports/").get();

                    Elements elements_ = document.select("div.scr_pic");
                  //  Log.e("^^^^^^^^^", "" + elements_.size());
                    Element element_ = elements_.get(0);
                    img_head = "http:" + element_.getElementsByTag("img").attr("src");
                    path_head ="http:" +  element_.getElementsByTag("a").attr("href");
                    desc_head = element_.getElementsByTag("span ").text();
                   // Log.e("***********", "" + img_head+"********"+path_head+"*********"+desc_head);

                    Elements elements = document.select("li.scvs");
                  //  Log.e("//////////",""+elements.size());
                    for (int i = 1; i < 6; i++) {
                        util = new SportUtil();
                        Element element = elements.get(i);
                        Elements elements2 = element.getElementsByTag("p");
                        String time = elements2.get(0).text();
                        String team = elements2.get(1).text();
                        Elements elements3 = element.getElementsByTag("span");
                        String img1 ="http:" +elements3.get(0).getElementsByTag("img").attr("src");
                        String img2 ="http:" +elements3.get(2).getElementsByTag("img").attr("src");
                        String path = elements.get(i).getElementsByTag("a").attr("href");
                      //  Log.e("***********", "" + time+"********"+team+"*********"+img1+"*********"+img2+"*********"+path);

                        util.setTime(time);
                        util.setTeam(team);
                        util.setImg_left(img1);
                        util.setImg_right(img2);
                        util.setPath(path);
                        list.add(util);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(123);
            }
        }.start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 123:
                    ImageLoader.getInstance().displayImage(img_head, first_head_img);
                    text_head.setText(desc_head);
                    adapter = new SportsAdapter(list,getActivity());
                    sport_list_view.setAdapter(adapter);

                    sport_list_view.setOnItemClickListener(listener);

                    sport_list_view.setOnTouchListener(new View.OnTouchListener() {
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

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), PlayerActivity.class);
            intent.putExtra("path",list.get(position).getPath());
            startActivity(intent);
        }
    };

    //图片点击事件
    class myonclick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.first_head_img:
                    Intent intent = new Intent(getActivity(), PlayerActivity.class);
                    intent.putExtra("path",desc_head);
                    startActivity(intent);
                    break;

            }

        }
    }
}
