package com.example.listview;
public class task_data {
    private String task;
    private String description;
    private String dep;

    public task_data(String task, String description, String dep) {
        this.task = task;
        this.description = description;
        this.dep = dep;
    }

    public String getTask() {
        return task;
    }

    public String getDescription() {
        return description;
    }

    public String getDep() {
        return dep;
    }
}