package com.lucidspring.currencyfair.test.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.lucidspring.currencyfair.entity.TradeEntity;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Common {

	private static final String GOOGLE_API_KEY = "AIzaSyC8AAkfoIlqIQEJZXkujCUlO-kvuHkx6OU";
	private static GeoApiContext context = new GeoApiContext().setApiKey(GOOGLE_API_KEY);

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
    
    public static List<TradeEntity> data(int dataSize) {

        String[] currencyFrom = {
                "AED", "AUD", "CAD", "EUR", "EGP", "GBP", "IDR", "INR", "USD", "JPY",
                "CNY", "CHF", "SGD", "MYR", "DKK", "SAR", "RUB", "QAR", "TRY", "VEF"};
        String[] countries = {
                "NO", "AU", "NL", "US", "NZ", "CA", "IE", "LI", "DE", "SE",
                "CH", "JP", "HK", "IS", "KR", "DK", "IL", "BE", "AT", "FR",
                "SI", "FI", "ES", "IT", "LU", "SG", "CZ", "AE", "GR", "GB",
                "CY", "AD", "BN", "EE", "SK", "MT", "QA", "HU", "PL", "LT",
                "PT", "BH", "LV", "CL", "AR", "HR", "BB", "UY", "PW", "RO",
        };

        List<TradeEntity> tradeData = new ArrayList<>();

        for (int i = 0; i < dataSize; i++) {

            // generate random numbers to get currencies
            Random random = new Random();
            int fci = random.nextInt(currencyFrom.length);
            int tci = random.nextInt(currencyFrom.length);
            if (tci == fci) {
                tci = tci == currencyFrom.length - 1 ? tci - 1 : tci + 1;
            }

            // generate random number to get country
            random = new Random();
            int ci = random.nextInt(countries.length);

            // generate random amounts & rate
            random = new Random();
            int sellAmount = random.nextInt(Integer.MAX_VALUE);
            int buyAmount = random.nextInt(Integer.MAX_VALUE);
            BigDecimal rate = new BigDecimal(Math.random());
            rate = rate.setScale(6, RoundingMode.HALF_UP);

            // generate random 6 digits user id
            random = new Random();
            StringBuilder userId = new StringBuilder(String.valueOf(100000 + random.nextInt(900000)));

            TradeEntity tradeEntity = new TradeEntity();
            tradeEntity.setUserId(userId.toString());
            tradeEntity.setAmountBuy(new BigDecimal(String.valueOf(buyAmount)));
            tradeEntity.setAmountSell(new BigDecimal(String.valueOf(sellAmount)));
            tradeEntity.setRate(rate);
            tradeEntity.setCurrencyFrom(currencyFrom[fci]);
            tradeEntity.setCurrencyTo(currencyFrom[tci]);
            tradeEntity.setOriginatingCountry(countries[ci]);
            tradeEntity.setTimePlaced(new Date());
	        tradeEntity.setCurrencyPair("");

            tradeData.add(tradeEntity);
        }

        return tradeData;
    }

    public static void loginAndSaveJsessionIdCookie(final String baseUrl, final String user, final String password,
                                                    final HttpHeaders httpHeaders) {

        String url = baseUrl + "/login";

        new RestTemplate().execute(url, HttpMethod.POST,
                                   new RequestCallback() {
                                       @Override
                                       public void doWithRequest(ClientHttpRequest request) throws IOException {
                                           MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                                           map.add("username", user);
                                           map.add("password", password);
                                           new FormHttpMessageConverter().write(map, MediaType.APPLICATION_FORM_URLENCODED, request);
                                       }
                                   },

                                   new ResponseExtractor<Object>() {
                                       @Override
                                       public Object extractData(ClientHttpResponse response) throws IOException {
                                           httpHeaders.add("Cookie", response.getHeaders().getFirst("Set-Cookie"));
                                           return null;
                                       }
                                   });
    }

	public static Point getCords(String address) throws Exception {

		GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
		Point point;
		if (results.length > 0) {
			LatLng latLng = results[0].geometry.location;
			point = new Point(latLng.lng, latLng.lat);
		} else {
			point = new Point(0, 0);
		}

//        logger.debug("Point={}", point);
		return point;
	}
}
