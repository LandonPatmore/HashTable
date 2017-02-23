package csc365hw1;

import java.util.ArrayList;

/**
 * Created by landon on 2/21/17.
 */
public class HashTable {
    private static int size;
    private KeyVal[] HT;
    private ArrayList<KeyVal> data;


    public HashTable(ArrayList<KeyVal> m){
        size = 809;
        HT = new KeyVal[nextPrime()];
        data = m;
    }

    public void insertHash() {
        for (KeyVal key : data){
            Hashing h = new Hashing(key.getKey(), key.getKey().length());
            int hash = h.hasher();
            KeyVal kv = new KeyVal(key.getKey(), key.getVal());

            System.out.println(hash);
            if(indexEmpty(hash)) {
                HT[hash] = kv;
            }
        }
        //for testing (will remove)
        displayHash();
    }

    //TESTING METHOD -- REMOVE
    private void displayHash(){
        for(int i = 0; i < HT.length; i++){
            if(HT[i] != null) {
                System.out.println(HT[i].getVal());
            }
        }
    }

    private boolean indexEmpty(int h){
        if(HT[h] != null){
            return false;
        }
        return true;
    }

    private void collisionFix(int h){
        if(!indexEmpty(h)){

        }
    }

    private static boolean isPrime(int n){
        if(n % 2 == 0) {
            return false;
        }

        for(int i = 3; i * i <= n; i+=2) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    private static int nextPrime(){
        for(int i = size; true; i++) {
            if (isPrime(i)) {
                return i;
            }
        }
    }

    private static class Hashing{
        private String hashableKey;
        private int index;
        private int length;

        private Hashing(String hk, int l){
            hashableKey = hk;
            length = l;
        }

        // Jenkins hash function
        private int hasher(){
            int i = 0;
            int hash = 0;
            while(i != length){
                hash += hashableKey.charAt(i++);
                hash += hash << 10;
                hash ^= hash >> 6;
            }
            hash += hash << 3;
            hash ^= hash >> 11;
            hash += hash << 15;
            return getIndex(hash);
        }

        private int getIndex(int cH){
            index = (cH & 0x7FFFFFFF) % nextPrime();
            return index;
        }
    }
}