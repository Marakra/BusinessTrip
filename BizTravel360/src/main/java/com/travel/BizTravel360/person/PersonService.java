package com.travel.BizTravel360.person;

import com.fasterxml.jackson.core.type.TypeReference;

import com.travel.BizTravel360.file.FileService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import jakarta.validation.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Transactional
@Service
public class PersonService implements PersonRepository {
    private final FileService fileService;
    private List<Person> people = new ArrayList<>();

    @Value("${people.file.path}")
    private String peopleFilePath;

    public PersonService(FileService fileService, @Value("${people.file.path}") String peopleFilePath) {
        this.fileService = fileService;
        this.peopleFilePath = peopleFilePath;
    }
    
    @PostConstruct
    private void init() {
        try {
            if (Files.exists(Paths.get(peopleFilePath))) {
                this.people = fileService.readFromFile(new TypeReference<List<Person>>() {}, peopleFilePath);

                log.info("Initialized people list with {} people", people.size());
                for (Person person : people) {
                    log.info("Person: {}", person);
                }
            }
        } catch (IOException e) {
            log.error("Failed to read people from JSON file: {}, message: {}", peopleFilePath, e.getMessage());
        }
    }
    
    
    @Override
    public Person savePerson(Person person) throws IOException {
        try {
            trimPerson(person);
            validatePerson(person);
            
            person.setPersonId(UUID.randomUUID());
            people.add(person);
            fileService.writerToFile(people, peopleFilePath);
            log.info("Person save successful");
            return person;
        } catch (IOException e) {
            log.error("Failed to save person: {}", person);
            throw new RuntimeException("Failed to save person: " + person);
        }
    }
    
    @Override
    public List<Person> fetchPeopleList() {
        return new ArrayList<>(people);
    }
    
    @Override
    public Person updatePerson(Person updatedPerson, UUID personUuid) {
        Person personToUpdate = findPersonByUuid(personUuid);
        
        trimPerson(updatedPerson);
        
        if (personToUpdate != null) {
            personToUpdate.setFirstName(updatedPerson.getFirstName());
            personToUpdate.setLastName(updatedPerson.getLastName());
            personToUpdate.setEmail(updatedPerson.getEmail());
            persistPeople();
            return personToUpdate;
        } else {
            return null;
        }
    }
    
    @Override
    public void deletePersonById(UUID personUuid) {
        people.removeIf(p -> Objects.equals(p.getPersonId(), personUuid));
        persistPeople();
    }
    
    @Override
    public Person findPersonByUuid(UUID personUuid) {
        Optional<Person> foundPerson = people.stream()
                .filter(p -> Objects.equals(p.getPersonId(), personUuid))
                .findFirst();
        
        return foundPerson.orElse(null);
    }
    
    private void validatePerson(Person person) {
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator((MessageInterpolator) new ParameterMessageInterpolator())
                .buildValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        
        if (!violations.isEmpty()) {
            violations.forEach(violation -> log.error(violation.getMessage()));
            throw new IllegalArgumentException("Invalid person data");
        }
    }
    
    private void persistPeople() {
        try {
            fileService.writerToFile(people, peopleFilePath);
        } catch (FileSystemException e) {
            log.error("Failed to persist people to JSON file due to file system issue: {}, message: {}", peopleFilePath, e.getMessage());
        } catch (IOException e){
            log.error("Failed to persist people to JSON file: {}, message: {}", peopleFilePath, e.getMessage());
        }
    }
    
    private void trimPerson(Person person){
        person.setFirstName(person.getFirstName().trim());
        person.setLastName(person.getLastName().trim());
        person.setEmail(person.getEmail().trim());
    }
}





