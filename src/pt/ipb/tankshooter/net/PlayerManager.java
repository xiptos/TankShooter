package pt.ipb.tankshooter.net;

import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractListModel;

import org.jgroups.Address;

@SuppressWarnings("serial")
public class PlayerManager extends AbstractListModel<NetworkPlayer> {
	Map<Integer, NetworkPlayer> players;
	private NetworkPlayer myPlayer;
	
	public PlayerManager() {
		players = new HashMap<Integer, NetworkPlayer>();
	}

	public synchronized  void addPlayer(NetworkPlayer player) {
		players.put(player.getNum(), player);
		fireIntervalAdded(this, player.getNum(), player.getNum());
	}

	public synchronized  void removePlayer(NetworkPlayer player) {
		players.remove(player.getNum());
		fireIntervalRemoved(this, player.getNum(), player.getNum());
	}
	
	public NetworkPlayer createPlayer(String id, String name, Address address) {
		if (players.size() > 9) {
			return null;
		}
		int currentTank = 0;
		for(int i=0; i<10; i++) {
			if(!players.containsKey(i)) {
				currentTank = i;
				break;
			}
		}
		NetworkPlayer player = new NetworkPlayer(id, name, currentTank, address);

		return player;
	}

	@Override
	public int getSize() {
		return players.size();
	}

	@Override
	public synchronized NetworkPlayer getElementAt(int index) {
		return players.get(index);
	}

	public synchronized Map<Integer, NetworkPlayer> getPlayers() {
		return players;
	}

	public synchronized void setPlayers(Map<Integer, NetworkPlayer> players) {
		this.players = players;
		fireContentsChanged(this, 0, players.size()-1);
	}

	public NetworkPlayer getMyPlayer() {
		return myPlayer;
	}
	
	public void setMyPlayer(NetworkPlayer myPlayer) {
		this.myPlayer = myPlayer;
	}

}
