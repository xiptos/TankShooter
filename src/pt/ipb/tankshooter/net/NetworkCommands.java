package pt.ipb.tankshooter.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.blocks.MessageDispatcher;
import org.jgroups.blocks.RequestHandler;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.util.Util;

import pt.ipb.game.engine.Command;
import pt.ipb.game.engine.InputHandler;

public class NetworkCommands extends ReceiverAdapter implements InputHandler, RequestHandler {
	private static final String CLUSTER = "TankShooter";
	int clusterNum = 0;
	JChannel channel;

	List<Command> commandList;
	private PlayerManager playerManager;
	private MessageDispatcher dispatcher;

	public NetworkCommands(PlayerManager playerManager) throws Exception {
		this.playerManager = playerManager;
		commandList = new ArrayList<>();
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

	public void turnRight(String name) throws Exception {
		Message msg = new Message(null, null, "right:" + name);
		channel.send(msg);
	}

	public void turnLeft(String name) throws Exception {
		Message msg = new Message(null, null, "left:" + name);
		channel.send(msg);
	}

	public void forward(String name) throws Exception {
		Message msg = new Message(null, null, "fw:" + name);
		channel.send(msg);
	}

	public void fire(String name) throws Exception {
		Message msg = new Message(null, null, "fire:" + name);
		channel.send(msg);
	}

	public void register(String name) throws Exception {
		Address coordinator = channel.getView().getMembers().get(0);
		List<Address> addrs = new ArrayList<>();
		addrs.add(coordinator);

		NetworkPlayer player = dispatcher.sendMessage(new Message(coordinator, new String("register:" + name)),
				RequestOptions.SYNC());
		playerManager.addPlayer(player);

		updatePlayerList(player);
	}

	public void updatePlayerList(NetworkPlayer player) throws Exception {
		dispatcher.castMessage(null, new Message(null, player), RequestOptions.ASYNC());
		System.out.println("MESSAGE SENTTTTTTTT");
//		channel.send(null, player);
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
		if (message instanceof String) {
			String str = (String) msg.getObject();
			if (str.startsWith("register:")) {
				String name = str.substring("register:".length());
				NetworkPlayer player = playerManager.createPlayer(channel.getAddressAsUUID(), name,
						channel.getAddress());
				playerManager.addPlayer(player);
				updatePlayerList(player);
				return player;
			} else {
				throw new Exception("Could not create tank entity");
			}
		} else if(message instanceof NetworkPlayer) {
			// update player list
			playerManager.addPlayer((NetworkPlayer) message);
			
		}
		return null;
	}

	@Override
	public void receive(Message msg) {
		System.out.println("MESSAGE RECEIVED!!!");
		Object message = msg.getObject();
		System.out.println("Received message of the type: " + message.getClass().getName());
		if (message instanceof NetworkPlayer) {
			// update player list
			playerManager.addPlayer((NetworkPlayer) message);
		} else if (message instanceof String) {
			String str = (String) msg.getObject();
			if (str.startsWith("right:")) {
				String name = str.substring("right:".length());
			}
		}
	}

	@Override
	public List<Command> handleInput() {
		return commandList;
	}

	public String getNetworkID() {
		return channel.getName(channel.getAddress());
	}

}
