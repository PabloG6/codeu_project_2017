package codeu.chat.storage;

import codeu.chat.common.Message;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

/**
 * Created by Pablo on 7/7/2017.
 */
public class MessageQueue {



    //add a int to the end of the queue
    public void enqueue(int o) {



    }

    public int dequeue() {

        return 0;

    }

    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue();
        for(int i = 0; i < 10; i++) {
            messageQueue.enqueue(i);
        }
        for (int i = 0; i < 10; i++) {
           System.out.println(messageQueue.dequeue());
        }
    }

}
