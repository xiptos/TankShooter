package pt.ipb.tankshooter.net;

import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import pt.ipb.tankshooter.PlayersListRenderer;

@SuppressWarnings("serial")
public class PlayersPanel extends JPanel {
	ListModel<Player> playerListModel;

	public PlayersPanel(ListModel<Player> playerListModel) {
		super(new BorderLayout());
		this.playerListModel = playerListModel;

		initComponents();
	}

	private void initComponents() {
		JList<Player> playerList = new JList<>(playerListModel);
		playerList.setCellRenderer(new PlayersListRenderer());
		
		add(new JScrollPane(playerList), BorderLayout.CENTER);
	}

}
