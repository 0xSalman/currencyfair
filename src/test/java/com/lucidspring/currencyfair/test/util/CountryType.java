
package com.lucidspring.currencyfair.test.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for countryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="countryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="countryCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="countryName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isoNumeric" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isoAlpha3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fipsCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="continent">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="EU"/>
 *               &lt;enumeration value="AS"/>
 *               &lt;enumeration value="NA"/>
 *               &lt;enumeration value="AF"/>
 *               &lt;enumeration value="AN"/>
 *               &lt;enumeration value="SA"/>
 *               &lt;enumeration value="OC"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="continentName">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Europe"/>
 *               &lt;enumeration value="Asia"/>
 *               &lt;enumeration value="North America"/>
 *               &lt;enumeration value="Africa"/>
 *               &lt;enumeration value="Antarctica"/>
 *               &lt;enumeration value="South America"/>
 *               &lt;enumeration value="Oceania"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="capital" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="areaInSqKm" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="population" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="currencyCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="languages" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="geonameId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="west" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="north" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="east" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="south" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="postalCodeFormat" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "countryType", propOrder = {
    "countryCode",
    "countryName",
    "isoNumeric",
    "isoAlpha3",
    "fipsCode",
    "continent",
    "continentName",
    "capital",
    "areaInSqKm",
    "population",
    "currencyCode",
    "languages",
    "geonameId",
    "west",
    "north",
    "east",
    "south",
    "postalCodeFormat"
})
public class CountryType {

    @XmlElement(required = true)
    protected String countryCode;
    @XmlElement(required = true)
    protected String countryName;
    @XmlElement(required = true)
    protected String isoNumeric;
    @XmlElement(required = true)
    protected String isoAlpha3;
    @XmlElement(required = true)
    protected String fipsCode;
    @XmlElement(required = true)
    protected String continent;
    @XmlElement(required = true)
    protected String continentName;
    @XmlElement(required = true)
    protected String capital;
    @XmlElement(required = true)
    protected String areaInSqKm;
    @XmlElement(required = true)
    protected String population;
    @XmlElement(required = true)
    protected String currencyCode;
    @XmlElement(required = true)
    protected String languages;
    @XmlElement(required = true)
    protected String geonameId;
    @XmlElement(required = true)
    protected String west;
    @XmlElement(required = true)
    protected String north;
    @XmlElement(required = true)
    protected String east;
    @XmlElement(required = true)
    protected String south;
    @XmlElement(required = true)
    protected String postalCodeFormat;

    /**
     * Gets the value of the countryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the value of the countryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryCode(String value) {
        this.countryCode = value;
    }

    /**
     * Gets the value of the countryName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the value of the countryName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryName(String value) {
        this.countryName = value;
    }

    /**
     * Gets the value of the isoNumeric property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsoNumeric() {
        return isoNumeric;
    }

    /**
     * Sets the value of the isoNumeric property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsoNumeric(String value) {
        this.isoNumeric = value;
    }

    /**
     * Gets the value of the isoAlpha3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsoAlpha3() {
        return isoAlpha3;
    }

    /**
     * Sets the value of the isoAlpha3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsoAlpha3(String value) {
        this.isoAlpha3 = value;
    }

    /**
     * Gets the value of the fipsCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFipsCode() {
        return fipsCode;
    }

    /**
     * Sets the value of the fipsCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFipsCode(String value) {
        this.fipsCode = value;
    }

    /**
     * Gets the value of the continent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContinent() {
        return continent;
    }

    /**
     * Sets the value of the continent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContinent(String value) {
        this.continent = value;
    }

    /**
     * Gets the value of the continentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContinentName() {
        return continentName;
    }

    /**
     * Sets the value of the continentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContinentName(String value) {
        this.continentName = value;
    }

    /**
     * Gets the value of the capital property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapital() {
        return capital;
    }

    /**
     * Sets the value of the capital property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapital(String value) {
        this.capital = value;
    }

    /**
     * Gets the value of the areaInSqKm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaInSqKm() {
        return areaInSqKm;
    }

    /**
     * Sets the value of the areaInSqKm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaInSqKm(String value) {
        this.areaInSqKm = value;
    }

    /**
     * Gets the value of the population property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPopulation() {
        return population;
    }

    /**
     * Sets the value of the population property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPopulation(String value) {
        this.population = value;
    }

    /**
     * Gets the value of the currencyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the value of the currencyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrencyCode(String value) {
        this.currencyCode = value;
    }

    /**
     * Gets the value of the languages property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguages() {
        return languages;
    }

    /**
     * Sets the value of the languages property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguages(String value) {
        this.languages = value;
    }

    /**
     * Gets the value of the geonameId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeonameId() {
        return geonameId;
    }

    /**
     * Sets the value of the geonameId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeonameId(String value) {
        this.geonameId = value;
    }

    /**
     * Gets the value of the west property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWest() {
        return west;
    }

    /**
     * Sets the value of the west property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWest(String value) {
        this.west = value;
    }

    /**
     * Gets the value of the north property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNorth() {
        return north;
    }

    /**
     * Sets the value of the north property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNorth(String value) {
        this.north = value;
    }

    /**
     * Gets the value of the east property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEast() {
        return east;
    }

    /**
     * Sets the value of the east property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEast(String value) {
        this.east = value;
    }

    /**
     * Gets the value of the south property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSouth() {
        return south;
    }

    /**
     * Sets the value of the south property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSouth(String value) {
        this.south = value;
    }

    /**
     * Gets the value of the postalCodeFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCodeFormat() {
        return postalCodeFormat;
    }

    /**
     * Sets the value of the postalCodeFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCodeFormat(String value) {
        this.postalCodeFormat = value;
    }

}
