package com.spring.data.mongodb.repository;

import java.util.List;

import com.spring.data.mongodb.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false)
public interface RatingRepository extends MongoRepository<Rating, String> {

  Rating findByTconst(@Param("tconst") String tconst);
  List<Rating> findByAverageRating(@Param("averageRating") String averageRating);
  List<Rating> findByNumVotes(@Param("numVotes") String numVotes);
}
