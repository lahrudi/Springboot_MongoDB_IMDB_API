package com.spring.data.mongodb.exception;

import org.bson.types.ObjectId;

import java.text.MessageFormat;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
public class PersonNotFoundException extends RuntimeException
{
    public PersonNotFoundException(ObjectId id) {
        super(MessageFormat.format("Could not find Person with id: {0}", id));
    }
}
