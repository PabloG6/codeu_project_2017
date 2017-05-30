package codeu.chat.common;

import codeu.chat.util.Time;

public class ServerInfo {
	public final Time startTime;

	public ServerInfo() {
		this.startTime = Time.now();
	}

	public ServerInfo(Time startTime) {
		this.startTime = startTime;
	}

	public long runLength() {
		return (Time.now().inMs() - startTime.inMs()) / 1000;
	}
}
