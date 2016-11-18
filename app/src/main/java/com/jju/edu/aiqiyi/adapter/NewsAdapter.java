package com.jju.edu.aiqiyi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jju.edu.aiqiyi.R;
import com.jju.edu.aiqiyi.entity.DaohangUtil;
import com.jju.edu.aiqiyi.util.NewsUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class NewsAdapter extends BaseAdapter {

    private List<NewsUtil> oList = new ArrayList<NewsUtil>();
    private Context oContext;
    private LayoutInflater oInflater;

    public NewsAdapter(List<NewsUtil> oList, Context oContext) {
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
            convertView = oInflater.inflate(R.layout.news_item_grid_layout, null);
            oholder = new ViewHolder();
            oholder.name = (TextView) convertView.findViewById(R.id.text_news);
            convertView.setTag(oholder);


        } else {
            oholder = (ViewHolder) convertView.getTag();
        }
        //Log.e("&&&&&&&&&&&","'"+oholder.name.getText());
        oholder.name.setText(oList.get(position).getName());
        return convertView;
    }

    public class ViewHolder {
        TextView name;
    }
}
