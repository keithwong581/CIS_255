
/*****************************************************
 * Keith Wong
 * CIS 255
 * 
 * Implements the main component for the carrot game.
 *****************************************************/

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class CarrotComponent extends JComponent 
{
	public static final int SIZE = 500;		//initial size
	public static final int PIXELS = 50;	// square size per image
	public static final int MOVE = 20;		// keyboard move
	public static final int GRAVITY = 2;	// gravity move
	public static final int CARROTS = 20;	//number of carrots
	public static final Font FONT = new Font("Dialog", 0, 16);
	
	private ArrayList myPoints;				//x,y upper left of each carrot
	
	private int myX;						//upper left of head x
	private int myY;						//upper left of head y
	private int myDy;						//change in y for gravity
	private Image headImage;				// image of the gray bunny
	private int grayScore = 0;				// int to keep track of gray bunny score
	
	private int secondX;					// upper left of the head of pink bunny
	private int secondY;					// upper right of the head of pink bunny
	private int secondDy;					// change in second head y for gravity
	private Image secondHead;				// head of pink bunny
	private int pinkScore = 0;				// score tracker for pink bunny
	
	private Image carrotImage; 

	public CarrotComponent() 
	{
		setPreferredSize(new Dimension(SIZE, SIZE));
	
		// getScaledInstance( ) gives us re-sized version of the image --
		// speeds up the drawImage( ) if the image is already the right size 
		// See paintComponent( )
		
		headImage = readImage("bugs-bunny2.jpg");
		headImage = headImage.getScaledInstance(PIXELS, PIXELS,
			Image.SCALE_SMOOTH); 
		
		secondHead = readImage("bunny2.jpg");
		secondHead = secondHead.getScaledInstance(PIXELS, PIXELS,
			Image.SCALE_SMOOTH);
		
		carrotImage = readImage("carrot.gif");
		carrotImage.getScaledInstance(PIXELS,PIXELS,Image.SCALE_SMOOTH);
		
		myPoints = new ArrayList(); 
	}
	
	// Utility -- create a random point within the window 
	// leaving PIXELS space at the right and bottom
	private Point randomPoint() 
	{
		Point p = new Point( (int) (Math.random() * (getWidth() - PIXELS)), (int) (Math.random() * (getHeight() - PIXELS)));
		
		return(p); 
	}
	
	// Reset things for the start of a game
	public void reset() 
	{
		myPoints.clear();			// removes all the points
		for (int i=0; i < CARROTS; i++) 
		{
			myPoints.add( randomPoint() ); 
		}
		myX = getWidth()/2; 
		myY = 0;
		myDy = 0;
		
		secondX = getWidth()/2;
		secondY = 0;
		secondDy = 0;		
		
		repaint();
	}
	
	// Advance things by one tick -- do gravity, check collisions
	public void tick() 
	{
		myDy = myDy + GRAVITY; // increase dy 
		myY += myDy; // figure new y
		
		secondDy = secondDy + GRAVITY;
		secondY += secondDy;
	
		// check if hit bottom
		if (myY + PIXELS >= getHeight()) 
		{
			// back y up
			myY -= myDy;
			// reverse direction of dy (i.e. bounce), but with 98% efficiency
			myDy = (int) (0.98 * - myDy); 
		}
		
		else if (secondY + PIXELS >= getHeight()) // same as above but this for pink bunny
		{
			secondY -= secondDy;
			secondDy = (int) (0.98 * - secondDy);
		}
		
		checkCollisions();
		repaint();
	}
	
	// Check the current x,y vs. the carrots
	public void checkCollisions()
	{	
		int carrotPoints = 5;
		
		for (int i=0; i < myPoints.size(); i++) 
		{
			Point point = (Point) myPoints.get(i); 
			
			// if we overlap a carrot, remove it and keep count of gray bunny score
			if ( (Math.abs(point.getX() - myX) <= PIXELS && Math.abs(point.getY() - myY) <= PIXELS) )
			{
				grayScore += carrotPoints;		// added score counter
				
				myPoints.remove(i); // removes the ith elem from an ArrayList 
				i--;				// tricky:
									// back i up, since we removed the ith element
				repaint();
			}
			
			// overlap a carrot remove it and keep count of score for pink bunny
			else if ((Math.abs(point.getX() - secondX) <= PIXELS && Math.abs(point.getY() - secondY) <= PIXELS) )
			{
				pinkScore += carrotPoints;		// added score counter
				
				myPoints.remove(i); // removes the ith elem from an ArrayList 
				i--;				// tricky:
									// back i up, since we removed the ith element
				repaint();
			}
			
		}
		
		if (myPoints.size() == 0) 
		{
			reset();  // new game 
			
			pinkScore = 0;				// reset scores after game is reset
			grayScore = 0;
		} 
	}
	
	// had to create method to call score of gray bunny
	public int getGrayScore()
	{
		return grayScore;
	}
	
	// had to create method to call score of pink bunny
	public int getPinkScore()
	{
		return pinkScore;
	}

	
	// Process one key click -- up, down, left, right
	// added second set of one key click-- W(up), Z(down), A(left), D(right)
	public void key(int code) 
	{
		if (code == KeyEvent.VK_UP)
		{
			 myY += -MOVE;
		}
		
		else if (code == KeyEvent.VK_DOWN) 
		{ 
			myY += MOVE;
		}
	
		else if (code == KeyEvent.VK_LEFT) 
		{
			myX += -MOVE;
		}
	
		else if (code == KeyEvent.VK_RIGHT)
		{
			myX += MOVE; 
		}
		
		else if (code == KeyEvent.VK_W)
		{
			secondY += -MOVE;
		}
		
		else if (code == KeyEvent.VK_Z) 
		{ 
			secondY += MOVE;
		}
		
		else if (code == KeyEvent.VK_D)
		{
			secondX += MOVE; 
		}
		
		else if (code == KeyEvent.VK_A)
		{
			secondX -= MOVE; 
		}
		
		
		checkCollisions( ); 
		repaint( );
	}
	
	// Utility to read in an Image object
	// If image cannot load, prints error output and returns null. 
	private Image readImage(String filename) 
	{
		Image image = null;
		
		try
		{
			image = ImageIO.read(new File(filename));
		}
		
		catch (IOException e) 
		{
			System.out.println("Failed to load image '" + filename + "'");
			e.printStackTrace( ); 
		}
		
		return(image); 
	}
	
	// Draws the heads and carrots
	// added the score title and score tracker to the frame 
	public void paintComponent(Graphics g)
	{
		g.setFont(FONT);
		g.drawString( "Pink-Bunny Score", 0, getHeight()/18);
		g.drawString(String.valueOf(getPinkScore()),0 , getHeight()/10);
		
		g.setFont(FONT);
		g.drawString("Gray-Bunny Score", getWidth()-getWidth()/3, getHeight()/18);
		g.drawString(String.valueOf( getGrayScore() ), getWidth()-getWidth()/3, getHeight()/10);

		g.drawImage(headImage, myX, myY, PIXELS, PIXELS, null);
		g.drawImage(secondHead, secondX, secondY, PIXELS,PIXELS,null); 
		
		// Draw all the carrots
		for (int i=0; i < myPoints.size( ); i++)
		{ Point p = (Point) myPoints.get(i);
		
		// point.getX( ) returns a double, so we must cast to int
		g.drawImage(carrotImage, (int) (p.getX( )), (int) (p.getY( )), PIXELS, PIXELS, null);
		}
	}
}