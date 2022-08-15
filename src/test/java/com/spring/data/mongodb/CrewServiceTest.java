package com.spring.data.mongodb;

import com.spring.data.mongodb.exception.CrewNotFoundException;
import com.spring.data.mongodb.exception.RatingNotFoundException;
import com.spring.data.mongodb.model.Crew;
import com.spring.data.mongodb.model.Rating;
import com.spring.data.mongodb.repository.CrewRepository;
import com.spring.data.mongodb.services.CrewService;
import com.spring.data.mongodb.services.RatingService;
import org.assertj.core.api.BDDAssertions;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;


@SpringBootTest(webEnvironment = NONE,
        classes = {
        SpringBootTestApplication.class,
        CrewService.class
        })
public class CrewServiceTest {
    @Autowired
    private CrewRepository crewRepository;

    @Autowired
    private CrewService crewService;

    @DisplayName("Returning saved Crew from service layer")
    @Test
    void getCrewByTconst_forSavedCrew_isReturned() {
        //given
        Crew savedCrew = crewRepository.save(Crew.builder()
                .tconst("tt0999901")
                .directors(Arrays.asList(new String[]{"director 1","director 2"}))
                .writers(Arrays.asList(new String[]{"writer 1","writer 2"}))
                .build());
        //when
        Crew retrievedCrew = crewService.findByTconst(savedCrew.getTconst());

        //then
        then(retrievedCrew.getTconst()).isEqualTo("tt0999901");
        then(retrievedCrew.getId()).isNotNull();
    }

    @Test
    void getCrewById_whenMissingCrew_notFoundExceptionThrown() {
        //given
        ObjectId id = ObjectId.get();
        //when
        Throwable throwable = catchThrowable(() -> crewService.getCrewById(id));
        //then
        BDDAssertions.then(throwable).isInstanceOf(CrewNotFoundException.class);
    }

}



