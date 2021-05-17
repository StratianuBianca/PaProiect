package inputparsing;

import java.util.Map;

//describes the input which will be obtain from user input
public class ParsedInput {
    Map<String,Integer> countries;
    Map<String,Integer> counties;
    Map<String,Integer> cities;

    public Map<String, Integer> getCities() { return cities; }
    public void setCities(Map<String, Integer> cities) { this.cities = cities; }

    public Map<String, Integer> getCounties() { return counties; }
    public void setCounties(Map<String, Integer> counties) { this.counties = counties; }

    public Map<String, Integer> getCountries() { return countries; }
    public void setCountries(Map<String, Integer> countries) { this.countries = countries; }

    public boolean equalParsedInput(ParsedInput parsedInput){
        if(!parsedInput.cities.keySet().equals(cities.keySet())) return false;
        if(!parsedInput.counties.keySet().equals(counties.keySet())) return false;
        return parsedInput.countries.keySet().equals(countries.keySet());
    }
    @Override
    public String toString() {
        return "ParsedInput{" +
                "countries=" + countries +
                ", counties=" + counties +
                ", cities=" + cities +
                '}';
    }
}
