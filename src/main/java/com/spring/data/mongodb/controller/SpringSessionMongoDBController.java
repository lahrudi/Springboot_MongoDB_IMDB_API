package com.spring.data.mongodb.controller;

import com.spring.data.mongodb.model.AppInfo;
import com.spring.data.mongodb.services.AppInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@ConditionalOnProperty(name = "session.controller.enabled", havingValue = "true")
@RequiredArgsConstructor
public class SpringSessionMongoDBController {

    private final AppInfoService appInfoService;

    @GetMapping("/session/count")
    public ResponseEntity<String> count(HttpSession session) {
        Integer counter = (Integer) session.getAttribute("count");
        log.info(" Count of HTTP requests per session that received : " + counter);
        return ResponseEntity.ok(" Count of HTTP requests per session that received : " + counter);
    }

    @GetMapping("/count")
    public ResponseEntity<String> countAll() {
        AppInfo appInfo = appInfoService.getLast();
        appInfo.setViewersCount(appInfo.getViewersCount() + 1);
        appInfoService.saveAppInfo(appInfo);
        log.info(" Count of all HTTP requests that received in the application since the last startup : " + appInfo.getViewersCount());
        return ResponseEntity.ok(" Count of all HTTP requests that received : " + appInfo.getViewersCount());
    }

}