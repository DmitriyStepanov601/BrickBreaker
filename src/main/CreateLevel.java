package main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import levelfiles.Files;
import handlers.MouseHandler;
import gameobjects.Brick;

/**
 * A class that describes how to create a level
 * @author Dmitriy Stepanov
 */
public class CreateLevel {
	private Brick mouseBrick;
	private final Brick[] bricks;
	private final Brick[] shopBricks = new Brick[7];

	private final Rectangle[] gameOptions = {
		new Rectangle(5, 625, 75, 30), new Rectangle(5, 675, 50, 50),
		new Rectangle(530, 680, 30, 30), new Rectangle(590, 680, 30, 30)
	};

	private final Image paintBucket;
	private final Image eraser;
	private final Image backArrow;
	private final int[][] gridPos;

	private int brickCount = 0;
	private final int level;
	private boolean isErasing = false;
	private boolean isPainting = false;

	/**
	 * Constructor - creating a new level of the game
	 * @param level - level the game
	 * @see CreateLevel#CreateLevel(int)
	 */
	public CreateLevel(int level) {
		this.level = level;
		paintBucket = new ImageLoader(ImageLoader.PaintBucket).getImage();
		eraser = new ImageLoader(ImageLoader.Eraser).getImage();
		backArrow = new ImageLoader(ImageLoader.arrow).getImage();
		bricks = new Brick[96];
		gridPos = Files.readLevel(level);

		for (int i = 0; i < gridPos.length; i++) {
			for (int j = 0; j < gridPos[0].length; j++) {
				bricks[brickCount] = new Brick(j * 50, i * 25, gridPos[i][j]);
				brickCount++;
			}
		}

		int prevX = 80;
		for (int i = 0; i < shopBricks.length; i++) {
			shopBricks[i] = new Brick(prevX, 680, i + 1);
			prevX += shopBricks[0].getWidth() + 7;
		}
	}

	public void tick() {
		if (mouseBrick != null) {
			if (Controller.mousePoint.y > (shopBricks.length + 1) * shopBricks[0].getHeight()) {
				mouseBrick.setX(Controller.mousePoint.x - mouseBrick.getWidth() / 2);
				mouseBrick.setY(Controller.mousePoint.y - mouseBrick.getHeight() / 2);
			} else {
				mouseBrick.setX((Controller.mousePoint.x /
						mouseBrick.getWidth()) * mouseBrick.getWidth());
				mouseBrick.setY((Controller.mousePoint.y /
						mouseBrick.getHeight()) * mouseBrick.getHeight());
			}
		}

		if (MouseHandler.MOUSEDOWN) {
			if (mouseBrick != null) {
				paintBricks();
			}

			for (Brick shopBrick : shopBricks) {
				if (Controller.mousePoint.x > shopBrick.getX()
						&& Controller.mousePoint.x < shopBrick.getX() + shopBrick.getWidth()) {
					if (Controller.mousePoint.y > shopBrick.getY()
							&& Controller.mousePoint.y < shopBrick.getY() + shopBrick.getHeight()) {
						mouseBrick = new Brick(Controller.mousePoint.x,
								Controller.mousePoint.y, shopBrick.level);
						isErasing = false;
					}
				}
			}

			if (gameOptions[0].contains(Controller.mousePoint)) {
				Files.SaveLevel(level, gridPos);
				Controller.switchStates(Controller.STATE.GAME, level + 47);
			}

			if (gameOptions[1].contains(Controller.mousePoint)) {
				Files.SaveLevel(level, gridPos);
				Controller.switchStates(Controller.STATE.PICKUSERLEVEL);
			}

			if (gameOptions[2].contains(Controller.mousePoint)) {
				isPainting = !isPainting;
				isErasing = false;
			}

			if (gameOptions[3].contains(Controller.mousePoint)) {
				mouseBrick = new Brick(Controller.mousePoint.x, Controller.mousePoint.y, 0);
				isErasing = !isErasing;
				isPainting = false;
			}

			MouseHandler.MOUSEDOWN = false;
		}
	}

	public void paintBricks() {
		if (mouseBrick.getY() < (shopBricks.length + 1) * shopBricks[0].getHeight() - 1) {
			if (isPainting) {
				for (int i = 0; i < gridPos.length; i++) {
					for (int j = 0; j < gridPos[i].length; j++) {
						gridPos[i][j] = mouseBrick.level;
						bricks[j + (i * 12)] = new Brick(j * mouseBrick.getWidth(),
								i * mouseBrick.getHeight(), mouseBrick.level);
					}
				}
			} else {
				int tempX = mouseBrick.getX() / mouseBrick.getWidth() % 12;
				int tempY = mouseBrick.getY() / mouseBrick.getHeight() % 8;
				System.out.println("xPos: " + tempX + "\nYPos: " + tempY + "\n");

				if (isErasing) {
					gridPos[(tempY)][tempX] = 0;
					bricks[tempX + (tempY * 12)] = new Brick(
							(Controller.mousePoint.x /
									mouseBrick.getWidth()) * mouseBrick.getWidth(),
							(Controller.mousePoint.y /
									mouseBrick.getHeight()) * mouseBrick.getHeight(), 0);
				} else {
					gridPos[tempY][tempX] = mouseBrick.level;
					bricks[tempX + (tempY * 12)] = new Brick(
							(Controller.mousePoint.x / mouseBrick.getWidth()) * mouseBrick.getWidth(),
							(Controller.mousePoint.y / mouseBrick.getHeight()) * mouseBrick.getHeight(),
							mouseBrick.level);
				}
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(0, 0, BrickBreaker.WIDTH, BrickBreaker.HEIGHT);
		g.setColor(Color.white);

		for (int i = 0; i < gridPos.length; i++) {
			for (int j = 0; j < gridPos[0].length; j++) {
				g.drawRect(j * bricks[0].getWidth() , i * bricks[0].getHeight(), bricks[0].getWidth(),
						bricks[0].getHeight());
			}
		}

		int count = 0;
		for (int[] gridPo : gridPos) {
			for (int j = 0; j < gridPos[0].length; j++) {
				if (gridPo[j] > 0) {
					g.drawImage(bricks[count].getImage(), bricks[count].getX(), bricks[count].getY(),
							null);
				}
				count++;
			}
		}

		g.fillRect(0, BrickBreaker.HEIGHT - 100, BrickBreaker.WIDTH, 100);
		g.setColor(Color.WHITE);
		g.drawString("Play!", gameOptions[0].x + 10, gameOptions[0].y + 25);
		g.setColor(Color.black);

		for (Rectangle gameOption : gameOptions) {
			if (gameOption.contains(Controller.mousePoint)) {
				g.drawRect(gameOption.x, gameOption.y, gameOption.width, gameOption.height);
			}
		}

		g.drawImage(backArrow, gameOptions[1].x, gameOptions[1].y, gameOptions[1].width,
				gameOptions[1].height,
				null);
		g.drawImage(paintBucket, gameOptions[2].x, gameOptions[2].y, gameOptions[2].width,
				gameOptions[2].height,
				null);
		g.drawImage(eraser, gameOptions[3].x, gameOptions[3].y, gameOptions[3].width,
				gameOptions[3].height,
				null);

		for (Brick shopBrick : shopBricks) {
			g.drawImage(shopBrick.getImage(), shopBrick.getX(), shopBrick.getY(), shopBrick.getWidth(),
					shopBrick.getHeight(), null);
		}

		if (isPainting) {
			g.drawRect(gameOptions[2].x, gameOptions[2].y, gameOptions[2].width, gameOptions[2].height);
		}

		if (isErasing) {
			g.drawRect(gameOptions[3].x, gameOptions[3].y, gameOptions[3].width, gameOptions[3].height);
		}

		if (mouseBrick != null) {
			g.drawImage(mouseBrick.getImage(), mouseBrick.getX(), mouseBrick.getY(), mouseBrick.getWidth(),
					mouseBrick.getHeight(), null);
		}
	}
}