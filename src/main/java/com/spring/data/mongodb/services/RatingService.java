package com.spring.data.mongodb.services;

import com.spring.data.mongodb.exception.RatingNotFoundException;
import com.spring.data.mongodb.model.Rating;
import com.spring.data.mongodb.repository.RatingRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.math.NumberUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
@Service
public class RatingService {
    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    public List<Rating> getAll() {
        return StreamSupport.stream(ratingRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Rating getRatingById(ObjectId id) {
        return ratingRepository
                .findById(id.toString())
                .orElseThrow(() -> new RatingNotFoundException(id));
    }

    public Rating findByTconst(String tconst) {
        return ratingRepository.findByTconst(tconst);
    }
    public List<Rating> findByAverageRating(String averageRating) {
        return StreamSupport.stream(ratingRepository.findByAverageRating(averageRating).spliterator(), false).collect(Collectors.toList());
    }

    public List<Rating> findByNumVotes(String numVotes) {
        return StreamSupport.stream(ratingRepository.findByNumVotes(numVotes).spliterator(), false).collect(Collectors.toList());
    }

    public void loadRatingData(String filePath) throws FileNotFoundException {
        if (ratingRepository.count() == 0) {

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
                            ratingRepository.save(Rating.builder()
                                    .tconst(csvRecord.get("tconst"))
                                    .averageRating((csvRecord.get("averageRating").replace("\\N","")))
                                    .numVotes( ( NumberUtils.isDigits(csvRecord.get("numVotes")) ? Integer.valueOf(csvRecord.get("numVotes")) : null))
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
