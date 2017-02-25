package csc365hw1;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by landon on 2/21/17.
 */
public class DataPuller {

    public DataPuller() {
    }

    public boolean getSentences() throws UnirestException {
        HttpResponse<JsonNode> jsonResponse;
        HashTable table = new HashTable();
        try {
            jsonResponse = Unirest.get("https://andyreagan-hedonometer-v1.p.mashape.com/timeseries/?format=json&limit=2500")
                    .header("X-Mashape-Key", "PPqTDCMRLDmsha4vRWPWnR8sll2Qp1gtIbljsnGKVluM1Z1dTE")
                    .header("Accept", "application/json")
                    .asJson();

            JSONArray data = jsonResponse.getBody().getObject().getJSONArray("objects");
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = data.getJSONObject(i);

                String[] date = obj.getString("date").split("T");
                Double happiness = obj.getDouble("happiness");

                DateHappiness dateHappiness = new DateHappiness(date[0], happiness);
                table.put(dateHappiness);
            }
            return true;
        } catch (UnirestException e) {
            e.printStackTrace();
            return false;
        }
    }

}
