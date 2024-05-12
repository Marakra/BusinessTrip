package org.infoshere.model;

import java.util.ArrayList;
import java.util.List;

public class Coach {
    private final int id;
    private String firstName;
    private String lastName;
    private String specialization;
    private List<Activity> activities = new ArrayList<Activity>();


    public Coach(int id, String firstName, String lastName, String specialization, List<Activity> activities) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.activities = activities;
    }

    public int getId() {return id;}

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
        return activities;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activities = activityList;
    }
}
