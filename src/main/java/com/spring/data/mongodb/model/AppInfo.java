package com.spring.data.mongodb.model;

import com.spring.data.mongodb.config.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = Constants.DB_APP_INFO_COLLECTION)
public class AppInfo {
    @Id
    private ObjectId id;
    @Field("startDate")
    private Long startDate;
    private String version;
    private Integer viewersCount;
}
