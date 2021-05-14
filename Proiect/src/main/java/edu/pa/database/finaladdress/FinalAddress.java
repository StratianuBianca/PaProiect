package edu.pa.database.finaladdress;

import edu.pa.correct.Address;
import edu.pa.database.model.AddressEntity;
import edu.pa.database.model.City;
import edu.pa.database.model.County;
import edu.pa.database.repository.AddressRepository;
import edu.pa.database.repository.CityRepository;
import edu.pa.database.repository.CountyRepository;
import inputparsing.InputParse;
import inputparsing.ParsedInput;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class FinalAddress {
    CityRepository cityRepository = new CityRepository();
    CountyRepository countyRepository = new CountyRepository();
    AddressRepository addressRepository = new AddressRepository();

    Map<Address, Integer> addressIntegerMap = new HashMap<>();

    public Address correctAddress(Address address) {
        System.out.println("Addresa primita: " + address);
        InputParse inputParse = new InputParse(address);
        ParsedInput parsedInput = checkParsedInput(inputParse.getParsedInput());

        return bestAddress(parsedInput);
    }

    //fill empty places of input based on existing locations
    ParsedInput checkParsedInput(ParsedInput parsedInput) {
        System.out.println("Parsed input to be checked: " + parsedInput);
        if (parsedInput.getCountries().isEmpty()) {
            Map<String, Integer> map = new HashMap<>();
            map.put("Romania", 20);
            parsedInput.setCountries(map);
            System.out.println("empty country");
        }
        if (parsedInput.getCities().isEmpty() && parsedInput.getCounties().isEmpty()) {
            Map<String, Integer> map = new HashMap<>();
            map.put("Bucuresti", 20);
            parsedInput.setCounties(map);
            Map<String, Integer> map1 = new HashMap<>();
            map1.put("Sector 1",20);
            parsedInput.setCities(map1);
            System.out.println("empty county and city");
        } else if (parsedInput.getCounties().isEmpty()) {
            Map<String, Integer> map = new HashMap<>();
            for (Map.Entry<String, Integer> city : parsedInput.getCities().entrySet()) {
                City cityEntry = cityRepository.findByName(city.getKey());
                map.put(cityEntry.getCounty(), city.getValue());
            }
            System.out.println("empty county");
            parsedInput.setCounties(map);
        } else if(parsedInput.getCities().isEmpty()) {
            Map<String, Integer> map = new HashMap<>();
            for (Map.Entry<String, Integer> county : parsedInput.getCounties().entrySet()) {
                County countyRe = countyRepository.findByName(county.getKey());
                map.put(countyRe.getCountyCapital(), county.getValue());
            }
            System.out.println("empty city");
            parsedInput.setCities(map);
        }

        System.out.println("Checked input: " + parsedInput);
        return parsedInput;
    }

    //get input, parse it, make test, return final address based on a score
    /*
     *  Verific inputul pentru lipsuri
     *  nu exista tara -> pune automat Romania
     *  nu exista niciun judet -> sunt folosite judetele localitatilor cu cel mai mare scor
     *  nu exista niciun oras -> resedinta judetului -> schimbare structura City + bool resedinta/nu
     *  nu exista nici oras si nici judet -> Bucuresti -> Bucuresti
     */


    /*  Optiunile cu cele mai mari scoruri, combinarile dintre ele, fiecare trece prin
     * --e corecta cu totul -> returnez adresa, mesaj a fost corecta
     * la fiecare schimbare adaug in adresa, daca nu e corecta din prima, pot sa mai incerc si celalte combinatii
     * -- legatura tara - judet
     *            ->  nu exista => legatura tara - localitate -> se corecteaza luand judetul de care apartine localitatea
     *               (caz putin probabil, ar insemna ca judetul nu e judet, deci ar fi gol in parsedInput de judet, dar care e completat deja)
     *            ->  exista => verific legatura tara - localitate (ma asigur ca exista localitatea)
     *                                    => exista : inlocuiesc judetul
     *                                    => nu exista: pun resedinta de judet
     *              ( inseamna ca legatura judet-localitate nu exista, este inlocuit judetul cu judetul localitatii,
     *                in ideea in care e mai putin probabil sa gresesti localitatea decat judetul)
     *
     * Map adrese -> scor
     * */


    private Address bestAddress(ParsedInput parsedInput) {

        Address address = new Address();
        checkCountryConnections(parsedInput);

        System.out.println("Map of addresses:" + addressIntegerMap);
        boolean set70 = false;
        boolean set100 = false;
         if (addressIntegerMap.containsValue(70))  set70 = true;
        if (addressIntegerMap.containsValue(100))  set100 = true;
        for (Map.Entry<Address, Integer> addressIntegerEntry: addressIntegerMap.entrySet()) {
              if(addressIntegerEntry.getValue().equals(100))    return addressIntegerEntry.getKey();
              if(!set100 && set70 && addressIntegerEntry.getValue().equals(70)) return addressIntegerEntry.getKey();
              if(!set70 && !set100) return addressIntegerEntry.getKey();
        }

        return address;
    }

    private void checkCountryConnections(ParsedInput parsedInput) {
        for (Map.Entry<String, Integer> country : parsedInput.getCountries().entrySet()) {
            boolean countyValue = parsedInput.getCounties().containsValue(20);
            checkCountyConnection(countyValue, parsedInput, country.getKey());
        }
    }

    private void checkCountyConnection(boolean countyValue, ParsedInput parsedInput, String country) {
        boolean cityValue = parsedInput.getCities().containsValue(20);
        if (countyValue) {
            for (Map.Entry<String, Integer> county : parsedInput.getCounties().entrySet()) {
                if (county.getValue() == 20) checkCityConnections(cityValue, parsedInput, country, county.getKey());
            }
        } else
            for (Map.Entry<String, Integer> county : parsedInput.getCounties().entrySet())
                checkCityConnections(cityValue, parsedInput, country, county.getKey());
    }

    private void checkCityConnections(boolean cityValue, ParsedInput parsedInput, String country, String county) {
        if (cityValue) {
            for (Map.Entry<String, Integer> city : parsedInput.getCities().entrySet()) {
                if (city.getValue() == 20) checkConnections(country, county, city.getKey());
            }
        } else
            for (Map.Entry<String, Integer> city : parsedInput.getCities().entrySet()) {
                checkConnections(country, county, city.getKey());
            }
    }

    /* -- legatura tara - judet
     *            ->  nu exista => legatura tara - localitate -> se corecteaza luand judetul de care apartine localitatea
     *               (caz putin probabil, ar insemna ca judetul nu e judet, deci ar fi gol in parsedInput de judet, dar care e completat deja)
     *            ->  exista => verific legatura tara - localitate (ma asigur ca exista localitatea)
     *                                    => exista : inlocuiesc judetul - daca nu e acelasi
     *                                    => nu exista: pun resedinta de judet
     *              ( inseamna ca legatura judet-localitate nu exista, este inlocuit judetul cu judetul localitatii,
     *                in ideea in care e mai putin probabil sa gresesti localitatea decat judetul)
     */

    void checkConnections(String country, String county, String city) {
        Address address = new Address();
        address.setCountry(country);
        address.setCounty(county);
        address.setCity(city);
        System.out.println("Address to be tested: " +address);
        AddressEntity addressEntity = new AddressEntity(city, county, country);
        if (addressRepository.fullAddress(addressEntity)) {
            addressIntegerMap.put(address, 100);
            return;
        }

        List<String> counties = addressRepository.cityToCountry(addressEntity);
        if (addressRepository.countyToCountry(addressEntity)) {
            if (!counties.isEmpty()){
                if (!counties.contains(county)) {
                    address.setCounty(counties.get(0));
                    addressIntegerMap.put(address, 70);
                } }else {
                    County countyCapital = countyRepository.findByName(county);
                    address.setCity(countyCapital.getCountyCapital());
                    addressIntegerMap.put(address, 70);
                }
        } else if (!counties.isEmpty()) {
            address.setCounty(counties.get(0));
            addressIntegerMap.put(address, 70);
        } else if(counties.isEmpty() && city.isEmpty()) {
            address.setCounty("Bucuresti");
            address.setCity("Bucuresti");
            addressIntegerMap.put(address, 40);
        }

    }

}
