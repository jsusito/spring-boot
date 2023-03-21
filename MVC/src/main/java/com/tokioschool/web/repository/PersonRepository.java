package com.tokioschool.web.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tokioschool.web.domain.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
	
	Set<Person> findByTypePersonOrderByName(Person.TypePerson person);

}
