package com.travel.BizTravel360._configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Configuration
public class AppConfig {
    
    private int timeBufferHours;
    
    public AppConfig(@Value("${delegation.validation.time-buffer}") int timeBufferHours) {
        this.timeBufferHours = timeBufferHours;
    }
    
}
