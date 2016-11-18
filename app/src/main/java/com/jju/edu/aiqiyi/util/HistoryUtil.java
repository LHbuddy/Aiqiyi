package com.jju.edu.aiqiyi.util;

/**
 * Created by 凌浩 on 2016/11/18.
 */

public class HistoryUtil {

    private String img;
    private String name;
    private String desc;
    private String path;

    public HistoryUtil() {
    }

    public HistoryUtil(String img, String name, String desc, String path) {
        this.img = img;
        this.name = name;
        this.desc = desc;
        this.path = path;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
