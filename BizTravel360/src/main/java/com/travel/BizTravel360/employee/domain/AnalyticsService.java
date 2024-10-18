package com.travel.BizTravel360.employee.domain;

import com.travel.BizTravel360.accommodation.domain.AccommodationRepository;
import com.travel.BizTravel360.accommodation.model.entity.Accommodation;
import com.travel.BizTravel360.employee.model.dto.AnalyticsDTO;
import com.travel.BizTravel360.employee.model.entity.Employee;
import com.travel.BizTravel360.transport.domain.TransportRepository;
import com.travel.BizTravel360.transport.model.entity.Trasport;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {
    
    private final AccommodationRepository accommodationRepository;
    private final TransportRepository transportRepository;
    
    public AnalyticsService(AccommodationRepository accommodationRepository, TransportRepository transportRepository) {
        this.accommodationRepository = accommodationRepository;
        this.transportRepository = transportRepository;
    }
    
    public AnalyticsDTO getAnalyticsForEmployee(Employee employee) {
        AnalyticsDTO analytics = new AnalyticsDTO();
        
        List<Accommodation> accommodations = accommodationRepository.findByEmployee(employee);
        List<Trasport> transports = transportRepository.findByEmployee(employee);
        
        Map<String, Long> popularAccommodationTypes = new HashMap<>();
        double totalAccommodationCost = 0.0;
        
        for (Accommodation accommodation : accommodations) {
            totalAccommodationCost += accommodation.getPrice();
            popularAccommodationTypes.merge(accommodation.getTypeAccommodation().name(), 1L, Long::sum);
        }
        
        analytics.setPopularTypeAccommodations(popularAccommodationTypes);
        analytics.setAccommodationCosts(Map.of("Total Accommodation Cost", totalAccommodationCost));
        
        Map<String, Long> popularTransportTypes = new HashMap<>();
        double totalTransportCost = 0.0;
        
        for (Trasport transport : transports) {
            totalTransportCost += transport.getPrice();
            popularTransportTypes.merge(transport.getTypeTransport().name(), 1L, Long::sum);
        }
        
        analytics.setTransportCosts(Map.of("Total Transport Cost", totalTransportCost));
        analytics.setPopularTypeAccommodations(popularTransportTypes);
        analytics.setTotalCost(totalAccommodationCost + totalTransportCost);
        
        return analytics;
    }
}
