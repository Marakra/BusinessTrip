package com.travel.BizTravel360.transport;

import java.util.Date;
import java.util.Objects;

public class Transport {
    private long transportId;
    private TypeTransport typeTransport;
    private String departureCity;
    private Date departureTime;
    private String arrivalCity;
    private Date arrivalTime;
    private double amount;
    
    public Transport(long transportId, TypeTransport typeTransport, String departureCity,
                     Date departureTime, String arrivalCity, Date arrivalTime, double amount) {
        this.transportId = transportId;
        this.typeTransport = typeTransport;
        this.departureCity = departureCity;
        this.departureTime = departureTime;
        this.arrivalCity = arrivalCity;
        this.arrivalTime = arrivalTime;
        this.amount = amount;
    }
    
    public long getTransportId() {
        return transportId;
    }
    
    public void setTransportId(long transportId) {
        this.transportId = transportId;
    }
    
    public TypeTransport getTypeTransport() {
        return typeTransport;
    }
    
    public void setTypeTransport(TypeTransport typeTransport) {
        this.typeTransport = typeTransport;
    }
    
    public String getDepartureCity() {
        return departureCity;
    }
    
    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }
    
    public Date getDepartureTime() {
        return departureTime;
    }
    
    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }
    
    public String getArrivalCity() {
        return arrivalCity;
    }
    
    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }
    
    public Date getArrivalTime() {
        return arrivalTime;
    }
    
    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
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
        Transport transport = (Transport) o;
        return transportId == transport.transportId
                && Double.compare(amount, transport.amount) == 0
                && typeTransport == transport.typeTransport
                && Objects.equals(departureCity, transport.departureCity)
                && Objects.equals(departureTime, transport.departureTime)
                && Objects.equals(arrivalCity, transport.arrivalCity)
                && Objects.equals(arrivalTime, transport.arrivalTime);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(transportId, typeTransport, departureCity,
                            departureTime, arrivalCity, arrivalTime, amount);
    }
}

