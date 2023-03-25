package com.example.todomobile;

import java.util.Date;

public class Task {

    private String taskTitle;
    private String description;
    private String beginDate;
    private String endDate;
    private String state;
    private String priority;
    private String url;

    public Task(String taskTitle, String description, String beginDate, String endDate, String state, String priority, String url){

        if(taskTitle !=null && description!=null && beginDate!=null && endDate!=null && state!=null && priority!=null && url != null){
            this.taskTitle = taskTitle;
            this.description = description;
            this.beginDate = beginDate;
            this.endDate = endDate;
            this.state = state;
            this.priority = priority;
            this.url = url;
        }
    }

    public String getTaskTitle() {return taskTitle;}
    public String getDescription() {return description;}
    public String getBeginDate() {return beginDate;}
    public String getEndDate() {return endDate;}
    public String getState() {return state;}
    public String getPriority() {return priority;}
    public String getUrl(){return url;}

    public void setTaskTitle(String taskTitle) {this.taskTitle = taskTitle;}
    public void setDescription(String description) {this.description = description;}
    public void setBeginDate(String beginDate) {this.beginDate = beginDate;}
    public void setEndDate(String endDate) {this.endDate = endDate;}
    public void setState(String state) {this.state = state;}
    public void setPriority(String priority) {this.priority = priority;}
    public void setUrl(String url){this.url = url;}
}