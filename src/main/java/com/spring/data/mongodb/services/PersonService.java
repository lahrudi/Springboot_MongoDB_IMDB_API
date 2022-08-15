package com.spring.data.mongodb.services;

import com.spring.data.mongodb.model.Person;
import org.apache.commons.lang3.math.NumberUtils;
import com.spring.data.mongodb.repository.PersonRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
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
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    public List<Person> getAll() {
        return StreamSupport.stream(personRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public List<Person> findByPrimaryName(String primaryName) {
        return StreamSupport.stream(personRepository.findByPrimaryName(primaryName).spliterator(), false).collect(Collectors.toList());
    }

    public void loadPersonData(String filePath) throws FileNotFoundException {
        if (personRepository.count() == 0) {
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
                            personRepository.save(Person.builder()
                                    .primaryName(csvRecord.get("primaryName"))
                                    .nconst(csvRecord.get("nconst"))
                                    .birthYear( ( NumberUtils.isDigits(csvRecord.get("birthYear")) ? Integer.valueOf(csvRecord.get("birthYear")) : null) )
                                    .deathYear( ( NumberUtils.isDigits(csvRecord.get("deathYear")) ? Integer.valueOf(csvRecord.get("deathYear")) : null) )
                                    .primaryProfession(Arrays.asList(csvRecord.get("primaryProfession").split(",")))
                                    .knownForTitles(Arrays.asList(csvRecord.get("primaryProfession").split(","))).build());
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
