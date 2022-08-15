package com.spring.data.mongodb.repository;

import com.spring.data.mongodb.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(path="people")
public interface PersonRepository extends MongoRepository<Person, String> {

    List<Person> findByPrimaryName(@Param("primaryName") String primaryName);
}