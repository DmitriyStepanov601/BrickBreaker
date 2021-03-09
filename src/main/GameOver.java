package main;
import handlers.MouseHandler;
import java.awt.*;

/**
 * A class describing the end of the game
 * @author Dmitriy Stepanov
 */
public class GameOver {
	private final Rectangle mainMenu;
	private final Image background;

	/**
	 * Constructor - creating a new end-of-game window
	 * @see GameOver#GameOver()
	 */
	public GameOver() {
		mainMenu = new Rectangle(BrickBreaker.WIDTH / 2 - 60, 380, 150, 55);
		background = new ImageLoader(ImageLoader.titleScreenBackground).getImage();
	}

	public void tick() {
		if(mainMenu.contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
			Controller.switchStates(Controller.STATE.MENU);
		}
	}

	public void render(Graphics g) {
		g.drawImage(background, 0, 0, BrickBreaker.WIDTH, BrickBreaker.HEIGHT, null);
		g.setColor(Color.black);
		g.setFont(Controller.bigFont);
		g.drawString("You Lose", BrickBreaker.WIDTH / 2 - g.getFontMetrics().
				stringWidth("You Lose") / 2, 150);
		g.drawString("Score: " + Controller.score, BrickBreaker.WIDTH / 2 - g.getFontMetrics()
				.stringWidth("Score" + Controller.score) / 2, 225);
		g.drawRect(mainMenu.x, mainMenu.y, mainMenu.width, mainMenu.height);

		if(mainMenu.contains(Controller.mousePoint)) {
			g.setColor(new Color(255, 255, 255, 150));
			g.fillRect(mainMenu.x, mainMenu.y, mainMenu.width, mainMenu.height);
			g.setColor(Color.BLACK);
		}
		g.drawString("Main Menu", mainMenu.x + 15, mainMenu.y + 30);
	}
}