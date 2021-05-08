package edu.pa.database.finaladdress;

import edu.pa.correct.Address;
import edu.pa.database.model.AddressEntity;
import edu.pa.database.model.City;
import edu.pa.database.repository.AddressRepository;
import edu.pa.database.repository.CityRepository;
import edu.pa.database.repository.CountyRepository;
import inputparsing.InputParse;
import inputparsing.ParsedInput;

import java.util.HashMap;
import java.util.Map;

public class FinalAddress {
    CityRepository cityRepository = new CityRepository();
    CountyRepository countyRepository = new CountyRepository();
    AddressRepository addressRepository = new AddressRepository();

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
     *                in ideea in care e mai putin probabil sa gresesti localitatea decat judetul)   */


    Address checkCountryConnections(ParsedInput parsedInput) {
        Address address = new Address();
        for (Map.Entry<String, Integer> country : parsedInput.getCountries().entrySet()) {
            boolean countyValue = parsedInput.getCounties().containsValue(20);
            address = checkCountyConnection(countyValue, parsedInput, country.getKey());
        }
        return address;
    }

   private Address checkCountyConnection (boolean countyValue, ParsedInput parsedInput, String country){
       boolean cityValue = parsedInput.getCities().containsValue(20);
       if(countyValue) {
           for (Map.Entry<String, Integer> county : parsedInput.getCounties().entrySet()) {
               if(county.getValue()==20) return checkCityConnections(cityValue, parsedInput, country, county.getKey());
           }
       }
       else
           for (Map.Entry<String, Integer> county : parsedInput.getCounties().entrySet())
                return checkCityConnections(cityValue, parsedInput, country, county.getKey());
           return null;
    }

    private Address checkCityConnections(boolean cityValue, ParsedInput parsedInput, String country, String county) {
        if (cityValue) {
            for (Map.Entry<String, Integer> city : parsedInput.getCities().entrySet()) {
                if (city.getValue() == 20) return checkConnections(country, county, city.getKey());
            }
        } else
            for (Map.Entry<String, Integer> city : parsedInput.getCities().entrySet()) {
                return checkConnections(country, county, city.getKey());
            }

            return null;
    }

    Address checkConnections(String country, String county, String city){
    Address address = new Address();
    if (addressRepository.fullAddress(new AddressEntity(country, county, city))) {
        address.setCountry(country);
        address.setCounty(county);
        address.setCity(city);
        }
        return address;
    }

    public Address correctAddress(Address address) {
        InputParse inputParse = new InputParse(address);
        ParsedInput parsedInput = checkParsedInput(inputParse.getParsedInput());
        return checkCountryConnections(parsedInput);
    }

    ParsedInput checkParsedInput(ParsedInput parsedInput) {
        Map<String, Integer> map = new HashMap<>();
        if (parsedInput.getCountries().isEmpty()) {
            map.put("Romania", 20);
            parsedInput.setCountries(map);
        }
        if (parsedInput.getCities().isEmpty() && parsedInput.getCounties().isEmpty()) {
            map.put("Bucuresti", 20);
            parsedInput.setCities(map);
            parsedInput.setCounties(map);
        } else if (parsedInput.getCounties().isEmpty()) {
            for (Map.Entry<String, Integer> city : parsedInput.getCities().entrySet()) {
                City cityEntry = cityRepository.findByName(city.getKey());
                map.put(cityEntry.getCounty(), city.getValue());
            }
            parsedInput.setCounties(map);
        } else {
            for (Map.Entry<String, Integer> county : parsedInput.getCounties().entrySet()) {
                City countyCapital = countyRepository.getCountyCapital(county.getKey());
                map.put(countyCapital.getName(), county.getValue());
            }
            parsedInput.setCities(map);
        }

        return parsedInput;
    }
}
