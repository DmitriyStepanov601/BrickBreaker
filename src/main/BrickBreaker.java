package main;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.*;
import java.io.IOException;

/**
 * A class that describes the graphical interface
 * @author Dmitriy Stepanov
 */
public class BrickBreaker extends JFrame {
	public static int WIDTH = 700;
	public static int HEIGHT = 760;

	/**
	 * Constructor - create a new interface of the game
	 * @see BrickBreaker#BrickBreaker()
	 */
	public BrickBreaker() {
		setTitle("Brick Breaker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Image windowIcon = null;
		try {
			windowIcon = ImageIO.read(BrickBreaker.class.getResource("/brickbreaker.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

        setIconImage(windowIcon);
		setContentPane(new Controller());
		pack();
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new BrickBreaker();
	}
}
