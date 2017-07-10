package codeu.chat.common;

import codeu.chat.util.Uuid;
import java.io.IOException;

import codeu.chat.util.Time;


/**
 * Created by Pablo on 6/1/2017.
 */

//holds the server information
public final class ServerInfo {
    private final static String SERVER_VERSION = "1.0.0";
    public  Uuid version;
    public final Time startTime;


    public ServerInfo() {
        this.startTime = Time.now();
        try {
            this.version = Uuid.parse(SERVER_VERSION);

        } catch( IOException ex) {

        }
    }


    public ServerInfo(Time startTime) {
        this.startTime = startTime;
    }

    public long duration() {
        long current = Time.now().inMs();
        long start = startTime.inMs();
        return (current-start)/1000;
    }

    public ServerInfo(Uuid version, Time startTime) {
        this.version = version;
        this.startTime = startTime;
    }
}



