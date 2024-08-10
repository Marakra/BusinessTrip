package com.travel.BizTravel360.transport;


import com.travel.BizTravel360.accommodation.TypeAccommodation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataGeneratorTransport {
    
    private static final Random RANDOM = new Random();
    
    public static List<Transport> generateRandomTransportList(int count) {
        List<Transport> transportList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            transportList.add(generateRandomTransport());
        }
        return transportList;
    }
    
    private static Transport generateRandomTransport() {
        Transport transport = new Transport();
        transport.setTransportId((long) RANDOM.nextLong(100000));
        transport.setTypeTransport(TypeTransport.values()[RANDOM.nextInt(TypeTransport.values().length)]);
        transport.setTransportIdentifier("Identifier" + RANDOM.nextLong(1000));
        transport.setDeparture("Delegation" + RANDOM.nextInt(1000));
        transport.setDepartureDateTime(LocalDateTime.now().plusDays(RANDOM.nextInt(10)).withMinute(RANDOM.nextInt(60)).withSecond(RANDOM.nextInt(60)));
        transport.setArrival("Arrival" + RANDOM.nextInt(1000));
        transport.setArrivalDateTime(transport.getDepartureDateTime().plusDays(RANDOM.nextInt(5) + 1));
        transport.setPrice(RANDOM.nextDouble() * 100);
        return transport;
    }
}
