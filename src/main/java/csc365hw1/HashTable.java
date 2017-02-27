package csc365hw1;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by landon on 2/21/17.
 */
public class HashTable {
    private CLinkedList[] HT;
    private int tableSize = 10000; //initial size of the array
    private int count = 0; //keeps track of how many elements are inside the array at a given time


    public HashTable() {
        HT = new CLinkedList[nextPrime(tableSize)];
    }

    public void put(KeyVal keyVal) { //puts the desired key into the hashtable
        Hashing h = new Hashing(keyVal.getKey(), keyVal.getKey().length());
        int hash = h.hasher() % nextPrime(tableSize);
        if (indexEmpty(hash)) {
            HT[hash] = new CLinkedList(keyVal);
            count++;
        }
        if (!indexEmpty(hash) && !HT[hash].head.getKey().equals(keyVal.getKey())) {
            HT[hash].add(keyVal);
            count++;
        }

        if((float)(count / tableSize) > 0.75){
            resize();
        }
    }

    public Double[] get(String key) { //gets the selected key value
        Hashing h = new Hashing(key, key.length());
        int hash = h.hasher() % nextPrime(tableSize);

        if (indexEmpty(hash)) {
            return null;
        }

        KeyVal head = HT[hash].head;

        while (!indexEmpty(hash) && !head.getKey().equals(key)) {
            head = head.getNext();
        }
        return head.getVal();
    }

    //FOR TESTING - WILL REMOVE
    public void displayHash(){
        for(int i = 0; i < HT.length; i++){
            System.out.print(i + " ");
            if(HT[i] != null){
                KeyVal kv = HT[i].head;
                System.out.print(kv.getKey() + " ");
                while(kv.getNext() != null){
                    kv = kv.getNext();
                    System.out.print(kv.getKey() + " ");
                }
            }
            System.out.println();
        }
    }


    public ArrayList<KeyVal> similarity(String key){
        //earsonsCorrelation pc = new PearsonsCorrelation();
        ArrayList<KeyVal> test = new ArrayList<>();
        Double check = 500000000.0;
        for(int i = 0; i < HT.length; i++){
            if(!indexEmpty(i)){
                KeyVal kv = HT[i].head;
                Double distance = ManhattanDistance(get(key), kv.getVal());
                if(distance < check && !kv.getKey().equals(key)) {
                    check = distance;
                    kv.setmD(check);
                    test.add(kv);
                }
                while(kv.getNext() != null && !kv.getNext().getKey().equals(key)){
                    kv = kv.getNext();
                    distance = ManhattanDistance(get(key), kv.getVal());
                    if(distance < check) {
                        check = distance;
                        kv.setmD(check);
                        test.add(kv);
                    }
                }
            }
        }

        Collections.sort(test); //sorts by descending order based on MD
        for(KeyVal k : test){ //prints out closest stocks under 500,000,000
            System.out.println(k + "     " + k.getmD());
        }
        System.out.println();

        return test;
    }

    public Double ManhattanDistance(Double[] x, Double[] y){
        Double sumx, sumy;
        sumx = sumy = 0.0;

        for(int i = 0; i < x.length; i++){
            sumx += x[i];
            sumy += y[i];
        }
        return Math.abs(sumx - sumy); //returns the absolute value between sum x and sum y
    }

    private boolean indexEmpty(int h) { //checks to see if the index is actually empty
        return HT[h] == null;
    }

    private boolean isPrime(int n) { //checks to see if the number is actually a prime number
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

    private int nextPrime(int n) { //finds the next prime
        for (int i = n; true; i++) {
            if (isPrime(i)) {
                return i;
            }
        }
    }

    private void resize(){ //auto resizes the HashTable if more than 75% of the table is full
        System.out.println("TO BE RESIZED");
        tableSize = 2 * tableSize;
        tableSize = nextPrime(tableSize); //sets the new size to the next available prime
        CLinkedList[] old = HT; //holds a reference to the old table
        System.out.println("SIZE OF NEW TABLE: " + tableSize);
        HT = new CLinkedList[tableSize]; //creates a new table
        count = 0; //resets the count to 0

        for(int i = 0; i < old.length; i++){ //puts the old values in the new table
            if(old[i] != null){
                KeyVal kv = old[i].head;
                put(kv);

                while (kv.getNext() != null) {
                    kv = kv.getNext();
                    put(kv);
                }

            }
        }
    }

    private class Hashing { //static class used for hashing values to insert into the hashtable
        private String hashableKey;
        private int hashed;
        private int length;

        private Hashing(String hk, int l) {
            hashableKey = hk;
            length = l;
        }

        // Jenkins hash function
        private int hasher() {
            int i = 0;
            int hash = 0;
            while (i != length) {
                hash += hashableKey.charAt(i++); //hashes each character of the key
                hash += hash << 10;
                hash ^= hash >> 6;
            }
            hash += hash << 3;
            hash ^= hash >> 11;
            hash += hash << 15;
            return getHash(hash);
        }

        private int getHash(int cH) { //gets the hash of the key
            hashed = (cH & 0x7FFFFFFF); //doesn't allow for negative hash values
            return hashed;
        }
    }
}