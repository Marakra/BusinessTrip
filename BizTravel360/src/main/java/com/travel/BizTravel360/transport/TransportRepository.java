package com.travel.BizTravel360.transport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public interface TransportRepository {
    //CRUD
    void saveTransport(Transport transport) throws IOException;
    void updateTransport(Transport transport, Long transportId) throws IOException;
    void deleteTransportById(Long transportId) throws IOException;
    
    void generateAndSaveRandomTransport(int count) throws IOException;
}
