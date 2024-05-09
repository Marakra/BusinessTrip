package org.infoshere.model;

public class Activity {
    private String nameActivity;
    private Coach coach;
    private String time;
    private TypeActivity typeActivity;


    public Activity(Coach coach, String nameActivity, String time, TypeActivity typeActivity) {
        this.coach = coach;
        this.nameActivity = nameActivity;
        this.time = time;
        this.typeActivity = typeActivity;
    }

    public String getName() {
        return nameActivity;
    }

    public void setName(String name) {
        this.nameActivity = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public void setTypeActivity(TypeActivity typeActivity) {
        this.typeActivity = typeActivity;
    }

    public TypeActivity getTypeActivity() {
        return typeActivity;
    }
}