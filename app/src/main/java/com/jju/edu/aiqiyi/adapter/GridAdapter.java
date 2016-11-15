package com.jju.edu.aiqiyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.entity.DaohangUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class GridAdapter extends BaseAdapter {

    private List<DaohangUtil> oList = new ArrayList<DaohangUtil>();
    private Context oContext;
    private LayoutInflater oInflater;

    public GridAdapter(List<DaohangUtil> oList, Context oContext) {
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
            convertView = LayoutInflater.from(oContext).inflate(R.layout.item_grid_layout, null);
            oholder = new ViewHolder();
            oholder.image = (ImageView) convertView.findViewById(R.id.item_grid_image);
            oholder.name = (TextView) convertView.findViewById(R.id.item_grid_name);
            convertView.setTag(oholder);
        } else {
            oholder = (ViewHolder) convertView.getTag();
        }
        oholder.image.setImageResource(oList.get(position).getImage());
        oholder.name.setText(oList.get(position).getName());
        return convertView;
    }

    public class ViewHolder {
        ImageView image;
        TextView name;
    }
}
