package com.travel.BizTravel360.employee.component;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RandomStringToGenerateNameToken {
    
    public static String generateRandomNameToken() {
        return UUID.randomUUID().toString();
    }
}
