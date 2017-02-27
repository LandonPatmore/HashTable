package csc365hw1;

/**
 * Created by landon on 2/22/17.
 */
public class KeyVal implements Comparable<KeyVal> {
    private String key;
    private Double[] val;
    private Double mD;

    private KeyVal next;

    public KeyVal(String k, Double[] v) {
        key = k;
        val = v;
        mD = 0.0;
    }

    public String getKey() {
        return key;
    }

    public Double[] getVal() {
        return val;
    }

    public KeyVal getNext() {
        return next;
    }

    public void setNext(KeyVal next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return key;
    }

    public Double getmD() {
        return mD;
    }

    public void setmD(Double mD) {
        this.mD = mD;
    }

    @Override
    public int compareTo(KeyVal o) {
        return this.getmD().compareTo(o.getmD());
    }
}
