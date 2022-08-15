package com.spring.data.mongodb.services;

import com.spring.data.mongodb.converter.JsonArrayToStringList;
import com.spring.data.mongodb.model.Principal;
import com.spring.data.mongodb.repository.PrincipalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
@Service
public class PrincipalService {
    private final PrincipalRepository principalRepository;
    private final JsonArrayToStringList jsonArrayToStringList;

    @Autowired
    public PrincipalService(PrincipalRepository principalRepository, JsonArrayToStringList jsonArrayToStringList) {
        this.principalRepository = principalRepository;
        this.jsonArrayToStringList = jsonArrayToStringList;
    }

    public Principal savePrincipal(Principal principal) {
        return principalRepository.save(principal);
    }

    public List<Principal> getAll() {
        return StreamSupport.stream(principalRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public List<Principal> findByTconst(String tconst) {
        return StreamSupport.stream(principalRepository.findByTconst(tconst).spliterator(), false).collect(Collectors.toList());
    }

    public List<Principal> findByNconst(String nconst) {
        return StreamSupport.stream(principalRepository.findByNconst(nconst).spliterator(), false).collect(Collectors.toList());
    }

    public void loadPrincipalData(String filePath) throws FileNotFoundException {
        if (principalRepository.count() == 0) {

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
                            principalRepository.save(Principal.builder()
                                    .category(csvRecord.get("category"))
                                    .nconst(csvRecord.get("nconst"))
                                    .characters(jsonArrayToStringList.convert((csvRecord.get("characters").equals("\\N") ? null : csvRecord.get("characters")))                                    )
                                    .job((csvRecord.get("job").equals("\\N") ? null : csvRecord.get("job")))
                                    .tconst(csvRecord.get("tconst"))
                                    .ordering(Integer.valueOf(csvRecord.get("ordering"))).build());
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
