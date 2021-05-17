package inputparsing;

import edu.pa.correct.Address;
import edu.pa.database.repository.AddressRepository;
import edu.pa.database.repository.CityRepository;
import edu.pa.database.repository.CountryRepository;
import edu.pa.database.repository.CountyRepository;

import java.text.Normalizer;
import java.util.*;
import java.util.stream.Collectors;

//Parsing and initial scoring, return a parsed input with score.
public class InputParse {
    private final CityRepository cityRepository = new CityRepository();
    private final CountryRepository countryRepository = new CountryRepository();
    private final CountyRepository countyRepository = new CountyRepository();
    private final AddressRepository addressRepository = new AddressRepository();

    private final ParsedInput parsedInput;

    //when there is a match
    int patternScore(InputType inputType, InputType wordType) {
        if (inputType.equals(wordType)) return 20;
        return 0;
    }

    static String capitalLetters (String word){
        String newWord;
        newWord = Arrays.stream(word.split("-"))
                .map(wordUp -> wordUp.substring(0, 1).toUpperCase(Locale.ROOT) + wordUp.substring(1).toLowerCase(Locale.ROOT)+ "-")
                .collect(Collectors.joining());
        newWord = newWord.substring(0, newWord.length() - 1);
        return newWord;
    }

    //eliminate words which contains a dot, numbers, replace diacritics, eliminate comma, make every word lowercase, only the first one is uppercase
    static List<String> makeStandard(String input) {
        return Arrays.stream(input.split(" "))
                .map(word -> word.replaceAll(",", ""))
                .map(InputParse::capitalLetters)
                .map(word -> Normalizer.normalize(word, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""))
                .filter(word -> !word.contains("."))
                .filter(word -> !word.matches(".*[0-9].*"))
                .collect(Collectors.toList());
    }

    boolean isValid(String string) {
        return string != null && !string.isEmpty();
    }


    //checks if a list of tokens are actual locations from database
    List<String> checkExistence(List<String> tokens, InputType inputType) {
        List<String> possibleNewTokens = new ArrayList<>();
        if (tokens != null)
            for (String token : tokens) {
                boolean isAdded = false;
                if (cityRepository.findByName(token) != null) {
                    if(!parsedInput.cities.containsKey(token))
                               parsedInput.cities.put(token, patternScore(inputType, InputType.CITY));
                    isAdded = true;
                }
                if (countryRepository.findByName(token) != null) {
                    if(!parsedInput.countries.containsKey(token))
                        parsedInput.countries.put(token, patternScore(inputType, InputType.COUNTRY));
                    isAdded = true;
                }
                if (countyRepository.findByName(token) != null) {
                    if(!parsedInput.counties.containsKey(token))
                        parsedInput.counties.put(token, patternScore(inputType, InputType.COUNTY));
                    isAdded = true;
                }
                if (!isAdded) possibleNewTokens.add(token);
            }

        return possibleNewTokens;
    }

    //some locations have names formed of 2 ore more words
    void possibleNewTokens(List<String> tokens) {
        List<String> possibleNewTokens = new ArrayList<>();
        if (tokens != null) {
            List<List<String>> allNames = new ArrayList<>();
            for (String token : tokens) {
                List<String> names = addressRepository.nameLike(token);
                if (!names.isEmpty()) {
                    if (!allNames.isEmpty()) {
                        for (List<String> existentList : allNames) {
                            String commonLocation = commonString(existentList, names);
                            if (commonLocation != null) possibleNewTokens.add(commonLocation);
                        }
                    }
                    allNames.add(names);
                }
            }
        }
        checkExistence(possibleNewTokens, InputType.OTHERS);
    }

    String commonString(List<String> firstList, List<String> secondList) {
        for (String token : firstList)
            if (secondList.contains(token)) return token;
        return null;
    }

    //check every field and get it to standard, score, and return the matches
    public InputParse(Address address) {
        parsedInput = new ParsedInput();
        parsedInput.countries = new HashMap<>();
        parsedInput.cities = new HashMap<>();
        parsedInput.counties = new HashMap<>();
        if (isValid(address.getCountry())) possibleNewTokens(checkExistence(makeStandard(address.getCountry()), InputType.COUNTRY));
        if (isValid(address.getCity())) possibleNewTokens(checkExistence(makeStandard(address.getCity()), InputType.CITY));
        if (isValid(address.getCounty())) possibleNewTokens(checkExistence(makeStandard(address.getCounty()), InputType.COUNTY));
        if (isValid(address.getPostalCode())) possibleNewTokens(checkExistence(makeStandard(address.getPostalCode()), InputType.OTHERS));
        if (isValid(address.getStreetAddress())) possibleNewTokens(checkExistence(makeStandard(address.getStreetAddress()), InputType.OTHERS));

    }

    public ParsedInput getParsedInput() {
        return parsedInput;
    }
}

