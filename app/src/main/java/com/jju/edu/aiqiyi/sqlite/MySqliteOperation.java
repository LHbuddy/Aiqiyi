package com.jju.edu.aiqiyi.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jju.edu.aiqiyi.util.HistoryUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 凌浩 on 2016/11/18.
 */

public class MySqliteOperation {

    //播放历史数据添加操作
    public static void history_add(SQLiteDatabase db,String img,String name,String desc,String path,String time){
        db.execSQL("insert into play_history(img,name,desc,path,time) values(?,?,?,?,?)",new String[]{img,name,desc,path,time});
    }
    //播放历史数据查找操作
    public static boolean history_exist(SQLiteDatabase db,String path){
        boolean exist=false;
        Cursor cursor = db.rawQuery("select * from play_history where path =?",new String[]{path});
        if (cursor.moveToNext()){
            exist = true;
        }
        return exist;
    }
    //播放历史数据删除操作（单个数据）
    public static void history_delete_one(SQLiteDatabase db,String path){
        db.execSQL("delete from play_history where path=?", new String[]{path});

    }
    //播放历史数据删除操作（所有数据）
    public static void history_delete_all(SQLiteDatabase db){
        db.execSQL("delete * from play_history",null);

    }
    //获取所有历史记录
    public static List<HistoryUtil> history_get_all(SQLiteDatabase db){
        List<HistoryUtil> list = new ArrayList<HistoryUtil>();
        Cursor cursor = db.rawQuery("select * from play_history",null);
        while(cursor.moveToNext()){
            HistoryUtil util = new HistoryUtil();
            String img = cursor.getString(cursor.getColumnIndex("img"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String desc = cursor.getString(cursor.getColumnIndex("desc"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String path = cursor.getString(cursor.getColumnIndex("path"));
            util.setImg(img);
            util.setName(name);
            util.setDesc(desc);
            util.setTime(time);
            util.setPath(path);
            list.add(util);
        }
        return list;
    }


}
