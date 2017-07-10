package codeu.chat.storage;

import codeu.chat.client.core.ConversationContext;
import codeu.chat.client.core.MessageContext;
import codeu.chat.common.Message;

import codeu.chat.util.Logger;
import codeu.chat.util.Uuid;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Pablo on 7/7/2017.
 */
public final class Storage implements Serializable {
    static HashMap<Uuid, Message> conversationMap = new HashMap<>();
    public static ConversationContext conversation;
    Queue<Message> messageQueue = new LinkedList<>();
    MessageContext messageContext;
    private static final Logger.Log LOG = Logger.newLog(Storage.class);

    public Storage() {
        System.out.println("----------------------------------------------------------------------Hello, Storage-----");
        try {
            Logger.enableFileOutput("chat_storage.log");
            Logger.enableConsoleOutput();

        } catch (IOException e) {
            LOG.error(e, "Failed to write message to server");
        }
    }

    public Storage(MessageContext messageContext) {
        this.messageContext = messageContext;
    }

    public void enqueue(Message message) {
        messageQueue.add(message);

        conversationMap.put(message.id, message);

    }


    public boolean write(Message message) {

        File file = new File("chat_log.txt");
        try {
            if (!file.exists())
                file.createNewFile();
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject("author: ".getBytes());
            out.writeObject(message.author.toString().getBytes());
            out.writeObject(" content: ".getBytes());
            out.writeObject(message.content.getBytes());
            out.writeObject("id: ".getBytes());
            out.writeObject(message.id.toString().getBytes() + "\n");

            out.close();
            fileOut.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }


    //get uuid of conversation and add to message

}
