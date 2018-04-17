package com.felipe.microservices.limitsservice;

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
}
