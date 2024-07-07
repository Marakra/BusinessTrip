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
    }
    
    @Override
    public Person savePerson(Person person) {
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Person> violation : violations) {
                log.error(violation.getMessage());
            }
            throw new IllegalArgumentException("Invalid person data");
        }
        
        person.setPersonId((long) (people.size() + 1));
        people.add(person);
        persistPeople();
        log.info("Person save successful");
        return person;
    }
    
    @Override
    public List<Person> fetchPeopleList() {
        try{
            List<Person> reversedList = new ArrayList<>(people);
            Collections.reverse(reversedList);
            
            for (int i = 0; reversedList.size() > i; i++) {
                reversedList.get(i).setPersonId((long) (i +1));
            }
            return reversedList;
        } catch (Exception e){
            log.error("Failed to read people list from JSON file", peopleFilePath, e);
            throw new RuntimeException("Failed to load people data", e);
        }
    }

    
    @Override
    public Person updatePerson(Person person, Long personId) {
        Optional<Person> personDBOpt = people.stream()
                .filter(p -> Objects.equals(p.getPersonId(), personId)).findFirst();
        
        if (personDBOpt.isPresent()) {
            Person personDB = personDBOpt.get();
            
            if (Objects.nonNull(person.getFirstName())
                    && !"".equalsIgnoreCase(person.getFirstName())) {
                personDB.setFirstName(person.getFirstName());
            }
            if (Objects.nonNull(person.getLastName())
                    && !"".equalsIgnoreCase(person.getLastName())) {
                personDB.setLastName(person.getLastName());
            }
            if (Objects.nonNull(person.getEmail())
                    && !"".equalsIgnoreCase(person.getEmail())) {
                personDB.setEmail(person.getEmail());
            }
            persistPeople();
            return personDB;
        } else {
            return null;
        }
    }
        
        @Override
        public void deletePersonById (Long personId){
            people.removeIf(p -> Objects.equals(p.getPersonId(), personId));
            persistPeople();
        }
        
    private void persistPeople(){
        try {
            fileService.writerToFile(people, peopleFilePath);
        } catch (IOException e) {
            log.error("Failed to persist people to JSON file", e);
        }
    }
}


