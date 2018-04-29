package com.felipe.microservices.limitsservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsConfigurationController {
    @Autowired
    private Configuration conf;

    @GetMapping(path = "/limits")
    public LimitsConfiguration retrieveLimitsFromConfigurations(){
        return new LimitsConfiguration(conf.getMinimum(),conf.getMaximum());
    }

    @HystrixCommand(fallbackMethod = "fallbackRetrieveConfiguration")
    @GetMapping(path = "/fault-tolerance-example")
    public LimitsConfiguration retrieveConfigurations(){
        throw new RuntimeException("Not available");
    }

    public LimitsConfiguration fallbackRetrieveConfiguration(){
        return new LimitsConfiguration(9,99999);
    }
}
