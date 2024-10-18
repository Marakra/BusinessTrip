package com.travel.BizTravel360.menu;


import com.travel.BizTravel360.employee.domain.EmployeeService;
import com.travel.BizTravel360.employee.model.dto.EmployeeDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {
    
    public final EmployeeService employeeService;
    
    public MenuController(EmployeeService employeeService) {this.employeeService = employeeService;}
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/login")
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            String currentEmployeeEmail = authentication.getName();
            EmployeeDTO employeeDTO = employeeService.getEmployeeByEmail(currentEmployeeEmail);
            
            return "redirect:/hello/" + employeeDTO.getFirstName() + "-" + employeeDTO.getLastName();
        }
        
        return "authorization/login";
    }
    
}