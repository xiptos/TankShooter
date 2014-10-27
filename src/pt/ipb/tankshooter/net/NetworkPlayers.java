package pt.ipb.tankshooter.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.swing.event.EventListenerList;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

public class NetworkPlayers extends ReceiverAdapter {
	private static final String CLUSTER = "TankShooter";
	int clusterNum = 0;
	JChannel channel;

	EventListenerList listenerList = new EventListenerList();
	PlayerManager playerManager;

	public NetworkPlayers(PlayerManager playerManager) {
		this.playerManager = playerManager;
	}

	public void start() throws Exception {
		// TODO: check the jgroups Draw demo 
		channel = new JChannel("etc/protocol.xml");

		while (true) {
			channel.connect(CLUSTER + clusterNum);
			View view = channel.getView();
			if (view.size() > 10) {
				clusterNum++;
			} else {
				break;
			}
			channel.disconnect();
		}
		channel.setReceiver(this);
		channel.getState(null, 10000);
	}

	public void enterGame(Player player) throws Exception {
		channel.send(null, new NCPlayerEnteringGame(player));
	}

	public void updatePlayer(Player player, NCPlayerUpdated.COMMAND command) throws Exception {
		channel.send(null, new NCPlayerUpdated(player, command));
	}

	public void stop() {
		channel.close();
	}

	@Override
	public void getState(OutputStream output) throws Exception {
		Util.objectToStream(playerManager.getPlayers(), new DataOutputStream(output));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setState(InputStream input) throws Exception {
		List<Player> players;
		players = (List<Player>) Util.objectFromStream(new DataInputStream(input));
		playerManager.setPlayers(players);
		for(Player player : players) {
			firePlayerEntered(new NetworkEvent(this, player));
		}
		
	}

	@Override
	public void viewAccepted(View view) {
		System.out.println("view accepted");
	}
	
	@Override
	public void receive(Message msg) {
		Object message = msg.getObject();
		if (message instanceof NCPlayerEnteringGame) {
			NCPlayerEnteringGame nc = (NCPlayerEnteringGame)message;
			Player player = nc.getPlayer();
			playerManager.addPlayer(player);
			firePlayerEntered(new NetworkEvent(this, player));

		} else if (message instanceof NCPlayerExitingGame) {
			NCPlayerExitingGame nc = (NCPlayerExitingGame)message;
			Player player = nc.getPlayer();
			playerManager.removePlayer(player);
			firePlayerExited(new NetworkEvent(this, player));

		} else if (message instanceof NCPlayerUpdated) {
			NCPlayerUpdated nc = (NCPlayerUpdated)message;
			Player player = nc.getPlayer();
			playerManager.updatePlayer(player);
			firePlayerUpdated(new NetworkEvent(this, player, nc.getCommand()));
		}
	}

	public String getNetworkID() {
		return channel.getName(channel.getAddress());
	}

	public void addNetworkListener(NetworkListener l) {
		listenerList.add(NetworkListener.class, l);
	}

	public void removeNetworkListener(NetworkListener l) {
		listenerList.remove(NetworkListener.class, l);
	}

	protected void firePlayerUpdated(NetworkEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == NetworkListener.class) {
				((NetworkListener) listeners[i + 1]).playerUpdated(e);;
			}
		}
	}

	protected void firePlayerEntered(NetworkEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == NetworkListener.class) {
				((NetworkListener) listeners[i + 1]).playerEntered(e);;
			}
		}
	}

	protected void firePlayerExited(NetworkEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == NetworkListener.class) {
				((NetworkListener) listeners[i + 1]).playerExited(e);
			}
		}
	}

	public String getClusterName() {
		return CLUSTER+clusterNum;
	}

}
