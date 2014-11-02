package pt.ipb.tankshooter.model;

import java.util.EventObject;

@SuppressWarnings("serial")
public class PlayerEvent extends EventObject {

    private Player player;

	public PlayerEvent(Object source, Player player) {
		super(source);
		this.player = player;
    }
    
	public PlayerEvent(Object source) {
		this(source, null);
	}

	public Player getPlayer() {
		return player;
	}
	
}
