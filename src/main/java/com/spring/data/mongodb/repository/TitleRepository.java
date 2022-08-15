package com.spring.data.mongodb.repository;

import com.spring.data.mongodb.model.Title;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(path = "titles")
public interface TitleRepository extends MongoRepository<Title, String> {

    List<Title> findByPrimaryTitle(@Param("primaryTitle") String primaryTitle);
    List<Title> findByTconst(@Param("tconst") String tconst);
}