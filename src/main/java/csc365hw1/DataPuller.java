package csc365hw1;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;

import java.security.Key;
import java.util.ArrayList;

/**
 * Created by landon on 2/21/17.
 */
public class DataPuller {
    //private ArrayList<String> setter;
    private ArrayList<KeyVal> stockInfo;
    private String URL = "https://www.quandl.com/api/v3/datatables/WIKI/PRICES.json?date.gte=20150101" +
            "&date.lt=20160101&ticker=MSFT,FB,GOOGL,INTC,CSCO,ORCL,AAPL,AMZN,AMD&";
    private String KEY = "api_key=aWGH5wHqiKkFgKFSSEuB";

    public DataPuller() {
        stockInfo = new ArrayList<>();
    }

    public ArrayList<KeyVal> getSentences() throws UnirestException {
        HttpResponse<JsonNode> jsonResponse;
        try {
            jsonResponse = Unirest.get(URL + KEY)
                    .header("Accept", "application/json")
                    .asJson();

            JSONArray data = jsonResponse.getBody().getObject().getJSONObject("datatable").getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                String key = data.getJSONArray(i).get(0) + " " + data.getJSONArray(i).get(1);
                double[] info = new double[5];
                for (int j = 2; j <= 6; j++) {
                    info[j - 2] = data.getJSONArray(i).getDouble(j);
                }

                KeyVal keyVal = new KeyVal(key, info);
                stockInfo.add(keyVal);
            }

            HashTable ht = new HashTable();
            for(KeyVal k : stockInfo){
                ht.put(k);
            }

            ht.displayHash();
            //ht.similarity("AAPL 2015-12-21");


            return stockInfo;
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }

}
