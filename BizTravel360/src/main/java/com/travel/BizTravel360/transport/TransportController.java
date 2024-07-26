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
    public String getAllTransports(Model model) throws IOException {
        List<Transport> transportList = transportService.fetchTransportList();
        log.info("Fetched {} transport", transportList.size());
        model.addAttribute("transports", transportList);
        return "transport/transports";
    }
    
    @GetMapping("/transport")
    public String showSaveTransportForm(Model model) {
        model.addAttribute("transport", new Transport());
        return "transport/createTransportForm";
    }
    
    @PostMapping("/transport")
    public String saveTransport(@Valid @ModelAttribute("transport") Transport transport,
                                BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            return "transport/createTransportForm";
        }
        transportService.saveTransport(transport);
        model.addAttribute("transport", new Transport());  // Reset the form
        
        return "transport/createTransportForm";
    }
    
    
    @GetMapping("/transport/{transportId}")
    public String showUpdateTransportForm(@PathVariable("transportId") Long transportId, Model model) throws IOException {
        Transport transport = transportService.findTransportById(transportId);
        model.addAttribute("transport", transport);
        return "transport/updateTransportForm";
    }
    
    @PostMapping("/update-transport")
    public String updateTransport(@Valid @ModelAttribute("transport") Transport transport,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            return "transport/updateTransportForm";
        }
            transportService.updateTransport(transport, transport.getTransportId());
            redirectAttributes.addFlashAttribute("successMessage");
        return "redirect:/transports";
    }
    
    
    @PostMapping("/delete-transport/{transportId}")
    public String deleteTransport(@PathVariable("transportId") Long transportId,
                                  RedirectAttributes redirectAttributes) throws IOException {
        transportService.deleteTransportById(transportId);
        redirectAttributes.addFlashAttribute("successMessage", "Transport " + transportId + " successfully deleted.");
        return "redirect:/transports";
    }
    
}
