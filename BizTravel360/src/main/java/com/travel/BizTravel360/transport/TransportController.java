package com.travel.BizTravel360.transport;

import com.travel.BizTravel360.transport.domain.TransportService;
import com.travel.BizTravel360.transport.model.dto.TransportDTO;
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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
public class TransportController {

    private static final String PAGE_DEFAULT_VALUE = "0";
    private static final String SIZE_DEFAULT_VALUE = "10";

    private final TransportService transportService;

    public TransportController(TransportService transportService) {
        this.transportService = transportService;
    }

    @GetMapping("/transports/employee")
    public String getAllTransports(@RequestParam(value = "page", defaultValue = PAGE_DEFAULT_VALUE) int page,
                                   @RequestParam(value = "size", defaultValue = SIZE_DEFAULT_VALUE) int size,
                                   Model model)  {
        Page<TransportDTO> transports = transportService.findByLoggedInEmployee(PageRequest.of(page, size));
        log.info("Fetched {} transport", transports.getTotalElements());
        model.addAttribute("transports", transports);

        int totalPages = transports.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.range(0, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "transport/transports";
    }

    @GetMapping("/transport/employee")
    public String showSaveTransportForm(Model model) {
        model.addAttribute("transport", new TransportDTO());
        return "transport/createTransportForm";
    }

    @PostMapping("/transport")
    public String saveTransport(@Valid @ModelAttribute("transport") TransportDTO transportDTO,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "transport/createTransportForm";
        }

        transportService.save(transportDTO);//accDTO -> acc map acc save to DB
        redirectAttributes.addFlashAttribute("successMessage", renderSuccessMessage(transportDTO, "created"));
        return "redirect:/transports/employee";
    }


    @GetMapping("/transport/employee/{id}")
    public String showUpdateTransportForm(@PathVariable("id") Long transportId, Model model) {
        TransportDTO transport = transportService.getById(transportId);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("formattedDepartureDateTime", formatter.format(transport.getDepartureDateTime()));
        model.addAttribute("formattedArrivalDateTime", formatter.format(transport.getArrivalDateTime()));
        model.addAttribute("transport", transport);


        return "transport/updateTransportForm";
    }


    @PostMapping("/update-transport")
    public String updateTransport(@Valid @ModelAttribute("transport") TransportDTO transportDTO,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes)  {
        if (bindingResult.hasErrors()) {
            return "transport/updateTransportForm";
        }
        transportService.updateTransport(transportDTO);
        redirectAttributes.addFlashAttribute("successMessage", renderSuccessMessage(transportDTO, "updated"));
        return "redirect:/transports/employee";
    }


    @PostMapping("/delete-transport/{id}")
    public String deleteTransport(@PathVariable("id") Long transportId,
                                  RedirectAttributes redirectAttributes) {
        transportService.deleteById(transportId);
        redirectAttributes.addFlashAttribute("successMessage", String.format("Successfully deleted transport with ID: %s", transportId));
        return "redirect:/transports/employee";
    }


    @GetMapping("/search-transport")
    public String searchAccommodation(@RequestParam(value = "page", defaultValue = PAGE_DEFAULT_VALUE) int page,
                                      @RequestParam(value = "size", defaultValue = SIZE_DEFAULT_VALUE) int size,
                                      @RequestParam(value = "keyword", defaultValue = "") String keyword,
                                      @RequestParam(value = "type", required = false) TypeTransport type,
                                      Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<TransportDTO> transports = transportService.searchTransport(keyword, type, pageable);

        model.addAttribute("transports", transports);
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalPages", transports.getTotalPages());
        model.addAttribute("currentPage", page);
        int totalPages = transports.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.range(0, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "transport/transports";
    }

    private String renderSuccessMessage(TransportDTO transportDTO, String action) {
        String successMessage = String.format("Successfully %s transport. Type: %s, Departure: %s Arrival: %s.",
                action,
                transportDTO.getType(),
                transportDTO.getDeparture(),
                transportDTO.getArrival());
        log.info(successMessage);
        return successMessage;
    }
}