package com.spring.data.mongodb.services;

import com.spring.data.mongodb.converter.IntegerToBoolean;
import com.spring.data.mongodb.model.Title;
import com.spring.data.mongodb.repository.TitleRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
@Service
public class TitleService {
    private final TitleRepository titleRepository;
    private final IntegerToBoolean integerToBoolean;

    @Autowired
    public TitleService(TitleRepository titleRepository, IntegerToBoolean integerToBoolean) {
        this.titleRepository = titleRepository;
        this.integerToBoolean = integerToBoolean;
    }

    public Title saveTitle(Title person) {
        return titleRepository.save(person);
    }

    public List<Title> getAll() {
        return StreamSupport.stream(titleRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public List<Title> findByPrimaryName(String primaryTitle) {
        return StreamSupport.stream(titleRepository.findByPrimaryTitle(primaryTitle).spliterator(), false).collect(Collectors.toList());
    }

    public List<Title> findByTconst(String tconst) {
        return StreamSupport.stream(titleRepository.findByTconst(tconst).spliterator(), false).collect(Collectors.toList());
    }

    public void loadTitleData(String filePath) throws FileNotFoundException {
        if (titleRepository.count() == 0) {

            InputStream is = new FileInputStream(filePath);
            try (
                    BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    CSVParser csvParser = new CSVParser(fileReader,
                            CSVFormat.MONGODB_TSV.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            ) {
                Iterable<CSVRecord> csvRecords = csvParser.getRecords();
                for (CSVRecord csvRecord : csvRecords) {
                    if (csvRecord.size() >= csvParser.getHeaderMap().size()) {
                        try {
                            titleRepository.save(Title.builder()
                                    .tconst(csvRecord.get("tconst"))
                                    .titleType(csvRecord.get("titleType"))
                                    .primaryTitle(csvRecord.get("primaryTitle"))
                                    .originalTitle(csvRecord.get("originalTitle"))
                                    .isAdult(integerToBoolean.convert(Integer.valueOf(csvRecord.get("isAdult"))))
                                    .startYear( ( NumberUtils.isDigits(csvRecord.get("startYear")) ? Integer.valueOf(csvRecord.get("startYear")) : null) )
                                    .endYear( ( NumberUtils.isDigits(csvRecord.get("endYear")) ? Integer.valueOf(csvRecord.get("endYear")) : null) )
                                    .runtimeMinutes( ( NumberUtils.isDigits(csvRecord.get("runtimeMinutes")) ? Integer.valueOf(csvRecord.get("runtimeMinutes")) : null) )
                                    .genres(Arrays.asList(csvRecord.get("genres").split(","))).build());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException("fail to parse TSV file: " + e.getMessage());
            }
        }
    }
}
