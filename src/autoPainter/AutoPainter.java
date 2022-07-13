package autoPainter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;			//or: import java.awt.event.WindowAdapter;&import java.awt.event.WindowEvent;
import java.awt.image.*;
public class AutoPainter extends Frame{		//Розширяє або наслідує клас Frame
//			 subclass			 superclass
	public static void main(String[] args) throws Exception{
		AutoPainter autoFirst = new AutoPainter("cayZuzana");
		autoFirst.setSize(frameLength, 600);
		autoFirst.setVisible(true);
		autoFirst.go();
	}
	AutoPainter(String title){
		super(title);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent ev) {
				System.exit(0);
			}
		});
	}

	int animationShift = 0;
	static int frameLength = 1200;

	boolean isMovingToLeft = false;

	int yCabine = 60, widthCabine = 110, heightCabine = 40;
	int xBody = 50, widthBody = 300, heightBody = 50;
	int xCabine = xBody + (widthBody - widthCabine)/2;
	int yBody = yCabine + heightCabine;
	int diameterWheel = 50;
	int shiftForLeftWheel = 30;
	int yWheel = yBody + heightBody;                      // calculate

	int xLeftWheel = xBody + shiftForLeftWheel; 
	int xRightWheel = xBody + widthBody - shiftForLeftWheel - diameterWheel;
	
	public void go() throws Exception{
		while(true) {
			if(!isMovingToLeft) {
				repaint();                    // invoke update
				Thread.sleep(4);
				if(animationShift < (frameLength - widthBody - xBody - 10)) {
					animationShift++;
				}else {
					isMovingToLeft = true;
				}
			}else {
				repaint();                    // invoke update
				Thread.sleep(4);
				if (animationShift > (10 - xBody)) {
					animationShift--;
				}else {
					isMovingToLeft = false;
				}
			}
		}
	}

	
	
	public void update(Graphics g) {
		int w = getSize().width, h = getSize().height;
		BufferedImage bi = (BufferedImage) createImage(w, h);
		Graphics2D big = bi.createGraphics();
		//!
		Rectangle cabine = new Rectangle(xCabine + animationShift, yCabine, widthCabine, heightCabine);
		Rectangle body = new Rectangle(xBody + animationShift, yBody, widthBody, heightBody);
		Ellipse2D.Double wheelLeft = new Ellipse2D.Double (xLeftWheel + animationShift, yWheel, diameterWheel, diameterWheel);
		Ellipse2D.Double wheelRight = new Ellipse2D.Double (xRightWheel + animationShift, yWheel, diameterWheel, diameterWheel);
		GeneralPath auto = new GeneralPath(cabine); 
		auto.append(body, false);
		auto.append(wheelLeft, false);
		auto.append(wheelRight, false);
		big.draw(auto);
		//!
		g.drawImage(bi, 0, 0, this);
	}
}

