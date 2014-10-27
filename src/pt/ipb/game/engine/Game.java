package pt.ipb.game.engine;

import java.awt.Graphics2D;

/**
 * A interface for a game.
 * 
 * Each game should implement this interface.
 * 
 * @author Eric
 */
public interface Game
{
    /**
     * Runs the startup logic.
     *
     * Startup is called by the game container before the first call to update
     * or draw.
     * @param gc game container calling startup.
     */
    public void startup(GameContainer gc);

    /**
     * Runs the update logic.
     * @param gc game container calling update.
     * @param delta time since the last update call.
     */
    public void update(GameContainer gc, double delta);

    /**
     * Runs the draw logic.
     * @param gc game container calling the draw.
     * @param g graphics object for the current frame.
     */
    public void draw(GameContainer gc, Graphics2D g);

    /**
     * Runs the shutdown logic.
     *
     * Shutdown is called by the game container after the last call to update
     * or draw.
     * @param gc game container calling the shutdown.
     */
    public void shutdown(GameContainer gc);
}
