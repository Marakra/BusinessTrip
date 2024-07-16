package com.travel.BizTravel360.transport;

import java.util.List;

public interface TransportRepository {
    
    void createTransport(Transport transport) throws Exception;
    List<Transport> readAllTransports() throws Exception;
}
