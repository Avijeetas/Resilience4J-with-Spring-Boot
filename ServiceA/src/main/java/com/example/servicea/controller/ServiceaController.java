package com.example.servicea.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/a")
public class ServiceaController {
    @Autowired
    private RestTemplate restTemplate;
    private static final String Base_URL="http://localhost:8083/";
    private static final String SERVICE_A="serviceA";

    @GetMapping
    @CircuitBreaker(name = SERVICE_A, fallbackMethod = "serviceAFallback")

    public String serviceA(){

        String url = Base_URL + "/b";
        return  restTemplate.getForObject(url, String.class);

    }

    public String serviceAFallback(Exception e){
        return "This is a fallback method for Service A";
    }
}
