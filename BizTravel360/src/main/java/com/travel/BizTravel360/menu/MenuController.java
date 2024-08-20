package com.travel.BizTravel360.menu;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/login")
    public String login() {
        return "authorization/login";
    }
    
    @GetMapping("sing-up")
    public String singUp() {
        return "authorization/signUp";
    }
    
    @GetMapping("/profile/employee")
    public String profile() {
        return "authorization/profileEmployee";
    }
}