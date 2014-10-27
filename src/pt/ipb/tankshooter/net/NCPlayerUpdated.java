package pt.ipb.tankshooter.net;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NCPlayerUpdated implements Serializable {
	public enum COMMAND { TURN_RIGHT, TURN_LEFT, FW, STOP_TURN_RIGHT, STOP_TURN_LEFT, STOP, FIRE }
	COMMAND command;
	Player player;
	
	public NCPlayerUpdated(Player player, COMMAND command) {
		this.player = player;
		this.command = command;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public COMMAND getCommand() {
		return command;
	}
}
