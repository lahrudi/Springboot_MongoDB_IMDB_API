package com.spring.data.mongodb.controller;

import com.spring.data.mongodb.model.AppInfo;
import com.spring.data.mongodb.services.AppInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class ReceivedRequestFilter implements Filter {

    private final AppInfoService appInfoService;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        Integer counter = (Integer) request.getSession().getAttribute("count");

        if (counter == null) {
            counter = 1;
        } else {
            counter++;
        }
        request.getSession().setAttribute("count", counter);

        AppInfo appInfo = appInfoService.getLast();
        appInfo.setViewersCount(appInfo.getViewersCount() + 1);
        appInfoService.saveAppInfo(appInfo);
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}
}
