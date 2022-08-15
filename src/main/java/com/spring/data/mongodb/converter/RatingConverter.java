package com.spring.data.mongodb.converter;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.spring.data.mongodb.model.Rating;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class RatingConverter implements Converter<Rating, DBObject> {

    @Override
    public DBObject convert(final Rating rating) {
        final DBObject dbObject = new BasicDBObject();
        dbObject.put("averageRating", rating.getAverageRating());
        dbObject.put("id", rating.getId());
        dbObject.put("tconst", rating.getTconst());
        dbObject.put("numVotes", rating.getNumVotes());
        dbObject.removeField("_class");
        return dbObject;
    }

}
