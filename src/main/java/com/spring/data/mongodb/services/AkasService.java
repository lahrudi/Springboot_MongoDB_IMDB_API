package com.spring.data.mongodb.services;

import com.spring.data.mongodb.converter.IntegerToBoolean;
import com.spring.data.mongodb.exception.AkasNotFoundException;
import com.spring.data.mongodb.model.Akas;
import com.spring.data.mongodb.repository.AkasRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.math.NumberUtils;
import org.bson.types.ObjectId;
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
public class AkasService {
    private final AkasRepository akasRepository;
    private final IntegerToBoolean integerToBoolean;

    @Autowired
    public AkasService(AkasRepository akasRepository, IntegerToBoolean integerToBoolean) {
        this.akasRepository = akasRepository;
        this.integerToBoolean = integerToBoolean;
    }

    public Akas saveAkas(Akas akas) {
        return akasRepository.save(akas);
    }

    public List<Akas> getAll() {
        return StreamSupport.stream(akasRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public List<Akas> findByTitle(String title) {
        return StreamSupport.stream(akasRepository.findByTitle(title).spliterator(), false).collect(Collectors.toList());
    }

    public Akas getAkasById(ObjectId id) {
        return akasRepository
                .findById(id.toString())
                .orElseThrow(() -> new AkasNotFoundException(id));
    }

    public List<Akas> findByOrdering(Integer ordering) {
        return StreamSupport.stream(akasRepository.findByOrdering(ordering).spliterator(), false).collect(Collectors.toList());
    }

    public void loadAkasData(String filePath) throws FileNotFoundException {
        if (akasRepository.count() == 0) {

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
                            akasRepository.save(Akas.builder()
                                    .titleId(csvRecord.get("titleId"))
                                    .ordering( ( NumberUtils.isDigits(csvRecord.get("ordering")) ? Integer.valueOf(csvRecord.get("ordering")) : null) )
                                    .title((csvRecord.get("title").replace("\\N","")))
                                    .region((csvRecord.get("region").replace("\\N","")))
                                    .language((csvRecord.get("language").replace("\\N","")))
                                    .types(Arrays.asList(csvRecord.get("types").replace("\\N","").split(",")))
                                    .attributes(Arrays.asList(csvRecord.get("attributes").replace("\\N","").split(",")))
                                    .isOriginalTitle(integerToBoolean.convert(Integer.valueOf(csvRecord.get("isOriginalTitle"))))
                            .build());
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
