package csc365hw1;

import java.util.ArrayList;

/**
 * Created by landon on 2/22/17.
 */
public class KeyVal {
    private String key;
    private ArrayList<Double> val;

    public KeyVal(String k, ArrayList<Double> v){
        key = k;
        val = v;
    }

    public String getKey() {
        return key;
    }

    public ArrayList<Double> getVal() {
        return val;
    }
}
