package edu.pa.correct;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Component
@SessionScope
public class Address {
    private String country;
    private String county;
    private String city;
    private String postalCode;
    private String streetAddress;
    private String correctAddress;

    public Address() { }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getCounty() {
        return county;
    }

    public String getCountry() {
        return country;
    }

    public void setCorrectAddress(String correctAddress) {
        this.correctAddress = correctAddress;
    }

    public String getCorrectAddress() {
        return correctAddress;
    }

    public boolean equalAddress(Address address){
        if(!address.getCountry().equals(this.getCountry())) return false;
        if(!address.getCounty().equals(this.getCounty())) return false;
        return address.getCity().equals(this.getCity());

    }

}

