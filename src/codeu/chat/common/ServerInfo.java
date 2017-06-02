package codeu.chat.common;

import codeu.chat.util.Uuid;

import java.io.IOException;

/**
 * Created by Pablo on 6/1/2017.
 */
public final class ServerInfo {
    private final static String SERVER_VERSION = "1.0.0";
    public  Uuid version;
    public ServerInfo() {
        try {
            this.version = Uuid.parse(SERVER_VERSION);
        } catch( IOException ex) {

        }
    }

    public ServerInfo(Uuid version) {
        this.version = version;
    }
}
