package com.jju.edu.aiqiyi.util;

import android.util.Log;

import com.jju.edu.aiqiyi.entity.MovieUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public class http_data {
    private static List<MovieUtil> oList = new ArrayList<>();


    public static List<MovieUtil> http_(String tagname) {

        try {
            String str = "http:";
            Document doc = Jsoup.connect("http://tv.sohu.com/movie/").get();
            Elements li = doc.select(tagname);
            // Elements elements = doc.select("div.pic");
            Log.e("num", "" + li.size());
            for (int i = 0; i < li.size(); i++) {
                String video_name = li.get(i).select("a").attr("title");
                String video_desc = li.get(i).select("p").first().text();
                String star = li.get(i).select("p").get(1).text();
                String video_image = str + li.get(i).getElementsByTag("img").attr("lazysrc");
                String video_path = li.get(i).getElementsByTag("strong").select("a").attr("href");
                MovieUtil util = new MovieUtil(video_path, video_image, video_name, video_desc, star);
                oList.add(util);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return oList;
    }


}
