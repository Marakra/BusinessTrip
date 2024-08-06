package com.travel.BizTravel360.search;

import com.travel.BizTravel360.employee.Employee;
import com.travel.BizTravel360.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final EmployeeService employeeService;

    @Autowired
    public SearchController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee")
    public String fullTextSearch(@RequestParam("query") String query, Model model) {
        List<Employee> searchResults = null;
        searchResults = employeeService.fullTextSearch(query);

        model.addAttribute("employees", searchResults);
        return "employees"; // nazwa strony z listą pracowników
    }
}