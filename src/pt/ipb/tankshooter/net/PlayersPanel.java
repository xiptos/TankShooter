package pt.ipb.tankshooter.net;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import pt.ipb.tankshooter.PlayersListRenderer;

@SuppressWarnings("serial")
public class PlayersPanel extends JPanel implements NetworkListener {
	ListModel<NetworkPlayer> playerListModel;
	JButton startButton;

	public PlayersPanel(ListModel<NetworkPlayer> playerListModel) {
		super(new BorderLayout());
		this.playerListModel = playerListModel;

		initComponents();
	}

	private void initComponents() {
		JList<NetworkPlayer> playerList = new JList<>(playerListModel);
		playerList.setCellRenderer(new PlayersListRenderer());
		
		add(new JScrollPane(playerList), BorderLayout.CENTER);
		
		startButton = new JButton("Start");
		startButton.setEnabled(false);
		add(startButton, BorderLayout.SOUTH);
	}
	
	public void setStartEnabled(boolean enabled) {
		this.startButton.setEnabled(enabled);
	}
	
	public void addActionListener(ActionListener l) {
		startButton.addActionListener(l);
	}

	public void removeActionListener(ActionListener l) {
		startButton.removeActionListener(l);
	}

	@Override
	public void playerRegistered(NetworkEvent e) {
		NetworkPlayers net = (NetworkPlayers)e.getSource();
		setStartEnabled(net.isCoordinator());
	}

	@Override
	public void playerUnregistered(NetworkEvent e) {
		NetworkPlayers net = (NetworkPlayers)e.getSource();
		setStartEnabled(net.isCoordinator());
	}

	@Override
	public void gameStarted(NetworkEvent e) {
	}

}
