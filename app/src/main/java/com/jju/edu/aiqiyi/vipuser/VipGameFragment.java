package com.jju.edu.aiqiyi.vipuser;

import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jju.edu.aiqiyi.PlayerActivity;
import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.VideoActivity;
import com.jju.edu.aiqiyi.adapter.VideoGridAdapter;
import com.jju.edu.aiqiyi.entity.VideoUtil;
import com.jju.edu.aiqiyi.wode.LoginActivity;
import com.jju.edu.aiqiyi.wode.SearchActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */

public class VipGameFragment extends Fragment {
    private View view;

    private GridView gv_video;

    //图片加载配置
    private DisplayImageOptions options;

    //加载路径
    private String path = "http://so.tv.sohu.com/list_p1128_p2_p3_p4_p5_p6_p7_p8_p9_p10_p11_p12_p13.html";

    //一次加载的数量
    private int count = 0;
    private VideoUtil videoUtil;
    private List<VideoUtil> list = new ArrayList<VideoUtil>();
    private VideoGridAdapter adapter;
    //是否已经加载好视频播放地址，判断点击事件是否执行
    private boolean isReady = false;
    //下一页的地址
    private String next_path;
    //设置一个FLAG，获得list的大小，设置GridView显示的item
    private int flag = 0;
    //判断是否是刷新，如果是就清空list集合中的内容
    private boolean isReflash;
    //判断是否是搞笑界面，如果是就加载另一个布局
    private boolean isGaoXiao = false;
    private LinearLayout progress;
    private String uid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.viphome_vipuser_fragment_layout, container, false);
        gv_video = (GridView) view.findViewById(R.id.gridview_vipgame);
        progress = (LinearLayout) view.findViewById(R.id.progress);

        http_(path);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        uid = LoginActivity.uid_get;
    }

    //加载视频信息
    private void http_(final String uri) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(uri).get();
                    Elements e = doc.getElementsByTag("li");

                    for (int i = 18; i < e.size(); i++) {
                        videoUtil = new VideoUtil();
                        Element element = e.get(i);
                        String path = element.getElementsByTag("a").get(0).attr("href");
                        String image_path = element.getElementsByTag("img").attr("src");
                        String video_name = element.getElementsByTag("a").get(2).attr("title");
                        String video_desc = "";
                        if (element.getElementsByTag("p").size() != 0) {
                            isGaoXiao = false;
                            video_desc = element.getElementsByTag("p").first().text();
                        } else {
                            isGaoXiao = true;
                            video_desc = element.getElementsByTag("h3").text();
                        }
                        videoUtil.setVideo_name(video_name);
                        videoUtil.setVideo_image(image_path);
                        videoUtil.setVideo_path(path);
                        videoUtil.setVideo_desc(video_desc);
                        if (isReflash) {  //判断是否是刷新，是则清除list集合中的内容
                            //再次加载内容
                            //清空List集合中的内容
                            list.clear();
                            list = null;
                        }
                        list.add(videoUtil);
                    }
                    //获得下一页的地址
                    Elements elements_next = doc.select("a[title]");
                    next_path = "http://so.tv.sohu.com" + elements_next.get(elements_next.size() - 1).attr("href");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(234);
            }
        }.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 234:
                    loadInfo();
                    break;
                case 235:
                    isReady = true;
                    progress.setVisibility(View.GONE);
                    break;
            }
        }
    };

    //加载信息
    private void loadInfo() {
        adapter = new VideoGridAdapter(list, getContext());
        if (isGaoXiao) {
            gv_video.setNumColumns(2);
        }
        gv_video.setAdapter(adapter);
        gv_video.smoothScrollByOffset(flag);
        isReady = false;
        loadmoreInfo();
        gv_video.setOnItemClickListener(gridViewOnItemClick);
        gv_video.setOnTouchListener(gridViewOnTouch);
    }

    //加载视频播放地址
    private void loadmoreInfo() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    if (flag != 0) {
                        flag -= 1;
                    }
                    //通过FLAG的值来获取视频播放地址
                    for (int i = flag; i < list.size(); i++) {
                        String path_ = list.get(i).getVideo_path();  //获取视频简介地址
                        String video_path = path_;
                        Document document = Jsoup.connect(path_).get();  //加载视频简介地址
                        if (document.select("a.btn-playFea").size() > 0) {  //如果没有获得，跳过
                            Elements e_1 = document.select("a.btn-playFea");
                            video_path = e_1.get(0).attr("href");
                            //设置视频播放地址
                        } else if (document.getElementById("hisPlay") != null) {
                            Element element = document.getElementById("hisPlay");
                            video_path = element.attr("href");
                        }
                        list.get(i).setVideo_path(video_path);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(235);
            }
        }.start();
    }

    //GridView的触摸下拉刷新和上拉加载事件
    private View.OnTouchListener gridViewOnTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            GridView gridView = (GridView) view;
            float y = 0;
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    y = motionEvent.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    if ((motionEvent.getY() - y) <= -8) {  //下拉
                        if (gridView.getFirstVisiblePosition() == 0 && gridView.getChildAt(0).getTop() >= 0) {
                            //滑到顶部,刷新
                            //刷新
                            isReflash = true;
                            http_(path);
                        }
                    } else if ((motionEvent.getY() - y) >= 8) {  //上拉
                        if (gridView.getLastVisiblePosition() == (gridView.getCount() - 1)
                                && gridView.getChildAt(gridView.getLastVisiblePosition() -
                                gridView.getFirstVisiblePosition()).getBottom() <= gridView.getMeasuredHeight()) {
                            //滑到底部，加载更多
                            //设置一个FLAG，获得list的大小，设置GridView显示的item
                            flag = list.size();
                            http_(next_path);
                        }
                    }
                    break;
            }
            return false;
        }
    };

    //GridView的点击事件
    private AdapterView.OnItemClickListener gridViewOnItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            if (uid.equals("")){
                //未登录操作
                Toast.makeText(getContext(), "您还没有登录！请登录...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),LoginActivity.class));
            }else{
                //判断视频播放地址是否加载完成
                if (isReady) {  //加载完成跳转
                    String play_path = list.get(i).getVideo_path();
                    System.out.println("视频播放地址：-----" + play_path);
                    Intent intent_play = new Intent(getContext(), PlayerActivity.class);
                    intent_play.putExtra("path", play_path);
                    startActivity(intent_play);
                } else {   //加载未完成提示
                    Toast.makeText(getContext(), "正在加载中,请稍候！", Toast.LENGTH_SHORT).show();
                }
            }



        }
    };


}
