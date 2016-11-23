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
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jju.edu.aiqiyi.PlayerActivity;
import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.VideoActivity;
import com.jju.edu.aiqiyi.adapter.SportsAdapter;
import com.jju.edu.aiqiyi.adapter.VideoGridAdapter;
import com.jju.edu.aiqiyi.entity.VideoUtil;
import com.jju.edu.aiqiyi.util.SportUtil;
import com.jju.edu.aiqiyi.wode.LoginActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */

public class VipRecordFragment extends Fragment {

    private List<VideoUtil> list01 = new ArrayList<VideoUtil>();
    private List<VideoUtil> list02 = new ArrayList<VideoUtil>();
    private List<VideoUtil> list03 = new ArrayList<VideoUtil>();
    private List<VideoUtil> list04 = new ArrayList<VideoUtil>();
    private VideoUtil util01;
    private VideoUtil util02;
    private VideoUtil util03;
    private VideoUtil util04;
    private VideoGridAdapter adapter01;
    private VideoGridAdapter adapter02;
    private VideoGridAdapter adapter03;
    private VideoGridAdapter adapter04;

    private GridView grid_view01;
    private GridView grid_view02;
    private GridView grid_view03;
    private TextView text_more01;
    private TextView text_more02;
    private TextView text_more03;
    private LinearLayout progress;

    private String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viprecord_vipuser_fragment_layout,container,false);

        grid_view01 = (GridView) view.findViewById(R.id.grid_view01);
        grid_view02 = (GridView) view.findViewById(R.id.grid_view02);
        grid_view03 = (GridView) view.findViewById(R.id.grid_view03);
        text_more01 = (TextView) view.findViewById(R.id.text_more01);
        text_more02 = (TextView) view.findViewById(R.id.text_more02);
        text_more03 = (TextView) view.findViewById(R.id.text_more03);
        progress = (LinearLayout) view.findViewById(R.id.progress);
        text_more01.setOnClickListener(new myonclick());
        text_more02.setOnClickListener(new myonclick());
        text_more03.setOnClickListener(new myonclick());

        getmessage_();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        uid = LoginActivity.uid_get;
    }

    //信息爬取方法
    public void getmessage_() {
        new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    Document document = Jsoup.connect("http://tv.sohu.com/documentary/").get();

                    Elements elements = document.select("li.lisi");
                //    Log.e("//////////",""+elements.size());
                    for (int i = 0; i < 6; i++) {
                        util01 = new VideoUtil();
                        Element element = elements.get(i);
                        Elements elements2 = element.getElementsByTag("span");
                       // String time = elements2.get(0).text();
                        String name = elements2.get(1).text();
                        Elements elements3 = element.getElementsByTag("a");
                        String desc = elements3.get(1).text();
                        String path = "http:" +elements.get(i).getElementsByTag("a").attr("href");
                        String image = "http:" +elements.get(i).getElementsByTag("img").attr("data-original");
                    //    Log.e("***********", "" + image+"********"+name+"*********"+desc+"*********"+path);
                        util01.setVideo_image(image);
                        util01.setVideo_name(name);
                        util01.setVideo_desc(desc);
                        util01.setVideo_path(path);
                        list01.add(util01);
                    }

                    for (int i = 10; i < 16; i++) {
                        util02 = new VideoUtil();
                        Element element = elements.get(i);
                        Elements elements2 = element.getElementsByTag("span");
                        // String time = elements2.get(0).text();
                        String name = elements2.get(1).text();
                        Elements elements3 = element.getElementsByTag("a");
                        String desc = elements3.get(1).text();
                        String path = "http:" +elements.get(i).getElementsByTag("a").attr("href");
                        String image = "http:" +elements.get(i).getElementsByTag("img").attr("data-original");
                     //   Log.e("***********", "" + image+"********"+name+"*********"+desc+"*********"+path);
                        util02.setVideo_image(image);
                        util02.setVideo_name(name);
                        util02.setVideo_desc(desc);
                        util02.setVideo_path(path);
                        list02.add(util02);
                    }

                    for (int i = 20; i < 40; i++) {
                        util03 = new VideoUtil();
                        Element element = elements.get(i);
                        Elements elements2 = element.getElementsByTag("span");
                        // String time = elements2.get(0).text();
                        String name = elements2.get(1).text();
                        Elements elements3 = element.getElementsByTag("a");
                        String desc = elements3.get(1).text();
                        String path = "http:" +elements.get(i).getElementsByTag("a").attr("href");
                        String image = "http:" +elements.get(i).getElementsByTag("img").attr("data-original");
                      //  Log.e("***********", "" + image+"********"+name+"*********"+desc+"*********"+path);
                        util03.setVideo_image(image);
                        util03.setVideo_name(name);
                        util03.setVideo_desc(desc);
                        util03.setVideo_path(path);
                        list03.add(util03);
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
                    adapter01 = new VideoGridAdapter(list01,getActivity());
                    grid_view01.setAdapter(adapter01);
                    grid_view01.setOnItemClickListener(listener);

                    adapter02 = new VideoGridAdapter(list02,getActivity());
                    grid_view02.setAdapter(adapter02);
                    grid_view02.setOnItemClickListener(listener);

                    adapter03 = new VideoGridAdapter(list03,getActivity());
                    grid_view03.setAdapter(adapter03);
                    grid_view03.setOnItemClickListener(listener);

                    progress.setVisibility(View.GONE);
                    break;

            }
        }
    };

    //grid view 监听事件
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Toast.makeText(getActivity(),""+parent,Toast.LENGTH_SHORT).show();

            if (uid.equals("")){
                //未登录操作
                Toast.makeText(getContext(), "您还没有登录！请登录...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),LoginActivity.class));
            }else{
                Intent intent = new Intent(getActivity(),PlayerActivity.class);
                String info = "";
                switch (parent.getId()){
                    case R.id.grid_view01:
                        info = list01.get(position).getVideo_path();
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

        }
    };

    class myonclick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), VideoActivity.class);
            intent.putExtra("path","http://so.tv.sohu.com/list_p1107_p2_p3_p4_p5_p6_p7_p8_p9_p10_p11_p12_p13.html");
            startActivity(intent);
        }
    }
}
