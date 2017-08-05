package codeu.chat.common;

import codeu.chat.util.Time;
import codeu.chat.util.Uuid;

import java.io.IOException;

// holds server info
public class ServerInfo {
  public final Time startTime;
  private final static String SERVER_VERSION = "1.0.0";
  public  Uuid version;

  public ServerInfo() {
    this.startTime = Time.now();
    try {
            this.version = Uuid.parse(SERVER_VERSION);
        } catch( IOException ex) {

        }
  }

  public ServerInfo(Time startTime, Uuid version) {
    this.startTime = startTime;
    this.version = version;
  }

  public long duration() {
    long current = Time.now().inMs();
    long start = startTime.inMs();
    return (current-start)/1000;
  }
}