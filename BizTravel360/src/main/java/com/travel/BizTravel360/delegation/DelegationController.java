package com.travel.BizTravel360.delegation;

import com.travel.BizTravel360.accommodation.domain.AccommodationService;
import com.travel.BizTravel360.employee.EmployeeService;
import com.travel.BizTravel360.transport.TransportService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
public class DelegationController {
    
    private  static final String PAGE_DEFAULT_VALUE = "0";
    private static final String SIZE_DEFAULT_VALUE = "10";
    
    private final DelegationService delegationService;
    private final EmployeeService employeeService;
    private final TransportService transportService;
    private final AccommodationService accommodationService;
    
    public DelegationController(DelegationService delegationService, EmployeeService employeeService,
                                TransportService transportService, AccommodationService accommodationService) {
        this.delegationService = delegationService;
        this.employeeService = employeeService;
        this.transportService = transportService;
        this.accommodationService = accommodationService;
    }
    
    @GetMapping("/delegations/employee")
    public String getAllDelegations(@RequestParam(value = "page", defaultValue = PAGE_DEFAULT_VALUE) int page,
                                    @RequestParam(value = "size", defaultValue = SIZE_DEFAULT_VALUE) int size,
                                    Model model) throws IOException {
        Page<Delegation> delegations = delegationService.fetchDelegationPage(PageRequest.of(page, size));
        log.info("Fetched {} delegation size: ", delegations.getTotalElements());
        model.addAttribute("delegations", delegations);
        
        int totalPages = delegations.getTotalPages();
        if (totalPages > 0){
            List<Integer> pageNumbers = IntStream.range(0, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "delegation/delegationsForEmployee";
    }
    
    @GetMapping("/delegation/employee")
    public String showCreateDelegationForm(Model model) throws IOException {
        model.addAttribute("delegation", new Delegation());
        model.addAttribute("employees", employeeService.loadEmployeeFromFile());
        model.addAttribute("transports", transportService.loadTransportFromFile());
      //  model.addAttribute("accommodations", accommodationService.loadAccommodationFromFile());
        return "delegation/createDelegationForm";
    }
    
    @PostMapping("/delegation")
    public String saveDelegation(@Valid @ModelAttribute("delegation") Delegation delegation,
                                 BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            log.error("Validation errors occurred: {}", bindingResult.getAllErrors());
            model.addAttribute("employees", employeeService.loadEmployeeFromFile());
            model.addAttribute("transports", transportService.loadTransportFromFile());
          //  model.addAttribute("accommodations", accommodationService.loadAccommodationFromFile());
            return "delegation/createDelegationForm";
        }
            delegationService.createDelegation(delegation);
            redirectAttributes.addFlashAttribute("successMessage", renderSuccessMessage(delegation, "created"));
            return "redirect:/delegations/employee";
        }
    
    private String renderSuccessMessage(Delegation delegation, String action) {
        String successMessage = String.format("Successfully %s delegation: %s",
                action,
                delegation.getNameDelegation());
        log.info(successMessage);
        return successMessage;
    }
    
}
