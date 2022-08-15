package com.spring.data.mongodb.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Component
@ReadingConverter
public class ArrayListToLinkedHashMap implements Converter< java.util.ArrayList, java.util.LinkedHashMap> {
    public String convert(String str) {
        return ( str == null || str.isEmpty() ? null : str );
    }

    @Override
    public LinkedHashMap convert(ArrayList source) {
        int i = 0;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        while (i < source.size())
        {
            linkedHashMap.put(i + 1, source.get(i));
            i++;
        }

        return linkedHashMap;
    }
}
