package org.infoshere.model;

import java.util.ArrayList;
import java.util.List;

public class Coach {
    private String name;
    private String lastName;
    private String specialization;
    private List<ActivityList> activityList = new ArrayList<ActivityList>();

    public Coach(String name, String lastName, String specialization, List<ActivityList> activityList) {
        this.name = name;
        this.lastName = lastName;
        this.specialization = specialization;
        this.activityList = activityList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public List<ActivityList> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<ActivityList> activityList) {
        this.activityList = activityList;
    }
}
