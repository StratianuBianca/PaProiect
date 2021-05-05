package inputparsing;

import edu.pa.correct.*;


import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Parsing and initial scoring, return a parsed input with score.
// maybe another class with scoring
// two words per name
public class InputParse {
    private final ParsedInput parsedInput;
    String[] countries = {"Romania", "Bulgaria", "Serbia", "Ungaria"};
    String[] cities = {"Galati", "Iasi", "Tecuci", "Focsani"};
    String[] counties = {"Brasov", "Valcea", "Galati", "Vrancea"};

    String createRegex(String word) {
        StringBuilder regex = new StringBuilder();
        char[] wordArray = word.toCharArray();
        for (char letter : wordArray) {
            regex.append(letter);
            regex.append('+');
        }
        return regex.toString();
    }

    //when there is a match this
    int patternScore(String input, String word, InputType inputType, InputType wordType) {
        int score = (int) (((float) word.length() / input.length())  * 70);
        if (inputType.equals(wordType)) score += 30;
        return score;
    }

    void makeStandard(String input, InputType inputType) {
        String[] inputArray = input.split(" ");
        for (String word : inputArray) {
            for (String country : countries) {
                Pattern pattern = Pattern.compile(createRegex(country), Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(word);
                if (matcher.find()) {
                    parsedInput.countries.put(country, patternScore(word, country, inputType, InputType.COUNTRY));
                }
            }
            for (String city : cities) {
                Pattern pattern = Pattern.compile(createRegex(city), Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(word);
                if (matcher.find()) {
                    parsedInput.cities.put(city, patternScore(word, city, inputType, InputType.CITY));
                }
            }
            for (String county : counties) {
                Pattern pattern = Pattern.compile(createRegex(county), Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(word);
                if (matcher.find()) {
                    parsedInput.counties.put(county, patternScore(word, county, inputType, InputType.COUNTY));
                }
            }
        }
    }
    boolean isValid(String string){
        return string != null && !string.isEmpty() ;
    }

    //check every field and get it to standard, score, and return the matchings
    public InputParse(Address address) {
        parsedInput = new ParsedInput();
        parsedInput.countries = new HashMap<>();
        parsedInput.cities = new HashMap<>();
        parsedInput.counties = new HashMap<>();
        if(isValid(address.getCountry())) makeStandard(address.getCountry(),InputType.COUNTRY);
        if(isValid(address.getCity())) makeStandard(address.getCity(), InputType.CITY);
        if(isValid(address.getCounty())) makeStandard(address.getCounty(),InputType.COUNTY);
        if(isValid(address.getPostalCode())) makeStandard(address.getPostalCode(),InputType.OTHERS);
        if(isValid(address.getStreetAddress())) makeStandard(address.getStreetAddress(), InputType.OTHERS);
        System.out.println(parsedInput);
    }
}

