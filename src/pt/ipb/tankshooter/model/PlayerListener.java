package pt.ipb.tankshooter.model;

import java.util.EventListener;

public interface PlayerListener extends EventListener {
	void playerEntered(PlayerEvent e);
	void playerExited(PlayerEvent e);
	void playerSelected(PlayerEvent e);
}
