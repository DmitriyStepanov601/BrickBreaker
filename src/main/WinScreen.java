package main;
import levelfiles.Files;
import levelfiles.Level;
import handlers.MouseHandler;
import java.awt.*;

/**
 * A class describing the window of victory in the game
 * @author Dmitriy Stepanov
 */
public class WinScreen {
	private final Rectangle mainMenu;
	private final Image background;
	private boolean unlockedNewLevel = false;

	/**
	 * Constructor - creates a new window of the victory in the game
	 * @see WinScreen#WinScreen()
	 */
	public WinScreen() {
		mainMenu = new Rectangle(BrickBreaker.WIDTH /2 - 60, 380, 150, 55);
		background = new ImageLoader(ImageLoader.titleScreenBackground).getImage();

		if(Controller.level + 1 < Level.MAX_LEVEL && Level.lockedLevels[Controller.level + 1]) {
			unlockedNewLevel = true;
		}

		if(Controller.level < Level.MAX_LEVEL - 1) {
			if(Level.lockedLevels[Controller.level + 1]) {
				Level.lockedLevels[Controller.level + 1] = false;
				Files.SaveProgress(Level.lockedLevels);
			}
		}
	}

	public void tick() {
		if(mainMenu.contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
			Controller.switchStates(Controller.STATE.MENU);
		}
	}

	public void render(Graphics g) {
		g.drawImage(background, 0, 0, BrickBreaker.WIDTH, BrickBreaker.HEIGHT, null);
		g.setColor(Color.BLACK);
		g.setFont(Controller.bigFont);
		g.drawString("You Win!!", BrickBreaker.WIDTH / 2 - g.getFontMetrics()
				.stringWidth("You Win!!") / 2, 150);
		g.drawString("Score: " + Controller.score, BrickBreaker.WIDTH/2 - g.getFontMetrics().
				stringWidth("Score" + Controller.score) / 2, 225);

		if(unlockedNewLevel) {
			g.setColor(Color.orange);
			g.drawString("New Level Unlocked!!", BrickBreaker.WIDTH / 2 - g.getFontMetrics().
					stringWidth("New Level Unlocked!!") / 2, 285);
			g.setColor(Color.black);
		}

		g.drawRect(mainMenu.x, mainMenu.y, mainMenu.width, mainMenu.height);
		if(mainMenu.contains(Controller.mousePoint)) {
			g.setColor(new Color(200, 200, 200, 100));
			g.fillRect(mainMenu.x, mainMenu.y, mainMenu.width, mainMenu.height);
			g.setColor(Color.black);
		}
		g.drawString("Main Menu", mainMenu.x + 15, mainMenu.y + 30);
	}
}