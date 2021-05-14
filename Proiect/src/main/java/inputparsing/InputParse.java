package inputparsing;

import edu.pa.correct.*;
import edu.pa.database.repository.AddressRepository;
import edu.pa.database.repository.CityRepository;
import edu.pa.database.repository.CountryRepository;
import edu.pa.database.repository.CountyRepository;


import java.text.Normalizer;
import java.util.*;
import java.util.stream.Collectors;

//Parsing and initial scoring, return a parsed input with score.
// maybe another class with scoring
// two words per name
public class InputParse {
    private final CityRepository cityRepository = new CityRepository();
    private final CountryRepository countryRepository = new CountryRepository();
    private final CountyRepository countyRepository = new CountyRepository();
    private final AddressRepository addressRepository = new AddressRepository();

    private final ParsedInput parsedInput;

    //when there is a match this
    int patternScore(InputType inputType, InputType wordType) {
        if (inputType.equals(wordType)) return 20;
        return 0;
    }

    //eliminate words which contains a dot, numbers, replace diacritics, eliminate comma
    static List<String> makeStandard(String input) {
        return Arrays.stream(input.split(" "))
                .map(word -> word.replaceAll(",", ""))
                .map(word -> word.substring(0, 1).toUpperCase(Locale.ROOT) + word.substring(1).toLowerCase(Locale.ROOT))
                .map(word -> Normalizer.normalize(word, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""))
                .filter(word -> !word.contains("."))
                .filter(word -> !word.matches(".*[0-9].*"))
                .collect(Collectors.toList());
    }

    boolean isValid(String string) {
        return string != null && !string.isEmpty();
    }


    List<String> checkExistence(List<String> tokens, InputType inputType) {
        List<String> possibleNewTokens = new ArrayList<>();
        if (tokens != null)
            for (String token : tokens) {
                boolean isAdded = false;
                if (cityRepository.findByName(token) != null) {
                    parsedInput.cities.put(token, patternScore(inputType, InputType.CITY));
                    isAdded = true;
                }
                if (countryRepository.findByName(token) != null) {
                    parsedInput.countries.put(token, patternScore(inputType, InputType.COUNTRY));
                    isAdded = true;
                }
                if (countyRepository.findByName(token) != null) {
                    parsedInput.counties.put(token, patternScore(inputType, InputType.COUNTY));
                    isAdded = true;
                }
                if (!isAdded) possibleNewTokens.add(token);
            }

        return possibleNewTokens;
    }


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

    //check every field and get it to standard, score, and return the matchings
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

