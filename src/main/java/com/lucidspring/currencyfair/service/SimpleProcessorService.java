package com.lucidspring.currencyfair.service;

import com.lucidspring.currencyfair.dto.CountryAggregate;
import com.lucidspring.currencyfair.dto.CurrencyAggregate;
import com.lucidspring.currencyfair.entity.CountryEntity;
import com.lucidspring.currencyfair.entity.TradeEntity;
import com.lucidspring.currencyfair.util.Common;
import com.lucidspring.currencyfair.util.LogLevel;
import com.lucidspring.currencyfair.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Simple concrete implementation of processor interface to read and produce two aggregates to render on front end
 * Top 5 traded pairs
 * Traded countries and top 5 traded pairs for each country
 */

@Service("simple")
public class SimpleProcessorService implements ProcessorService {

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CountryRepository countryRepository;
    @Autowired
    Common common;

	@Override
	public void process() {

        LoggerUtil.logEnter(SimpleProcessorService.class, LogLevel.INFO, "process");

		updateCurrencyAggregate();
		updateCountryAggregate();

        LoggerUtil.logExit(SimpleProcessorService.class, LogLevel.INFO, "process");
	}

	private List<CurrencyAggregate> updateCurrencyAggregate() {

        LoggerUtil.logEnter(SimpleProcessorService.class, LogLevel.DEBUG, "updateCurrencyAggregate");

		long start = System.currentTimeMillis();

		List<CurrencyAggregate> resultAggregate = getCurrencyCounter(10, null);

		long runTime = System.currentTimeMillis() - start;
		LoggerUtil.logDebug(SimpleProcessorService.class, "Took {}ms to retrieve currency aggregate", runTime);

        common.notifyWSClient("/topic/currencyAggregate", resultAggregate);

        LoggerUtil.logExit(SimpleProcessorService.class, LogLevel.DEBUG, "updateCurrencyAggregate");
		return resultAggregate;
	}

	private List<CountryAggregate> updateCountryAggregate() {

        LoggerUtil.logEnter(SimpleProcessorService.class, LogLevel.DEBUG, "updateCountryAggregate");

		long start = System.currentTimeMillis();
		int limit = 5;

		List<AggregationOperation> operations = new ArrayList<>();
		operations.add(group("originatingCountry").count().as("tradeCount").push("currencyPair").as("pairs"));
		operations.add(project("tradeCount", "pairs").and("_id").as("code"));

		Aggregation agg = newAggregation(operations);

		AggregationResults<CountryAggregate> results = mongoTemplate.aggregate(agg, TradeEntity.class, CountryAggregate.class);
		List<CountryAggregate> resultAggregate = results.getMappedResults();

		/**
		 * Count top 5 traded pairs for each country
		 * Note: performance of in memory calculation of the pairs is much better
		 * than querying database for every country
		 */
		for(CountryAggregate countryAggregate : resultAggregate) {

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
		}

		long runTime = System.currentTimeMillis() - start;
		LoggerUtil.logDebug(SimpleProcessorService.class, "Took {}ms to retrieve country aggregate", runTime);

        common.notifyWSClient("/topic/countryAggregate", resultAggregate);

        LoggerUtil.logExit(SimpleProcessorService.class, LogLevel.DEBUG, "updateCountryAggregate");
		return resultAggregate;
	}

	private List<CurrencyAggregate> getCurrencyCounter(int limit, Criteria criteria) {

		List<AggregationOperation> operations = new ArrayList<>();
		if(criteria != null)    operations.add(match(criteria));
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
