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
        while (head.getNext() != null) {
            head = head.getNext();
        }
        if (head.getNext() == null) {
            head.setNext(keyVal);
        }
    }

}
