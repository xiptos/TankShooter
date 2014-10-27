package pt.ipb.tankshooter.net;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NCPlayerExitingGame implements Serializable {
	
	private Player player;

	public NCPlayerExitingGame(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
}
