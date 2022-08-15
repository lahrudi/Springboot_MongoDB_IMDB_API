package com.spring.data.mongodb.model;

import com.spring.data.mongodb.config.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = Constants.DB_EPISODE_COLLECTION)
public class Episode {
    @Id
    private ObjectId id;
    private String tconst;
    private String parentTconst;
    private String seasonNumber;
    private String episodeNumber;
}
