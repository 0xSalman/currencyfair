package com.lucidspring.currencyfair.service;

import com.lucidspring.currencyfair.entity.TradeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Trade DB interaction service using Spring Data repository functionality
 */
public interface TradeRepository extends MongoRepository<TradeEntity, String> {

	public Long countByOriginatingCountry(String country);

	@Query("{'currencyFrom': ?0, 'currencyTo': ?1 }")
	public List<TradeEntity> findByCurrencies(String from, String to);

	public Long countByCurrencyFromAndCurrencyTo(String from, String to);
}
