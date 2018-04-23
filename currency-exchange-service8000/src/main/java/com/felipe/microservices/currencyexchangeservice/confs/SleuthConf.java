package com.felipe.microservices.currencyexchangeservice.confs;

import brave.sampler.Sampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
* Implementação do Spring Cloud Sleuth
*
* O que é Spring Cloud Sleuth ?
*
* Spring Cloud Sleuth implements a distributed tracing solution for Spring Cloud
*
* */

@Configuration
public class SleuthConf {

   /* Nós precisamos dizer a nossa aplicação, quantas vezes nós queremos exportar nossos logs para o Zipkin.
    * No exemplo abaixo, vamos exportar SEMPRE.
    */
    @Bean
    public Sampler defaultSampler(){
        return Sampler.ALWAYS_SAMPLE;
    }
}

