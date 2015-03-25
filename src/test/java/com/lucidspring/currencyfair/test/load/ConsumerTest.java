package com.lucidspring.currencyfair.test.load;

import com.lucidspring.currencyfair.entity.TradeEntity;
import com.lucidspring.currencyfair.test.util.Common;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

public class ConsumerTest {

	private final String URL = "http://localhost:8080/sendTrade";
	private final int RECORDS = 50000;

	@Test
	public void postTrades() {

		RestTemplate restTemplate = new RestTemplate();
		List<TradeEntity> tradeData = Common.data(RECORDS);

		long startTime = System.currentTimeMillis();

		for (TradeEntity tradeEntity : tradeData) {
			ResponseEntity<String> response = restTemplate.postForEntity(URL, tradeEntity, String.class);
			assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		}

		long runTime = System.currentTimeMillis() - startTime;
		System.out.println("postTrade() - Exit. Took " + runTime + "ms to process " + RECORDS + " records");
	}
}
