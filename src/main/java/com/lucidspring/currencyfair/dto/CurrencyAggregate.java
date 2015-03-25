package com.lucidspring.currencyfair.dto;

/**
 * DTO to hold currency aggregate information for front end
 */
public class CurrencyAggregate implements Comparable {

	private String pair;
	private int tradeCount;

	public String getPair() {
		return this.pair;
	}

	public void setPair(String pair) {
		this.pair = pair;
	}

	public int getTradeCount() {
		return tradeCount;
	}

	public void setTradeCount(int tradeCount) {
		this.tradeCount = tradeCount;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(getPair()).append(": ").append(tradeCount);
		return stringBuilder.toString();
	}

	@Override
	public int compareTo(Object o) {
		CurrencyAggregate t2 = (CurrencyAggregate)o;
		// descending order
		return Integer.compare(t2.getTradeCount(), tradeCount);
	}
}
