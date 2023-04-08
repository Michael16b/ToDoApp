package com.example.todomobile;

public class Task {

    /**Identifiant de la tâche*/
    private int id;
    /**Titre de la tâche*/
    private String taskTitle;
    /**Description de la tâche*/
    private String description;
    /**Date de début de la tâche*/
    private String beginDate;
    /**Date de fin de la tâche*/
    private String endDate;
    /**Contexte (lieu) de la tâche*/
    private String context;
    /**Priorité de la tâche*/
    private String priority;
    /**URL de la tâche*/
    private String url;

    /**Constructeur de la tâche*/
    public Task(int id, String taskTitle, String description, String beginDate, String endDate, String context, String priority, String url){
        //Vérification de valeurs
        if(taskTitle !=null && description!=null && beginDate!=null && endDate!=null && context!=null && priority!=null && url != null){
            this.id = id;
            this.taskTitle = taskTitle;
            this.description = description;
            this.beginDate = beginDate;
            this.endDate = endDate;
            this.context = context;
            this.priority = priority;
            this.url = url;
        }
    }

    /**Getter de l'indentifiant*/
    public int getId() {return id;}
    /**Getter du titre*/
    public String getTaskTitle() {return taskTitle;}
    /**Getter de la description*/
    public String getDescription() {return description;}
    /**Getter de la date de début*/
    public String getBeginDate() {return beginDate;}
    /**Getter de la date de fin*/
    public String getEndDate() {return endDate;}
    /**Getter du contexte*/
    public String getContext() {return context;}
    /**Getter de la priorité*/
    public String getPriority() {return priority;}
    /**Getter de l'url*/
    public String getUrl(){return url;}

    /**Setter de l'identifiant*/
    public void setId(int id) {this.id = id;}
    /**Setter du Titre*/
    public void setTaskTitle(String taskTitle) {this.taskTitle = taskTitle;}
    /**Setter de la description*/
    public void setDescription(String description) {this.description = description;}
    /**Setter de la date de début*/
    public void setBeginDate(String beginDate) {this.beginDate = beginDate;}
    /**Setter de la date de fin*/
    public void setEndDate(String endDate) {this.endDate = endDate;}
    /**Setter du contexte*/
    public void setContext(String state) {this.context = state;}
    /**Setter de la priorité*/
    public void setPriority(String priority) {this.priority = priority;}
    /**Setter de l'url*/
    public void setUrl(String url){this.url = url;}

}