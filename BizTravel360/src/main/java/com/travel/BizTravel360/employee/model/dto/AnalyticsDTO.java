package com.travel.BizTravel360.employee.model.dto;

import lombok.Data;

import java.util.Map;

@Data
public class AnalyticsDTO {
    
    private Long id;
    private Map<String, Long> popularTypeAccommodations;
    private Map<String, Double> accommodationCosts;
    private Map<String, Double> transportCosts;
    private double totalCost;
}
