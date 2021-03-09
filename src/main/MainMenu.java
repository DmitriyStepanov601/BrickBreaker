package main;
import handlers.MouseHandler;
import gameobjects.Ball;
import gameobjects.Brick;
import gameobjects.Paddle;

import java.awt.*;

/**
 * A class describing an example game
 * @author Dmitriy Stepanov
 */
public class MainMenu {
	private final Rectangle bound = new Rectangle(110, 460, 250, 45);
	private final Image titleScreenForeground;
	private final Image titleScreenBackground;

	private final Paddle paddle;
	private Ball ball;
	private final Brick[] bricks;
	private final int[][] gridPos = {
			{7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7},
			{6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6},
			{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5},
			{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
			{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
			{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
	};

	/**
	 * Constructor - creating a new example game
	 * @see MainMenu#MainMenu()
	 */
	public MainMenu() {
		titleScreenForeground = new ImageLoader(ImageLoader.titleForeground).getImage();
		titleScreenBackground = new ImageLoader(ImageLoader.titleScreenBackground).getImage();
		paddle = new Paddle(BrickBreaker.WIDTH / 2 + 50, 640);
		ball = new Ball(paddle.getX() + paddle.width / 2 - 12,
				paddle.getY() - paddle.height / 2 - 10, false);
		bricks = new Brick[66];
		int count = 0;

		for(int i = 0; i < gridPos.length; i++) {
			for(int j = 0; j < gridPos[0].length; j++) {
				bricks[count] = new Brick(j * 50 + 75, i * 25 + 75, gridPos[i][j]);
				count++;
			}
		}
	}

	public void tick() {
		if(bound.contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
			Controller.switchStates(Controller.STATE.PICKLEVEL);
		}

		tickGame();
	}

	private void tickGame() {
		ball.tick();
		ball.checkBrickCollision(bricks);
		ball.checkPaddleCollision(paddle);

		if(ball.getY() > BrickBreaker.HEIGHT - 50) {
			ball = new Ball(paddle.getX() + paddle.width / 2 - 12,
					paddle.getY() - paddle.height / 2 - 10,
						false);
		}

		if((ball.getX() < paddle.getX() + paddle.getWidth() / 2) && paddle.getX() > 70) {
			paddle.moveLeft();
		}

		if((ball.getX() > paddle.getX() + paddle.getWidth() / 2) &&
				paddle.getX() + paddle.getWidth() < 630) {
			paddle.moveRight();
		}
	}

	public void render(Graphics g) {
		g.drawImage(titleScreenBackground, 0, 0, BrickBreaker.WIDTH,
				BrickBreaker.HEIGHT, null);
		g.setColor(Color.black);

		if (bound.contains(Controller.mousePoint)) {
			g.drawRect(bound.x, bound.y, bound.width, bound.height);
		}

		int count = 0;
		for (int[] gridPo : gridPos) {
			for (int j = 0; j < gridPos[0].length; j++) {
				if (gridPo[j] > 0) {
					g.drawImage(bricks[count].getImage(), bricks[count].getX(),
							bricks[count].getY(), null);
				}
				count++;
			}
		}

		g.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(), null);
		g.drawImage(ball.getImage(), ball.getX(), ball.getY(), null);
		g.drawImage(titleScreenForeground, 0, 0, BrickBreaker.WIDTH,
				BrickBreaker.HEIGHT, null);
	}
}