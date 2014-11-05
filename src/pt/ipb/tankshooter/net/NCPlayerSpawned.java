package pt.ipb.tankshooter.net;

import java.io.Serializable;

import pt.ipb.tankshooter.model.Player;

@SuppressWarnings("serial")
public class NCPlayerSpawned implements Serializable {
	
	private Player player;

	public NCPlayerSpawned(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
}
