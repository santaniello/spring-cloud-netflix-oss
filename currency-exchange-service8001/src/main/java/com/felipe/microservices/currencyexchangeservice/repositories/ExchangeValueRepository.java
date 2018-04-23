package com.felipe.microservices.currencyexchangeservice.repositories;

import com.felipe.microservices.currencyexchangeservice.models.ExchangeValue;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ExchangeValueRepository extends MongoRepository<ExchangeValue,String> {
    ExchangeValue findByFromAndTo(String from, String to);
}
