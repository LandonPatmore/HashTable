package csc365hw1;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by landon on 2/21/17.
 */
public class HashTable {
    private static int size;
    private CLinkedList[] HT;
    private ArrayList<Double> checker;


    public HashTable() {
        size = 4001;
        HT = new CLinkedList[nextPrime()];
        checker = new ArrayList<>();
    }

    public void put(KeyVal keyVal) {
        Hashing h = new Hashing(keyVal.getKey(), keyVal.getKey().length());
        int hash = h.hasher() % nextPrime();
        if (indexEmpty(hash)) {
            HT[hash] = new CLinkedList(keyVal);
        }
        if (!indexEmpty(hash) && !HT[hash].head.getKey().equals(keyVal.getKey())) {
            HT[hash].add(keyVal);
        }
    }

    public double[] get(String key) {
        Hashing h = new Hashing(key, key.length());
        int hash = h.hasher() % nextPrime();

        if (indexEmpty(hash)) {
            return null;
        }

        KeyVal head = HT[hash].head;

        while (!indexEmpty(hash) && !HT[hash].head.getKey().equals(key)) {
            head = HT[hash].head.getNext();
        }
        return head.getVal();
    }

    public void displayHash(){
        for(int i = 0; i < HT.length; i++){
            System.out.print(i + " ");
            if(HT[i] != null){
                KeyVal kv = HT[i].head;
                System.out.print(kv.getKey() + " " + Arrays.toString(kv.getVal()) + "  ");
                while(kv.getNext() != null){
                    kv = kv.getNext();
                    System.out.print(kv.getKey() + " " + Arrays.toString(kv.getVal()) + "  ");
                }
            }
            System.out.println();
        }
    }

    public void similarity(String key){
        PearsonsCorrelation pc = new PearsonsCorrelation();
        for(int i = 0; i < HT.length; i++){
            if(HT[i] != null){
                KeyVal kv = HT[i].head;
                System.out.println(kv.getKey() + "  " + pc.correlation(get(key), kv.getVal()));
                while(kv.getNext() != null){
                    kv = kv.getNext();
                    System.out.println(kv.getKey() + "  " + pc.correlation(get(key), kv.getVal()));
                }
            }
        }
    }

    private boolean indexEmpty(int h) {
        if (HT[h] != null) {
            return false;
        }
        return true;
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

    private int nextPrime() {
        for (int i = size; true; i++) {
            if (isPrime(i)) {
                return i;
            }
        }
    }

    private static class Hashing {
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