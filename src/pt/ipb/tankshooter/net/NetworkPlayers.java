package pt.ipb.tankshooter.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.EventListenerList;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

import pt.ipb.tankshooter.model.DefaultPlayerModel;
import pt.ipb.tankshooter.model.Player;

public class NetworkPlayers extends ReceiverAdapter {
	private static final String CLUSTER = "TankShooter";
	int clusterNum = 0;
	Map<Address, Player> playerMap = new HashMap<>();
	JChannel channel;

	EventListenerList listenerList = new EventListenerList();
	private DefaultPlayerModel playerModel;

	public NetworkPlayers(DefaultPlayerModel playerModel) {
		this.playerModel = playerModel;
	}

	public void start() throws Exception {
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

	public void spawn(Player player) throws Exception {
		channel.send(null, new NCPlayerSpawned(player));
	}

	public void playerDied(Player player) throws Exception {
		channel.send(null, new NCPlayerDied(player));
	}

	public void enterGame(Player player) throws Exception {
		channel.send(null, new NCPlayerEnteringGame(player));
	}

	public void exitGame(Player player) throws Exception {
		channel.send(null, new NCPlayerExitingGame(player));
	}

	public void updatePlayer(Player player, List<NCPlayerUpdated.COMMAND> commands) throws Exception {
		channel.send(null, new NCPlayerUpdated(player, commands));
	}

	public void stop() {
		channel.close();
	}

	@Override
	public void getState(OutputStream output) throws Exception {
		Util.objectToStream(playerModel.getPlayers(), new DataOutputStream(output));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setState(InputStream input) throws Exception {
		List<Player> players;
		players = (List<Player>) Util.objectFromStream(new DataInputStream(input));
		playerModel.setPlayers(players);
		for (Player player : players) {
			firePlayerEntered(new NetworkEvent(this, player));
		}
	}

	@Override
	public void viewAccepted(View view) {
		// check if removed
		List<Address> viewAddresses = view.getMembers();
		for (Address registeredAddresss : playerMap.keySet()) {
			if (!viewAddresses.contains(registeredAddresss)) {
				Player player = playerMap.get(registeredAddresss);
				playerMap.remove(registeredAddresss);
				firePlayerExited(new NetworkEvent(this, player));
			}
		}
	}

	@Override
	public void receive(Message msg) {
		Object message = msg.getObject();
		if (message instanceof NCPlayerEnteringGame) {
			NCPlayerEnteringGame nc = (NCPlayerEnteringGame) message;
			Player player = nc.getPlayer();
			playerMap.put(msg.getSrc(), player);
			firePlayerEntered(new NetworkEvent(this, player));

		} else if (message instanceof NCPlayerExitingGame) {
			NCPlayerExitingGame nc = (NCPlayerExitingGame) message;
			Player player = nc.getPlayer();
			playerMap.remove(msg.getSrc());
			firePlayerExited(new NetworkEvent(this, player));

		} else if (message instanceof NCPlayerUpdated) {
			NCPlayerUpdated nc = (NCPlayerUpdated) message;
			Player player = nc.getPlayer();
			firePlayerUpdated(new NetworkEvent(this, player, nc.getCommands()));

		} else if (message instanceof NCPlayerSpawned) {
			NCPlayerSpawned nc = (NCPlayerSpawned) message;
			Player player = nc.getPlayer();
			firePlayerSpawned(new NetworkEvent(this, player));

		} else if (message instanceof NCPlayerDied) {
			NCPlayerDied nc = (NCPlayerDied) message;
			Player player = nc.getPlayer();
			firePlayerDied(new NetworkEvent(this, player));
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

	protected void firePlayerSpawned(NetworkEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == NetworkListener.class) {
				((NetworkListener) listeners[i + 1]).playerSpawned(e);
				;
			}
		}
	}

	protected void firePlayerDied(NetworkEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == NetworkListener.class) {
				((NetworkListener) listeners[i + 1]).playerDied(e);
				;
			}
		}
	}

	protected void firePlayerUpdated(NetworkEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == NetworkListener.class) {
				((NetworkListener) listeners[i + 1]).playerUpdated(e);
				;
			}
		}
	}

	protected void firePlayerEntered(NetworkEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == NetworkListener.class) {
				((NetworkListener) listeners[i + 1]).playerEntered(e);
				;
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
		return CLUSTER + clusterNum;
	}

}
