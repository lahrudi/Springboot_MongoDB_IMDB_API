package com.spring.data.mongodb.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class IntegerToBoolean implements Converter<Integer,Boolean> {
    @Override
    public Boolean convert(Integer integer) {
        switch (integer) {
            case 1 :
                return true;
            case 0:
                return false;
        }
        return false;
    }
}