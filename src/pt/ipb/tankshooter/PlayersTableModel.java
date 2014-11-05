package pt.ipb.tankshooter;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

import pt.ipb.game.engine.SpriteSheet;
import pt.ipb.tankshooter.model.Player;
import pt.ipb.tankshooter.model.PlayerEvent;
import pt.ipb.tankshooter.model.PlayerListener;
import pt.ipb.tankshooter.model.PlayerModel;

@SuppressWarnings("serial")
public class PlayersTableModel extends AbstractTableModel implements PlayerListener {
	PlayerModel playerModel;
	private SpriteSheet spriteSheet;

	public PlayersTableModel(PlayerModel playerModel) {
		this.playerModel = playerModel;
		spriteSheet = new SpriteSheet("pt/ipb/tankshooter/resources/MulticolorTanks.png", 32, 5);
	}

	@Override
	public int getRowCount() {
		return playerModel.getPlayerCount();
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
		Player player = playerModel.getPlayer(rowIndex);
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

	@Override
	public void playerEntered(PlayerEvent e) {
		int index = playerModel.getIndexOf(e.getPlayer());
		fireTableRowsInserted(index, index);
	}

	@Override
	public void playerExited(PlayerEvent e) {
		int index = playerModel.getIndexOf(e.getPlayer());
		fireTableRowsDeleted(index, index);
	}

	@Override
	public void playerSpawned(PlayerEvent e) {
		fireTableDataChanged();
	}

	@Override
	public void playerDied(PlayerEvent e) {
		fireTableDataChanged();
	}
}
