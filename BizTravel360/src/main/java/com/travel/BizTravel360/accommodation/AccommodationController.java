package com.travel.BizTravel360.accommodation;

import com.travel.BizTravel360.accommodation.domain.AccommodationService;
import com.travel.BizTravel360.accommodation.model.dto.AccommodationDTO;

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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
public class AccommodationController {

    private  static final String PAGE_DEFAULT_VALUE = "0";
    private static final String SIZE_DEFAULT_VALUE = "10";

    private final AccommodationService accommodationService;
    
    public AccommodationController(AccommodationService accommodationService) {this.accommodationService = accommodationService;}
    
    @GetMapping("accommodations/employee")
    public String getAllAccommodations(@RequestParam(value = "page", defaultValue = PAGE_DEFAULT_VALUE) int page,
                                       @RequestParam(value = "size", defaultValue = SIZE_DEFAULT_VALUE) int size,
                                       Model model) {
        Page<AccommodationDTO> accommodations = accommodationService.findByLoggedInEmployee(PageRequest.of(page, size));
        log.info("Fetched accommodationList: {}", accommodations.getTotalElements());
        model.addAttribute("accommodations", accommodations);

        int totalPages = accommodations.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.range(0, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "accommodation/accommodations";
    }

    @GetMapping("/accommodation/employee")
    public String showSaveAccommodationForm(Model model) {
        model.addAttribute("accommodation", new AccommodationDTO());
        return "accommodation/createAccommodationForm";
    }

    @PostMapping("/accommodation")
    public String saveAccommodation(@Valid @ModelAttribute("accommodation") AccommodationDTO accommodationDTO,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            return "accommodation/createAccommodationForm";
        }
        accommodationService.save(accommodationDTO);//accDTO -> acc map acc save to DB
        redirectAttributes.addFlashAttribute("successMessage", renderSuccessMessage(accommodationDTO, "created"));
        
        return "redirect:/accommodations/employee";
    }

    @GetMapping("/accommodation/employee/{id}")
    public String showUpdateAccommodationForm(@PathVariable("id") Long accommodationId, Model model) {
        AccommodationDTO accommodation = accommodationService.getById(accommodationId);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        model.addAttribute("formattedCheckIn", formatter.format(accommodation.getCheckIn()));
        model.addAttribute("formattedCheckOut", formatter.format(accommodation.getCheckOut()));
        model.addAttribute("accommodation", accommodation);
        
        return "accommodation/updateAccommodationForm";
    }

    @PostMapping("/update-accommodation/employee")
    public String updateAccommodation(@Valid @ModelAttribute("accommodation") AccommodationDTO accommodationDTO,
                                      BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "accommodation/updateAccommodationForm";
        }
        accommodationService.updateAccommodation(accommodationDTO);
        redirectAttributes.addFlashAttribute("successMessage", renderSuccessMessage(accommodationDTO, "updated"));
        return "redirect:/accommodations/employee";
    }

    @PostMapping("/delete-accommodation/{accommodationId}")
    public String deleteAccommodation(@PathVariable("accommodationId") Long accommodationId,
                                      RedirectAttributes redirectAttributes) {
        accommodationService.deleteById(accommodationId);
        redirectAttributes.addFlashAttribute("successMessage", String.format("Successfully deleted accommodation with ID: %s", accommodationId));
        return "redirect:/accommodations/employee";
    }
    
    @GetMapping("/search-accommodation")
    public String searchAccommodation(@RequestParam(value = "page", defaultValue = PAGE_DEFAULT_VALUE) int page,
                                      @RequestParam(value = "size", defaultValue = SIZE_DEFAULT_VALUE) int size,
                                      @RequestParam(value = "keyword", defaultValue = "") String keyword,
                                      @RequestParam(value = "type", required = false) TypeAccommodation type,
                                      Model model) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<AccommodationDTO> accommodations = accommodationService.searchAccommodation(keyword, type, pageable);
        
        model.addAttribute("accommodations", accommodations);
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalPages", accommodations.getTotalPages());
        model.addAttribute("currentPage", page);
        
        int totalPages = accommodations.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.range(0, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "accommodation/accommodations";
    }
    
    private String renderSuccessMessage(AccommodationDTO accommodationDTO, String action) {
        String successMessage = String.format("Successfully %s accommodation: %s",
                action,
                accommodationDTO.getName());
        log.info(successMessage);
        return successMessage;
    }
}
