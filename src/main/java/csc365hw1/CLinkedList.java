package csc365hw1;

/**
 * Created by landon on 2/24/17.
 */
public class CLinkedList {

    KeyVal head;

    public CLinkedList(KeyVal kv) {
        head = kv;
    }

    public void add(KeyVal keyVal) {
        KeyVal k = head;
        while (k.getNext() != null) {
            k = k.getNext();
        }
        k.setNext(keyVal);
    }

}
