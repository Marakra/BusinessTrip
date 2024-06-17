package com.travel.BizTravel360.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/accommodation")
    public String accommodation(Model model) {
        model.addAttribute("fragments", "accommodation");
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("fragments", "dashboard");
        return "index";
    }

}