package com.spring.data.mongodb.services;

import com.spring.data.mongodb.converter.IntegerToBoolean;
import com.spring.data.mongodb.exception.CrewNotFoundException;
import com.spring.data.mongodb.model.Crew;
import com.spring.data.mongodb.repository.CrewRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
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
public class CrewService {
    private final CrewRepository crewRepository;
    private final IntegerToBoolean integerToBoolean;

    @Autowired
    public CrewService(CrewRepository crewRepository, IntegerToBoolean integerToBoolean) {
        this.crewRepository = crewRepository;
        this.integerToBoolean = integerToBoolean;
    }

    public Crew saveCrew(Crew akas) {
        return crewRepository.save(akas);
    }

    public List<Crew> getAll() {
        return StreamSupport.stream(crewRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Crew findByTconst(String tconst) {
        return crewRepository.findByTconst(tconst);
    }

    public Crew getCrewById(ObjectId id) {
        return crewRepository
                .findById(id.toString())
                .orElseThrow(() -> new CrewNotFoundException(id));
    }

    public List<Crew> findByDirectors(String directors) {
        return StreamSupport.stream(crewRepository.findByDirectors(directors).spliterator(), false).collect(Collectors.toList());
    }

    public List<Crew> findByWriters(String writers) {
        return StreamSupport.stream(crewRepository.findByWriters(writers).spliterator(), false).collect(Collectors.toList());
    }

    public void loadCrewData(String filePath) throws FileNotFoundException {
        if (crewRepository.count() == 0) {

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
                            crewRepository.save(Crew.builder()
                                    .tconst(csvRecord.get("tconst"))
                                    .directors(Arrays.asList(csvRecord.get("directors").replace("\\N","").split(",")))
                                    .writers(Arrays.asList(csvRecord.get("writers").replace("\\N","").split(",")))
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
