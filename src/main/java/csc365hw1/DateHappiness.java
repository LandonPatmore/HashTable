package csc365hw1;

/**
 * Created by landon on 2/22/17.
 */
public class DateHappiness {
    private String key;
    private Double val;

    private DateHappiness next;

    public DateHappiness(String k, Double v){
        key = k;
        val = v;
    }

    public String getKey() {
        return key;
    }

    public Double getVal() {
        return val;
    }

    public void setNext(DateHappiness next) { this.next = next; }

    public DateHappiness getNext() { return next; }

}
