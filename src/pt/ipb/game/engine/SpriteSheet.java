package pt.ipb.game.engine;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class represents a sprite sheet.
 * @author rlopes
 *
 */
public class SpriteSheet {
	private static BufferedImage spriteSheet;
	
	int tileWidth, tileHeight;

	private int frameDelay;
	
	public SpriteSheet(String file, int tileSize, int frameDelay) {
		this(file, tileSize, tileSize, frameDelay);
	}
	
    public SpriteSheet(String file, int tileWidth, int tileHeight, int frameDelay) {
    	this.tileHeight = tileHeight;
    	this.tileWidth = tileWidth;
    	this.frameDelay = frameDelay;
    	spriteSheet = loadSpriteSheet(file);
	}

    private BufferedImage loadSpriteSheet(String file) {

        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(ClassLoader.getSystemResource(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprite;
    }

    public Sprite getSprite(int xGrid, int yGrid) {
        if (spriteSheet != null) {
        	BufferedImage image = spriteSheet.getSubimage(xGrid * tileWidth, yGrid * tileHeight, tileWidth, tileHeight); 
        	Sprite sprite = new Sprite(image, frameDelay);
        	return  sprite;
        }
        return null;
    }
}
