package com.spring.data.mongodb;

import com.spring.data.mongodb.exception.RatingNotFoundException;
import com.spring.data.mongodb.model.Rating;
import com.spring.data.mongodb.repository.RatingRepository;
import com.spring.data.mongodb.services.RatingService;
import org.assertj.core.api.BDDAssertions;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;


@SpringBootTest(webEnvironment = NONE,
        classes = {
        SpringBootTestApplication.class,
        RatingService.class
        })
public class RatingServiceTest {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private RatingService ratingService;

    @DisplayName("Returning saved Rating from service layer")
    @Test
    void getRatingByTconst_forSavedRating_isReturned() {
        //given
        Rating savedRating = ratingRepository.save(Rating.builder()
                .tconst("tt0999901")
                .averageRating("6.5")
                .numVotes(6881)
                .build());

        //when
        Rating retrievedRating = ratingService.findByTconst(savedRating.getTconst());

        //then
        then(retrievedRating.getAverageRating()).isEqualTo("6.5");
        then(retrievedRating.getId()).isNotNull();
    }

    @Test
    void getRatingById_whenMissingRating_notFoundExceptionThrown() {
        //given
        ObjectId id = ObjectId.get();
        //when
        Throwable throwable = catchThrowable(() -> ratingService.getRatingById(id));
        //then
        BDDAssertions.then(throwable).isInstanceOf(RatingNotFoundException.class);
    }

}



