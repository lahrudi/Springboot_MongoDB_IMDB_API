package com.spring.data.mongodb.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@Component
@WritingConverter
public class NullToEmptyString implements Converter<String,String> {
    @Override
    public String convert(String str) {
        return (str == null ? "" : str);
    }
}
