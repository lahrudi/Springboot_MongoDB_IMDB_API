package com.spring.data.mongodb.exception;

import org.bson.types.ObjectId;

import java.text.MessageFormat;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
public class CrewNotFoundException extends RuntimeException
{
    public CrewNotFoundException(ObjectId id) {
        super(MessageFormat.format("Could not find Crew with id: {0}", id));
    }
}
