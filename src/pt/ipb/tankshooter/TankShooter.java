package pt.ipb.tankshooter;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import pt.ipb.game.engine.GameLoop;
import pt.ipb.game.engine.PaneledGameContainer;
import pt.ipb.tankshooter.net.NetworkEvent;
import pt.ipb.tankshooter.net.NetworkListener;
import pt.ipb.tankshooter.net.NetworkPlayers;
import pt.ipb.tankshooter.net.Player;
import pt.ipb.tankshooter.net.PlayerManager;
import pt.ipb.tankshooter.net.PlayersPanel;

public class TankShooter implements NetworkListener {
	private final static int HEIGHT = 600;
	private final static int WIDTH = 1024;
	private final static String BACKGROUND = "pt/ipb/tankshooter/resources/background";
	TankShooterGame game;
	PaneledGameContainer gameContainer;

	NetworkPlayers networkPlayers;
	PlayerManager playerManager;
	PlayersPanel playersPanel;
	KeyInputHandler keyInputHandler;
	NetInputHandler netInputHandler;

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
		Player player = new Player(name);
		networkPlayers.enterGame(player);
		game.addTank(player);
		game.setTank(game.getTank(player));
		keyInputHandler = new KeyInputHandler(game, game.getTank(player));

		gameContainer.addKeyListener(keyInputHandler);
		gameContainer.addKeyListener(new KeyBasedNetCommandGenerator(game, game.getTank(player), networkPlayers));
		gameContainer.addInputHandler(keyInputHandler);

	}

	private void initNetwork() throws Exception {
		playerManager = new PlayerManager();
		networkPlayers = new NetworkPlayers(playerManager);
		playersPanel = new PlayersPanel(playerManager);
		networkPlayers.addNetworkListener(netInputHandler);
		networkPlayers.addNetworkListener(this);
		networkPlayers.start();
	}

	private void initGame() throws IOException {

		int backPattern = new Random().nextInt(2);
		Image background = ImageIO.read(ClassLoader.getSystemResource(BACKGROUND + backPattern + ".png"));
		game = new TankShooterGame(WIDTH, HEIGHT, background);
		gameContainer = new PaneledGameContainer(WIDTH, HEIGHT);
		gameContainer.init(new GameLoop(), game);

		netInputHandler = new NetInputHandler(game);
		gameContainer.addInputHandler(netInputHandler);
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
		for (int i = 0; i < 1; i++)
			new TankShooter().start();
	}

	public void start() {
		try {
			networkPlayers.updateState();
		} catch (Exception e) {
			e.printStackTrace();
		}
		gameContainer.start();
	}

	@Override
	public void playerEntered(NetworkEvent e) {
		game.addTank(e.getPlayer());
	}

	@Override
	public void playerExited(NetworkEvent e) {
		game.removeTank(e.getPlayer());
	}

	@Override
	public void playerUpdated(NetworkEvent e) {
	}

}
