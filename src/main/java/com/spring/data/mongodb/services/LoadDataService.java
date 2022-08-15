package com.spring.data.mongodb.services;

import com.spring.data.mongodb.model.AppInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadDataService {

    private final PrincipalService principalService;
    private final PersonService personService;
    private final TitleService titleService;
    private final AkasService akasService;
    private final CrewService crewService;
    private final RatingService ratingService;
    private final AppInfoService appInfoService;

    @Value("${api.version}")
    private String version;

    @Value("${person.filePath}")
    private String personFilePath;

    @Value("${principal.filePath}")
    private String principalFilePath;

    @Value("${title.filePath}")
    private String titleFilePath;

    @Value("${akas.filePath}")
    private String akasFilePath;

    @Value("${crew.filePath}")
    private String crewFilePath;

    @Value("${rating.filePath}")
    private String ratingFilePath;

    @EventListener(value = ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() throws IOException {

        personService.loadPersonData(personFilePath);
        principalService.loadPrincipalData(principalFilePath);
        titleService.loadTitleData(titleFilePath);
        crewService.loadCrewData(crewFilePath);
        ratingService.loadRatingData(ratingFilePath);
        akasService.loadAkasData(akasFilePath);
        appInfoService.saveAppInfo(AppInfo.builder().startDate(new Date().getTime()).version(version).viewersCount(0).build());

        log.info("The IMDB data is loaded.");

    }
}
