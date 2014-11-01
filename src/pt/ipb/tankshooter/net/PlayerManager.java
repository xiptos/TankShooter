package pt.ipb.tankshooter.net;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

import pt.ipb.game.engine.SpriteSheet;

@SuppressWarnings("serial")
public class PlayerManager extends AbstractTableModel {
	List<Player> players;
	private Player myPlayer;
	private SpriteSheet spriteSheet;

	public PlayerManager() {
		players = new ArrayList<>();
		spriteSheet = new SpriteSheet("pt/ipb/tankshooter/resources/MulticolorTanks.png", 32, 5);
	}

	public synchronized void addPlayer(Player player) {
		if (players.contains(player)) {
			players.set(players.indexOf(player), player);
		} else {
			players.add(player);
		}
		System.out.println("PlayerManager.player added");
		fireTableRowsInserted(players.size() - 1, players.size() - 1);
	}

	public synchronized void removePlayer(Player player) {
		int index = players.indexOf(player);
		players.remove(index);
		fireTableRowsDeleted(index, index);
	}

	public synchronized List<Player> getPlayers() {
		return players;
	}

	public synchronized void setPlayers(List<Player> players) {
		this.players = players;
		fireTableDataChanged();
	}

	public Player getMyPlayer() {
		return myPlayer;
	}

	public void setMyPlayer(Player myPlayer) {
		this.myPlayer = myPlayer;
	}

	public void updatePlayer(Player player) {
		int index = players.indexOf(player);
		if (index >= 0) {
			Player existingPlayer = players.get(index);
			existingPlayer.setAngle(player.getAngle());
			existingPlayer.setX(player.getX());
			existingPlayer.setY(player.getY());
			existingPlayer.setPoints(player.getPoints());
			fireTableRowsUpdated(index, index);
		}
	}

	public int getIndexOf(Player player) {
		return players.indexOf(player);
	}

	@Override
	public int getRowCount() {
		return players.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "";
		case 1:
			return "Nome";
		case 2:
			return "Pontos";
		}
		return null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Image.class;
		case 1:
			return String.class;
		case 2:
			return Integer.class;
		}
		return null;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Player player = players.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return new ImageIcon(spriteSheet.getSprite(0, rowIndex).getFrame());
		case 1:
			return player.getId();
		case 2:
			return player.getPoints();
		}
		return null;
	}
}
