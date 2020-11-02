/********************************
 * Keith Wong
 * CIS 254
 * 
 * Creates and moves affirmations
 ********************************/

import java.awt.Color;

//Encapsulate data for one affirmation: a string and an x,y 
public class Affirm{

	private String myString;
	private int myX;
	private int myY;
	private Color color = Color.BLUE;
	
	public final String[] GOOD = new String[] {"Lookin' good", "Yo go!", "Word!", "You can do it", "Mind the gap"};
	
	// Creates an affirm with the given string and x,y 
	public Affirm(String string, int x, int y) {
		myString = string;
		myX = x;
		myY = y;
		}
	
	public String getString(){return myString;}
	public int getX(){return myX;}
	public int getY(){return myY;}
	
	// Moves x,y by the given deltas 
	public void move(int dx, int dy) {
	
		myX += dx;
		myY += dy;
		}
	
	// True if x,y is within the rectangle (0,0), (width,height) 
	public boolean isWithin(int width, int height){
		
		return(myX>=0 && myX<width && myY>=0 && myY<height);
		}
	
	// Sets x and y 
	public void set(int newX, int newY) {
		
		myX = newX;
		myY = newY;
		}
	
	}