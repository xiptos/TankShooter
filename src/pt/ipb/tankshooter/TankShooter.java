package pt.ipb.tankshooter;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import pt.ipb.game.engine.GameLoop;
import pt.ipb.game.engine.PaneledGameContainer;
import pt.ipb.tankshooter.model.DefaultPlayerModel;
import pt.ipb.tankshooter.model.Player;
import pt.ipb.tankshooter.net.NetworkEvent;
import pt.ipb.tankshooter.net.NetworkListener;
import pt.ipb.tankshooter.net.NetworkPlayers;

public class TankShooter implements KeyListener {
	private final static int HEIGHT = 600;
	private final static int WIDTH = 1024;
	private final static String BACKGROUND = "pt/ipb/tankshooter/resources/background";

	TankShooterGame game;
	PaneledGameContainer gameContainer;
	NetworkPlayers networkPlayers;

	DefaultPlayerModel playerModel;
	private NetInputHandler netInputHandler;
	protected KeyInputHandler keyInputHandler;
	
	public TankShooter() {
		try {
			initGame();
			initNetwork();
			initFrame(WIDTH, HEIGHT);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void initGame() throws IOException {
		playerModel = new DefaultPlayerModel();
		
		int backPattern = new Random().nextInt(3);
		Image background = ImageIO.read(ClassLoader.getSystemResource(BACKGROUND + backPattern + ".png"));
		game = new TankShooterGame(WIDTH, HEIGHT, background);
		playerModel.addPlayerListener(game);

		gameContainer = new PaneledGameContainer(WIDTH, HEIGHT);
		gameContainer.addKeyListener(this);
		
		gameContainer.init(new GameLoop(), game);
	}

	private void initNetwork() throws Exception {
		networkPlayers = new NetworkPlayers(playerModel);
		
		networkPlayers.addNetworkListener(new NetworkListener() {
			
			@Override
			public void playerUpdated(NetworkEvent e) {
			}
			
			@Override
			public void playerExited(NetworkEvent e) {
				playerModel.removePlayer(e.getPlayer());
			}
			
			@Override
			public void playerEntered(NetworkEvent e) {
				playerModel.addPlayer(e.getPlayer());
			}
		});
		
		netInputHandler = new NetInputHandler(game);
		gameContainer.addInputHandler(netInputHandler);

		networkPlayers.addNetworkListener(netInputHandler);
		
		networkPlayers.start();
	}



	private void initPlayer() throws Exception {
		SwingWorker<Player, Void> worker = new SwingWorker<Player, Void>() {
		    @Override
		    public Player doInBackground() {
				String name = System.getProperty("user.name");
				name = JOptionPane.showInputDialog("Nome do jogador:", name);
				if (name == null) {
					name = networkPlayers.getNetworkID();
				}
				Player player = new Player(name);
				player.setX(10);
				player.setY(50 + 50 * playerModel.getPlayerCount());
				return player;
		    }

		    @Override
		    public void done() {
		    	try {
					Player player = get();

					playerModel.addPlayer(player);
					playerModel.setSelectedPlayer(player);
					networkPlayers.enterGame(player);
					
					keyInputHandler = new KeyInputHandler(game, game.getTank(player));
					gameContainer.addInputHandler(keyInputHandler);

					gameContainer.addKeyListener(keyInputHandler);
					gameContainer.addKeyListener(new KeyBasedNetCommandGenerator(game, game.getTank(player), networkPlayers));

				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		};
		worker.execute();
	}

	private void initFrame(int width, int height) {
		
		// create a frame to contain our game
		JFrame container = new JFrame("Tank Shooter");

		// get hold the content of the frame and set up the resolution of the
		// game
		JPanel panel = (JPanel) container.getContentPane();
		panel.add(gameContainer, BorderLayout.CENTER);
		PlayersTableModel tableModel = new PlayersTableModel(playerModel);
		playerModel.addPlayerListener(tableModel);
		PlayersPanel playersPanel = new PlayersPanel(tableModel);
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
		gameContainer.start();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(game.isPlaying()) {
			return;
		}
		if (e.getKeyCode() == KeyEvent.VK_F5) {
			try {
				initPlayer();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
