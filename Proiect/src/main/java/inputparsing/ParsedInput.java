package inputparsing;

import java.util.Map;

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

    @Override
    public String toString() {
        return "ParsedInput{" +
                "countries=" + countries +
                ", counties=" + counties +
                ", cities=" + cities +
                '}';
    }
}
