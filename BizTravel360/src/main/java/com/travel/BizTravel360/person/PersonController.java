package com.travel.BizTravel360.person;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class PersonController {
    
    private final PersonService personService;

   
    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }
    
    @GetMapping("/people")
    public String getAllPeople(Model model) {
        List<Person> personList = personService.fetchPeopleList();
        log.info("Fetched {} people", personList.size());
        Map<UUID, UUID> randomIdsMap = personList.stream()
                .collect(Collectors.toMap(
                        Person::getPersonId,
                        person -> personService.getRandomIdByPersonId(person.getPersonId())
                ));
        
        model.addAttribute("people", personList);
        model.addAttribute("randomIdsMap", randomIdsMap);
        
        return "person/people";
    }
    
    @GetMapping("/create")
    public String showCreatePersonForm(Model model) {
        model.addAttribute("person", new Person());
        return "person/personForm";
    }
    
    @GetMapping("/updatePerson/{randomId}")
    public String updatePersonForm(@PathVariable("randomId") UUID randomId, Model model) {
        
        Person person = personService.findPersonByUuid(randomId);
        if (person != null){
            model.addAttribute("person", person);
            log.info("Fetched {} person", person);
            return "person/personForm";
        }else {
            log.error("Failed to find person with random id {}", randomId);
            return "redirect:/people";
        }
    }
    
    @PostMapping("/handlePerson")
    public String saveOrUpdatePerson(@Valid @ModelAttribute("person") Person person,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            return "person/personForm";
        }
        try {
            if (person.getPersonId() == null) {
                Person savedPerson = personService.savePerson(person);
                String successMessage = "Person " + savedPerson.getFirstName() + " " + savedPerson.getLastName() + " created successfully";
                redirectAttributes.addFlashAttribute("successMessage", successMessage);
            } else {
                UUID randomId = personService.getRandomIdByPersonId(person.getPersonId());
                Person updatedPerson = personService.updatePerson(person, randomId);
                String updateMessage = "Person " + updatedPerson.getFirstName() + " " + updatedPerson.getLastName() + " updated successfully";
                redirectAttributes.addFlashAttribute("successMessage", updateMessage);
            }
        } catch (RuntimeException | IOException e) {
            log.error("Error saving/updating person {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage", "Operation failed");
        }
        return "redirect:/people";
    }
    
    @PostMapping("/deletePerson/{randomId}")
    public String deletePerson(@PathVariable("randomId") UUID randomId, RedirectAttributes redirectAttributes) {
        try {
            personService.deletePersonById(randomId);
            redirectAttributes.addFlashAttribute("successMessage", "Person deleted successfully");
        } catch (RuntimeException e) {
            log.error("Error deleting person with random ID {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage", "Operation failed");
        }
        return "redirect:/people";
    }

}



