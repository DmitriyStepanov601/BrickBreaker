package parts;
import main.BrickBreaker;
import main.ImageLoader;
import java.awt.*;
import java.util.Random;

/**
 * Class describing a brick
 * @author Dmitriy Stepanov
 */
public class Brick {
	public ImageLoader loader;
	private final int width = 50;
	private final int height = 25;
	private int x;
	private int y;
	public int level;
	public int originalLevel;
	private final boolean addScore = false;
	public boolean dropPowerup = false;
	public boolean hasDied = false;
	public Rectangle bounds;
	private final Random rand;
	private int powerup = 0;

	/**
	 * Constructor - creating a new ball
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param level - the level of bricks (from 0 to 7)
	 * @see Brick#Brick(int,int,int)
	 */
	public Brick(int x, int y, int level) {
		this.x = x;
		this.y = y;
		originalLevel = level;
		this.level = level;
		rand = new Random();

		if(level > 0) {
			powerup = rand.nextInt(6);
		}

		bounds = new Rectangle(x, y, width, height);
		loader = new ImageLoader(ImageLoader.brick);
	}

	public Image getImage() {
			return loader.getSubImage(level);
		}

	public void setX(int pos) {
		if(pos >= 0 && pos < BrickBreaker.WIDTH) {
			this.x = pos;
		}
	}

	public void setY(int pos) {
		if(pos >= 0 && pos < BrickBreaker.HEIGHT) {
			this.y = pos;
		}
	}

	public int getX() {
			return this.x;
		}
	public int getY() {
			return this.y;
		}
	public int getWidth() {
			return this.width;
		}
	public int getHeight() {
			return this.height;
		}

	public void hasCollided() {
		if(level >= 1) {
			level--;

			if(level == 0) {
				hasDied = true;

				if(hasPowerup() > 0) {
					dropPowerup = true;
				}
			}
		}
	}

	public void destroyed(){
		level = 0;
		hasDied = true;

		if(hasPowerup() > 0) {
			dropPowerup = true;
		}
	}

	public int hasPowerup() {
			return powerup;
		}
	public void render(Graphics g) {
			g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
		}
}