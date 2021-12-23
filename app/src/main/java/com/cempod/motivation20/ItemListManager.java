package com.cempod.motivation20;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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


  }

public void refreshLists() {

    String myDate = dateFormat.format(new Date());
    todayList.clear();
    everydayList.clear();
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    Cursor c = db.query("daytask", null, "date = ?", new String[]{myDate}, null, null, null);

    if (c.moveToFirst()) {

        int idColIndex = c.getColumnIndex("id");
        int dateColIndex = c.getColumnIndex("date");
        int taskColIndex = c.getColumnIndex("task");
        int isCompleteColIndex = c.getColumnIndex("isComplete");
        int typeColIndex = c.getColumnIndex("type");
        int ratingColIndex = c.getColumnIndex("rating");
        int targetColIndex = c.getColumnIndex("target");
        int progressColIndex = c.getColumnIndex("progress");
        do {


            boolean isComplete;
            if (c.getString(isCompleteColIndex).contains("true")) {
                isComplete = true;
            } else {
                isComplete = false;
            }
            todayList.add(new Task(c.getString(typeColIndex), c.getString(taskColIndex), isComplete, c.getInt(ratingColIndex), c.getInt(targetColIndex), c.getInt(progressColIndex)));

        } while (c.moveToNext());

        c = db.query("everydaytask", null, null, null, null, null, null);

        if (c.moveToFirst()) {

            idColIndex = c.getColumnIndex("id");
            taskColIndex = c.getColumnIndex("task");
            isCompleteColIndex = c.getColumnIndex("isComplete");
            typeColIndex = c.getColumnIndex("type");
            ratingColIndex = c.getColumnIndex("rating");
            targetColIndex = c.getColumnIndex("target");
            progressColIndex = c.getColumnIndex("progress");
            do {


                boolean isComplete;
                if (c.getString(isCompleteColIndex).contains("true")) {
                    isComplete = true;
                } else {
                    isComplete = false;
                }
                everydayList.add(new Task(c.getString(typeColIndex), c.getString(taskColIndex), isComplete, c.getInt(ratingColIndex), c.getInt(targetColIndex), c.getInt(progressColIndex)));

            } while (c.moveToNext());

            dbHelper.close();
        }
    }
}


}
