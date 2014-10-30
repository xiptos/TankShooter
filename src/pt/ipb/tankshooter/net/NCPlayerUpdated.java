package pt.ipb.tankshooter.net;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class NCPlayerUpdated implements Serializable {
	public enum COMMAND { TURN_RIGHT, TURN_LEFT, FW, STOP_TURN_RIGHT, STOP_TURN_LEFT, STOP, FIRE, BACK }
	List<COMMAND> commands;
	Player player;
	
	public NCPlayerUpdated(Player player, List<COMMAND> commands) {
		this.player = player;
		this.commands = commands;
	}
	
	public Player getPlayer() {
		return player;
	}

	public List<COMMAND> getCommands() {
		return commands;
	}
}
