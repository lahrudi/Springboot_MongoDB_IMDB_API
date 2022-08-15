package com.spring.data.mongodb;

import com.spring.data.mongodb.config.MongoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;


@Configuration
@ComponentScan(basePackages = "com.spring.data.mongodb.*",
		excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
				pattern = ".*[DataService]"))
@ContextConfiguration(classes = MongoConfig.class)
public class SpringBootTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTestApplication.class, args);
	}

}
