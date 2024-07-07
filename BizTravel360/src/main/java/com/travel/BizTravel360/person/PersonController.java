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
    
    private final PersonRepository personRepository;
    
    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    
    
    @GetMapping("/people")
    public String getAllPeople(Model model) {
        
        model.addAttribute("people", personRepository.fetchPeopleList());
        
        return "person/people";
    }
    
    @PostMapping("/createPerson")
    public String createPerson(
            @ModelAttribute("person") @Valid Person person,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            return "person/createPersonForm";
        }
        Person savedPerson = personRepository.savePerson(person);
        if (savedPerson != null) {
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Person created successfully");
        } else {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Person creation failed");
        }
        
        return "redirect:/people";
    }
    
    @GetMapping("/createPersonForm")
    public String showCreatePersonForm(Model model) {
        
        model.addAttribute("person", new Person());
        
        return "person/createPersonForm";
    }
    
    @GetMapping("/editPersonForm/{personId}")
    public String showEditPersonForm(
            @PathVariable("personId") Long personId,
            Model model) {
        Person person = personRepository.fetchPeopleList()
                .stream()
                .filter(p -> p.getPersonId().equals(personId))
                .findFirst()
                .orElse(null);
        
        if (person == null){
            return "redirect:/people";
        }
        
        model.addAttribute("person", person);
        return "person/updatePersonForm";
    }
    
    @PostMapping("/updatePerson/{personId}")
    public String updatePerson(
            @PathVariable("personId") Long personId,
            @ModelAttribute("person") @Valid Person person,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            return "person/updatePersonForm";
        }
        
        Person updatedPerson = personRepository.updatePerson(person, personId);
        if (updatedPerson != null) {
            redirectAttributes.addFlashAttribute(
                    "successMessage", "Person updated successfully");
        } else {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", "Person update failed");
        }
        
        return "redirect:/people";
    }
    
    @DeleteMapping("/deletePerson/{personId}")
    public String deletePerson(
            @PathVariable("personId") Long personId,
            RedirectAttributes redirectAttributes) {
        
        personRepository.deletePersonById(personId);
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Person deleted successfully");
        
        return "redirect:people";
    }
    
    
}



