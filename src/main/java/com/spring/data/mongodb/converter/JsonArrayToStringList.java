package com.spring.data.mongodb.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.data.mongodb.config.Constants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Component
@ReadingConverter
public class JsonArrayToStringList implements Converter<String, List<String>> {
    @Override
    public List<String> convert(String jsonString) {
        try {
            jsonString = ( jsonString == null || jsonString.isEmpty() ? "[]" : jsonString );

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonString);
            
            if (!jsonNode.isArray()) {
                throw new IOException(Constants.ERR_JSON_DATA_FORMAT);
            } else {
                ArrayList<String> results = new ArrayList<>();

                Iterator<JsonNode> it = jsonNode.elements();
                while (it.hasNext()) {
                    results.add(it.next().asText());
                }

                return results;
            }
        } catch (IOException e) {
            if (jsonString.contains(",")) {
                return Arrays.asList(jsonString.split(","));
            } else {
                return Arrays.asList(jsonString);
            }
        }
    }
}
