package pt.ipb.tankshooter.net;

import java.util.EventListener;

public interface NetworkListener extends EventListener {
	void playerEntered(NetworkEvent e);
	void playerExited(NetworkEvent e);
	void playerUpdated(NetworkEvent e);
	void playerSpawned(NetworkEvent e);
	void playerDied(NetworkEvent e);
}
