package com.travel.BizTravel360.delegation;

import com.travel.BizTravel360.accommodation.domain.AccommodationService;
import com.travel.BizTravel360.accommodation.model.dto.AccommodationDTO;
import com.travel.BizTravel360.accommodation.model.entity.Accommodation;
import com.travel.BizTravel360.delegation.domain.DelegationService;
import com.travel.BizTravel360.delegation.model.dto.DelegationDTO;
import com.travel.BizTravel360.delegation.model.entity.Delegation;
import com.travel.BizTravel360.employee.domain.EmployeeService;
import com.travel.BizTravel360.transport.domain.TransportService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public DelegationController(DelegationService delegationService) {this.delegationService = delegationService;}

    @GetMapping("/delegations/employee")
    public String getAllDelegations(@RequestParam(value = "page", defaultValue = PAGE_DEFAULT_VALUE) int page,
                                    @RequestParam(value = "size", defaultValue = SIZE_DEFAULT_VALUE) int size,
                                    Model model)  {

        Page<DelegationDTO> delegations = delegationService.findAll(PageRequest.of(page, size));
        log.info("Fetched accommodationList: {}", delegations.getTotalElements());
        model.addAttribute("accommodations", delegations);

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
    public String showCreateDelegationForm(Model model) throws IOException {
        model.addAttribute("delegation", new Delegation());
     //   model.addAttribute("employees", employeeService.loadEmployeeFromFile());
     //   model.addAttribute("transports", transportService.loadTransportFromFile());
      //  model.addAttribute("accommodations", accommodationService.loadAccommodationFromFile());
        return "delegation/createDelegationForm";
    }

    @PostMapping("/delegation")
    public String saveDelegation(@Valid @ModelAttribute("delegation") DelegationDTO delegationDTO,
                                 BindingResult bindingResult, RedirectAttributes redirectAttributes)  {

        if (bindingResult.hasErrors()) {
            return "delegation/createDelegationForm";
        }
            delegationService.save(delegationDTO);
            redirectAttributes.addFlashAttribute("successMessage", renderSuccessMessage(delegationDTO, "created"));
            return "redirect:/delegations/employee";
        }

    private String renderSuccessMessage(DelegationDTO delegationDTO, String action) {
        String successMessage = String.format("Successfully %s delegation: %s",
                action,
                delegationDTO.getNameDelegation());
        log.info(successMessage);
        return successMessage;
    }

}
