package com.lucidspring.currencyfair.service;

import com.lucidspring.currencyfair.entity.CountryEntity;
import com.lucidspring.currencyfair.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Country DB interaction service which reads the whole collection during initialization
 */
@Component
public class CountryRepository {

	private MongoTemplate mongoTemplate;
	private List<CountryEntity> countries;
	private Map<String, CountryEntity> pointMap;

	@Autowired
	public CountryRepository(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
		pointMap = new HashMap<>();
		retrieveCountries();
		LoggerUtil.logDebug(CountryRepository.class, "Countries info loaded successfully");
	}

	public void save(CountryEntity countryEntity) {
		mongoTemplate.save(countryEntity);
	}

	public void save(List<CountryEntity> countries) {
		for (CountryEntity country: countries) {
			save(country);
		}
	}

	private void retrieveCountries() {
		countries = mongoTemplate.findAll(CountryEntity.class);
		for(CountryEntity country : countries) {
			pointMap.put(country.getCode(), country);
		}
	}

	public List<CountryEntity> getCountries() {
		return countries;
	}

	public void setCountries(List<CountryEntity> countries) {
		this.countries = countries;
	}

	public Map<String, CountryEntity> getPointMap() {
		return pointMap;
	}

	public void setPointMap(Map<String, CountryEntity> pointMap) {
		this.pointMap = pointMap;
	}
}
