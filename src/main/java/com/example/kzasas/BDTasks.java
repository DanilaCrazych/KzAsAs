package com.example.kzasas;

public class BDTasks {
    int id;
    String staff, task, comit, description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getCoomit() {
        return comit;
    }

    public void setCoomit(String coomit) {
        this.comit = coomit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BDTasks(int id, String staff, String task, String comit, String description) {
        this.id = id;
        this.staff = staff;
        this.task = task;
        this.comit = comit;
        this.description = description;
    }
}
