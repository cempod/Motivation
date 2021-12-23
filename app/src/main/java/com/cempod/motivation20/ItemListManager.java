package com.cempod.motivation20;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ItemListManager {
    private List<Task> todayList;
    private List<Task> everydayList;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
DBHelper dbHelper;
Context context;
  public ItemListManager(List<Task> todayList, List<Task> everydayList, Context context) {
      this.todayList = todayList;
      this.everydayList = everydayList;
      this.context = context;
      dbHelper = new DBHelper(context);
  }

  public  ItemListManager(Context context){
      this.context = context;
      dbHelper = new DBHelper(context);
  }

  public void addTodayTask(Task task, Date date){
      ContentValues cv = new ContentValues();
      SQLiteDatabase db = dbHelper.getWritableDatabase();
      cv.put("date", dateFormat.format(date).toString());
      cv.put("task", task.getTask());
      cv.put("isComplete","false");
      cv.put("type",task.getType().toString());
      cv.put("rating",Integer.toString(task.getRating()));
      cv.put("target",Integer.toString(task.getTarget()));
      cv.put("progress","0");
      db.insert("daytask", null, cv);
      dbHelper.close();

  }
  public  void addEverydayTask(Task task){
      ContentValues cv = new ContentValues();
      SQLiteDatabase db = dbHelper.getWritableDatabase();
      cv.put("task", task.getTask());
      cv.put("isComplete","false");
      cv.put("type",task.getType().toString());
      cv.put("rating",Integer.toString(task.getRating()));
      cv.put("target",Integer.toString(task.getTarget()));
      cv.put("progress","0");
      db.insert("everydaytask", null, cv);
      dbHelper.close();

      this.everydayList.add(task);
  }




}
