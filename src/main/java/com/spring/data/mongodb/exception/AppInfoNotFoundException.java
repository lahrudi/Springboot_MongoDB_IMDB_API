package com.spring.data.mongodb.exception;

import org.bson.types.ObjectId;

import java.text.MessageFormat;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
public class AppInfoNotFoundException extends RuntimeException
{
    public AppInfoNotFoundException() {
        super("Could not find AppInfo");
    }
}
