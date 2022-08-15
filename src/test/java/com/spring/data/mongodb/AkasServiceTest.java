package com.spring.data.mongodb;

import com.spring.data.mongodb.exception.AkasNotFoundException;
import com.spring.data.mongodb.exception.RatingNotFoundException;
import com.spring.data.mongodb.model.Akas;
import com.spring.data.mongodb.model.Rating;
import com.spring.data.mongodb.repository.AkasRepository;
import com.spring.data.mongodb.services.AkasService;
import com.spring.data.mongodb.services.RatingService;
import org.apache.commons.lang3.math.NumberUtils;
import org.assertj.core.api.BDDAssertions;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;


@SpringBootTest(webEnvironment = NONE,
        classes = {
                SpringBootTestApplication.class,
                AkasService.class
        })
public class AkasServiceTest {
    @Autowired
    private AkasRepository akasRepository;

    @Autowired
    private AkasService akasService;

    @DisplayName("Returning saved Akas from service layer")
    @Test
    void getAkasByOrdering_forSavedAkas_isReturned() {
        //given
        Akas savedAkas = akasRepository.save(
                Akas.builder()
                        .titleId("titleId")
                        .ordering(10)
                        .title("title")
                        .region("region")
                        .language("EN")
                        .types(Arrays.asList(new String[]{"type 1", "type 2"}))
                        .attributes(Arrays.asList(new String[]{"attribute 1", "attribute 2"}))
                        .isOriginalTitle(true)
                        .build()
        );

        //when
        List<Akas> retrievedAkasList = akasService.findByOrdering(savedAkas.getOrdering());

        //then
        then(retrievedAkasList.get(0).getOrdering()).isEqualTo(10);
        then(retrievedAkasList.get(0).getId()).isNotNull();
    }

    @Test
    void getAkasById_whenMissingAkas_notFoundExceptionThrown() {
        //given
        ObjectId id = ObjectId.get();
        //when
        Throwable throwable = catchThrowable(() -> akasService.getAkasById(id));
        //then
        BDDAssertions.then(throwable).isInstanceOf(AkasNotFoundException.class);
    }

}



