package pt.ipb.game.engine;

import java.util.logging.Logger;

/**
 * A manager for the game loop.
 * 
 * @author Eric
 */
public class GameLoop {
	private double minDelta;
	private double maxDelta;
	private double maxUpdateDelay;
	private double maxDrawDelay;
	private boolean runFlag = false;
	private boolean paused = false;
	private PerformanceMeter meter = new PerformanceMeter();

	/**
	 * Constructs the default game loop.
	 */
	public GameLoop() {
		this(1.0 / 60.0);
	}

	/**
	 * Constructs a fixed delta game loop.
	 * 
	 * @param delta
	 */
	public GameLoop(double delta) {
		this(delta, delta);
	}

	/**
	 * Constructs a variable delta game loop.
	 * 
	 * @param minDelta
	 *            minimum time between updates.
	 * @param maxDelta
	 *            maximum time between updates.
	 */
	public GameLoop(double minDelta, double maxDelta) {
		this(minDelta, maxDelta, 0.5, 0.5);
	}

	/**
	 * Constructs a game loop.
	 * 
	 * @param minDelta
	 *            the minimum time between updates.
	 * @param maxDelta
	 *            the maximum time between updates.
	 * @param maxUpdateDelay
	 *            the maximum the game loop may run behind.
	 * @param maxDrawDelay
	 *            the maximum time between calls to draw.
	 */
	public GameLoop(double minDelta, double maxDelta, double maxUpdateDelay, double maxDrawDelay) {
		// error checking on input parameters
		if (minDelta < 0.0)
			Logger.getLogger(GameLoop.class.getName()).severe("minDelta < 0");
		if (maxDelta < minDelta)
			Logger.getLogger(GameLoop.class.getName()).severe("maxDelta < minDelta");
		if (maxUpdateDelay < 0)
			Logger.getLogger(GameLoop.class.getName()).severe("maxUpdateDelay < 0");
		if (maxDrawDelay < 0)
			Logger.getLogger(GameLoop.class.getName()).severe("maxDrawDelay < 0");

		// assign input parameters
		this.minDelta = minDelta;
		this.maxDelta = maxDelta;
		this.maxUpdateDelay = maxUpdateDelay;
		this.maxDrawDelay = maxDrawDelay;
	}

	/**
	 * Gets the maximum delta.
	 * 
	 * @return the maximum delta.
	 */
	public double getMaxDelta() {
		return maxDelta;
	}

	/**
	 * Gets the maximum draw delay.
	 * 
	 * @return the maximum draw delay.
	 */
	public double getMaxDrawDelay() {
		return maxDrawDelay;
	}

	/**
	 * Gets the maximum update delay.
	 * 
	 * @return the maximum update delay.
	 */
	public double getMaxUpdateDelay() {
		return maxUpdateDelay;
	}

	/**
	 * Gets the minimum delta.
	 * 
	 * @return the minimum delta.
	 */
	public double getMinDelta() {
		return minDelta;
	}

	public PerformanceMeter getPerformanceMeter() {
		return meter;
	}

	/**
	 * Begins the game loop.
	 * 
	 * @param gc
	 *            the game container to be managed.
	 * @param game
	 *            the game to be managed.
	 */
	public void start(GameContainer gc, Game game) {
		Logger.getLogger(GameLoop.class.getName()).fine(getClass().getCanonicalName() + ": Starting game loop");
		if (gc == null)
			Logger.getLogger(GameLoop.class.getName()).severe("loopable == null");

		runFlag = true;
		paused = false;

		gc.init(this, game);
		// convert the time to seconds
		double nextTime = getTimeInSeconds();
		double lastDrawTime = nextTime - maxDrawDelay;

		while (runFlag) {
			// convert the time to seconds
			double currTime = getTimeInSeconds();
			double delta = currTime - nextTime;
			if (delta > maxUpdateDelay) {
				Logger.getLogger(GameLoop.class.getName()).info(
						getClass().getCanonicalName() + ": Game is running slow, resyncing game clock");
				nextTime = currTime;
			}

			if (paused) {
				nextTime = currTime;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// do nothing
				}
			} else if (currTime >= nextTime) {
				// assign the time for the next update
				nextTime = Math.min(currTime + minDelta, nextTime + maxDelta);
				// limit delta range
				delta = Math.max(Math.min(delta, maxDelta), minDelta);
				meter.beginUpdate();
				gc.update(delta);
				if ((currTime < nextTime) || ((currTime - lastDrawTime) > maxDrawDelay)) {
					meter.beginDraw();
					gc.draw();
					lastDrawTime = currTime;
				}
			} else {
				meter.beginSleep();
				// calculate the time to sleep in ms
				currTime = getTimeInSeconds();
				if (currTime < nextTime) {
					// sleep thread
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// do nothing
					}
				}
			}
		}

		Logger.getLogger(GameLoop.class.getName()).fine(getClass().getCanonicalName() + ": Game loop stopping");
		gc.shutdown();
	}

	/**
	 * Convenience method to convert nanoseconds to seconds.
	 * 
	 * @return the time in seconds.
	 */
	private double getTimeInSeconds() {
		return (double) System.nanoTime() / 1000000000.0;
	}

	/**
	 * Call to halt the main loop after the current update or draw.
	 */
	public void stop() {
		Logger.getLogger(GameLoop.class.getName()).fine(getClass().getCanonicalName() + ": Game loop stop requested");
		runFlag = false;
	}

	public void setPaused(boolean isPaused) {
		paused = isPaused;
	}

	public boolean getPaused() {
		return paused;
	}
}