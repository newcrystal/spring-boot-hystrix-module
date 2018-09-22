package com.crystal.hystrix;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crystal.hystrix.service.HelloWorldPojoService;
import com.crystal.hystrix.service.HelloWorldService;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTest {
	@LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
	
	@Test
	public void contextLoads() {}
	
	@Test
    public void testFallback() throws Exception {
		ResponseEntity<String> res = this.restTemplate.getForEntity("http://localhost:" + port + "/helloWorld", String.class);
                assertEquals("incorrect fallback", "Hello World Failed", res.getBody());
    }
	
	@Test
    public void testPojoFallback() throws Exception {
		ResponseEntity<String> res = this.restTemplate.getForEntity("http://localhost:" + port + "/helloWorld-pojo", String.class);
                assertEquals("incorrect fallback", "Hello World Failed", res.getBody());
    }
}

@Configuration
@RestController
@SpringBootApplication
@EnableAutoConfiguration
class Application {
	@Autowired
	private HelloWorldService helloWorldService;
	
	private final HelloWorldPojoService helloWorldPojoService = new HelloWorldPojoService("test");
	
	@RequestMapping("/helloWorld")
	public String helloWorld() {
		return helloWorldService.helloWorld();
	}
	
	@RequestMapping("/helloWorld-pojo")
	public String helloWorldPojo() {
		return helloWorldPojoService.execute();
	}
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run(args);
	}
}