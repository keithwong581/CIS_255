/******************************
 * Keith Wong
 * CIS 254
 * 
 * Component that owns a bunch 
 * of Affirm objects and draws
 * their data on screen 
 ******************************/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;

public class AffirmComponent extends JComponent {

	private ArrayList affirms;
	private ArrayList colors;
	public static final int SIZE = 400;
	public static final Font FONT = new Font("Dialog", 0, 24);
	public static final int SPEED = 8;
	
	public AffirmComponent() {
	
		affirms = new ArrayList();
		colors = new ArrayList();
		setPreferredSize(new Dimension(SIZE,SIZE));
		}
	
	// Draw all the affirmations.
	public void paintComponent(Graphics g) {
	
	g.setFont(FONT);
	for(int i=0;i<affirms.size();i++){
		
		Affirm a = (Affirm) affirms.get(i);
		Color c = (Color) colors.get(i);
		g.setColor(c);
		g.drawString(a.getString(), a.getX(), a.getY());
		}
	
	}
	
	public int randomInt(int bound) {
	
	return (int) (Math.random()*bound);
	}
	
	// Adds a string -- make and add an Affirm for the string 
	public void add(String string) {
	
	Affirm a = new Affirm(string, randomInt(getWidth()), randomInt(getHeight()));
	affirms.add(a);
	Color c = new Color(randomInt(256),randomInt(256),randomInt(256));
	colors.add(c);
	repaint();
	}
	
	// Removes an affirmation at random
	public void removeRandom() {
	
	if(affirms.size()>0){

		affirms.remove(randomInt(affirms.size()));
		}
	
	repaint();
	}
	
	// Moves all the affirmations to the center
	public void moveToCenter() {
		
	int width = getWidth();
	int height = getHeight();	
	for(int i=0;i<affirms.size();i++) {
	
		Affirm a = (Affirm) affirms.get(i);
		a.set(width/2,height/2);
		}

	repaint();
	}
	// Moves all the affirmations a little 
	public void moveAll() {
	
	int width = getWidth();
	int height = getHeight();
	for(int i=0;i<affirms.size();i++) {
	
		Affirm a = (Affirm) affirms.get(i);
		int dx = randomInt(2*SPEED+1)-SPEED;
		int dy = randomInt(2*SPEED+1)-SPEED;
		a.move(dx,dy);
			if(!a.isWithin(width,height)) {
				
				a.set(randomInt(width), randomInt(height));
			}
		}
	
	repaint();
	}

}