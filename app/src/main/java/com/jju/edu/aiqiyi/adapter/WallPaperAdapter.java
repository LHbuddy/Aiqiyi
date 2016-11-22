package com.jju.edu.aiqiyi.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jju.edu.aiqiyi.R;

import java.util.List;

/**
 * Created by Administrator on 2016/11/22.
 */

public class WallPaperAdapter extends BaseAdapter {

    private Context context;
    private List<Bitmap> list;
    private LayoutInflater inflater;

    public WallPaperAdapter(Context context,List<Bitmap> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoulder houlder = null;
        if (view==null){
            houlder = new ViewHoulder();
            view = inflater.inflate(R.layout.wallpaper_gridview_item_layout,null);
            houlder.iv_wallpaper_item = (ImageView) view.findViewById(R.id.iv_wallpaper_item);
            view.setTag(houlder);
        }else {
            houlder = (ViewHoulder) view.getTag();
        }
        houlder.iv_wallpaper_item.setImageBitmap(list.get(i));
        return view;
    }

    class ViewHoulder{
        ImageView iv_wallpaper_item;
    }
}
