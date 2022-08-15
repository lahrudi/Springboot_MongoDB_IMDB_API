package com.spring.data.mongodb.repository;

import com.spring.data.mongodb.model.Principal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(exported = false)
public interface PrincipalRepository extends MongoRepository<Principal, String> {

    List<Principal> findByTconst(@Param("tconst") String tconst);
    List<Principal> findByNconst(@Param("nconst") String nconst);
}