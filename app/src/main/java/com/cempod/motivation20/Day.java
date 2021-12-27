package com.cempod.motivation20;

public class Day {
    private String date;
    private int taskCount;
    private int completeCount;

    public Day(String date, int taskCount, int completeCount){
        this.date = date;
        this.completeCount = completeCount;
        this.taskCount = taskCount;
    }

    public String getDate() {
        return date;
    }
    public int getTaskCount() {
        return taskCount;
    }
    public int getCompleteCount() {
        return completeCount;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }
    public void setCompleteCount(int completeCount) {
        this.completeCount = completeCount;
    }
}
