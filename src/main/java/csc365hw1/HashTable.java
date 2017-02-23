package csc365hw1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by landon on 2/21/17.
 */
public class HashTable {
    private static int size;
    private List<List<Double>>[] HT;
    private List<String> ids;


    public HashTable(List<String> l){
        size = 809;
        ids = l;
        HT = new ArrayList[size];
    }

    public void tellEmpty(){
        for(int i = 0; i < size; i++){
            if(HT[i] == null){
                System.out.println("TRUE");
            }
        }
    }


    public void insertHash(){
        Hashing h;

        for(String id: ids) {
            h = new Hashing(id, id.length());
            System.out.println("The index = " + h.hasher() + " for value " + id);
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

        private boolean isPrime(int n){
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

        private int nextPrime(){
            for(int i = size; true; i++) {
                if (isPrime(i)) {
                    return i;
                }
            }
        }

        private int getIndex(int cH){
            System.out.println(nextPrime());
            index = (cH & 0x7FFFFFFF) % nextPrime();
            return index;
        }
    }
}
