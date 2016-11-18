package com.jju.edu.aiqiyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.entity.VideoUtil;
import com.jju.edu.aiqiyi.util.SportUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class SportsAdapter extends BaseAdapter {

    private List<SportUtil> oList = new ArrayList<SportUtil>();
    private Context oContext;
    private LayoutInflater oInflater;


    public SportsAdapter(List<SportUtil> oList, Context oContext) {
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
            convertView = LayoutInflater.from(oContext).inflate(R.layout.sport_list_item_layout, null);
            oholder = new ViewHolder();
            oholder.image_left = (ImageView) convertView.findViewById(R.id.img_left);
            oholder.image_right = (ImageView) convertView.findViewById(R.id.img_right);
            oholder.time = (TextView) convertView.findViewById(R.id.text_time);
            oholder.team = (TextView) convertView.findViewById(R.id.text_team);
            convertView.setTag(oholder);
        } else {
            oholder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(oList.get(position).getImg_left(), oholder.image_left);
        ImageLoader.getInstance().displayImage(oList.get(position).getImg_right(), oholder.image_right);
        oholder.time.setText(oList.get(position).getTime());
        oholder.team.setText(oList.get(position).getTeam());
      //  oholder.desc.setText(oList.get(position).getVideo_desc());
        return convertView;
    }

    public class ViewHolder {
        ImageView image_left,image_right;
        TextView time;
        TextView team;
    }
}
