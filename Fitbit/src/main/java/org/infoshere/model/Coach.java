package org.infoshere.model;

import java.util.ArrayList;
import java.util.List;

public class Coach {
    private final int coachId;
    private String firstName;
    private String lastName;
    private String specialization;
    private List<Activity> activities = new ArrayList<Activity>();


    public Coach(int coachId, String firstName, String lastName, String specialization, List<Activity> activities) {
        this.coachId = coachId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.activities = activities;
    }

    public int getCoachId() {
        return coachId;
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

    public List<Activity> getActivities() {return activities;}

    public void setActivities(List<Activity> activities) {this.activities = activities;}
}
