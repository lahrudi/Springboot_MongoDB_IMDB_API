package com.spring.data.mongodb.exception;

import org.bson.types.ObjectId;

import java.text.MessageFormat;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
public class PrincipalNotFoundException extends RuntimeException
{
    public PrincipalNotFoundException(ObjectId id) {
        super(MessageFormat.format("Could not find Principal with id: {0}", id));
    }
}
