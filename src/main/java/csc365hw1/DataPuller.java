package csc365hw1;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by landon on 2/21/17.
 */
public class DataPuller {
    private static String STATION_ID;
    private static String URL;
    private static List<Double> VALUES;
    private static Map<String, List<Double>> MAP;

    public DataPuller(String sid){
        STATION_ID = sid;
        URL = "https://tidesandcurrents.noaa.gov/api/datagetter?range=720&station=" + STATION_ID + "&product=water_level&datum=MTL&units=english&time_zone=gmt&application=365hasher&format=json";
        VALUES = new ArrayList<Double>();
    }

    public Map<String, List<Double>> getTidalData() throws UnirestException {
        final HttpResponse<JsonNode> response = Unirest.get(URL).asJson(); //makes a HTTP request to NOAA

        JSONObject rep = response.getBody().getObject(); //stores response in variable for ease of use

        String name = rep.getJSONObject("metadata").getString("name"); //grabs the station id
        JSONArray jA = rep.getJSONArray("data"); //grabs just the data JSONArray

        for(Object t : jA) { //enhanced for loop
            if (t instanceof JSONObject) {
                Double val = ((JSONObject) t).getDouble("v"); //grabs the value from the key
                VALUES.add(val); //adds values to values arraylist
            }
        }
        MAP.put(name, VALUES); //gives the map the <station name, <values>>

        return MAP; //returns the map
    }
}
