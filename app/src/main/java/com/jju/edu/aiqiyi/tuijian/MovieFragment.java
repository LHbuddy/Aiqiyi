package com.jju.edu.aiqiyi.tuijian;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jju.edu.aiqiyi.PlayerActivity;
import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.adapter.MovieGridAdapter;
import com.jju.edu.aiqiyi.entity.MovieUtil;
import com.jju.edu.aiqiyi.util.http_data;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */

public class MovieFragment extends Fragment {
    private List<MovieUtil> olist = new ArrayList<MovieUtil>();
    private List<MovieUtil> olist_ = new ArrayList<MovieUtil>();
    private View view;
    private ListView m_list;
    private DisplayImageOptions options;
    private LinearLayout progress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.movie_fragment_layout, container, false);
        m_list = (ListView)view.findViewById(R.id.m_list);
        progress = (LinearLayout) view.findViewById(R.id.progress);
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
                getContext()).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(configuration);

        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)// 防止内存溢出的，图片太多就这这个。还有其他设置
                //如Bitmap.Config.ARGB_565
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnLoading(R.drawable.phone_variety_focus_cover_default_bg)//默认图片
                .showImageOnFail(R.drawable.phone_variety_focus_cover_default_bg)
                .showImageForEmptyUri(R.drawable.phone_variety_focus_cover_default_bg)//url为空會显示该图片
                //.displayer(new RoundedBitmapDisplayer(20))//圆角
                .build();
        jsoup_();
        return view;
    }
     public void jsoup_() {
         new Thread() {
             public void run() {
                 olist = http_data.http_("li.active");

//                 olist = http_data.http_("cinema-con");
//                for (int i=0;i<olist.size();i++){
//                     olist_.add(olist.get(i));
//                 }

                 handler.sendEmptyMessage(123);
             }
         }.start();
     }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 123) {
//                for (int i=0;i<olist.size();i++){
//                    olist_.add(olist.get(i));
//                }
                m_list.setAdapter(new MovieGridAdapter(olist,getContext()));
                m_list.setOnItemClickListener(listener);

                progress.setVisibility(View.GONE);
            }
        }
    };
    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            String url = olist.get(position).getVideo_path();
            Intent intent = new Intent();
            intent.putExtra("path", url);
            intent.setClass(getContext(),PlayerActivity.class);
//           // intent.putExtra("cache",
//                    Environment.getExternalStorageDirectory().getAbsolutePath()
//                            + "/VideoCache/" + System.currentTimeMillis() + ".mp4");
           startActivity(intent);


        }
    };
}
