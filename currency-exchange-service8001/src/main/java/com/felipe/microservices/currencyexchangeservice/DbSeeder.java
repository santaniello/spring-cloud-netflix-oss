package com.felipe.microservices.currencyexchangeservice;

import com.felipe.microservices.currencyexchangeservice.models.ExchangeValue;
import com.felipe.microservices.currencyexchangeservice.repositories.ExchangeValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

// This interface provides access to application arguments as string array.
// Let's see the example code for more clarity.
@Component
public class DbSeeder implements CommandLineRunner {

    private ExchangeValueRepository repository;

    @Autowired
    public DbSeeder(ExchangeValueRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        ExchangeValue ex1 = new ExchangeValue(
                "USD",
                "INR",
                new BigDecimal(65)
        );

        ExchangeValue ex2 = new ExchangeValue(
                "EUR",
                "INR",
                new BigDecimal(75)
        );

        ExchangeValue ex3 = new ExchangeValue(
                "AUD",
                "INR",
                new BigDecimal(25)
        );

        this.repository.deleteAll();

        List<ExchangeValue> exchanges = Arrays.asList(ex1,ex2,ex3);
        exchanges.forEach(e -> this.repository.save(e));
    }
}