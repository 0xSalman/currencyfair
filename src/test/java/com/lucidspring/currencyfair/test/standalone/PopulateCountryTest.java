package com.lucidspring.currencyfair.test.standalone;

import com.lucidspring.currencyfair.Application;
import com.lucidspring.currencyfair.entity.CountryEntity;
import com.lucidspring.currencyfair.service.CountryRepository;
import com.lucidspring.currencyfair.test.util.Common;
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
			Point point = Common.getCords(address);
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
}
