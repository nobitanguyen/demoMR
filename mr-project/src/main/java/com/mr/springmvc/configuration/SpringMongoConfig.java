package com.mr.springmvc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@ComponentScan(basePackages = "com.mr.springmvc")
@EnableMongoRepositories("com.mr.springmvc.repository")
public class SpringMongoConfig extends AbstractMongoConfiguration {

	@Override
	public String getDatabaseName() {
		return "menurestaurant";
	}

	@Override
	@Bean
	public Mongo mongo() throws Exception {
		return new MongoClient("192.168.10.56");
	}
}
