/***************************************************************
 * Keith Wong
 * CIS 255
 * 
 * Creates the station
 * Makes the rockets fire in the same direction as the cannon
 ***************************************************************/

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

class Station {
	
	public Station (double ix, double iy) { x = ix; y = iy; }
	
	private double angle = Math.PI / 2.0; // public static final double PI 3.141592653589793d
	private int hits = 0;
	private final double x;	
	private final double y;
	
	public void moveLeft( ) { angle = angle + 0.1; }
	public void moveRight( ) { angle = angle - 0.1; }

	public void fire (List<Rocket>rockets) {
		double cosAngle = Math.cos(angle);
		double sinAngle = Math.sin(angle);
		// rocket goes same direction as gun is pointing
		Rocket r = new Rocket(x + 15 * cosAngle, y - 15 * sinAngle,
				5 * cosAngle, - 5 * sinAngle);
		rockets.add(r);
	}
	
	public void checkHit (Asteroid rock) {
		if (rock.nearTo((double) x, (double) y))
			hits += rock.size;
	}
	
	public void paint (Graphics g) {
		g.setColor (Color.red);
		double lv = 20 * Math.sin(angle);
		double lh = 20 * Math.cos(angle);
		g.drawLine((int) x, (int) y, (int) (x + lh), (int) (y - lv));
		g.drawString("hits: " + hits, (int) (x + 10), (int) (y - 5));
	}
}