package main;
import java.awt.*;
import javax.swing.JPanel;

import levelfiles.Level;
import handlers.KeyHandler;
import handlers.MouseHandler;
import gameobjects.Ball;
import gameobjects.Brick;
import gameobjects.Paddle;
import gameobjects.Powerup;

/**
 * A class that describes the logic of the game
 * @author Dmitriy Stepanov
 */
public class Game extends JPanel {
	private final Paddle paddle;
	private final Ball[] balls;
	private Brick[] bricks;
	private Image lives;
	private Powerup[] powerups;

	private final int[][] gridPos;
 	private int score = 0;
 	private boolean hasStarted = false;
	private int livesLeft = 3;
 	private int totalBallCount;
 	private int totalBricks = 0;

 	private final boolean debug = false;
 	private final int level;
 	private final Rectangle back = new Rectangle(0, BrickBreaker.HEIGHT - 60, 30, 30);
	private final Rectangle back2 = new Rectangle(BrickBreaker.WIDTH - 40,
			BrickBreaker.HEIGHT - 60, 30, 30);
	private final Image backArrow;
	private final Image backArrow2;

	/**
	 * Constructor - creating new game logic
	 * @param level - level the game
	 * @see Game#Game(int)
	 */
	public Game(int level) {
		paddle = new Paddle(BrickBreaker.WIDTH / 2 - 50, 640);
		balls = new Ball[3];

		backArrow = new ImageLoader(ImageLoader.arrow).getImage();
		backArrow2 = new ImageLoader(ImageLoader.arrow2).getImage();
		balls[0] = new Ball(paddle.getX() + paddle.width / 2 - 12,
				paddle.getY() - paddle.height / 2 - 10, false);

		totalBallCount = 1;
		this.level = level;
		gridPos = Level.getLevel(level);

		for (int[] gridPo : gridPos) {
			for (int j = 0; j < gridPos[0].length; j++) {
				if (gridPo[j] != 0) {
					totalBricks++;
				}
			}
		}

		init();
	}

	private void init() {
		int count = 0;
		lives = new ImageLoader(ImageLoader.ball).getImage();
		bricks = new Brick[96];
		powerups = new Powerup[5];

		for(int i = 0; i < gridPos.length; i++) {
			for(int j = 0; j < gridPos[0].length; j++) {
				bricks[count] = new Brick(j * 50 + 50, i * 25 + 25, gridPos[i][j]);
				count++;
			}
		}
	}

	public void tick() {
		if(hasStarted) {
			for(int i = 0; i < balls.length; i++) {
				if(balls[i] != null) {
					balls[i].tick();
					balls[i].checkBrickCollision(bricks);
					balls[i].checkPaddleCollision(paddle);

					if(balls[i].getY() > BrickBreaker.HEIGHT - 50) {
						if(totalBallCount <= 1) {
							hasStarted = false;
							balls[i] = new Ball(paddle.getX() + paddle.width / 2 - 12,
									paddle.getY() - paddle.height / 2 - 10, false);
							livesLeft -= 1;

							if(livesLeft < 0) {
								Controller.score = score;
								Controller.switchStates(Controller.STATE.GAMEOVER);
							}
						} else {
							balls[i] = null;
							totalBallCount--;
						}
					}
				}
			}

			for (Brick brick : bricks) {
				if (brick.hasDied) {
					totalBricks--;
					score += brick.originalLevel;

					if (totalBricks == 0) {
						Controller.score = score;
						Controller.switchStates(Controller.STATE.WINSCREEN);
					}

					brick.hasDied = false;
				}

				if (brick.dropPowerup) {
					for (int j = 0; j < powerups.length; j++) {
						if (powerups[j] == null) {
							powerups[j] = new Powerup(brick.getX(), brick.getY(), brick.hasPowerup());
							brick.dropPowerup = false;
							break;
						}
					}
				}

				brick.dropPowerup = false;
			}

			for(int i = 0; i < powerups.length; i++) {
				if(powerups[i] != null) {
					powerups[i].tick();

					if(powerups[i].remove) {
						powerups[i] = null;
					}

					if(paddle.isColliding(powerups[i])) {
						if(powerups[i].powerup == 1) {
							for(int j = 0; j < balls.length; j++) {
								if(balls[j] == null) {
									balls[j] = new Ball(BrickBreaker.WIDTH / 2, 350, false);
									totalBallCount++;
									break;
								}
							}
						}

						if(powerups[i].powerup == 2) {   //grow paddle
							paddle.width += 15;
						}

						if(powerups[i].powerup == 3) {   //fire ball
							for (Ball ball : balls) {
								if (ball != null) {
									ball.setOnFire(4);
									break;
								}
							}
						}

						powerups[i] = null;
					}
				}
			}
		} else {
			for (Ball ball : balls) {
				if (ball != null) {
					ball.setX(paddle.getX() + paddle.width / 2 - ball.width / 2);
				}
			}

			for(int i = 0; i < powerups.length; i++) {
				if(powerups[i] != null) {
					powerups[i] = null;
				}
			}

			if(KeyHandler.UP) {
				hasStarted = true;
			}
		}

		if(KeyHandler.LEFT) {
			paddle.moveLeft();
		}

		if(KeyHandler.RIGHT) {
			paddle.moveRight();
		}

		if (MouseHandler.MOUSEDOWN) {
			if (back.contains(Controller.mousePoint)) {
				Controller.switchStates(Controller.STATE.PICKUSERLEVEL);
			}

			if (back2.contains(Controller.mousePoint)) {
				Controller.switchStates(Controller.STATE.PICKLEVEL);
			}

			MouseHandler.MOUSEDOWN = false;
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(0, 0, 700, 740);
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

		for (Powerup powerup : powerups) {
			if (powerup != null) {
				g.drawImage(powerup.getImage(), powerup.getX(), powerup.getY(), powerup.getWidth(),
						powerup.getWidth(), null);
			}
		}

		g.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(), paddle.getWidth(),
				paddle.getHeight(), null);

		for (Ball ball : balls) {
			if (ball != null) {
				g.drawImage(ball.getImage(), ball.getX(), ball.getY(), null);
			}
		}

		g.setColor(Color.WHITE);

		if(debug) {
			for (Brick brick : bricks) {
				brick.render(g);
			}
		}

		g.setColor(Color.yellow);
		g.fillRect(0, BrickBreaker.HEIGHT - 60, BrickBreaker.WIDTH, 160);
		g.setColor(Color.black);
		g.setFont(Controller.smallFont);
		g.drawString("Score: " + score, 50, BrickBreaker.HEIGHT - 42);
		g.drawImage(lives, 130, BrickBreaker.HEIGHT - 55, 15, 15, null);
		g.drawString(": " + livesLeft, 150, BrickBreaker.HEIGHT - 42);
		g.drawImage(backArrow, back.x, back.y, back.width, back.height, null);
		g.drawImage(backArrow2, back2.x, back2.y, back2.width, back2.height, null);
	}
}
