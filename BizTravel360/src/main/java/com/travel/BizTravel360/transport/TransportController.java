package com.travel.BizTravel360.transport;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
public class TransportController {
    
    private final TransportService transportService;
    
    @Autowired
    public TransportController(TransportService transportService) {
        this.transportService = transportService;
    }
    
    @GetMapping("/transports")
    public String getAllTransports(Model model) {
        try {
            List<Transport> transports = transportService.readAllTransports();
            
            Collections.reverse(transports);
            
            for (int i = 0; i < transports.size(); i++) {
                transports.get(i).setTransportId(i+1);
            }
            
            model.addAttribute("transports", transports);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load transport data", e);
        }
        return "pages/transports";
    }
}
