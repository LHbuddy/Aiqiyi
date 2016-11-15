package com.jju.edu.aiqiyi.entity;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class DaohangUtil {
    private String name;
    private int image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "DaohangUtil{" +
                "name='" + name + '\'' +
                ", image=" + image +
                '}';
    }
}
