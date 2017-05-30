package codeu.chat.common;

import java.io.IOException;

import codeu.chat.util.Time;
import codeu.chat.util.Uuid;

// added ServerInfo class that holds server info

public final class ServerInfo {
	private final static String SERVER_VERSION = "1.0.0";
	public final Uuid version;
	public final Time startTime;

	public ServerInfo() {
		Uuid tempVersion = null;
		try {
			tempVersion = Uuid.parse(SERVER_VERSION);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			new RuntimeException("Error parsing server version", e);
		}
		this.version = tempVersion;
		this.startTime = Time.now();
	}

	public ServerInfo(Uuid version) {
		this.version = version;
		this.startTime = null;
	}

	public ServerInfo(Time startTime) {
		Uuid tempVersion = null;
		try {
			tempVersion = Uuid.parse(SERVER_VERSION);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			new RuntimeException("Error parsing server version", e);
		}
		this.version = tempVersion;
		this.startTime = startTime;
	}
}
