package com.jju.edu.aiqiyi.entity;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class VideoUtil {
    private String video_image;
    private String video_name;
    private String video_path;
    private String video_desc;

    public String getVideo_desc() {
        return video_desc;
    }

    public void setVideo_desc(String video_desc) {
        this.video_desc = video_desc;
    }

    public String getVideo_image() {
        return video_image;
    }

    public void setVideo_image(String video_image) {
        this.video_image = video_image;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getVideo_path() {
        return video_path;
    }

    public void setVideo_path(String video_path) {
        this.video_path = video_path;
    }

    @Override
    public String toString() {
        return "VideoUtil{" +
                "video_image='" + video_image + '\'' +
                ", video_name='" + video_name + '\'' +
                ", video_path='" + video_path + '\'' +
                ", video_desc='" + video_desc + '\'' +
                '}';
    }

    public VideoUtil(String video_path, String video_image, String video_name, String video_desc) {
        this.video_path = video_path;
        this.video_image = video_image;
        this.video_name = video_name;
        this.video_desc = video_desc;
    }
}
