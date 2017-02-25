package csc365hw1;

/**
 * Created by landon on 2/24/17.
 */
public class CLinkedList {

    DateHappiness head;

    public CLinkedList(DateHappiness kv){
        head = kv;
    }

    public void add(DateHappiness dateHappiness){
        while(head.getNext() != null){
            head = head.getNext();
        }
        if(head.getNext() == null){
            head.setNext(dateHappiness);
        }
    }

    public void display(){
        while(head.getNext() != null){
            System.out.print(head.getKey() + " ");
            head = head.getNext();
        }
    }

}
