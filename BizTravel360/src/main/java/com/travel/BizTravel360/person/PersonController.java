package com.travel.BizTravel360.person;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
public class PersonController {
    
    private final PersonService personService;

   
    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }
    
    @GetMapping("/people")
    public String getAllPeople(Model model) {
        model.addAttribute("people", personService.fetchPeopleList());
        
        return "person/people";
    }
    
    @PostMapping("/createPerson")
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            return "person/createPerson";
        }
        Person savedPerson = personService.savePerson(person);
        if (savedPerson != null) {
            String successMessage = "Person " + savedPerson.getFirstName() + " "
                                    + savedPerson.getLastName() + " created successfully";
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Person creation failed");

        }
        
        return "redirect:/people";
    }
    
    @GetMapping("/createPerson")
    public String showCreatePersonForm(Model model) {
        model.addAttribute("person", new Person());
        return "/person/createPerson";
    }
    
    @GetMapping("/editPerson/{personId}")
    public String showEditPersonForm(@PathVariable("personId") Long personId, Model model) {
        
        Person person = personService.findPersonById(personId);
        
        if (person != null){
            model.addAttribute("person", person);
            return "person/updatePerson";
        }else {
            return "redirect:/people";
        }
    }
    
    @PostMapping("/updatePerson/{personId}")
    public String updatePerson(@PathVariable("personId") Long personId, @ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            return "person/updatePerson";
        }
        
        Person updatedPerson = personService.updatePerson(person, personId);
        if (updatedPerson != null) {
            String updateMessage = "Person " + updatedPerson.getFirstName() + " "
                    + updatedPerson.getLastName() + " created successfully";
            redirectAttributes.addFlashAttribute("successMessage", updateMessage);

        } else {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", "Person update failed");
        }
        
        return "redirect:/people";
    }
    

    @PostMapping("/deletePerson/{personId}")
    public String deletePerson(
            @PathVariable("personId") Long personId,
            RedirectAttributes redirectAttributes) {
        

        personService.deletePersonById(personId);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Person deleted successfully");
        

        return "redirect:/people";
    }

}



