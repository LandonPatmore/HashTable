package csc365hw1;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by landon on 2/21/17.
 */
public class HashTable {
    private CLinkedList[] HT;
    private int tableSize = 10000;
    private int count = 0;


    public HashTable() {
        HT = new CLinkedList[nextPrime(tableSize)];
    }

    public void put(KeyVal keyVal) {
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

    public Double[] get(String key) {
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
            if(HT[i] != null){
                KeyVal kv = HT[i].head;
                if(ManhattanDistance(get(key), kv.getVal()) < check && !kv.getKey().equals(key)) {
                    check = ManhattanDistance(get(key), kv.getVal());
                    kv.setmD(check);
                    test.add(kv);
                    //System.out.println(kv.getKey() + "  " + ManhattanDistance(get(key), kv.getVal()));
                }
                while(kv.getNext() != null && !kv.getNext().getKey().equals(key)){
                    kv = kv.getNext();
                    if(ManhattanDistance(get(key), kv.getVal()) < check) {
                        check = ManhattanDistance(get(key), kv.getVal());
                        kv.setmD(check);
                        test.add(kv);
                        //System.out.println(kv.getKey() + "  " + ManhattanDistance(get(key), kv.getVal()));
                    }
                }
            }
        }
        Collections.sort(test);
        System.out.println("SIM");
        for(KeyVal k : test){
            System.out.println(k + "     " + k.getmD());
        }
        System.out.println();

        return test;
    }

    public Double ManhattanDistance(Double[] x, Double[] y){
        Double sumx = 0.0;
        Double sumy = 0.0;

        for(int i = 0; i < x.length; i++){
            sumx += x[i];
            sumy += y[i];
        }
        return Math.abs(sumx - sumy);
    }

    private boolean indexEmpty(int h) {
        return HT[h] == null;
    }

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

    private int nextPrime(int n) {
        for (int i = n; true; i++) {
            if (isPrime(i)) {
                return i;
            }
        }
    }

    private void resize(){
        System.out.println("TO BE RESIZED");
        tableSize = 2 * tableSize;
        tableSize = nextPrime(tableSize);
        CLinkedList[] old = HT;
        System.out.println("SIZE OF NEW TABLE: " + tableSize);
        HT = new CLinkedList[tableSize];
        count = 0;

        for(int i = 0; i < old.length; i++){
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

    private class Hashing {
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
                hash += hashableKey.charAt(i++);
                hash += hash << 10;
                hash ^= hash >> 6;
            }
            hash += hash << 3;
            hash ^= hash >> 11;
            hash += hash << 15;
            return getIndex(hash);
        }

        private int getIndex(int cH) {
            hashed = (cH & 0x7FFFFFFF);
            return hashed;
        }
    }
}