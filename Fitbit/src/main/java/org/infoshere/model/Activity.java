package org.infoshere.model;

import java.util.Objects;

public class Activity {
    private String nameActivity;
    private DayOfTheWeek dayOfTheWeek;
    private TypeActivity typeActivity;


    public Activity(String nameActivity, DayOfTheWeek dayOfTheWeek, TypeActivity typeActivity) {
        this.nameActivity = nameActivity;
        this.dayOfTheWeek = DayOfTheWeek.valueOf(String.valueOf(dayOfTheWeek));
        this.typeActivity = TypeActivity.valueOf(String.valueOf(typeActivity));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(nameActivity, activity.nameActivity)
                && Objects.equals(dayOfTheWeek, activity.dayOfTheWeek)
                && typeActivity == activity.typeActivity;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "nameActivity='" + nameActivity + '\'' +
                ", dayOfTheWeek=" + dayOfTheWeek +
                ", typeActivity=" + typeActivity +
                '}';
    }

    @Override
    public int hashCode() {return Objects.hash(nameActivity);}

    public String getName() {
        return nameActivity;
    }

    public void setName(String name) {
        this.nameActivity = name;
    }

    public DayOfTheWeek getTime() {
        return dayOfTheWeek;
    }

    public void setTime(DayOfTheWeek dayOfTheWeek) {this.dayOfTheWeek = dayOfTheWeek;
    }

    public void setTypeActivity(TypeActivity typeActivity) {
        this.typeActivity = typeActivity;
    }

    public TypeActivity getTypeActivity() {
        return typeActivity;
    }
}
