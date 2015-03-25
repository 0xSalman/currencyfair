package com.lucidspring.currencyfair.test.standalone;

import com.lucidspring.currencyfair.Application;
import com.lucidspring.currencyfair.entity.CountryEntity;
import com.lucidspring.currencyfair.entity.TradeEntity;
import com.lucidspring.currencyfair.service.CountryRepository;
import com.lucidspring.currencyfair.test.util.Common;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class ConsumerControllerTest {

    private final String URL = "http://localhost:8080/sendTrade";
    private final int RECORDS = 100;
    private MockMvc mockMvc;
	private Map<String, CountryEntity> pointMap;

    @Autowired
    private WebApplicationContext webApplicationContext;
	@Autowired
	private CountryRepository countryRepository;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	    pointMap = countryRepository.getPointMap();
    }

    @Test
    public void postTrade() {

        System.out.println("postTrade() - Start");

        RestTemplate restTemplate = new RestTemplate();
        List<TradeEntity> tradeData = Common.data(RECORDS);

        long startTime = System.currentTimeMillis();

        for (TradeEntity tradeEntity : tradeData) {
            ResponseEntity <String> response = restTemplate.postForEntity(URL, tradeEntity, String.class);
            assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        }

        long runTime = System.currentTimeMillis() - startTime;
        System.out.println("postTrade() - Exit. Took " + runTime + "ms to process " + RECORDS + " records");
    }

    @Test
    public void postTradeByMock() throws Exception {

        System.out.println("postTradeByMock() - Start");

        List<TradeEntity> tradeData = Common.data(RECORDS);

        long startTime = System.currentTimeMillis();

        for (TradeEntity tradeEntity : tradeData) {
            mockMvc.perform(
                    post("/sendTrade")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(Common.convertObjectToJsonBytes(tradeEntity))
                           )
                   .andExpect(status().isOk());
        }

        long runTime = System.currentTimeMillis() - startTime;
        System.out.println("postTradeByMock() - Exit. Took " + runTime + "ms to process " + RECORDS + " records");
    }

}
