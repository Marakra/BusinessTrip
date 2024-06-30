package com.travel.BizTravel360.person;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
public class PersonController {
    
    private final PersonService personService;
    
   @Autowired
   public PersonController(PersonService personService) {
       this.personService = personService;
   }
   
    @GetMapping("/people")
    public String getAllPeople(Model model) {
        try {
            List<Person> people = personService.readAll();
            
            Collections.reverse(people);
            
            for (int i = 0; i < people.size(); i++) {
                people.get(i).setPersonId(i+1);
            }
            model.addAttribute("people", people);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load people data", e);
        }
        return "pages/people";
    }
    
    @GetMapping("createPersonForm")
    public String createPersonForm(Model model) {
       model.addAttribute("person", new Person());
       return "createObjects/createPersonForm";
    }
    
    @PostMapping("createPerson")
    public String createPerson(@ModelAttribute Person person) {
       try {
           personService.createPerson(person);
           return "redirect:people";
       } catch (IOException e) {
           throw new RuntimeException("Failed to create new person", e);
       }
    }
}
