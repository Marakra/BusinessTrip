package org.infoshere.model;

public enum DayOfTheWeek {
    MONDAY("monday"),
    TUESDAY("tuesday"),
    WEDNESDAY("wednesday"),
    THURSDAY("thursday"),
    FRIDAY("friday"),
    SATURDAY("saturday"),
    SUNDAY("sunday");

    public final String label;

    private DayOfTheWeek(String label) {
        this.label = label;
    }
}
