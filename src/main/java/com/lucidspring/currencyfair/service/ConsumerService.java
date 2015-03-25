package com.lucidspring.currencyfair.service;

import com.lucidspring.currencyfair.entity.TradeEntity;
import org.springframework.scheduling.annotation.Async;

/**
 * Consumer interface which is invoked using strategy pattern
 */

public interface ConsumerService {

	// call this method asynchronously using servlet 3.0 feature
	// enabling this can free up REST http thread quickly
	@Async
	public void saveTrade(TradeEntity trade);
}
