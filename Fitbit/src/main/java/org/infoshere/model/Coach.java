package org.infoshere.model;

import java.util.ArrayList;
import java.util.List;

public class Coach {
    private String firstName;
    private String lastName;
    private String specialization;
    private List<Activity> activityList = new ArrayList<Activity>();

    public Coach(String firstName, String lastName, String specialization, List<Activity> activityList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.activityList = activityList;
    }

    public String getName() {
        return firstName;
    }

    public void setName(String name) {
        this.firstName = name;
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

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }
}
