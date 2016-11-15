package com.jju.edu.aiqiyi.util;

/**
 * Created by 凌浩 on 2016/11/15.
 */

public class AdvertUtil {
    private String img_path;
    private String video_path;


    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getVideo_path() {
        return video_path;
    }

    public void setVideo_path(String video_path) {
        this.video_path = video_path;
    }

    @Override
    public String toString() {
        return "AdvertUtil{" +
                "img_path='" + img_path + '\'' +
                ", video_path='" + video_path + '\'' +
                '}';
    }
}
