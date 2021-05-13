package edu.pa.database.initialization;

import edu.pa.database.model.City;
import edu.pa.database.model.Country;
import edu.pa.database.model.County;
import edu.pa.database.repository.CityRepository;
import edu.pa.database.repository.CountryRepository;
import edu.pa.database.repository.CountyRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class DatabaseInitialization {

    static public void databaseInitialization() {
        CountryRepository countryRepository = new CountryRepository();
        CountyRepository countyRepository = new CountyRepository();
        CityRepository cityRepository = new CityRepository();

        try{
            FileReader reader = new FileReader("src/main/java/edu/pa/database/initialization/RomaniaFinal.json");

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            countryRepository.save(new Country(Integer.parseInt( (String) jsonObject.get("id")),(String) jsonObject.get("nume")));
            JSONArray array = (JSONArray) jsonObject.get("judete");
            for (Object county: array){
                JSONObject countyObj = (JSONObject) county;
                countyRepository.save(new County(Integer.parseInt( (String) countyObj.get("id")), (String) countyObj.get("nume"), (String) jsonObject.get("nume"), (String) countyObj.get("resedinta")));
                JSONArray cityArray = (JSONArray) countyObj.get("localitati");
                for(Object city: cityArray){
                    JSONObject cityObj = (JSONObject) city;
                    cityRepository.save(new City( Integer.parseInt( (String) cityObj.get("id")), (String) cityObj.get("nume"), (String) countyObj.get("nume")));
                }
            }
        } catch (FileNotFoundException | ParseException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }
}
