package edu.pa.database.model;

public class AddressEntity {
    String city;
    String county;
    String country;

    public AddressEntity() {}

    public AddressEntity(String city, String county, String country) {
        this.city = city;
        this.county = county;
        this.country = country;
    }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getCounty() { return county; }
    public void setCounty(String county) { this.county = county; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
}
