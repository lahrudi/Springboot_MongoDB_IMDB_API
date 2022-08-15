package com.spring.data.mongodb.repository;

import com.spring.data.mongodb.model.AppInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RepositoryRestResource(exported = false)
public interface AppInfoRepository extends MongoRepository<AppInfo, String> {
  Optional<AppInfo> findTopByVersionOrderByStartDateDesc(String name);
}
