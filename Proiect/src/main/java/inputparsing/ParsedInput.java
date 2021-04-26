package inputparsing;

import java.util.Map;

public class ParsedInput {
    Map<String,Integer> countries;
    Map<String,Integer> counties;
    Map<String,Integer> cities;

    @Override
    public String toString() {
        return "ParsedInput{" +
                "countries=" + countries +
                ", counties=" + counties +
                ", cities=" + cities +
                '}';
    }
}
