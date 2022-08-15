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
@Document(collection = Constants.DB_TITLE_COLLECTION)
public class Title {
    @Id
    private ObjectId id;

    public String tconst;
    public String titleType;
    public String primaryTitle;
    public String originalTitle;
    public Boolean isAdult;
    public Integer startYear;
    public Integer endYear;
    public Integer runtimeMinutes;
    public List<String> genres;
}
