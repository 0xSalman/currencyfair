package com.lucidspring.currencyfair.test.standalone;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.lucidspring.currencyfair.entity.CountryEntity;
import com.lucidspring.currencyfair.Application;
import com.lucidspring.currencyfair.service.CountryRepository;
import com.lucidspring.currencyfair.test.util.CountryType;
import com.lucidspring.currencyfair.test.util.GeonamesType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class PopulateCountryTest {

	private static final String GOOGLE_API_KEY = "AIzaSyC8AAkfoIlqIQEJZXkujCUlO-kvuHkx6OU";
	private static GeoApiContext context = new GeoApiContext().setApiKey(GOOGLE_API_KEY);

	@Autowired
	private CountryRepository countryRepository;

	@Test
	public void addCountries() throws Exception {

		File file = new File("countries.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(GeonamesType.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		GeonamesType countries = (GeonamesType) jaxbUnmarshaller.unmarshal(file);

		List<CountryEntity> countryEntities = new ArrayList<>();

		for(CountryType country: countries.getCountry()) {
			String address =  country.getCapital() + ", " + country.getCountryName();
			Point point = getCords(address);
//			System.out.println(address + ": cords(" + point.getX() + ", " + point.getY() + ")");
			CountryEntity countryEntity = new CountryEntity();
			countryEntity.setName(country.getCountryName());
			countryEntity.setCode(country.getCountryCode());
			countryEntity.setCapital(country.getCapital());
			countryEntity.setLocation(point);
			countryEntities.add(countryEntity);
		}

		countryRepository.save(countryEntities);
	}

	private Point getCords(String address) throws Exception {

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
