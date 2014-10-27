package pt.ipb.tankshooter.net;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NCPlayerEnteringGame implements Serializable {
	
	private Player player;

	public NCPlayerEnteringGame(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
}
