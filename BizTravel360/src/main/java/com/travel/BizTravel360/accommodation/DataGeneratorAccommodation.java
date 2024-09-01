package com.travel.BizTravel360.accommodation;


import com.travel.BizTravel360.accommodation.model.entity.Accommodation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataGeneratorAccommodation {
    
    //TODO add task to do generator to accommodation
    private static final Random RANDOM = new Random();
    
    public static List<Accommodation> generateRandomAccommodationsList(int count) {
        List<Accommodation> accommodations = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            accommodations.add(generateRandomAccommodation());
        }
        return accommodations;
    }
    
    
    private static Accommodation generateRandomAccommodation() {
        Accommodation accommodation = new Accommodation();
        accommodation.setId((long) RANDOM.nextInt(100000));
        accommodation.setNameAccommodation("Accommodation " + RANDOM.nextInt(1000));
        accommodation.setTypeAccommodation(TypeAccommodation.values()[RANDOM.nextInt(TypeAccommodation.values().length)]);
        accommodation.setAddress("Address " + RANDOM.nextInt(1000));
        accommodation.setCheckIn(LocalDateTime.now().plusDays(RANDOM.nextInt(10)).withMinute(RANDOM.nextInt(60)).withSecond(RANDOM.nextInt(60)));
        accommodation.setCheckOut(accommodation.getCheckIn().plusDays(RANDOM.nextInt(5) + 1));
        accommodation.setPrice(RANDOM.nextDouble() * 100);
        return accommodation;
    }
}
