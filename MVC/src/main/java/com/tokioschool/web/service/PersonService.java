package com.tokioschool.web.service;

import java.util.Set;

import com.tokioschool.web.domain.Person;

public interface PersonService {
	public void save(Person person);
	Set<Person> findByTypePerson(Person.TypePerson person);
}
