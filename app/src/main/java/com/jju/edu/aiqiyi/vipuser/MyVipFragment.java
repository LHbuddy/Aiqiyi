package com.jju.edu.aiqiyi.vipuser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.entity.VideoUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Administrator on 2016/11/14.
 */

public class MyVipFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myvip_vipuser_fragment_layout,container,false);

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
                    Document document = Jsoup.connect("http://tv.sohu.com/show/").get();

                    Elements elements_ = document.select(".colL");
                    //  Log.e("^^^^^^^^^", "" + elements_.size());
                    Element element_ = elements_.get(0);
//                    img_head = "http:" + element_.getElementsByTag("img").attr("data-original");
//                    path_head = element_.getElementsByTag("a").attr("href");
//                    desc_head = element_.getElementsByTag("a").text();

                    Elements elements = document.select(".lisi");
                    //  Log.e("//////////",""+elements.size());
                    for (int i = 0; i < 4; i++) {
//                        util = new VideoUtil();
                        String img = elements.get(i).getElementsByTag("img").attr("data-original");
                        Element element = elements.get(i);
                        Elements elements2 = element.getElementsByTag("p");
                        String name = elements2.get(0).text();
                        String desc = elements2.get(1).text();
                        //  String desc = elements.get(i).getElementsByTag(".p_bt").text();
                        String path = elements.get(i).getElementsByTag("a").attr("href");
                        // Log.e("222222", "" + img + "******" + name + "******" + desc + "******" + path);
//                        util.setVideo_image("http:" + img);
//                        util.setVideo_name(name);
//                        util.setVideo_desc(desc);
//                        util.setVideo_path("http:" +path);
//                        list.add(util);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
              //  handler.sendEmptyMessage(123);
            }
        }.start();
    }
}
