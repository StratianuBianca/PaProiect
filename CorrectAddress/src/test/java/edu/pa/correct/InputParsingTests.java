package edu.pa.correct;

import inputparsing.InputParse;
import inputparsing.ParsedInput;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class InputParsingTests {

    //diacritics
    @Test
    void inputParseTest1 (){
        Address address = new Address();
        address.setCountry("România");
        address.setCounty("Mehedinți");
        address.setCity("Sfântu Gheorghe");

        ParsedInput parsedInput = new ParsedInput();
        Map<String,Integer> cityMap = new HashMap<>();
        cityMap.put("Sfantu Gheorghe",0);
        parsedInput.setCities(cityMap);

        Map<String,Integer> countyMap = new HashMap<>();
        countyMap.put("Mehedinti",0);
        parsedInput.setCounties(countyMap);

        Map<String,Integer> countryMap = new HashMap<>();
        countryMap.put("Romania",0);
        parsedInput.setCountries(countryMap);

        InputParse inputParse = new InputParse(address);
        assertTrue(inputParse.getParsedInput().equalParsedInput(parsedInput));
    }


    //Word format: only the first letter uppercase
    @Test
    void inputParseTest2 (){

        Address address = new Address();
        address.setCountry("ROMANIA");
        address.setCounty("AuveRgNe-RhoNe-ALpes");
        address.setCity("TeCuCi");

        ParsedInput parsedInput = new ParsedInput();
        Map<String,Integer> cityMap = new HashMap<>();
        cityMap.put("Tecuci",0);
        parsedInput.setCities(cityMap);


        Map<String,Integer> countyMap = new HashMap<>();
        countyMap.put("Auvergne-Rhone-Alpes",0);
        parsedInput.setCounties(countyMap);

        Map<String,Integer> countryMap = new HashMap<>();
        countryMap.put("Romania",0);
        parsedInput.setCountries(countryMap);

        InputParse inputParse = new InputParse(address);
        assertTrue(inputParse.getParsedInput().equalParsedInput(parsedInput));
    }

    //remove irrelevant words from input
    @Test
    void inputParseTest3 (){
        Address address = new Address();
        address.setCountry("tara Romania");
        address.setCounty("jud. Galati");
        address.setCity("mun. Tecuci");
        address.setPostalCode("805300");
        address.setStreetAddress("Str. Lalelelor, nr. 159");

        ParsedInput parsedInput = new ParsedInput();
        Map<String,Integer> cityMap = new HashMap<>();
        cityMap.put("Galati",0);
        cityMap.put("Tecuci",0);
        parsedInput.setCities(cityMap);


        Map<String,Integer> countyMap = new HashMap<>();
        countyMap.put("Galati",0);
        parsedInput.setCounties(countyMap);

        Map<String,Integer> countryMap = new HashMap<>();
        countryMap.put("Romania",0);
        parsedInput.setCountries(countryMap);

        InputParse inputParse = new InputParse(address);
        System.out.println(inputParse.getParsedInput());
        assertTrue(inputParse.getParsedInput().equalParsedInput(parsedInput));

    }

   //get input from only one field
    @Test
    void inputParseTest4 (){

        Address address = new Address();
        address.setStreetAddress("Romania, jud. Galati, mun. Tecuci, str. Gh. Petrascu, nr. 32, bl. A3, sc 5, ap. 47");


        ParsedInput parsedInput = new ParsedInput();
        Map<String,Integer> cityMap = new HashMap<>();
        cityMap.put("Galati",0);
        cityMap.put("Tecuci",0);
        parsedInput.setCities(cityMap);


        Map<String,Integer> countyMap = new HashMap<>();
        countyMap.put("Galati",0);
        parsedInput.setCounties(countyMap);

        Map<String,Integer> countryMap = new HashMap<>();
        countryMap.put("Romania",0);
        parsedInput.setCountries(countryMap);

        InputParse inputParse = new InputParse(address);
        System.out.println(inputParse.getParsedInput());
        assertTrue(inputParse.getParsedInput().equalParsedInput(parsedInput));
    }




}
