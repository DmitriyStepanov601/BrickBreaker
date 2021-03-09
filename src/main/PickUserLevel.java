package main;
import handlers.MouseHandler;
import java.awt.*;

/**
 * A class that describes the user level of the game
 * @author Dmitriy Stepanov
 */
public class PickUserLevel {
	private final Image arrow;

	private final Rectangle[] levels = {
			new Rectangle(60, 130, 75, 75),
			new Rectangle(160, 130, 75, 75),
			new Rectangle(260, 130, 75, 75),
			new Rectangle(360, 130, 75, 75),
			new Rectangle(460, 130, 75, 75),
			new Rectangle(560, 130, 75, 75),
			new Rectangle(60, 230, 75, 75),
			new Rectangle(160, 230, 75, 75),
			new Rectangle(260, 230, 75, 75),
			new Rectangle(360, 230, 75, 75),
			new Rectangle(460, 230, 75, 75),
			new Rectangle(560, 230, 75, 75),
			new Rectangle(60, 330, 75, 75),
			new Rectangle(160, 330, 75, 75),
			new Rectangle(260, 330, 75, 75),
			new Rectangle(360, 330, 75, 75),
			new Rectangle(460, 330, 75, 75),
			new Rectangle(560, 330, 75, 75),
			new Rectangle(60, 430, 75, 75),
			new Rectangle(160, 430, 75, 75),
			new Rectangle(260, 430, 75, 75),
			new Rectangle(360, 430, 75, 75),
			new Rectangle(460, 430, 75, 75),
			new Rectangle(560, 430, 75, 75),
			new Rectangle(60, 530, 75, 75),
			new Rectangle(160, 530, 75, 75),
			new Rectangle(260, 530, 75, 75),
			new Rectangle(360, 530, 75, 75),
			new Rectangle(460, 530, 75, 75),
			new Rectangle(560, 530, 75, 75)
	};

	private final Rectangle userLevel = new Rectangle(50, 390, 380, 25);
	private final Rectangle[] arrows = { new Rectangle(5, BrickBreaker.HEIGHT - 80,
			50, 50) };

	/**
	 * Constructor - creating a new user level
	 * @see PickUserLevel#PickUserLevel()
	 */
	public PickUserLevel() {
		arrow = new ImageLoader(ImageLoader.arrow).getImage();
	}

	public void tick(){
		if(MouseHandler.MOUSEDOWN) {
			for(int i = 0; i < levels.length; i++) {
				if(levels[i].contains(Controller.mousePoint)) {
					Controller.switchStates(Controller.STATE.CREATELEVEL, i);
				}
			}

			for (Rectangle rectangle : arrows) {
				if (rectangle.contains(Controller.mousePoint)) {
					Controller.switchStates(Controller.STATE.MENU);
				}
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
		g.drawString("Choose User Created Level", BrickBreaker.WIDTH/2 - g.getFontMetrics().
				stringWidth("Choose User Created Level")/2, 80);

		for(int i = 0; i < levels.length; i++) {
			g.setColor(Color.black);
			g.drawString("" + (i), levels[i].x + 35, levels[i].y + 45);

			if(levels[i].contains(Controller.mousePoint)) {
				g.setColor(new Color(255, 255, 255, 150));
				g.fillRect(levels[i].x, levels[i].y, levels[i].width, levels[i].height);
			}

			g.drawRect(levels[i].x, levels[i].y, levels[i].width, levels[i].height);
		}

		for(int i = 0; i < arrows.length; i++) {
			if(i == 0) {
				g.drawImage(arrow, arrows[i].x, arrows[i].y, arrows[i].width, arrows[i].height, null);
			}
		}
	}
}