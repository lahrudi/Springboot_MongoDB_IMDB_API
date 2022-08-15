package com.spring.data.mongodb.model;

import com.spring.data.mongodb.config.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = Constants.DB_PERSON_COLLECTION)
public class Person {

    @Id
    private ObjectId id;
    public String nconst;
    public String primaryName;
    public Integer birthYear;
    public Integer deathYear;
    public List<String> primaryProfession;
    public List<String> knownForTitles;
}
