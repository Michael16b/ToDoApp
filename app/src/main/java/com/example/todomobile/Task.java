package com.example.todomobile;

import java.util.Date;

public class Task {

    private String taskTitle;
    private String description;
    private String beginDate;
    private String endDate;
    private String context;
    private String priority;
    private String url;

    public Task(String taskTitle, String description, String beginDate, String endDate, String context, String priority, String url){

        if(taskTitle !=null && description!=null && beginDate!=null && endDate!=null && context!=null && priority!=null && url != null){
            this.taskTitle = taskTitle;
            this.description = description;
            this.beginDate = beginDate;
            this.endDate = endDate;
            this.context = context;
            this.priority = priority;
            this.url = url;
        }
    }

    public String getTaskTitle() {return taskTitle;}
    public String getDescription() {return description;}
    public String getBeginDate() {return beginDate;}
    public String getEndDate() {return endDate;}
    public String getContext() {return context;}
    public String getPriority() {return priority;}
    public String getUrl(){return url;}

    public void setTaskTitle(String taskTitle) {this.taskTitle = taskTitle;}
    public void setDescription(String description) {this.description = description;}
    public void setBeginDate(String beginDate) {this.beginDate = beginDate;}
    public void setEndDate(String endDate) {this.endDate = endDate;}
    public void setContext(String state) {this.context = state;}
    public void setPriority(String priority) {this.priority = priority;}
    public void setUrl(String url){this.url = url;}
}