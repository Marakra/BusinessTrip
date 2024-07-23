package com.travel.BizTravel360.transport;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
public class TransportController {
    
    private final TransportService transportService;
    
    public TransportController(TransportService transportService) {this.transportService = transportService;}
    
    @GetMapping("/transports")
    public String getAllTransports(Model model) {
        List<Transport> transportList = transportService.fetchTransportList();
        log.info("Fetched {} transport", transportList.size());
        model.addAttribute("transports", transportList);
        return "transport/transports";
    }
    
    @GetMapping("/transportForm")
    public String showSaveTransportForm(Model model) {
        model.addAttribute("transport", new Transport());
        return "transport/createTransportForm";
    }
    
    @PostMapping("/newTransport")
    public String saveTransport(@Valid @ModelAttribute("transport") Transport transport,
                                BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            return "transport/createTransportForm";
        }
            Transport savedTransport = transportService.saveTransport(transport);
            String successMessage = String.format("Transport %s (%s) successfully saved. Departure: %s, Arrival: %s.",
                    savedTransport.getTypeTransport(),
                    savedTransport.getTransportIdentifier(),
                    savedTransport.getDeparture(),
                    savedTransport.getArrival());
            model.addAttribute("successMessage", successMessage);
            model.addAttribute("transport", new Transport());  // Reset the form
        return "transport/createTransportForm";
    }
    
    
    @GetMapping("/updateTransport/{transportId}")
    public String showUpdateTransportForm(@PathVariable("transportId") Long transportId, Model model) throws IOException {
        Transport transport = transportService.findTransportById(transportId);
        model.addAttribute("transport", transport);
        return "transport/updateTransportForm";
    }
    
    @PostMapping("/updateTransport")
    public String updateTransport(@Valid @ModelAttribute("transport") Transport transport, Long transportId,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            return "transport/updateTransportForm";
        }
            Transport updatedTransport = transportService.updateTransport(transport, transportId);
            String successMessage = String.format("Transport %s (%s) successfully updated. Departure: %s, Arrival: %s.",
                    updatedTransport.getTypeTransport(),
                    updatedTransport.getTransportIdentifier(),
                    updatedTransport.getDeparture(),
                    updatedTransport.getArrival());
            
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
        return "redirect:/transports";
    }
    
    @PostMapping("/deleteTransport/{transportId}")
    public String deleteTransport(@PathVariable("transportId") Long transportId) throws IOException {
        transportService.deleteTransportById(transportId);
        return "redirect:/transports";
    }
    
}
