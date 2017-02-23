package csc365hw1;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by landon on 2/21/17.
 */
public class DataPuller {
    private ArrayList<Double> VALUES;
    private ArrayList<KeyVal> MAP;

    public DataPuller(){
        VALUES = new ArrayList<>();
        MAP = new ArrayList<>();
    }

    private ArrayList<KeyVal> getTidalData(List<String> ls) throws UnirestException {
        for(String station : ls) {
            String URL = "https://tidesandcurrents.noaa.gov/api/datagetter?range=720&station=" + station +
                    "&product=water_level&datum=MTL&units=english&time_zone=gmt&application=365hasher&format=json";
            System.out.println(URL);

            HttpResponse<JsonNode> response = Unirest.get(URL).asJson(); //makes a HTTP request to NOAA

            JSONObject rep = response.getBody().getObject(); //stores response in variable for ease of use
            JSONObject check = rep.optJSONObject("metadata"); //checker for old python script(will update once done and remove this)

            if(check != null) { //if it doesnt throw back an error

                String name = check.getString("name"); //grabs the station id
                JSONArray jA = rep.getJSONArray("data"); //grabs just the data JSONArray

                for (Object t : jA) { //enhanced for loop
                    if (t instanceof JSONObject) {
                        String val = ((JSONObject) t).getString("v"); //grabs the value from the key
                        if (val != null && val.length() > 0) {
                            VALUES.add(Double.parseDouble(val)); //adds values to values arraylist
                        }
                    }
                }
                KeyVal k = new KeyVal(name, VALUES);
                MAP.add(k); //gives the list the <station name, <values>>
            }
        }

        return MAP; //returns the hashMap
    }

    public ArrayList<KeyVal> gatherData() throws IOException, UnirestException {
//        PythonRunner pr = new PythonRunner();
//        pr.pythonRunner();

        FileDataGrabber fd = new FileDataGrabber();
        return getTidalData(fd.tidalStationIds());
    }


    private static class FileDataGrabber{
        private static File F;
        private static List<String> IDS;
        private static String line;

        private FileDataGrabber() {
            F = new File("Active_Tidal_Stations.txt"); //grabs the tidal station file
            IDS = new ArrayList<>(); //
            line = "";
        }

        private List<String> tidalStationIds() throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(F)); //wraps a bufferreader around a filereader to buffer the input

            while((line = bufferedReader.readLine()) != null) {
                IDS.add(line);
            }

            bufferedReader.close();
            return IDS;
        }
    }

    private static class PythonRunner {

        private void pythonRunner() throws IOException {
            System.out.println("Python Script running...");
            // set up the command and parameter
            String pythonScriptPath = "/Users/landon/Desktop/365HW1/crawler.py";
            String[] cmd = new String[2];
            cmd[0] = "python"; // check version of installed python: python -V
            cmd[1] = pythonScriptPath;

            // create runtime to execute external command
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(cmd);

            System.out.println("Python Script done running.");
        }
    }
}
