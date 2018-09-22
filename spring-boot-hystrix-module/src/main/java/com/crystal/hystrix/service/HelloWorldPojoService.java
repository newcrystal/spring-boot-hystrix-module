package com.crystal.hystrix.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HelloWorldPojoService extends HystrixCommand<String>{
	Logger logger = LoggerFactory.getLogger(HelloWorldPojoService.class);
	private final String name;

    public HelloWorldPojoService(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"));
        this.name = name;
    }
    
	@Override
	protected String run() throws Exception {
		throw new RuntimeException("run failed.");
	}
	
	@Override
    protected String getFallback() {
		logger.info("Hello World Failed. name : {}", name);
        return "Hello World Failed";
    }

}
