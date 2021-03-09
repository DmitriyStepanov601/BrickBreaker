package main;
import levelfiles.Files;
import levelfiles.Level;
import handlers.MouseHandler;
import java.awt.*;

/**
 * A class that describes the level of the game
 * @author Dmitriy Stepanov
 */
public class PickLevel {
	private final Image arrow;
	private final Image arrow2;
	private final Image locked;
	private int page = 1;

	private final Rectangle[] levels = {
			new Rectangle(63, 120, 75, 75),
			new Rectangle(163, 120, 75, 75),
			new Rectangle(263, 120, 75, 75),
			new Rectangle(363, 120, 75, 75),
            new Rectangle(463, 120, 75, 75),
			new Rectangle(563, 120, 75, 75),
			new Rectangle(63, 220, 75, 75),
			new Rectangle(163, 220, 75, 75),
			new Rectangle(263, 220, 75, 75),
			new Rectangle(363, 220, 75, 75),
            new Rectangle(463, 220, 75, 75),
			new Rectangle(563, 220, 75, 75),
            new Rectangle(63, 320, 75, 75),
			new Rectangle(163, 320, 75, 75),
            new Rectangle(263, 320, 75, 75),
			new Rectangle(363, 320, 75, 75),
            new Rectangle(463, 320, 75, 75),
			new Rectangle(563, 320, 75, 75),
			new Rectangle(63, 420, 75, 75),
			new Rectangle(163, 420, 75, 75),
			new Rectangle(263, 420, 75, 75),
			new Rectangle(363, 420, 75, 75),
			new Rectangle(463, 420, 75, 75),
			new Rectangle(563, 420, 75, 75),
			new Rectangle(63, 520, 75, 75),
			new Rectangle(163, 520, 75, 75),
			new Rectangle(263, 520, 75, 75),
			new Rectangle(363, 520, 75, 75),
			new Rectangle(463, 520, 75, 75),
			new Rectangle(563, 520, 75, 75)
	};

	private final Rectangle userLevel = new Rectangle(50, 630, 600, 25);
	private final Rectangle[] arrows = {
	        new Rectangle(5, BrickBreaker.HEIGHT - 80, 50, 50),
            new Rectangle(BrickBreaker.WIDTH - 60, BrickBreaker.HEIGHT - 80,
					50, 50)
	};

	/**
	 * Constructor - creating a new level of the game
	 * @see PickLevel#PickLevel()
	 */
	public PickLevel() {
		arrow = new ImageLoader(ImageLoader.arrow).getImage();
		arrow2 = new ImageLoader(ImageLoader.arrow2).getImage();
		locked = new ImageLoader(ImageLoader.locked).getImage();
		Level.lockedLevels = Files.readFile(Files.LEVELPATH);
	}

	public void tick() {
		if(MouseHandler.MOUSEDOWN) {
			for(int i = 0; i < levels.length; i++) {
				if(levels[i].contains(Controller.mousePoint)) {
					if(!Level.lockedLevels[i + (8 * (page - 1))]) {
						Controller.switchStates(Controller.STATE.GAME, i * page);
					}
				}
			}

			for(int i = 0; i < arrows.length; i++) {
				if(arrows[i].contains(Controller.mousePoint)) {
					if(i == 0) {
						if(page > 1) {
							page--;
						} else {
							Controller.switchStates(Controller.STATE.MENU);
						}
					} else {
						if(page < 3) {
							page++;
						}
					}
				}
			}

			if(userLevel.contains(Controller.mousePoint)) {
				Controller.switchStates(Controller.STATE.PICKUSERLEVEL);
			}

			MouseHandler.MOUSEDOWN = false;
		}
	}

	public void render(Graphics g) {
		g.setFont(Controller.bigFont);
		Graphics2D g2 = (Graphics2D)g;
	    GradientPaint blueToBlack = new GradientPaint(0, 0, Color.CYAN, 0, 600, Color.BLUE);
	    g2.setPaint(blueToBlack);

		g.fillRect(0, 0, BrickBreaker.WIDTH, BrickBreaker.HEIGHT);
		g.setColor(Color.black);
		g.drawString("Choose Level", BrickBreaker.WIDTH / 2 - g.getFontMetrics().
                        stringWidth("Choose Level") / 2, 80);
		g.drawString("Page " + page, BrickBreaker.WIDTH/2 - g.getFontMetrics().
                        stringWidth("Page " + page) / 2, BrickBreaker.HEIGHT - 40);

		for(int i = 0; i < levels.length; i++) {
			g.setColor(Color.black);
			g.drawString("" + (i + (8 * (page - 1))), levels[i].x + 35, levels[i].y + 45);

			if(levels[i].contains(Controller.mousePoint)) {
				g.setColor(new Color(255, 255, 255, 150));
				g.fillRect(levels[i].x, levels[i].y, levels[i].width, levels[i].height);
			}

			g.drawRect(levels[i].x, levels[i].y, levels[i].width, levels[i].height);
		}

		for(int i = 0; i < levels.length; i++) {
			if(Level.lockedLevels[i + (8 * (page - 1))]) {
				g.drawImage(locked, levels[i].x, levels[i].y, levels[i].width, levels[i].height, null);
			}
		}

		g.drawRect(userLevel.x, userLevel.y, userLevel.width, userLevel.height);
		g.drawString("User Created Levels", BrickBreaker.WIDTH / 2 - g.getFontMetrics().
				stringWidth("User Created Levels") / 2, 650);

		if(userLevel.contains(Controller.mousePoint)) {
			g.setColor(new Color(200, 200, 200, 100));
			g.fillRect(userLevel.x, userLevel.y, userLevel.width, userLevel.height);
			g.setColor(Color.black);
		}

		for(int i = 0; i < arrows.length; i++) {
			if(i == 0) {
			    g.drawImage(arrow, arrows[i].x, arrows[i].y, arrows[i].width, arrows[i].height, null);
			}else {
				g.drawImage(arrow2, arrows[i].x, arrows[i].y, arrows[i].width, arrows[i].height, null);
			}
		}
	}
}