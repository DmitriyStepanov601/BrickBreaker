package main;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A class that describes the loading of game resources
 * @author Dmitriy Stepanov
 */
public class ImageLoader {
	private BufferedImage img;
	public static String titleForeground = "/BrickBreakerForeground.png";
	public static String titleScreenBackground = "/BrickBreakerBackground.png";
	public static String paddle = "/Paddle.png";
	public static String brick = "/Bricks.png";
	public static String ball = "/Ball.png";
	public static String fireBall = "/FireBall.png";
	public static String arrow = "/Arrow.png";
	public static String arrow2 = "/Arrow2.png";
	public static String locked = "/locked.png";
	public static String PUMultiBall = "/PUMultiBall.png";
	public static String PUGrowth = "/PUGrowth.png";
	public static String PUFireball = "/PUFireball.png";
	public static String PaintBucket = "/PaintBucket.png";
	public static String Eraser = "/Eraser.png";

	/**
	 * Constructor - creating a new resource loader
	 * @param path - the path showing where the images for the game are located
	 * @see ImageLoader#ImageLoader(String)
	 */
	public ImageLoader(String path) {
		try {
			img = ImageIO.read(ImageLoader.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getImage() {
		return img;
	}
	public BufferedImage getSubImage(int section) {
		return img.getSubimage(0, section * 25, 50, 25);
	}
}