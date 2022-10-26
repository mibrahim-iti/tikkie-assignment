package com.tikkie.service;

import com.tikkie.dao.PersonDynamoDbDao;
import com.tikkie.mapper.PersonMapper;
import com.tikkie.model.Person;
import com.tikkie.request.CreatePersonRequest;

import java.util.List;
import java.util.Optional;

/**
 * @author Mohamed Ibrahim
 * @created Tuesday 25 Oct 2022
 */
public class PersonService {

    private final PersonMapper personMapper;

    private final PersonDynamoDbDao personDynamoDbDao;


    public PersonService(final PersonMapper personMapper, final PersonDynamoDbDao personDynamoDbDao) {

        this.personMapper = personMapper;
        this.personDynamoDbDao = personDynamoDbDao;
    }

    public Optional<Person> find(final String personId) {

        return personDynamoDbDao.find(personId);
    }

    public Optional<Person> find(final String firstname, final String lastname) {

        return personDynamoDbDao.find(firstname, lastname);
    }

    public List<Person> findAll() {

        return personDynamoDbDao.findAll();
    }

    public String save(final CreatePersonRequest createPersonRequest) {

        final Person person = personMapper.toPerson(createPersonRequest);

        return personDynamoDbDao.save(person);
    }

    public void delete(final String personId) {

        personDynamoDbDao.delete(personId);
    }

}
