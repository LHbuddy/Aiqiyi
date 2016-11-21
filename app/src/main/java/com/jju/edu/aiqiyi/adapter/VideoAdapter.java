package com.jju.edu.aiqiyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.entity.LoadedImage;
import com.jju.edu.aiqiyi.entity.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/20 0020.
 */

public class VideoAdapter extends BaseAdapter {

    private List<Video> listVideos;
    private Context context;
    private int local_postion = 0;
    boolean imageChage = false;
    private LayoutInflater mLayoutInflater;
    private ArrayList<LoadedImage> photos = new ArrayList<LoadedImage>();

    public VideoAdapter(List<Video> listVideos, Context context) {

        this.listVideos = listVideos;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int position) {
        return photos.get(position);
    }

    public void addPhoto(LoadedImage image) {
        photos.add(image);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_lv_layout, null);
            holder.img = (ImageView) convertView.findViewById(R.id.video_img);
            holder.title = (TextView) convertView.findViewById(R.id.video_title);
            holder.time = (TextView) convertView.findViewById(R.id.video_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(listVideos.get(position).getTitle());//ms
        long min = listVideos.get(position).getDuration() / 1000 / 60;
        long sec = listVideos.get(position).getDuration() / 1000 % 60;
        holder.time.setText(min + " : " + sec);
        holder.img.setImageBitmap(photos.get(position).getBitmap());

        return convertView;
    }

    public final class ViewHolder {
        public ImageView img;
        public TextView title;
        public TextView time;
    }
}
