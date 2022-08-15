package com.spring.data.mongodb.exception;

import org.bson.types.ObjectId;

import java.text.MessageFormat;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
public class RatingNotFoundException extends RuntimeException
{
    public RatingNotFoundException(ObjectId id) {
        super(MessageFormat.format("Could not find Rating with id: {0}", id));
    }
}
