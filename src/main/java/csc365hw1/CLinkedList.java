package csc365hw1;

/**
 * Created by landon on 2/24/17.
 */

/**
 * Custom LinkedList class for HashTable in the instance of collisions instead of linear probing which causes
 * clustering
 */
public class CLinkedList {

    KeyVal head;

    /**
     *
     * @param kv KeyVal that is the root node for the CLinkedList
     */

    public CLinkedList(KeyVal kv) {
        head = kv;
    }

    /**
     *
     * @param keyVal takes a KeyVal to add to the CLinkedList
     */

    public void add(KeyVal keyVal) {
        KeyVal k = head;
        while (k.getNext() != null) {
            k = k.getNext();
        }
        k.setNext(keyVal);
    }

}
