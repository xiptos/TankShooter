package pt.ipb.tankshooter.model;

import java.util.EventListener;

public interface PlayerListener extends EventListener {
	void playerEntered(PlayerEvent e);
	void playerExited(PlayerEvent e);
	void playerSpawned(PlayerEvent e);
	void playerDied(PlayerEvent e);
}
