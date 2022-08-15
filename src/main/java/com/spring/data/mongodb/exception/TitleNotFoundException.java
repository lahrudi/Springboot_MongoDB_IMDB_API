package com.spring.data.mongodb.exception;

import org.bson.types.ObjectId;

import java.text.MessageFormat;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
public class TitleNotFoundException extends RuntimeException
{
    public TitleNotFoundException(ObjectId id) {
        super(MessageFormat.format("Could not find Title with id: {0}", id));
    }
}
