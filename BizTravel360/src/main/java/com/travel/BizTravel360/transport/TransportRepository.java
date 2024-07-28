package com.travel.BizTravel360.transport;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public interface TransportRepository {
    
    void saveTransport(Transport transport) throws IOException;
    List<Transport> fetchTransportList() throws IOException;
    void updateTransport(Transport transport, Long transportId) throws IOException;
    void deleteTransportById(Long transportId) throws IOException;
    Transport findTransportById(Long transportId) throws IOException;
}
