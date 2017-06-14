package codeu.chat.server;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.HashMap;

import codeu.chat.common.ConversationHeader;
import codeu.chat.common.Message;
import codeu.chat.common.User;
import codeu.chat.util.Logger;
import codeu.chat.util.Uuid;

public class LocalFile implements Serializable
{
    private static final long serialVersionUID = 1L;
    //Instance varibles for saving the current data of server.
    private final HashMap<Uuid,User> users;
    private final HashMap<Uuid,ConversationHeader> conversationHeaders;
    private final HashMap<Uuid,Message> messages;

    private transient final File file;

    private static final Logger.Log LOG = Logger.newLog(LocalFile.class);

    private transient boolean hasModified = false;//It indicates if there is a new data should be handled.
    public LocalFile(File file)
    {
        this.file = file;
        LocalFile newClass;
        HashMap<Uuid,User>tempUsers = new HashMap<>();
        HashMap<Uuid,ConversationHeader>tempConversationHeaders= new HashMap<>();
        HashMap<Uuid,Message>tempMessages= new HashMap<>();
        try
        {
            //Deserialization
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            FileInputStream fileInputStream = new FileInputStream(randomAccessFile.getFD());
		    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            //Read the information to current instance
		    newClass = (LocalFile)objectInputStream.readObject();
            tempUsers = newClass.users;
            tempConversationHeaders = newClass.conversationHeaders;
            tempMessages = newClass.messages;

            fileInputStream.close();
            objectInputStream.close();
        }
        } catch (IOException e) {
          System.err.println("ERROR: An error occurred while reading from a local file. Please try again.");
        throw new RuntimeException(e);
  
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: An error occurred while deserializing data. Required classes were not found.");    
            throw new RuntimeException(e);
        }
        {
            System.out.println("ERROR:Unable to read data.");
            System.out.println("WARNING: If the file doesn't exist, a new local file will be created.");
            exception.printStackTrace();
        }
        conversationHeaders = tempConversationHeaders;
            messages = tempMessages;
        }
    }
    /**
     * Save the data into local file.
     * 
     * @exception   Failed to save data.
     */
    public void saveData() throws IOException
    {
        if(!hasModified)//Only save the data when the data is updated.
        {
            return;
        }
        if(!file.exists())
        {
            file.createNewFile();
        }
        //Serialization
         FileOutputStream fileOutputStream = new FileOutputStream(file);
         ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
         
         try (FileOutputStream fileOutputStream = new FileOutputStream(randomAccessFile.getFD());
         ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream) ) {

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this); 
        LOG.info("Data saved");
  }
  objectOutputStream.close();
}
    //Get a copy of users using @return  ArrayList<User> Current users

    public ArrayList<User> getCopyOfUsers()
    {
        return new ArrayList<>(users.values());
    }
    //Get a copy of conversations
    public ArrayList<ConversationHeader> getCopyOfConversationHeaders()
    {
        return new ArrayList<>(conversationHeaders.values());
    }
    //Get a copy of messages
    public ArrayList<Message> getCopyOfMessages()
    {
        return new ArrayList<>(messages.values());
    }
    //Add a new user
    public void addUser(User user)
    {
        if(users.containsKey(user.id))//If repetition happens, hasMofified should be false still.
        {
            return;
         }
        users.put(user.id, user);
        hasModified = true;
    }
    //Add a new conversation
    public void addConversationHeader(ConversationHeader header)
    {
        if(conversationHeaders.containsKey(header.id))
        {
            return;
        }
        conversationHeaders.put(header.id, header);//If repetition happens, hasMofified should be false still.
        hasModified = true;
    }
    //Add a new message
    public void addMessage(Message message)
    {
        if(messages.containsKey(message.id))
        {
            return;
        }
        messages.put(message.id, message);//If repetition happens, hasMofified should be false still.
        hasModified = true;
    }
hasModified = false;
}
