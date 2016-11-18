package com.jju.edu.aiqiyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.entity.MovieUtil;
import com.jju.edu.aiqiyi.entity.VideoUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class MovieGridAdapter extends BaseAdapter {

    private List<MovieUtil> oList = new ArrayList<MovieUtil>();
    private Context oContext;
    private LayoutInflater oInflater;


    public MovieGridAdapter(List<MovieUtil> oList, Context oContext) {
        this.oList = oList;
        this.oContext = oContext;
        oInflater = LayoutInflater.from(oContext);
    }

    @Override
    public int getCount() {
        return oList.size();
    }

    @Override
    public Object getItem(int position) {
        return oList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder oholder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(oContext).inflate(R.layout.movie_grid_img_message_item, null);
            oholder = new ViewHolder();
            oholder.image = (ImageView) convertView.findViewById(R.id.grid_item_iv_image);
            oholder.name = (TextView) convertView.findViewById(R.id.grid_item_tv_name);
            oholder.desc = (TextView) convertView.findViewById(R.id.grid_item_tv_desc);
            oholder.star = (TextView) convertView.findViewById(R.id.grid_item_tv_star);
            convertView.setTag(oholder);
        } else {
            oholder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(oList.get(position).getVideo_image(), oholder.image);
        oholder.name.setText(oList.get(position).getVideo_name());
        oholder.desc.setText(oList.get(position).getVideo_desc());
        oholder.star.setText(oList.get(position).getVideo_star());
        return convertView;
    }

    public class ViewHolder {
        ImageView image;
        TextView name;
        TextView desc;
        TextView star;
    }
}
