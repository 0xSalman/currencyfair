package com.lucidspring.currencyfair.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;

/**
 * DB entity that holds information about countries and is mapped to mongodb collection
 */

@Document(collection = "countries")
public class CountryEntity implements Serializable {

	@Id
	private String id;
	private String name;
	private String code;
	private String capital;
	private Point location;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}
}
