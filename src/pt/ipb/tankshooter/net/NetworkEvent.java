package pt.ipb.tankshooter.net;

import java.util.EventObject;

@SuppressWarnings("serial")
public class NetworkEvent extends EventObject {

    private Player player;

	public NetworkEvent(Object source, Player player) {
        super(source);
        this.player = player;
    }
    
	public NetworkEvent(Object source) {
		super(source);
	}

	public Player getPlayer() {
		return player;
	}
}
