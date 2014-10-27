package pt.ipb.tankshooter.net;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NCPlayerRegistered implements Serializable {
	NetworkPlayer player;
	
	public NCPlayerRegistered(NetworkPlayer player) {
		this.player = player;
	}
	
	public NetworkPlayer getPlayer() {
		return player;
	}

}
