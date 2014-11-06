package pt.ipb.tankshooter.net;

import java.io.Serializable;

import pt.ipb.tankshooter.model.Player;

@SuppressWarnings("serial")
public class NCKilledPlayer implements Serializable {
	
	private Player killed;
	private Player shooter;

	public NCKilledPlayer(Player killed, Player shooter) {
		this.killed = killed;
		this.shooter = shooter;
	}

	
	public Player getKilled() {
		return killed;
	}
	
	public Player getShooter() {
		return shooter;
	}
}
