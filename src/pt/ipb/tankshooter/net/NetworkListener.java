package pt.ipb.tankshooter.net;

import java.util.EventListener;

public interface NetworkListener extends EventListener {
	void playerRegistered(NetworkEvent e);
	void playerUnregistered(NetworkEvent e);
	void gameStarted(NetworkEvent e);
}
