package com.travel.BizTravel360.controllers;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/accommodations")
    public String accommodation(Model model) {
        model.addAttribute("fragments", "accommodation");
        return "menu_categories/accommodations";
    }
    
    @GetMapping("/documents")
    public String documents(Model model) {
        model.addAttribute("fragments", "documents");
        return "menu_categories/documents";
    }
    
    @GetMapping("/expenses")
    public String expenses(Model model) {
        model.addAttribute("fragments", "expenses");
        return "menu_categories/expenses";
    }
    
    @GetMapping("/reports")
    public String reports(Model model) {
        model.addAttribute("fragments", "reports");
        return "menu_categories/reports";
    }
    
    @GetMapping("/transports")
    public String transports(Model model) {
        model.addAttribute("fragments", "transports");
        return "menu_categories/transports";
    }

    @GetMapping("/people")
    public String people(Model model) {
        model.addAttribute("fragments", "people");
        return "menu_categories/people";
    }
    

}