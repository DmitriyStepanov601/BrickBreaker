package gameobjects;
import main.BrickBreaker;
import main.ImageLoader;
import java.awt.*;

/**
 * Class describing the paddle
 * @author Dmitriy Stepanov
 */
public class Paddle {
	private final ImageLoader loader;
	public Rectangle bounds;
	private final int width = 100;
	private final int height = 30;
	private int x;
	private final int y;
	private final int moveSpeed = 10;

	/**
	 * Constructor - creating a new ball
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @see Paddle#Paddle(int,int)
	 */
	public Paddle(int x, int y) {
		this.x = x;
		this.y = y;
		bounds = new Rectangle(x, y, width, height);
		loader = new ImageLoader(ImageLoader.paddle);
	}

	public Image getImage() {
		return loader.getImage();
	}

	public void setX(int pos) {
		if(pos > 0 && pos < BrickBreaker.WIDTH) {
			this.x = pos;
		}
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}

	public boolean isColliding(Powerup powerup) {
		if(powerup != null) {
			return bounds.intersects(powerup.bounds);
		}
		return false;
	}

	public void moveLeft() {
		if(x > 0) {
			x -= moveSpeed;
		}

		bounds = new Rectangle(x, y, width, height);
	}

	public void moveRight() {
		if(x + width < BrickBreaker.WIDTH) {
			x += moveSpeed;
		}

		bounds = new Rectangle(x, y, width, height);
	}

	public void render(Graphics g) {
		g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
	}
}