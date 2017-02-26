package csc365hw1;

import java.util.Arrays;

/**
 * Created by landon on 2/22/17.
 */
public class KeyVal {
    private String key;
    private double[] val;

    private KeyVal next;

    public KeyVal(String k, double[] v) {
        key = k;
        val = v;
        next = null;

    }

    public String getKey() {
        return key;
    }

    public double[] getVal() {
        return val;
    }

    public KeyVal getNext() {
        return next;
    }

    public void setNext(KeyVal next) {
        this.next = next;
    }

//    @Override
//    public String toString() {
//        return key + " " + Arrays.toString(val);
//    }


    @Override
    public String toString() {
        return key;
    }
}
