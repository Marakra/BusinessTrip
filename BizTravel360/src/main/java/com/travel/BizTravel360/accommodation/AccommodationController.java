package com.travel.BizTravel360.accommodation;

import ch.qos.logback.core.joran.conditional.IfAction;
import com.travel.BizTravel360.accommodation.domain.AccommodationService;
import com.travel.BizTravel360.accommodation.model.entity.Accommodation;
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

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
public class AccommodationController {

    private  static final String PAGE_DEFAULT_VALUE = "0";
    private static final String SIZE_DEFAULT_VALUE = "1";
    private static final int GENERATE_RANDOM_ACCOMMODATIONS = 15;

    private final AccommodationService accommodationService;

    public AccommodationController(AccommodationService accommodationService) {this.accommodationService = accommodationService;}

    @GetMapping("accommodations/employee")
    public String getAllAccommodations(@RequestParam(value = "page", defaultValue = PAGE_DEFAULT_VALUE) int page,
                                       @RequestParam(value = "size", defaultValue = SIZE_DEFAULT_VALUE) int size,
                                       Model model) {
        Page<Accommodation> accommodations = accommodationService.findAll(PageRequest.of(page, size));
        log.info("Fetched accommodationList: {}", accommodations.getTotalElements());
        model.addAttribute("accommodations", accommodations);

        int totalPages = accommodations.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.range(0, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "accommodation/accommodationsForEmployee";
    }

    @GetMapping("/accommodation/employee")
    public String showSaveAccommodationForm(Model model) {
        model.addAttribute("accommodation", new Accommodation());
        return "accommodation/createAccommodationForm";
    }

    @PostMapping("/accommodation")
    public String saveAccommodation(@Valid @ModelAttribute("accommodation") Accommodation accommodation,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            return "accommodation/createAccommodationForm";
        }
        accommodationService.save(accommodation);
        redirectAttributes.addFlashAttribute("successMessage", renderSuccessMessage(accommodation, "created"));
        
        return "redirect:/accommodations/employee";
    }

    @GetMapping("/accommodation/employee/{id}")
    public String showUpdateAccommodationForm(@PathVariable("id") Long accommodationId, Model model) {
        Optional<Accommodation> accommodations = accommodationService.getById(accommodationId);
        Accommodation accommodation = accommodations.get();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("formattedCheckIn", formatter.format(accommodation.getCheckIn()));
        model.addAttribute("formattedCheckOut", formatter.format(accommodation.getCheckOut()));
        model.addAttribute("accommodation", accommodation);
        
        return "accommodation/updateAccommodationForm";
    }

    @PostMapping("/update-accommodation/employee")
    public String updateAccommodation(@Valid @ModelAttribute("accommodation") Accommodation accommodation,
                                      BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "accommodation/updateAccommodationForm";
        }
        accommodationService.updateAccommodation(accommodation);
        redirectAttributes.addFlashAttribute("successMessage", renderSuccessMessage(accommodation, "updated"));
        return "redirect:/accommodations/employee";
    }

    @PostMapping("/delete-accommodation/{accommodationId}")
    public String deleteAccommodation(@PathVariable("accommodationId") Accommodation accommodationId,
                                      RedirectAttributes redirectAttributes) {
        accommodationService.deleteById(accommodationId.getId());
        redirectAttributes.addFlashAttribute("successMessage", renderSuccessMessage(accommodationId, "deleted"));
        return "redirect:/accommodations/employee";
    }

    @PostMapping("/generate-random-accommodation")
    public String generationRandomAccommodation(RedirectAttributes redirectAttributes) {
      //  accommodationService.generateAndSaveRandomAccommodation(GENERATE_RANDOM_ACCOMMODATIONS);
        redirectAttributes.addFlashAttribute("message", "Random accommodations generated successfully!");
        return "redirect:/accommodations/employee";
    }
    
    @GetMapping("/search-accommodation")
    public String searchAccommodation(@RequestParam(value = "page", defaultValue = PAGE_DEFAULT_VALUE) int page,
                                      @RequestParam(value = "size", defaultValue = SIZE_DEFAULT_VALUE) int size,
                                      @RequestParam(value = "keyword", defaultValue = "") String keyword,
                                      @RequestParam(value = "type", required = false) TypeAccommodation type,
                                      Model model) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Accommodation> accommodations = accommodationService.searchAccommodation(keyword, pageable);
        
        model.addAttribute("accommodations", accommodations);
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalPages", accommodations.getTotalPages());
        
        
        return "accommodation/accommodationsForEmployee";
    }
    
    private String renderSuccessMessage(Accommodation accommodation, String action) {
        String successMessage = String.format("Successfully %s accommodation: %s",
                action,
                accommodation.getNameAccommodation());
        log.info(successMessage);
        return successMessage;
    }
}
