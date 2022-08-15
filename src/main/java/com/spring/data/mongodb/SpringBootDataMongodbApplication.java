package com.spring.data.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.metrics.export.atlas.EnableAtlasMetrics;

@SpringBootApplication
public class SpringBootDataMongodbApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SpringBootDataMongodbApplication.class);
//		app.setApplicationStartup(new BufferingApplicationStartup(2048));
		app.run(args);
	}

}
