package csc365hw1;

/**
 * Created by landon on 2/21/17.
 */
public class HashTable {
    private static int size;
    private CLinkedList[] HT;


    public HashTable() {
        size = 2500;
        HT = new CLinkedList[nextPrime()];
    }

    public void put(DateHappiness dateHappiness) {
        Hashing h = new Hashing(dateHappiness.getKey(), dateHappiness.getKey().length());
        int hash = h.hasher() % nextPrime();
        if (indexEmpty(hash)) {
            HT[hash] = new CLinkedList(dateHappiness);
        }
        if (!indexEmpty(hash) && !HT[hash].head.getKey().equals(dateHappiness.getKey())) {
            HT[hash].add(dateHappiness);
        }
    }

    public Double get(String key) {
        Hashing h = new Hashing(key, key.length());
        int hash = h.hasher() % nextPrime();

        if (indexEmpty(hash)) {
            return null;
        }

        DateHappiness head = HT[hash].head;

        while (!indexEmpty(hash) && !HT[hash].head.getKey().equals(key)) {
            head = HT[hash].head.getNext();
        }
        return head.getVal();
    }

    public String similarity(String key) {
        String closest = "";
        Double check = 0.0;

        for (int i = 0; i < HT.length; i++) {
            if (HT[i] != null) {
                DateHappiness head = HT[i].head;
                if (HT[i].head.getNext() != null) {
                    head = head.getNext();
                    if (ManhattanDistance(get(key), head.getVal()) >= check) {
                        check = head.getVal();
                        closest = head.getKey();
                    } else {
                        if (ManhattanDistance(get(key), head.getVal()) >= check) {
                            check = head.getVal();
                            closest = head.getKey();
                        }
                    }
                }
            }
        }

        return "Closest Key: " + closest + " | Value: " + check;
    }

    public Double ManhattanDistance(Double x, Double y) {
        return Math.abs(x - y);
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