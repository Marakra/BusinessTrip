package org.infoshere.model;

public class ActivityList {
    private String name;
    private String coach;
    private String time;
    private String typeActivity;

    public ActivityList(String coach, String name, String time, String typeActivity) {
        this.coach = coach;
        this.name = name;
        this.time = time;
        this.typeActivity = typeActivity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getTypeActivity() {
        return typeActivity;
    }

    public void setTypeActivity(String typeActivity) {
        this.typeActivity = typeActivity;
    }
}
