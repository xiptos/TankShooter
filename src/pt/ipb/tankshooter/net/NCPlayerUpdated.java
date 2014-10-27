package pt.ipb.tankshooter.net;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NCPlayerUpdated implements Serializable {
	Player player;
	
	public NCPlayerUpdated(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
}
