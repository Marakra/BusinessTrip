package com.travel.BizTravel360.delegation.configuration;

import com.travel.BizTravel360.delegation.conversions.StringToAccommodationConverter;
import com.travel.BizTravel360.delegation.conversions.StringToTransportConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    private final StringToTransportConverter stringToTransportConverter;
    private final StringToAccommodationConverter stringToAccommodationConverter;
    
    public WebConfig(StringToTransportConverter stringToTransportConverter,
                     StringToAccommodationConverter stringToAccommodationConverter) {
        this.stringToTransportConverter = stringToTransportConverter;
        this.stringToAccommodationConverter = stringToAccommodationConverter;
    }
    
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToTransportConverter);
        registry.addConverter(stringToAccommodationConverter);
    }
}
