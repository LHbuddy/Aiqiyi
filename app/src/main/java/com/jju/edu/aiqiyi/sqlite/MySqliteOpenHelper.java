package com.jju.edu.aiqiyi.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 凌浩 on 2016/10/11.
 */

public class MySqliteOpenHelper extends SQLiteOpenHelper{


    public MySqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table play_history(id integer primary key autoincrement,img,name,desc,path,time,username)");
        db.execSQL("create table collect_video(id integer primary key autoincrement,img,name,desc,path,username)");
        db.execSQL("create table search_history(id integer primary key autoincrement,name,username)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
