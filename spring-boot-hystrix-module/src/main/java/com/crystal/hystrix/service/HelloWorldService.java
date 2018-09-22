package com.crystal.hystrix.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class HelloWorldService {
	Logger logger = LoggerFactory.getLogger(HelloWorldService.class);
	
	@HystrixCommand(fallbackMethod="fallback")
	public String helloWorld() {
		throw new RuntimeException("called Failed.");
	}
	
	public String fallback() {
		logger.info("Hello World Failed");
		return "Hello World Failed";
	}
}
