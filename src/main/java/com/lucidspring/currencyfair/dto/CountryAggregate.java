package com.lucidspring.currencyfair.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.geo.Point;

import java.util.List;

/**
 * DTO to hold country aggregate information for front end
 */

@JsonIgnoreProperties({"pairs"})
public class CountryAggregate {

	private String code;
	private int tradeCount;
	private List<CurrencyAggregate> currencyPairs;
	private String[] pairs;
	private Point location;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public String[] getPairs() {
		return pairs;
	}

	public void setPairs(String[] pairs) {
		this.pairs = pairs;
	}

	public int getTradeCount() {
		return tradeCount;
	}

	public void setTradeCount(int tradeCount) {
		this.tradeCount = tradeCount;
	}

	public List<CurrencyAggregate> getCurrencyPairs() {
		return currencyPairs;
	}

	public void setCurrencyPairs(List<CurrencyAggregate> currencyPairs) {
		this.currencyPairs = currencyPairs;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {

		StringBuilder stringBuilder = new StringBuilder("{");
		stringBuilder.append("code: ").append(code).append(", ");
		stringBuilder.append("tradeCount: ").append(tradeCount).append(", ");
		stringBuilder.append("topTradedPairs: [");
		for (int i = 0; i < currencyPairs.size(); i++) {
			stringBuilder.append(currencyPairs.get(i).toString());
			if(i != currencyPairs.size() - 1)
				stringBuilder.append(", ");
		}
		stringBuilder.append("]");
		stringBuilder.append("}");

		return stringBuilder.toString();
	}
}
