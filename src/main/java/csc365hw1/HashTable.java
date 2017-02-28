package csc365hw1;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by landon on 2/21/17.
 */

/**
 * Custom HashTable Implementation
 */
public class HashTable {
    private KeyVal[] HT;
    private int tableSize = 10000;
    private int count = 0;

    /**
     * creates a new Array of CLinkedLists
     */

    public HashTable() {
        HT = new KeyVal[nextPrime(tableSize)];
    }

    /**
     * Puts a KeyVal into the HashTable and will automatically turn collisions into a CLinkedList
     * @param keyVal takes a KeyVal to put into the HashTable
     */

    public void put(KeyVal keyVal) {
        Hashing h = new Hashing(keyVal.getKey(), keyVal.getKey().length());
        int hash = h.hasher() % nextPrime(tableSize);
        if (indexEmpty(hash)) {
            HT[hash] = keyVal;
            count++;
        }
        if (!indexEmpty(hash) && !HT[hash].getKey().equals(keyVal.getKey())) {
            HT[hash].setNext(keyVal);
            count++;
        }

        if((float)(count / tableSize) > 0.75){
            resize();
        }
    }

    /**
     *
     * @param key takes a String and checks to see if the key is within the HashTable
     * @return either a null if it can't be found or the Double[] of Values of the Key
     */

    public Double[] get(String key) {
        Hashing h = new Hashing(key, key.length());
        int hash = h.hasher() % nextPrime(tableSize);

        if (indexEmpty(hash)) {
            return null;
        }

        KeyVal head = HT[hash];

        while (!indexEmpty(hash) && !head.getKey().equals(key)) {
            head = head.getNext();
        }
        return head.getVal();
    }

    /**
     * Testing method to see how the HashTable was placing KeyVals
     */

    public void displayHash(){
        for(int i = 0; i < HT.length; i++){
            System.out.print(i + " ");
            if(HT[i] != null){
                KeyVal kv = HT[i];
                System.out.print(kv.getKey() + " ");
                while(kv.getNext() != null){
                    kv = kv.getNext();
                    System.out.print(kv.getKey() + " ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Looks to find stocks with a similarity of 500,000,000 or less
     * @param key takes a String and checks its values against all of the other keys' values to find the most
     *            similar stock to the one requested
     * @return ArrayList of KeyVals to then be used in the GUI to display the most similar keys to the requested one
     */

    public ArrayList<KeyVal> similarity(String key){
        ArrayList<KeyVal> test = new ArrayList<>();
        Double check = 200000.0;
        for(int i = 0; i < HT.length; i++){
            if(!indexEmpty(i)){
                KeyVal kv = HT[i];
                Double distance = ManhattanDistance(get(key), kv.getVal());
                if(!kv.getKey().equals(key)) {
                    if(distance < check) {
                        check = distance;
                        kv.setmD(distance);
                        test.add(kv);
                    }
                }
                while(kv.getNext() != null && !kv.getNext().getKey().equals(key)){
                    kv = kv.getNext();
                    distance = ManhattanDistance(get(key), kv.getVal());
                    if(distance < check) {
                        check = distance;
                        kv.setmD(distance);
                        test.add(kv);
                    }
                }
            }
        }

        Collections.sort(test);
        for(KeyVal k : test){
            System.out.println(k + "     " + k.getmD());
        }
        System.out.println();

        return test;
    }

    /**
     *
     * @param x requested key's values
     * @param y current index's values
     * @return Absolute value of sum(x) - sum(y)
     */

    private Double ManhattanDistance(Double[] x, Double[] y){
        Double sumx, sumy;
        sumx = sumy = 0.0;

        for(int i = 0; i < x.length; i++){
            sumx += x[i];
            sumy += y[i];
        }
        return Math.abs(sumx - sumy);
    }

    /**
     *
     * @param h takes the hashValue % tableSize
     * @return if the index is actually null or not
     */

    private boolean indexEmpty(int h) { //checks to see if the index is actually empty
        return HT[h] == null;
    }

    /**
     *
     * @param n takes an integer
     * @return if the requested integer is actually a prime or not
     */

    private boolean isPrime(int n) {
        if (n % 2 == 0) {
            return false;
        }

        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param n takes an integer
     * @return the next possible prime
     */

    private int nextPrime(int n) {
        for (int i = n; true; i++) {
            if (isPrime(i)) {
                return i;
            }
        }
    }

    /**
     * Auto resizes the HashTable when the HashTable is filled 0.75 or larger
     */

    private void resize(){
        System.out.println("TO BE RESIZED");

        tableSize = 2 * tableSize;
        tableSize = nextPrime(tableSize);
        KeyVal[] old = HT;

        System.out.println("SIZE OF NEW TABLE: " + tableSize);

        HT = new KeyVal[tableSize];
        count = 0;

        for(int i = 0; i < old.length; i++){
            if(old[i] != null){
                KeyVal kv = old[i];
                put(kv);

                while (kv.getNext() != null) {
                    kv = kv.getNext();
                    put(kv);
                }
            }
        }
    }

    /**
     * Custom hashing class to hash the keys for placement in the HashTable
     */

    private class Hashing {
        private String hashableKey;
        private int hashed;
        private int length;

        /**
         *
         * @param hk a key
         * @param l the length of the key requested
         */

        private Hashing(String hk, int l) {
            hashableKey = hk;
            length = l;
        }

        /**
         * Jenkins One-At-A-Time Hash Function implementation
         * @return the hash value of the given key
         */
        private int hasher() {
            int i = 0;
            int hash = 0;
            while (i != length) {
                hash += hashableKey.charAt(i++);
                hash += hash << 10;
                hash ^= hash >> 6;
            }
            hash += hash << 3;
            hash ^= hash >> 11;
            hash += hash << 15;
            return getHash(hash);
        }

        /**
         *
         * @param h takes the hash value
         * @return the actual hash value by not allowing any negatives
         */

        private int getHash(int h) {
            hashed = (h & 0x7FFFFFFF);
            return hashed;
        }
    }
}