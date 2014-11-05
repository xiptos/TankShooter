package pt.ipb.tankshooter.model;

import java.util.List;

public interface PlayerModel {

	List<Player> getPlayers();

	int getIndexOf(Player player);

	int getPlayerCount();

	Player getPlayer(int index);

}
