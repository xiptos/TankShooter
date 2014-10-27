package pt.ipb.tankshooter.net;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

@SuppressWarnings("serial")
public class PlayerManager extends AbstractListModel<Player> {
	List<Player> players;
	private Player myPlayer;
	
	public PlayerManager() {
		players = new ArrayList<>();
	}

	public synchronized  void addPlayer(Player player) {
		players.add(player);
		fireIntervalAdded(this, players.size()-1, players.size()-1);
	}

	public synchronized  void removePlayer(Player player) {
		int index = players.indexOf(player);
		players.remove(index);
		fireIntervalRemoved(this, index, index);
	}
	
	@Override
	public int getSize() {
		return players.size();
	}

	@Override
	public synchronized Player getElementAt(int index) {
		return players.get(index);
	}

	public synchronized List<Player> getPlayers() {
		return players;
	}

	public synchronized void setPlayers(List<Player> players) {
		this.players = players;
		fireContentsChanged(this, 0, players.size()-1);
	}

	public Player getMyPlayer() {
		return myPlayer;
	}
	
	public void setMyPlayer(Player myPlayer) {
		this.myPlayer = myPlayer;
	}

	public void updatePlayer(Player player) {
		int index = players.indexOf(player);
		if(index>=0) {
			Player existingPlayer = players.get(index);
			existingPlayer.setAngle(player.getAngle());
			existingPlayer.setX(player.getX());
			existingPlayer.setY(player.getY());
			existingPlayer.setPoints(player.getPoints());
			fireContentsChanged(this, index, index);
		}
	}

	public int getIndexOf(Player player) {
		return players.indexOf(player);
	}
}
