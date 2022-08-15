package com.spring.data.mongodb.exception;

import org.bson.types.ObjectId;

import java.text.MessageFormat;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
public class AkasNotFoundException extends RuntimeException
{
    public AkasNotFoundException(ObjectId id) {
        super(MessageFormat.format("Could not find Akas with id: {0}", id));
    }
}
