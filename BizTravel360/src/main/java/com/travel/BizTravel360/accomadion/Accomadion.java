package com.travel.BizTravel360.accomadion;

import java.util.Date;
import java.util.Objects;

public class Accomadion {

    private String name;
    private String location;
    private Date checkInDate;
    private Date checkOutDate;
    private double amount;

    public Accomadion(String name, String location, double amount, Date checkOutDate, Date checkInDate) {
        this.name = name;
        this.location = location;
        this.amount = amount;
        this.checkOutDate = checkOutDate;
        this.checkInDate = checkInDate;
    }

    public boolean isBooked() {
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accomadion that = (Accomadion) o;
        return Double.compare(amount, that.amount) == 0 && Objects.equals(name, that.name) && Objects.equals(location, that.location) && Objects.equals(checkInDate, that.checkInDate) && Objects.equals(checkOutDate, that.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, location, checkInDate, checkOutDate, amount);
    }
}
