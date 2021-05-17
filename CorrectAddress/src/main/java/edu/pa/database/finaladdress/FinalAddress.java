package edu.pa.database.finaladdress;

import edu.pa.correct.Address;
import edu.pa.database.model.AddressEntity;
import edu.pa.database.model.City;
import edu.pa.database.model.County;
import edu.pa.database.repository.AddressRepository;
import edu.pa.database.repository.CityRepository;
import edu.pa.database.repository.CountryRepository;
import edu.pa.database.repository.CountyRepository;
import inputparsing.InputParse;
import inputparsing.ParsedInput;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class FinalAddress {
    CityRepository cityRepository = new CityRepository();
    CountyRepository countyRepository = new CountyRepository();
    CountryRepository countryRepository = new CountryRepository();
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
        if (parsedInput.getCountries().isEmpty() && parsedInput.getCities().isEmpty() && parsedInput.getCounties().isEmpty()) {
            Map<String, Integer> map = new HashMap<>();
            map.put("Romania", 20);
            parsedInput.setCountries(map);
            Map<String, Integer> map2 = new HashMap<>();
            map2.put("Bucuresti", 20);
            parsedInput.setCounties(map2);
            Map<String, Integer> map1 = new HashMap<>();
            map1.put("Sector 1", 20);
            parsedInput.setCities(map1);
        } else {
            if (parsedInput.getCountries().isEmpty()) {
                Map<String, Integer> map = new HashMap<>();
                String country = "";
                if (!parsedInput.getCounties().isEmpty()) {
                    for (Map.Entry<String, Integer> county : parsedInput.getCounties().entrySet()) {
                        country = countyRepository.getCountry(county.getKey());
                    }
                } else if (!parsedInput.getCities().isEmpty()) {
                    for (Map.Entry<String, Integer> city : parsedInput.getCities().entrySet()) {
                        country = cityRepository.getCountry(city.getKey());
                    }
                }
                map.put(country, 10);
                parsedInput.setCountries(map);
            }
            if (parsedInput.getCounties().isEmpty()) {
                Map<String, Integer> map = new HashMap<>();
                String county = "";
                if (!parsedInput.getCities().isEmpty()) {
                    for (Map.Entry<String, Integer> city : parsedInput.getCities().entrySet()) {
                        City city1 = cityRepository.findByName(city.getKey());
                        county = city1.getCounty();
                    }
                } else if (!parsedInput.getCountries().isEmpty()) {
                    for (Map.Entry<String, Integer> country : parsedInput.getCountries().entrySet()) {
                        county = countryRepository.getCapital(country.getKey());
                    }
                }
                map.put(county, 10);
                parsedInput.setCounties(map);
            }
            if (parsedInput.getCities().isEmpty()) {
                Map<String, Integer> map = new HashMap<>();
                String city = "";
                if (!parsedInput.getCounties().isEmpty()) {
                    for (Map.Entry<String, Integer> county : parsedInput.getCounties().entrySet()) {
                        County county1 = countyRepository.findByName(county.getKey());
                        System.out.println(county1.getName());
                        city = county1.getCountyCapital();
                    }
                } else if (!parsedInput.getCountries().isEmpty()) {
                    for (Map.Entry<String, Integer> country : parsedInput.getCountries().entrySet()) {
                        city = countryRepository.getCapital(country.getKey());
                    }
                }
                map.put(city, 10);
                parsedInput.setCities(map);
            }
        }
        System.out.println("Checked input: " + parsedInput);
        return parsedInput;
    }

    private Address bestAddress(ParsedInput parsedInput) {

        Address address = new Address();
        checkCountryConnections(parsedInput);

        System.out.println("Map of addresses:" + addressIntegerMap);
        boolean set70 = false;
        boolean set100 = false;
        if (addressIntegerMap.containsValue(70)) set70 = true;
        if (addressIntegerMap.containsValue(100)) set100 = true;
        for (Map.Entry<Address, Integer> addressIntegerEntry : addressIntegerMap.entrySet()) {
            if (addressIntegerEntry.getValue().equals(100)) return addressIntegerEntry.getKey();
            if (!set100 && set70 && addressIntegerEntry.getValue().equals(70)) return addressIntegerEntry.getKey();
            if (!set70 && !set100) return addressIntegerEntry.getKey();
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

    void checkConnections(String country, String county, String city) {
        Address address = new Address();
        address.setCountry(country);
        address.setCounty(county);
        address.setCity(city);
        System.out.println("Address to be tested: " + address);
        AddressEntity addressEntity = new AddressEntity(city, county, country);
        if (addressRepository.fullAddress(addressEntity)) {
            addressIntegerMap.put(address, 100);
            return;
        }

        List<String> counties = addressRepository.cityToCountry(addressEntity);
        if (addressRepository.countyToCountry(addressEntity)) {
            if (!counties.isEmpty()) {
                if (!counties.contains(county)) {
                    address.setCounty(counties.get(0));
                    addressIntegerMap.put(address, 70);
                }
            } else {
                County countyCapital = countyRepository.findByName(county);
                address.setCity(countyCapital.getCountyCapital());
                addressIntegerMap.put(address, 70);
            }
        } else if (!counties.isEmpty()) {
            address.setCounty(counties.get(0));
            addressIntegerMap.put(address, 70);
        } else {
            address.setCountry(countyRepository.getCountry(address.getCounty()));
            addressIntegerMap.put(address, 40);
        }

    }

}
