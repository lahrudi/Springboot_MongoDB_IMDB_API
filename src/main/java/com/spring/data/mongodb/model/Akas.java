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
@Document(collection = Constants.DB_AKAS_COLLECTION)
public class Akas {
    @Id
    private ObjectId id;
    private String titleId;
    private Integer ordering;
    private String title;
    private String region;
    private String language;
    private List<String> types;
    private List<String> attributes;
    private Boolean isOriginalTitle;
}
