package com.travel.BizTravel360.employee;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;


@Slf4j
@Controller
public class EmployeeController {

    private final EmployeeService employeeService;
    
    public EmployeeController(EmployeeService employeeService) {this.employeeService = employeeService;}
    
    @GetMapping("/employees")
    public String getAllEmployees(Model model) throws IOException {
        List<Employee> employees = employeeService.fetchEmployeeList();
        log.info("Fetched: {} employees", employees.size());
        model.addAttribute("employees", employees);
        return "employee/employees";
    }
    
    @GetMapping("/employee")
    public String showSaveEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/createEmployeeForm";
    }
    
    @PostMapping("/employee")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee,
                               BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            return "employee/createEmployeeForm";
        }
        employeeService.saveEmployee(employee);
        model.addAttribute("employee", new Employee());
        return "employee/createEmployeeForm";
    }
    
    @GetMapping("/employee/{employeeId}")
    public String showUpdateEmployeeForm(@PathVariable("employeeId") Long employeeId, Model model) throws IOException {
        Employee employee = employeeService.findEmployeeById(employeeId);
        model.addAttribute("employee", employee);
        return "employee/updateEmployeeForm";
    }
    
    @PostMapping("update-employee")
    public String updateEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            return "employee/updateEmployeeForm";
        }
        employeeService.updateEmployee(employee, employee.getEmployeeId());
        redirectAttributes.addFlashAttribute("successMessage");
        return "redirect:/employees";
    }
    
    @PostMapping("/delete-employee/{employeeId}")
    public String deleteEmployee(@PathVariable("employeeId") Long employeeId,
                                 RedirectAttributes redirectAttributes) throws IOException {
        employeeService.deleteEmployeeById(employeeId);
        redirectAttributes.addFlashAttribute("successMessage", "Employee has been deleted");
        return "redirect:/employees";
    }
    
}
