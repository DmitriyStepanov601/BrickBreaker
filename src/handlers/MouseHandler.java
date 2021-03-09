package handlers;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import main.Controller;

/**
 * A class that describes how to manipulate the mouse to control buttons
 * @author Dmitriy Stepanov
 */
public class MouseHandler extends MouseAdapter implements MouseMotionListener, MouseListener {
	public static boolean MOUSEDOWN = false;
	public static boolean hasPressed = false;
	
	public MouseHandler() {}

	public void mousePressed(MouseEvent e) {
		if(!hasPressed) {
			MOUSEDOWN = true;
			hasPressed = true;
		}
	}

	public void mouseReleased(MouseEvent e) {
		MOUSEDOWN = false;
		hasPressed = false;
	}

	public void mouseDragged(MouseEvent e) {
		MOUSEDOWN = false;
	}
	public void mouseMoved(MouseEvent e) {
		Controller.mousePoint = e.getPoint();
	}
}