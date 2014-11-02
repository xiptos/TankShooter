package pt.ipb.tankshooter.net;

import java.util.EventObject;
import java.util.List;

import pt.ipb.tankshooter.model.Player;
import pt.ipb.tankshooter.net.NCPlayerUpdated.COMMAND;

@SuppressWarnings("serial")
public class NetworkEvent extends EventObject {

    private Player player;
	private List<COMMAND> commands;

	public NetworkEvent(Object source, Player player) {
		this(source, player, null);
    }
    
	public NetworkEvent(Object source) {
		this(source, null, null);
	}

	public NetworkEvent(Object source, Player player, List<COMMAND> commands) {
		super(source);
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
