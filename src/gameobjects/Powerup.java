package gameobjects;
import main.BrickBreaker;
import main.ImageLoader;
import java.awt.*;

/**
 * A class that describes the type of power up
 * @author Dmitriy Stepanov
 */
public class Powerup {
	public int powerup;
	public Rectangle bounds;
	private final int x;
	private int y;

	private final int width = 25;
	private final int height = 25;
	public boolean remove = false;
	private final int speed = 2;
	private Image image;

	/**
	 * Constructor - creating a new power up
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param type - type of ball
	 * @see Powerup#Powerup(int,int,int)
	 */
	public Powerup(int x, int y, int type) {
		powerup = type;
		this.x = x;
		this.y = y;
		bounds = new Rectangle(x, y, width, height);

		if(powerup == 1) {
			image = new ImageLoader(ImageLoader.PUMultiBall).getImage();
		}

		if(powerup == 2) {
			image = new ImageLoader(ImageLoader.PUGrowth).getImage();
		}

		if(powerup == 3) {
			image = new ImageLoader(ImageLoader.PUFireball).getImage();
		}
	}

	public void tick() {
		if(y < BrickBreaker.HEIGHT) {
			y += speed;
			bounds = new Rectangle(x, y, width, height);
		} else {
			remove = true;
		}
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public Image getImage() {
		return image;
	}
}