package com.lucidspring.currencyfair.test.standalone;

import com.lucidspring.currencyfair.dto.CountryAggregate;
import com.lucidspring.currencyfair.dto.CurrencyAggregate;
import com.lucidspring.currencyfair.entity.CountryEntity;
import com.lucidspring.currencyfair.Application;
import com.lucidspring.currencyfair.entity.TradeEntity;
import com.lucidspring.currencyfair.service.CountryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class DBQueryTest {

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CountryRepository countryRepository;

	@Test
	public void aggregateByCountry() {

		int limit = 5;

		List<AggregationOperation> operations = new ArrayList<>();
		operations.add(group("originatingCountry").count().as("tradeCount")
				.push("currencyPair").as("pairs"));
		operations.add(project("tradeCount", "pairs").and("_id").as("code"));
		operations.add(sort(Sort.Direction.DESC, "tradeCount"));
		operations.add(limit(limit));

		Aggregation agg = newAggregation(operations);
		AggregationResults<CountryAggregate> results = mongoTemplate.aggregate(agg, TradeEntity.class, CountryAggregate.class);
		List<CountryAggregate> topTradedCountries = results.getMappedResults();

		for(CountryAggregate countryAggregate : topTradedCountries) {
			Map<String, Integer> pairMap = new HashMap<>();
			for(String string: countryAggregate.getPairs()) {
				if (pairMap.containsKey(string)) {
					pairMap.put(string, pairMap.get(string) + 1);
				} else {
					pairMap.put(string, 1);
				}
			}

			List<CurrencyAggregate> pairList = new ArrayList<>();
			for (Map.Entry<String, Integer> entry : pairMap.entrySet()) {
				CurrencyAggregate ca = new CurrencyAggregate();
				ca.setPair(entry.getKey());
				ca.setTradeCount(entry.getValue());
				pairList.add(ca);
			}

			Collections.sort(pairList);
			int min = Math.min(limit, pairList.size());
			List<CurrencyAggregate> limitedPairs = pairList.subList(0, min);

			CountryEntity country = countryRepository.getPointMap().get(countryAggregate.getCode());
			countryAggregate.setName(country.getName());
			countryAggregate.setLocation(country.getLocation());
			countryAggregate.setCurrencyPairs(limitedPairs);

//			countryAggregate.setCurrencyPairs(topTradedCurrencies(countryAggregate.getCode(), 5));
			System.out.println(countryAggregate.toString());
		}
	}

	private List<CurrencyAggregate> topTradedCurrencies(String country, int limit) {

		List<AggregationOperation> operations = new ArrayList<>();
		operations.add(match(Criteria.where("originatingCountry").is(country)));
		operations.add(group("currencyPair").count().as("tradeCount"));
		operations.add(project("tradeCount").and("_id").as("pair"));
		operations.add(sort(Sort.Direction.DESC, "tradeCount"));
		operations.add(limit(limit));

		Aggregation agg = newAggregation(operations);
		AggregationResults<CurrencyAggregate> results = mongoTemplate.aggregate(agg, TradeEntity.class, CurrencyAggregate.class);
		List<CurrencyAggregate> topTradedPairs = results.getMappedResults();

		return topTradedPairs;
	}

}
