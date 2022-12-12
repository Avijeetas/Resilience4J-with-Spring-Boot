package com.example.servicea.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@RestController
@RequestMapping("/a")
public class ServiceaController {
    @Autowired
    private RestTemplate restTemplate;
    private static final String Base_URL="http://localhost:8083/";
    private static final String SERVICE_A="serviceA";
    int count=1;
    @GetMapping
 // @CircuitBreaker(name = SERVICE_A, fallbackMethod = "serviceAFallback")
    @Retry(name = SERVICE_A)
    public String serviceA(){
        System.out.println("retry called "+(++count)+" at "+new Date());
        String url = Base_URL + "/b";
        return  restTemplate.getForObject(url, String.class);

    }

    public String serviceAFallback(Exception e){
        return "This is a fallback method for Service A";
    }
}
