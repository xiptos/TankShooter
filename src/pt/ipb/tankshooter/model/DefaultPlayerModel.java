package pt.ipb.tankshooter.model;

import java.util.ArrayList;
import java.util.List;

public class DefaultPlayerModel extends AbstractPlayerModel {
	List<Player> playerList;
	Player selectedPlayer;
	
	public DefaultPlayerModel() {
		playerList = new ArrayList<>();
	}

	public void addPlayer(Player player) {
		if(playerList.contains(player)) {
			return;
		}
		playerList.add(player);
		firePlayerEntered(new PlayerEvent(this, player));
	}
	
	public void removePlayer(Player player) {
		playerList.remove(player);
		firePlayerExited(new PlayerEvent(this, player));
	}
	
	public void setSelectedPlayer(Player player) {
		this.selectedPlayer = player;
		firePlayerSelected(new PlayerEvent(this, player));
	}
	
	@Override
	public List<Player> getPlayers() {
		return playerList;
	}

	@Override
	public Player getSelectedPlayer() {
		return selectedPlayer;
	}

	@Override
	public int getIndexOf(Player player) {
		return playerList.indexOf(player);
	}

	@Override
	public int getPlayerCount() {
		return playerList.size();
	}

	@Override
	public Player getPlayer(int index) {
		return playerList.get(index);
	}

	public void setPlayers(List<Player> players) {
		for(Player player : this.playerList) {
			firePlayerExited(new PlayerEvent(this, player));
		}
		playerList.clear();
		playerList.addAll(players);
		for(Player player : this.playerList) {
			firePlayerEntered(new PlayerEvent(this, player));
		}
	}

}