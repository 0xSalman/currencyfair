package com.lucidspring.currencyfair.test.standalone;

import com.lucidspring.currencyfair.Application;
import com.lucidspring.currencyfair.test.util.Common;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class IndexControllerTest {

	@Test
	public void loadData() throws Exception {

		System.out.println("loadData() - Start");

        HttpHeaders httpHeaders = new HttpHeaders();
        Common.loginAndSaveJsessionIdCookie("test", "currencyfair", httpHeaders);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/loadData", HttpMethod.GET,
                                                                httpEntity, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo("Success"));

        System.out.println("loadData() - Exit");
	}
}
