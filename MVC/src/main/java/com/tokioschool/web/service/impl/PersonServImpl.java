package com.tokioschool.web.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokioschool.web.domain.Person;
import com.tokioschool.web.domain.Person.TypePerson;
import com.tokioschool.web.repository.PersonRepository;
import com.tokioschool.web.service.PersonService;

@Service
public class PersonServImpl implements PersonService {

	@Autowired
	PersonRepository personRepository;
	@Override
	public void save(Person person) {
		personRepository.save(person);
	}
	@Override
	public Set<Person> findByTypePerson(TypePerson person) {
		
		return personRepository.findByTypePersonOrderByName(person);
	}

}
