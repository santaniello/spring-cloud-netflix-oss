package com.felipe.microservices.currencyconversionservice;


import com.felipe.microservices.currencyconversionservice.services.CurrencyExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeService exchangeService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable String from,
                                                  @PathVariable String to,
                                                  @PathVariable BigDecimal quantity){

        Map<String,String> uriVariables = new HashMap<>();
        uriVariables.put("from",from);
        uriVariables.put("to",to);
        ResponseEntity<CurrencyConversionBean> responseEntity =
                new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversionBean.class,
                uriVariables);

        CurrencyConversionBean response = responseEntity.getBody();

        System.out.println(response.getId());

        return new CurrencyConversionBean(response.getId(),
                                          from,
                                          to,
                                          response.getConversionMultiple(),
                                          quantity,
                                          quantity.multiply(response.getConversionMultiple()),
                                          response.getPort());

    }

    @GetMapping("/currency-feign-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean feignConvertCurrency(@PathVariable("from") String from,
                                                       @PathVariable("to") String to,
                                                       @PathVariable("quantity") BigDecimal quantity) {

        CurrencyConversionBean response = exchangeService.retrieveExchangeValue(from, to);
        logger.info("{}", response);
        return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
                quantity.multiply(response.getConversionMultiple()), response.getPort());
    }
}
