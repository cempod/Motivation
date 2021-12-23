package com.cempod.motivation20;

import java.util.List;

public class ItemListManager {
    private List<Task> todayList;
    private List<Task> everydayList;

  public ItemListManager(List<Task> todayList, List<Task> everydayList) {
      this.todayList = todayList;
      this.everydayList = everydayList;
  }

  public  ItemListManager(){

  }

  public void addTodayTask(Task task){
      this.todayList.add(task);
  }
  public  void addEverydayTask(Task task){
      this.everydayList.add(task);
  }




}
