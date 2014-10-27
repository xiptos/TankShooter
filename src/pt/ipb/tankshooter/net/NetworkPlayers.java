package pt.ipb.tankshooter.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.event.EventListenerList;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.blocks.MessageDispatcher;
import org.jgroups.blocks.RequestHandler;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.util.Util;

public class NetworkPlayers extends ReceiverAdapter implements RequestHandler {
	private static final String CLUSTER = "TankShooter";
	int clusterNum = 0;
	JChannel channel;

	EventListenerList listenerList = new EventListenerList();

	private PlayerManager playerManager;
	private MessageDispatcher dispatcher;

	public NetworkPlayers(PlayerManager playerManager) {
		this.playerManager = playerManager;
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
		dispatcher = new MessageDispatcher(channel, this);
	}

	public void register(String name) throws Exception {
		Address coordinator = channel.getView().getMembers().get(0);
		List<Address> addrs = new ArrayList<>();
		addrs.add(coordinator);

		NetworkPlayer player = dispatcher.sendMessage(new Message(coordinator, new NCRegisterPlayer(name)),
				RequestOptions.SYNC());
		playerManager.addPlayer(player);
		playerManager.setMyPlayer(player);

		updatePlayerList(player);
	}

	public void updatePlayerList(NetworkPlayer player) throws Exception {
		dispatcher.castMessage(null, new Message(null, new NCPlayerRegistered(player)), RequestOptions.ASYNC());
	}

	public void stop() {
		channel.close();
		dispatcher.stop();
	}

	@Override
	public void getState(OutputStream output) throws Exception {
		Util.objectToStream(playerManager.getPlayers(), new DataOutputStream(output));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setState(InputStream input) throws Exception {
		Map<Integer, NetworkPlayer> players;
		players = (Map<Integer, NetworkPlayer>) Util.objectFromStream(new DataInputStream(input));
		playerManager.setPlayers(players);
	}

	@Override
	public Object handle(Message msg) throws Exception {
		Object message = msg.getObject();
		if (message instanceof NCRegisterPlayer) {
			String name = ((NCRegisterPlayer) message).getName();
			NetworkPlayer player = playerManager.createPlayer(channel.getAddressAsUUID(), name, channel.getAddress());
			playerManager.addPlayer(player);
			updatePlayerList(player);
			firePlayerRegistered(new NetworkEvent(this, player));
			return player;
		} else if (message instanceof NCPlayerRegistered) {
			// update player list
			playerManager.addPlayer(((NCPlayerRegistered) message).getPlayer());

		} else if ("START".equals(message)) {
			fireGameStarted(new NetworkEvent(this));
		}
		return null;
	}

	public String getNetworkID() {
		return channel.getName(channel.getAddress());
	}

	public boolean isCoordinator() {
		if (channel.getView().getMembers().isEmpty()) {
			return false;
		}
		return channel.getAddress().equals(channel.getView().getMembers().get(0));
	}

	public void addNetworkListener(NetworkListener l) {
		listenerList.add(NetworkListener.class, l);
	}

	public void removeNetworkListener(NetworkListener l) {
		listenerList.remove(NetworkListener.class, l);
	}

	protected void fireGameStarted(NetworkEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == NetworkListener.class) {
				((NetworkListener) listeners[i + 1]).gameStarted(e);;
			}
		}
	}

	protected void firePlayerRegistered(NetworkEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == NetworkListener.class) {
				((NetworkListener) listeners[i + 1]).playerRegistered(e);;
			}
		}
	}

	protected void firePlayerUnregistered(NetworkEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == NetworkListener.class) {
				((NetworkListener) listeners[i + 1]).playerUnregistered(e);
			}
		}
	}

	public String getClusterName() {
		return CLUSTER+clusterNum;
	}

	public void sendStartGame() throws Exception {
		dispatcher.castMessage(null, new Message(null, "START"), RequestOptions.ASYNC());
	}

}
