package edu.pa.database.initialization;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;

public class FileStandardizer {

    public static void fileStandardizer (String path, String pathStandard){
        try{
            FileWriter fileWriter = new FileWriter(pathStandard);
            FileReader reader = new FileReader(path);
            int id = 39684;
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            fileWriter.write("{\n" +
                    "  \"nume\": \""+ jsonObject.get("name") +"\",\n" +
                    "  \"id\": \""+ id +"\",\n" +
                    "  \"judete\": [");
            id++;
            JSONArray array = (JSONArray) jsonObject.get("states");
            for (Object county: array){
                JSONObject countyObj = (JSONObject) county;
                fileWriter.write(" {\n" +
                        "      \"nume\": \""+ Normalizer.normalize((CharSequence) countyObj.get("name"), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "") +"\",\n" +
                        "      \"id\": \""+ id +"\",\n" +
                        "      \"resedinta\": \"Alba Iulia\",\n" +
                        "      \"localitati\": [\n");
                JSONArray cityArray = (JSONArray) countyObj.get("cities");
                id++;
                for(Object city: cityArray){
                    JSONObject cityObj = (JSONObject) city;
                    fileWriter.write("{\n" +
                            "          \"nume\": \""+Normalizer.normalize((CharSequence) cityObj.get("name"), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")+"\",\n" +
                            "          \"id\": \""+ id +"\"\n" +
                            "        },");
                    id++;
                }
                fileWriter.write("]\n" +
                        "    },");
            }
            fileWriter.write(" \n ]\n" +
                    "}");
            fileWriter.close();
            reader.close();
        } catch (FileNotFoundException | ParseException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
