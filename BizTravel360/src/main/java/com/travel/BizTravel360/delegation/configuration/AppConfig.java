package com.travel.BizTravel360.delegation.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    
    private int timeBufferHours;
    
    public AppConfig(@Value("${delegation.validation.time-buffer}") int timeBufferHours) {
        this.timeBufferHours = timeBufferHours;
    }
    
    public int getTimeBufferHours() {
        return timeBufferHours;
    }
    
    public void setTimeBufferHours(int timeBufferHours) {
        this.timeBufferHours = timeBufferHours;
    }
}
