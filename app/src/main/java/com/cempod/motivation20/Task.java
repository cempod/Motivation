package com.cempod.motivation20;

public class Task {
    private String type;
    private String task;
    private boolean isComplete;
    private String id;
    private int rating;
    private int target;
    private int progress;
    private int index;

    Task(String type, String task,boolean isComplete, int rating, int target , int progress, String id){
this.type = type;
        this.task = task;
        this.isComplete = isComplete;
        this.id = id;
        this.rating = rating;
        this.target = target;
        this.progress = progress;
        index = 0;
    }


    Task(String type,String task){
        this.type = type;
        this.task = task;
    }

    public String getType() {
        return this.type;
    }

    public String getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getTarget() {
        return this.target;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return this.rating;
    }


    public String getTask() {
        return this.task;
    }
    public void setTask(String task) {
        this.task = task;
    }

    public boolean getIsComplete() {
        return this.isComplete;
    }
    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }
}