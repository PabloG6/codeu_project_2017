package codeu.chat.common;

import java.io.IOException;

import codeu.chat.util.Time;

// holds server info
public class ServerInfo {
  public final Time startTime;

  public ServerInfo() {
    this.startTime = Time.now();
  }

  public ServerInfo(Time startTime) {
    this.startTime = startTime;
  }

  public long duration() {
    long current = Time.now().inMs();
    long start = startTime.inMs();
    return (current-start)/1000;
  }
}
