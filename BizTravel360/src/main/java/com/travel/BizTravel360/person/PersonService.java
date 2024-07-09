package com.travel.BizTravel360.person;

import com.fasterxml.jackson.core.type.TypeReference;
import com.travel.BizTravel360.file.FileService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.*;

@Slf4j
@Transactional
@Service
public class PersonService implements PersonRepository {
    
    private final FileService fileService;
    private List<Person> people = new ArrayList<>();
    @Value("${people.file.path}")
    private String peopleFilePath;
    
    @Autowired
    public PersonService(FileService fileService, @Value("${people.file.path}") String peopleFilePath) {
        this.fileService = fileService;
        this.peopleFilePath = peopleFilePath;
    }
    
    @PostConstruct
    private void init() {
        try {
            people = fileService.readFromFile(new TypeReference<List<Person>>() {}, peopleFilePath);
        } catch (IOException e) {
            log.error("Failed to read people list from JSON file", peopleFilePath, e);
            people = Collections.emptyList();
        }
        updatePersonIds();

    }
    
    @Override
    public Person savePerson(Person person) {
        validatePerson(person);

        
        person.setPersonId((long) (people.size() + 1));
        people.add(person);
        persistPeople();
        log.info("Person save successful");
        return person;
    }
    
    @Override
    public List<Person> fetchPeopleList() {
        return new ArrayList<>(people);
    }

    
    @Override
    public Person updatePerson(Person updatedPerson, Long personId) {
        Optional<Person> personToUpdate = people.stream()
                .filter(p -> Objects.equals(p.getPersonId(), personId)).findFirst();
        
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
    public void deletePersonById (Long personId){
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
        return people.stream()
                .filter(p -> Objects.equals(p.getPersonId(), personId))
                .findFirst()
                .orElse(null);
    }
    
    void updatePersonIds() {
        for (int i = 0; i < people.size(); i++) {
            people.get(i).setPersonId((long) (i + 1));
        }
    }
    
    private void persistPeople(){
        try {
            fileService.writerToFile(people, peopleFilePath);
        } catch (IOException e) {
            log.error("Failed to persist people to JSON file", e);
        }
    }
}


