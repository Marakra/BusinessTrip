package com.travel.BizTravel360.transport;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
public class TransportController {
    
    private final TransportService transportService;
    
    @Autowired
    public TransportController(TransportService transportService) {this.transportService = transportService;}
    
    @GetMapping("/transports")
    public String getAllTransports(Model model) {
            List<Transport> transports = transportService.readAllTransports();
            model.addAttribute("transports", transports);
        return "transport/transports";
    }
    
//    @GetMapping("createTransportForm")
//    public String createTransport(Model model) {
//        model.addAttribute("transport", new Transport());
//        return "transport/createTransportForm";
//    }
//
//    @PostMapping("createTransport")
//    public String createTransport (@ModelAttribute Transport transport) {
//        try {
//            transportService.createTransport(transport);
//            return "redirect:/transports";
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to create transport", e);
//        }
//    }
    
}
