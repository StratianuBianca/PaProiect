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
            FileReader reader = new FileReader("src/main/java/edu/pa/database/initialization/Romania.json");

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            countryRepository.save(new Country((int) jsonObject.get("id"),(String) jsonObject.get("name")));
            JSONArray array = (JSONArray) jsonObject.get("states");
            for (Object county: array){
                JSONObject countyObj = (JSONObject) county;
                countyRepository.save(new County((int) countyObj.get("id"), (String) countyObj.get("name"), (String) jsonObject.get("name")));
                JSONArray cityArray = (JSONArray) countyObj.get("cities");
                for(Object city: cityArray){
                    JSONObject cityObj = (JSONObject) city;
                    cityRepository.save(new City((int) cityObj.get("id"), (String) cityObj.get("name"), (String) countyObj.get("name")));
                }
            }
        } catch (FileNotFoundException | ParseException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }
}
