package com.cempod.motivation20;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {

        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // таблица ежедневных задач
        db.execSQL("create table everydaytask ("
                + "id integer primary key autoincrement,"
                + "task text,"
                + "isComplete text,"
                + "type text,"
                + "rating text,"
                + "target text,"
                + "progress text"
                + ");");

        //таблица задач по датам
        db.execSQL("create table daytask ("
                + "id integer primary key autoincrement,"
                + "date text,"
                + "task text,"
                + "isComplete text,"
                + "type text,"
                + "rating text,"
                + "target text,"
                + "progress text"
                + ");");

        //сводная таблица по датам
        db.execSQL("create table taskarchive ("
                + "id integer primary key autoincrement,"
                + "date text,"
                + "task text,"
                + "isComplete text,"
                + "type text,"
                + "rating text,"
                + "target text,"
                + "progress text"
                + ");");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}