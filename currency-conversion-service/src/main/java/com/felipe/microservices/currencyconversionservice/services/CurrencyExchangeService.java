package com.felipe.microservices.currencyconversionservice.services;

import com.felipe.microservices.currencyconversionservice.CurrencyConversionBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/* Não precisamos mais do atributo url pois o Ribbon vai fazer o balance
 * entre as instâncias da aplicação...
 * @FeignClient(name="currency-exchange-service", url="localhost:8000")
**/

/* Ao invés de chamarmos direto a aplicação, iremos chamar o Zuul e ele
 * atuará como um proxy centralizando os logs das requisições, segurança
 * e etc...
 * @FeignClient(name="currency-exchange-service")
 * */
@FeignClient(name="netflix-zuul-api-gateway-server")
/* O Ribbon realiza o balance entre todas as instâncias
   da aplicação currency-exchange-service */
@RibbonClient(name="currency-exchange-service")
public interface CurrencyExchangeService {

    // @GetMapping("/currency-exchange/from/{from}/to/{to}")
     @GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
     public CurrencyConversionBean retrieveExchangeValue(
             @PathVariable("from") String from,
             @PathVariable("to") String to);
}
