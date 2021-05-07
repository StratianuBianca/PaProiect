package edu.pa.database.model;

public class AddressEntity {
    City city;
    County county;
    Country country;

    public AddressEntity() {}

    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }

    public County getCounty() { return county; }
    public void setCounty(County county) { this.county = county; }

    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }
}
