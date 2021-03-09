package gameobjects;
import main.BrickBreaker;
import main.ImageLoader;
import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A class that describes the behavior of the ball
 * @author Dmitriy Stepanov
 */
public class Ball {
	private ImageLoader loader;
	private final int width = 23;
	private final int height = 23;
	private int x;
	private int y;

	private double dx = 0.25;
	private double dy = -6;
	private final int dir = 0;
	private final int speed;

	private Rectangle bounds;
	private final Random rand;
	private final Timer timer;
	private boolean onFire = false;
	private int fireSec = 0;

	/**
	 * Constructor - creating a new ball
	 * @param x - X coordinate
	 * @param y - Y coordinate
	 * @param onFire - the condition of the ball
	 * @see Ball#Ball(int,int,boolean)
	 */
	public Ball(int x, int y, boolean onFire) {
		this.x = x;
		this.y = y;
		this.onFire = onFire;
		rand = new Random();
		this.speed = 7;
		dx = 0;
		bounds = new Rectangle(x, y, width, height);
		loader = new ImageLoader(ImageLoader.ball);
		timer = new Timer();
	}

	public Image getImage() {
			return loader.getImage();
		}

	public void setX(int pos) {
		if(pos > 0 && pos < BrickBreaker.WIDTH) {
			this.x = pos;
		}
	}

	public void setY(int pos) {
		if(pos > 0 && pos < BrickBreaker.WIDTH) {
			this.y = pos;
		}
	}

	public int getX() {
			return this.x;
		}
	public int getY() {
			return this.y;
		}

	public void setOnFire(int seconds) {
		if(!onFire) {
			fireSec = seconds;
			onFire = true;
			timer.schedule(new RemindTask(), seconds * 1000L);
			loader = new ImageLoader(ImageLoader.fireBall);
		}
	}

	class RemindTask extends TimerTask {
		public void run() {
			onFire = false;
			loader = new ImageLoader(ImageLoader.ball);
		}
	}

	public void tick() {
		if (x + dx < 0 || x + width + dx > BrickBreaker.WIDTH) {
			dx *= -1;
		}

		if (y + dy < 0 || y + height + dy > BrickBreaker.HEIGHT) {
			dy *= -1;
			if(dx == 0) {
				dx = 1.5;
			}
		}

		x += dx;
		y += dy;
		bounds = new Rectangle(x, y, width, height);
	}

	public void checkBrickCollision(Brick[] bricks) {
		for (Brick brick : bricks) {
			if (brick.level > 0) {
				if (bounds.intersects(brick.bounds)) {
					if (!onFire) {
						switchDirections(brick);
						brick.hasCollided();
					} else {
						brick.destroyed();
					}
				}
			}
		}
	}

	public void checkPaddleCollision(Paddle paddle) {
		if(bounds.intersects(paddle.bounds)) {
			hitPaddle(paddle);
		}
	}

	public void switchDirections(Brick brick) {
		int random = rand.nextInt(3);
		double randomSpeed = rand.nextInt(3);

		if(random == 1) {
			if(dx < 10) {
				dx += randomSpeed;
			} else {
				dx -= randomSpeed;
			}
		}

		if(random == 2) {
			if(dx > -10) {
				dx += randomSpeed;
			} else {
				dx -= randomSpeed;
			}
		}

		if (x + dx < brick.getX() || x + width + dx > brick.getX() + brick.getWidth()) {
			dx *= -1;
		}

		if (y + dy < brick.getY() || y + height + dy > brick.getY() + brick.getHeight()) {
			dy *= -1;
		}

		x += dx;
		y += dy;
		bounds = new Rectangle(x, y, width, height);
	}

	public void hitPaddle(Paddle paddle) {
		if(y + height - 5 < paddle.getY()) {
			if (x + dx < paddle.getX() || x + width + dx > paddle.getX() + paddle.getWidth()) {
				dx *= -1;
			}

			if (y + dy < paddle.getY() || y + height + dy > paddle.getY() + paddle.getHeight()) {
				dy *= -1;
	        }
		}

		x += dx;
		y += dy;
	}

	public void render(Graphics g) {
		g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
	}
}