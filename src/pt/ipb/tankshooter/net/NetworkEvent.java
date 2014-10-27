package pt.ipb.tankshooter.net;

import java.util.EventObject;

@SuppressWarnings("serial")
public class NetworkEvent extends EventObject {

    private NetworkPlayer player;

	public NetworkEvent(Object source, NetworkPlayer player) {
        super(source);
        this.player = player;
    }
    
	public NetworkEvent(Object source) {
		super(source);
	}

	public NetworkPlayer getPlayer() {
		return player;
	}
}
