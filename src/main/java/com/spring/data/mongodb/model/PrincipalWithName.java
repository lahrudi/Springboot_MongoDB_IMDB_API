package com.spring.data.mongodb.model;

import com.spring.data.mongodb.config.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = Constants.DB_PRINCIPAL_COLLECTION)
public class PrincipalWithName {
    @Id
    private ObjectId id;
    public String tconst;
    public Integer ordering;
    public LinkedHashMap<?,?> person;
    public String category;
    public String job;
    public List<String> characters;
}
