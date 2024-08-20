package com.travel.BizTravel360.employee;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Slf4j
@Controller
public class EmployeeController {

    private static final String PAGE_DEFAULT_VALUE = "0";
    private static final String SIZE_DEFAULT_VALUE = "10";
    private static final int GENERATE_RANDOM_EMPLOYEE = 15;

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    //URL will have first and last name after login
    @GetMapping("/hello/employee")
    public String hello(Model model) {
        return "employee/viewEmployee";
    }
    @GetMapping("/employees")
    public String getAllEmployees(@RequestParam(value = "page", defaultValue = PAGE_DEFAULT_VALUE) int page,
                                  @RequestParam(value = "size", defaultValue = SIZE_DEFAULT_VALUE) int size,
                                  Model model) throws IOException {
        Page<Employee> employees = employeeService.fetchEmployeePage(PageRequest.of(page, size));
        log.info("Fetched: {} employees", employees.getTotalElements());
        model.addAttribute("employees", employees);

        int totalPages = employees.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.range(0, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "employee/employees";
    }
    
    @GetMapping("/employee")
    public String showSaveEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/createEmployeeForm";
    }
    
    @PostMapping("/employee")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            return "employee/createEmployeeForm";
        }
        employeeService.saveEmployee(employee);
       redirectAttributes.addFlashAttribute("successMessage", renderSuccessMessage(employee, "created"));
        return "redirect:/employees";
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
        redirectAttributes.addFlashAttribute("successMessage", renderSuccessMessage(employee, "updated"));
        return "redirect:/employees";
    }
    
    @PostMapping("/delete-employee/{employeeId}")
    public String deleteEmployee(@PathVariable("employeeId") Long employeeId,
                                 RedirectAttributes redirectAttributes) throws IOException {
        Employee employee = employeeService.findEmployeeById(employeeId);
        employeeService.deleteEmployeeById(employeeId);
        redirectAttributes.addFlashAttribute("successMessage", renderSuccessMessage(employee, "deleted"));
        return "redirect:/employees";
    }
    
    @GetMapping("/search-employee")
    public String searchEmployees(@RequestParam(value = "page", defaultValue = PAGE_DEFAULT_VALUE) int page,
                                  @RequestParam(value = "size", defaultValue = SIZE_DEFAULT_VALUE) int size,
                                  @RequestParam(value = "keyword", defaultValue = "") String keyword,
                                  @RequestParam(value = "position", required = false) PositionEmployee position,
                                  Model model) throws IOException {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employees = employeeService.searchEmployee(keyword, position, pageable);
        
        model.addAttribute("employees", employees);
        model.addAttribute("keyword", keyword);
        
        int totalPages = employees.getTotalPages();
    
        List<Integer> pageNumbers = IntStream.range(0, totalPages).boxed().collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);
        return "employee/employees";
    }
    
    
    @PostMapping("/generate-random-employees")
    public String generateRandomEmployee(RedirectAttributes redirectAttributes) throws IOException {
        employeeService.generateAnsSaveRandomEmployee(GENERATE_RANDOM_EMPLOYEE);
        redirectAttributes.addFlashAttribute("message", "Random employees generated successfully!");
        return "redirect:/employees";
    }

    private String renderSuccessMessage(Employee employee, String action) {
        String successMessage = String.format("Successfully %s employee: %s %s, position: %s",
                action,
                employee.getFirstName(),
                employee.getLastName(),
                employee.getPosition());
        log.info(successMessage);
        return successMessage;
    }

}
