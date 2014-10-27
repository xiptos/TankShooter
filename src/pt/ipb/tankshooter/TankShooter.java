package pt.ipb.tankshooter;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import pt.ipb.game.engine.GameLoop;
import pt.ipb.game.engine.PaneledGameContainer;
import pt.ipb.tankshooter.net.NetUcastInputHandler;
import pt.ipb.tankshooter.net.NetworkEvent;
import pt.ipb.tankshooter.net.NetworkListener;
import pt.ipb.tankshooter.net.NetworkPlayer;
import pt.ipb.tankshooter.net.NetworkPlayers;
import pt.ipb.tankshooter.net.PlayerManager;
import pt.ipb.tankshooter.net.PlayersPanel;

public class TankShooter implements ActionListener, NetworkListener {
	private final static int HEIGHT = 600;
	private final static int WIDTH = 1024;
	private final static String BACKGROUND = "pt/ipb/tankshooter/resources/background";
	TankShooterGame game;
	PaneledGameContainer gameContainer;

	NetworkPlayers networkPlayers;
	PlayerManager playerManager;
	PlayersPanel playersPanel;
	KeyInputHandler keyInputHandler;
	NetUcastInputHandler netInputHandler;

	public TankShooter() {
		try {
			initGame();
			initNetwork();
			initFrame(WIDTH, HEIGHT);
			initPlayer();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initPlayer() throws Exception {
		String name = System.getProperty("user.name");
		name = JOptionPane.showInputDialog("Nome do jogador:", name);
		if (name == null) {
			name = networkPlayers.getNetworkID();
		}
		networkPlayers.register(name);
	}

	private void initNetwork() throws Exception {
		playerManager = new PlayerManager();
		networkPlayers = new NetworkPlayers(playerManager);
		playersPanel = new PlayersPanel(playerManager);
		playersPanel.addActionListener(this);
		networkPlayers.addNetworkListener(playersPanel);
		networkPlayers.addNetworkListener(this);
		networkPlayers.start();
	}

	private void initGame() throws IOException {

		int backPattern = new Random().nextInt(2);
		Image background = ImageIO.read(ClassLoader.getSystemResource(BACKGROUND + backPattern + ".png"));
		game = new TankShooterGame(background);
		gameContainer = new PaneledGameContainer(WIDTH, HEIGHT);
		gameContainer.init(new GameLoop(), game);
	}

	private void initFrame(int width, int height) {
		// create a frame to contain our game
		JFrame container = new JFrame("Tank Shooter");

		// get hold the content of the frame and set up the resolution of the
		// game
		JPanel panel = (JPanel) container.getContentPane();
		panel.add(gameContainer, BorderLayout.CENTER);
		panel.add(playersPanel, BorderLayout.WEST);

		// finally make the window visible
		container.pack();
		container.setResizable(false);
		container.setVisible(true);

		// add a listener to respond to the user closing the window. If they
		// do we'd like to exit the game
		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	}

	/**
	 * The entry point into the game. We'll simply create an instance of class
	 * which will start the display and game loop.
	 * 
	 * @param argv
	 *            The arguments that are passed into our game
	 */
	public static void main(String argv[]) {
		for(int i=0; i<5; i++)
		new TankShooter();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			networkPlayers.sendStartGame();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		start();
	}

	public void start() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		NetworkPlayer myPlayer = playerManager.getMyPlayer();
		for (NetworkPlayer player : playerManager.getPlayers().values()) {
			game.addTank(player);
		}
		networkPlayers.stop();
		try {

			TankEntity tank = game.getTank(myPlayer);
			game.setTank(tank);
			netInputHandler = new NetUcastInputHandler(game, networkPlayers.getClusterName());
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
				
				@Override
				public void run() {
					try {
						netInputHandler.sendUpdate(tank);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, 1000, 100);
			
			keyInputHandler = new KeyInputHandler(game, tank);
			gameContainer.addKeyListener(keyInputHandler);
			gameContainer.addInputHandler(keyInputHandler);
			gameContainer.addInputHandler(netInputHandler);

			gameContainer.start();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	@Override
	public void playerRegistered(NetworkEvent e) {
	}

	@Override
	public void playerUnregistered(NetworkEvent e) {
	}

	@Override
	public void gameStarted(NetworkEvent e) {
		start();
	}
}
