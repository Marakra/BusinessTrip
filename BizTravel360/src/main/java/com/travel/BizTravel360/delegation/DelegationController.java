package com.travel.BizTravel360.delegation;

import com.travel.BizTravel360.accommodation.domain.AccommodationService;
import com.travel.BizTravel360.delegation.domain.DelegationService;
import com.travel.BizTravel360.delegation.model.dto.DelegationDTO;
import com.travel.BizTravel360.employee.domain.EmployeeService;
import com.travel.BizTravel360.employee.model.entity.Employee;
import com.travel.BizTravel360.transport.domain.TransportService;
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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
public class DelegationController {

    private static final String PAGE_DEFAULT_VALUE = "0";
    private static final String SIZE_DEFAULT_VALUE = "10";

    private final DelegationService delegationService;
    private final TransportService transportService;
    private final AccommodationService accommodationService;
    private final EmployeeService employeeService;

    public DelegationController(DelegationService delegationService, TransportService transportService, AccommodationService accommodationService, EmployeeService employeeService) {
        this.delegationService = delegationService;
        this.transportService = transportService;
        this.accommodationService = accommodationService;
        this.employeeService = employeeService;
    }

    @GetMapping("/delegations/employee")
    public String getAllDelegations(@RequestParam(value = "page", defaultValue = PAGE_DEFAULT_VALUE) int page,
                                    @RequestParam(value = "size", defaultValue = SIZE_DEFAULT_VALUE) int size,
                                    Model model) {

        Page<DelegationDTO> delegations = delegationService.findAll(PageRequest.of(page, size));
        log.info("Fetched delegationslist: {}", delegations.getTotalElements());
        model.addAttribute("delegations", delegations);

        int totalPages = delegations.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.range(0, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "delegation/delegations";
    }


    @GetMapping("/delegation/employee")
    public String showCreateDelegationForm(@RequestParam(value = "page", defaultValue = PAGE_DEFAULT_VALUE) int page,
                                           @RequestParam(value = "size", defaultValue = SIZE_DEFAULT_VALUE) int size,
                                           Model model) {
        model.addAttribute("delegation", new DelegationDTO());
        model.addAttribute("employees", employeeService.findAll(PageRequest.of(page, size)));
        model.addAttribute("transports", transportService.findAll(PageRequest.of(page, size)));
        model.addAttribute("accommodations", accommodationService.findAll(PageRequest.of(page, size)));
        return "delegation/createDelegationForm";
    }

    @PostMapping("/delegation")
    public String saveDelegation(@Valid @ModelAttribute("delegation") DelegationDTO delegationDTO,
                                 BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "delegation/createDelegationForm";
        }
        delegationService.save(delegationDTO);
        redirectAttributes.addFlashAttribute("successMessage", renderSuccessMessage(delegationDTO, "created"));

        return "redirect:/delegations/employee";
    }

    @PostMapping("/update-delegation/employee")
    public String updateAccommodation(@Valid @ModelAttribute("delegation") DelegationDTO delegationDTO,
                                      BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "delegation/updateDelegationForm";
        }
        delegationService.updateDelegation(delegationDTO);
        redirectAttributes.addFlashAttribute("successMessage", renderSuccessMessage(delegationDTO, "updated"));
        return "redirect:/accommodations/employee";
    }

    @PostMapping("/delete-delegation/{delegationId}")
    public String deleteDelegation(@PathVariable("delegationId") Long delegationId,
                                   RedirectAttributes redirectAttributes) {
        delegationService.deleteById(delegationId);
        redirectAttributes.addFlashAttribute("successMessage", String.format("Successfully deleted accommodation with ID: %s", delegationId));
        return "redirect:/delegations/employee";
    }


    @GetMapping("/search-delegation")
    public String searchDelegation(@RequestParam(value = "page", defaultValue = PAGE_DEFAULT_VALUE) int page,
                                   @RequestParam(value = "size", defaultValue = SIZE_DEFAULT_VALUE) int size,
                                   @RequestParam(value = "keyword", defaultValue = "") String keyword,
                                   Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<DelegationDTO> delegations = delegationService.searchDelegation(keyword, pageable);

        model.addAttribute("delegations", delegations);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalPages", delegations.getTotalPages());
        model.addAttribute("currentPage", page);

        int totalPages = delegations.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.range(0, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "accommodation/accommodations";
    }

    private String renderSuccessMessage(DelegationDTO delegationDTO, String action) {
        String successMessage = String.format("Successfully %s delegation: %s",
                action,
                delegationDTO.getNameDelegation());
        log.info(successMessage);
        return successMessage;
    }

}
