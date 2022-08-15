package com.spring.data.mongodb.services;

import com.spring.data.mongodb.exception.AppInfoNotFoundException;
import com.spring.data.mongodb.exception.RatingNotFoundException;
import com.spring.data.mongodb.model.AppInfo;
import com.spring.data.mongodb.model.Rating;
import com.spring.data.mongodb.repository.AppInfoRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.math.NumberUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
@Service
public class AppInfoService {
    private final AppInfoRepository appInfoRepository;

    @Value("${api.version}")
    private String version;

    @Autowired
    public AppInfoService(AppInfoRepository ratingRepository) {
        this.appInfoRepository = ratingRepository;
    }

    public AppInfo saveAppInfo(AppInfo appInfo) {
        return appInfoRepository.save(appInfo);
    }

    public List<AppInfo> getAll() {
        return StreamSupport.stream(appInfoRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public AppInfo getLast() {
        return appInfoRepository
                .findTopByVersionOrderByStartDateDesc(version)
                .orElseThrow(() -> new AppInfoNotFoundException());
    }

}
