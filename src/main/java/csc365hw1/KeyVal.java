package csc365hw1;

/**
 * Created by landon on 2/22/17.
 */

/**
 * Custom class for storing keys and values in a custome HashTable
 */
public class KeyVal implements Comparable<KeyVal> {
    private String key;
    private Double[] val;
    private Double mD;

    private KeyVal next;

    /**
     *
     * @param k key string
     * @param v double[] values
     */
    public KeyVal(String k, Double[] v) {
        key = k;
        val = v;
        mD = 0.0;
    }

    /**
     *
     * @return gets the key
     */

    public String getKey() {
        return key;
    }

    /**
     *
     * @return gets the values
     */

    public Double[] getVal() {
        return val;
    }

    /**
     *
     * @return gets the next KeyVal for the CLinkedList
     */

    public KeyVal getNext() {
        return next;
    }

    /**
     *
     * @param next sets the next KeyVal for the CLinkedList
     */

    public void setNext(KeyVal next) {
        this.next = next;
    }

    /**
     *
     * @return custom toString method for GUI
     */

    @Override
    public String toString() {
        return key;
    }

    /**
     *
     * @return gets the ManhattanDistance for the specified KeyVal
     */

    public Double getmD() {
        return mD;
    }

    /**
     *
     * @param mD sets the ManhattanDistance when comparing KeyVals
     */

    public void setmD(Double mD) {
        this.mD = mD;
    }

    /**
     *
     * @param o KeyVal object
     * @return overrides the compareTo method to compare ManhattanDistances of the current and requested keys
     */

    @Override
    public int compareTo(KeyVal o) {
        return this.getmD().compareTo(o.getmD());
    }
}