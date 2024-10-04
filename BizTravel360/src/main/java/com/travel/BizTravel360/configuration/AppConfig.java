package com.travel.BizTravel360.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
public class AppConfig {
    
    private int timeBufferHours;
    
    public AppConfig(@Value("${delegation.validation.time-buffer}") int timeBufferHours) {
        this.timeBufferHours = timeBufferHours;
    }
    
}
