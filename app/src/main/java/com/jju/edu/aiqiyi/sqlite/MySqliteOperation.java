package com.jju.edu.aiqiyi.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jju.edu.aiqiyi.util.HistoryUtil;
import com.jju.edu.aiqiyi.util.NewsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 凌浩 on 2016/11/18.
 */

public class MySqliteOperation {

    /***
     * 播放历史数据库操作
     * @param db
     * @param img
     * @param name
     * @param desc
     * @param path
     * @param time
     * @param username
     */

    //播放历史数据添加操作
    public static void history_add(SQLiteDatabase db,String img,String name,String desc,String path,String time,String username){
        db.execSQL("insert into play_history(img,name,desc,path,time,username) values(?,?,?,?,?,?)",new String[]{img,name,desc,path,time,username});
    }
    //播放历史数据查找操作
    public static boolean history_exist(SQLiteDatabase db,String path,String username){
        boolean exist=false;
        Cursor cursor = db.rawQuery("select * from play_history where path =? and username=?",new String[]{path,username});
        if (cursor.moveToNext()){
            exist = true;
        }
        return exist;
    }
    //播放历史数据删除操作（单个数据）
    public static void history_delete_one(SQLiteDatabase db,String path,String username){
        db.execSQL("delete from play_history where path=? and username=?", new String[]{path,username});

    }
    //播放历史数据删除操作（所有数据）
    public static void history_delete_all(SQLiteDatabase db,String username){
        db.execSQL("delete  from play_history where username=?",new String[]{username});

    }
    //获取所有历史记录
    public static List<HistoryUtil> history_get_all(SQLiteDatabase db,String username){
        List<HistoryUtil> list = new ArrayList<HistoryUtil>();
        Cursor cursor = db.rawQuery("select * from play_history where username=?",new String[]{username});
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

    /****
     * 搜索记录数据库操作
     * @param db
     * @param name
     * @param username
     */

    //搜索历史添加数据操作
    public static void search_add(SQLiteDatabase db,String name,String username){
       // Log.e("%%%%%%%%%%%%11111","'"+name+"******"+username);
        db.execSQL("insert into search_history(name,username) values(?,?)",new String[]{name,username});
    }
    //判断数据是否存在
    public static boolean search_exist(SQLiteDatabase db,String name,String username){
        boolean exist=false;
        Cursor cursor = db.rawQuery("select * from search_history where name =? and username=?",new String[]{name,username});
        if (cursor.moveToNext()){
            exist = true;
        }
        return exist;
    }
    //删除所有查找记录
    public static void search_delete(SQLiteDatabase db,String username){
        db.execSQL("delete  from search_history where username=?",new String[]{username});

    }
    //查找所有记录
    public static List<NewsUtil> search_all(SQLiteDatabase db, String username){
        List<NewsUtil> list = new ArrayList<NewsUtil>();
        Cursor cursor = db.rawQuery("select * from search_history where username=?",new String[]{username});
        while(cursor.moveToNext()){
            NewsUtil util = new NewsUtil();
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String path = cursor.getString(cursor.getColumnIndex("username"));
            util.setName(name);
            util.setPath(path);
           // Log.e("%%%%%%%%%%%%22222","'"+name+"******"+path);
            list.add(util);
        }
        return list;
    }

    /*****
     * 收藏记录数据库操作
     */
    //添加收藏记录
    public static void collect_add(SQLiteDatabase db,String img,String name,String desc,String path,String username){
        db.execSQL("insert into collect_video(img,name,desc,path,username) values(?,?,?,?,?)",new String[]{img,name,desc,path,username});
    }
    //判断该数据是否已存在
    public static boolean collect_exist(SQLiteDatabase db,String path,String username){
        boolean exist=false;
        Cursor cursor = db.rawQuery("select * from collect_video where path =? and username=?",new String[]{path,username});
        if (cursor.moveToNext()){
            exist = true;
        }
        return exist;
    }
    //删除单个数据
    public static void collect_delete_one(SQLiteDatabase db,String path,String username){
        db.execSQL("delete from collect_video where path=? and username=?", new String[]{path,username});
    }
    //删除所有数据
    public static void collect_delete_all(SQLiteDatabase db,String username){
        db.execSQL("delete  from collect_video where username=?",new String[]{username});
    }
    //查找所有数据
    public static List<HistoryUtil> collect_select_all(SQLiteDatabase db,String username){
        List<HistoryUtil> list = new ArrayList<HistoryUtil>();
        Cursor cursor = db.rawQuery("select * from collect_video where username=?",new String[]{username});
        while(cursor.moveToNext()){
            HistoryUtil util = new HistoryUtil();
            String img = cursor.getString(cursor.getColumnIndex("img"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String desc = cursor.getString(cursor.getColumnIndex("desc"));
            String path = cursor.getString(cursor.getColumnIndex("path"));
            util.setImg(img);
            util.setName(name);
            util.setDesc(desc);
            util.setPath(path);
            list.add(util);
        }
        return list;
    }


}
