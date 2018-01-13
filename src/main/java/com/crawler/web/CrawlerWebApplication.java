package com.crawler.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.crawler.web.service", "com.crawler.web.controller"})
public class CrawlerWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrawlerWebApplication.class, args);
	}
}
