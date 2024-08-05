package com.travel.BizTravel360.accommodation;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
public class AccommodationController {
    
    private final AccommodationService accommodationService;
    
    public AccommodationController(AccommodationService accommodationService) {this.accommodationService = accommodationService;}
    
    @GetMapping("/accommodations")
    public String getAllAccommodations(@RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "10") int size,
                                       Model model) throws IOException {
        Page<Accommodation> accommodations = accommodationService.fetchAccommodationPage(PageRequest.of(page, 10));
        log.info("Fetched accommodationList: {}", accommodations.getTotalElements());
        model.addAttribute("accommodations", accommodations);
        
        int totalPages = accommodations.getTotalPages();
        if (totalPages == 0) {
            List<Integer> pageNumbers = IntStream.range(0, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "accommodation/accommodations";
    }
    
    @GetMapping("/accommodation")
    public String showSaveAccommodationForm(Model model) {
        model.addAttribute("accommodation", new Accommodation());
        return "accommodation/createAccommodationForm";
    }
    
    @PostMapping("/accommodation")
    public String saveAccommodation(@Valid @ModelAttribute("accommodation") Accommodation accommodation,
                                    BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            return "accommodation/createAccommodationForm";
        }
        accommodationService.saveAccommodation(accommodation);
        model.addAttribute("accommodation", new Accommodation());
        return "accommodation/createAccommodationForm";
    }
    
    @GetMapping("/accommodation/{accommodationId}")
    public String showUpdateAccommodationForm(@PathVariable("accommodationId") Long accommodationId, Model model) throws IOException {
        Accommodation accommodation = accommodationService.findAccommodationById(accommodationId);
        model.addAttribute("accommodation", accommodation);
        return "accommodation/updateAccommodationForm";
    }
    
    @PostMapping("/update-accommodation")
    public String updateAccommodation(@Valid @ModelAttribute("accommodation") Accommodation accommodation,
                                      BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            return "accommodation/updateAccommodationForm";
        }
        accommodationService.updateAccommodation(accommodation, accommodation.getAccommodationId());
        redirectAttributes.addFlashAttribute("message", "accommodation updated successfully");
        return "redirect:/accommodations";
    }
    
    @PostMapping("/delete-accommodation/{accommodationId}")
    public String deleteAccommodation(@PathVariable("accommodationId") Long accommodationId,
                                      RedirectAttributes redirectAttributes) throws IOException {
        accommodationService.deleteAccommodationById(accommodationId);
        redirectAttributes.addFlashAttribute("message", "accommodation deleted successfully");
        return "redirect:/accommodations";
    }
}
