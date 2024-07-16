package com.travel.BizTravel360.person;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.travel.BizTravel360.file.FileService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Transactional
@Service
public class PersonService implements PersonRepository {
    
    private final FileService fileService;
    private final ObjectMapper objectMapper;
    private List<Person> people = new ArrayList<>();
    
    @Value("${people.file.path}")
    private String peopleFilePath;
    
    public PersonService(FileService fileService, @Value("${people.file.path}") String peopleFilePath, ObjectMapper objectMapper) {
        this.fileService = fileService;
        this.peopleFilePath = peopleFilePath;
        this.objectMapper = objectMapper;
    }
    
    @PostConstruct
    private void init() {
        try {
            if (Files.exists(Paths.get(peopleFilePath))) {
                String json = Files.readString(Paths.get(peopleFilePath));
                if (!json.isBlank()) {
                    if (json.startsWith("[")) {
                        Person[] peopleArray = objectMapper.readValue(json, Person[].class);
                        people.addAll(Arrays.asList(peopleArray));
                    } else {
                        Person person = objectMapper.readValue(json, Person.class);
                        people.add(person);
                    }
                }
            }
        } catch (IOException e) {
            log.error("Failed to read people from JSON file: {}", peopleFilePath, e);
        }
        
    }
    
    @Override
    public Person savePerson(Person newPerson) {
        validatePerson(newPerson);
        
        newPerson.setPersonId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        people.add(newPerson);
        persistPeople();
        log.info("Person save successful");
        return newPerson;
    }
    
    @Override
    public List<Person> fetchPeopleList() {
        return new ArrayList<>(people);
    }
    
    
    @Override
    public Person updatePerson(Person updatedPerson, Long personId) {
        Optional<Person> personToUpdate = people.stream()
                .filter(p -> Objects.equals(p.getPersonId(), personId))
                .findFirst();
        
        if (personToUpdate.isPresent()) {
            Person person = personToUpdate.get();
            person.setFirstName(updatedPerson.getFirstName());
            person.setLastName(updatedPerson.getLastName());
            person.setEmail(updatedPerson.getEmail());
            persistPeople();
            return person;
        } else {
            return null;
        }
    }
    
    @Override
    public void deletePersonById(Long personId) {
        people.removeIf(p -> Objects.equals(p.getPersonId(), personId));
        persistPeople();
    }
    
    private void validatePerson(Person person) {
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        
        if (!violations.isEmpty()) {
            violations.forEach(violation -> log.error(violation.getMessage()));
            throw new IllegalArgumentException("Invalid person data");
        }
    }
    
    public Person findPersonById(Long personId) {
        Optional<Person> foundPerson = people.stream()
                .filter(p -> Objects.equals(p.getPersonId(), personId))
                .findFirst();
        
        return foundPerson.orElse(null);
    }
    
    private void persistPeople() {
        try {
            fileService.writerToFile(people, peopleFilePath);
        } catch (IOException e) {
            log.error("Failed to persist people to JSON file", e);
        }
    }
}


