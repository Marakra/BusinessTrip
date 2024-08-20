package com.travel.BizTravel360.transport;

import com.travel.BizTravel360.accommodation.Accommodation;
import com.travel.BizTravel360.accommodation.TypeAccommodation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
public class TransportController {

    private static final String PAGE_DEFAULT_VALUE = "0";
    private static final String SIZE_DEFAULT_VALUE = "10";
    private static final int GENERATE_RANDOM_TRANSPORT = 15;

    private final TransportService transportService;

    public TransportController(TransportService transportService) {this.transportService = transportService;}

    @GetMapping("/transports/employee")
    public String getAllTransports(@RequestParam(value = "page", defaultValue = PAGE_DEFAULT_VALUE) int page,
                                   @RequestParam(value = "size", defaultValue = SIZE_DEFAULT_VALUE) int size,
                                   Model model) throws IOException {
        Page<Transport> transports = transportService.fetchTransportPage(PageRequest.of(page, size));
        log.info("Fetched {} transport", transports.getTotalElements());
        model.addAttribute("transports", transports);

        int totalPages = transports.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.range(0, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "transport/transportsForEmployee";
    }

    @GetMapping("/transport")
    public String showSaveTransportForm(Model model) {
        model.addAttribute("transport", new Transport());
        return "transport/createTransportForm";
    }

    @PostMapping("/transport")
    public String saveTransport(@Valid @ModelAttribute("transport") Transport transport,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            return "transport/createTransportForm";
        }
        transportService.saveTransport(transport);
        redirectAttributes.addFlashAttribute("successMessage", renderSuccessMessage(transport, "created"));
        return "redirect:/transports";
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
        redirectAttributes.addFlashAttribute("successMessage", renderSuccessMessage(transport, "updated"));
        return "redirect:/transports";
    }


    @PostMapping("/delete-transport/{transportId}")
    public String deleteTransport(@PathVariable("transportId") Long transportId,
                                  RedirectAttributes redirectAttributes) throws IOException {
        Transport transport = transportService.findTransportById(transportId);
        transportService.deleteTransportById(transportId);
        redirectAttributes.addFlashAttribute("successMessage", renderSuccessMessage(transport, "deleted"));
        return "redirect:/transports";
    }

    @PostMapping("/generate-random-transport")
    public String generateRandomTransport(RedirectAttributes redirectAttributes) throws IOException {
        transportService.generateAndSaveRandomTransport(GENERATE_RANDOM_TRANSPORT);
        redirectAttributes.addFlashAttribute("message", "Random transports generated successfully!");
        return "redirect:/transports";
    }
    
    @GetMapping("/search-transport")
    public String searchAccommodation(@RequestParam(value = "page", defaultValue = PAGE_DEFAULT_VALUE) int page,
                                      @RequestParam(value = "size", defaultValue = SIZE_DEFAULT_VALUE) int size,
                                      @RequestParam(value = "keyword", defaultValue = "") String keyword,
                                      @RequestParam(value = "type", required = false) TypeTransport type,
                                      Model model) throws IOException {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Transport> transports = transportService.searchTransport(keyword, type, pageable);
        
        model.addAttribute("transports", transports);
        model.addAttribute("type", type);
        
        int totalPages = transports.getTotalPages();
        List<Integer> pageNumbers = IntStream.range(0, totalPages).boxed().collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);
        
        return "transport/transports";
    }

    private String renderSuccessMessage(Transport transport, String action){
        String successMessage = String.format("Successfully %s transport. Type: %s, Departure: %s Arrival: %s.",
                action,
                transport.getTypeTransport(),
                transport.getDeparture(),
                transport.getArrival());
        log.info(successMessage);
        return successMessage;
    }
}