package pt.ipb.tankshooter.net;

import java.util.EventObject;

@SuppressWarnings("serial")
public class NetworkEvent extends EventObject {

    private Player player;
	private NCPlayerUpdated.COMMAND command;

	public NetworkEvent(Object source, Player player) {
		this(source, player, null);
    }
    
	public NetworkEvent(Object source) {
		this(source, null, null);
	}

	public NetworkEvent(Object source, Player player, NCPlayerUpdated.COMMAND command) {
		super(source);
		this.player = player;
		this.command = command;
	}

	public Player getPlayer() {
		return player;
	}
	
	public NCPlayerUpdated.COMMAND getCommand() {
		return command;
	}
}
