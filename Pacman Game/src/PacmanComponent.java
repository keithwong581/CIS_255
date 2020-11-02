/**********************
 * Keith Wong
 * CIS 255
 * 
 **********************/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

// Subclass of JComponent that handles the drawing
// and collisions of our little Pacman world
public class PacmanComponent extends JComponent 
{
	private int initialX; // initial X position to start from on each game
	private int initialY; // initial Y position to start from on each game
	
	private PacmanPoint pacman; // point representing current position of player
	private PacmanPoint bunny; // point representing current position of bunny
	private PacmanPoint cherry; // point representing current position of cherry
	
	private double cherryProb; // if no cherry is currently present, the probability that one will appear
	
	// the Images for the bunny, pacman, and cherry
	private Image bunnyImage;
	private Image pacmanImage;
	private Image cherryImage;
	
	private int score; // the score in the current game
	private int [][] world; // a 2D [x][y] array representing the world. Entries are EMPTy, WALL, or PELLET
	
	public static final int EMPTY = 0;
	public static final int WALL = 1;
	public static final int PELLET = 2;
	
	// point scores for eating various things
	public static final int PELLET_SCORE = 5;
	public static final int CHERRY_SCORE = 400;

	// constructor for the PacmanComponent. Sets size to 500x500
	// loads the images and the world, and resets the game to be ready to play
	
	public PacmanComponent(String world)
	{
		setPreferredSize(new Dimension(500,500)); // next slide
		pacmanImage = readImage("pacman.png");
		cherryImage = readImage("cherry.jpg");
		bunnyImage = readImage("bunny.jpg");
		loadFile(world); 
		reset(); // resets the game state to the beginning
	}
	// provided the utility method to read in an Image object.
	// If the image cannot load, prints error output and returns null.
	// Uses Java standard ImageIO.read() method
	
	private Image readImage(String filename) {
		Image image = null;
		try {
			image = ImageIO.read(new File (filename));
		}
		catch (IOException e) {
			System.out.println("Failed to load image" + filename + "'");
			e.printStackTrace();
		}
		return(image);
	}
	
	// loads the game out of a file
	
	private void loadFile(String filename) {
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(filename));
			
			String line = in.readLine();
			String[] info = line.split(" ");
			
			// first line has size of world and in it location and probability
			int numCols = Integer.parseInt(info[0]);
			int numRows = Integer.parseInt(info[1]);
			world = new int[numCols][numRows];
			
			initialX = Integer.parseInt(info[2]);
			initialY = Integer.parseInt(info[3]);
			//using Double.parseDouble to read out the probability
			cherryProb = Double.parseDouble(info[4]);
			// Taking advantage of the fact that the file's numbers
			// correspond to the programs constants
			for(int y = 0; y < numRows; y++)
			{
				line = in.readLine();
				String[]data = line.split(" ");
				
				for (int x = 0; x < numCols; x++)
				{
					world[x][y] = Integer.parseInt(data[x]);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
				
	}
	// Resets the game state to the beginning
	public void reset() {
		// resets the pacman to the beginning, and picks a random
		// start for the cherry and bunny
		pacman = new PacmanPoint(initialX,initialY);
		cherry = randomPoint();
		bunny = randomPoint();
		
		score = 0;
				
		// All EMPTY squares must have PELLETS in them.
		for (int x = 0; x < world.length; x++) {
			for (int y = 0; y < world[x].length; y++){
				if (world[x][y] == EMPTY) {
					world[x][y] = PELLET;
				}
			}
		}
		repaint();
	}
	// Helper method to repeatedly pick (x,y) pairs
	// until we find a point that isn't a WALL
	
	private PacmanPoint randomPoint()
	{
		while(true)
		{
			int x = (int)(Math.random()*world.length); // row position
			int y = (int)(Math.random()*world[x].length); // col position
			if (world[x][y]!=WALL)
			{
				return new PacmanPoint(x,y);
			}
		}
	}
	public void paintComponent(Graphics g) {
		int squareWidth = getWidth() / world.length; // figure out the pixel
		int squareHeight = getHeight() / world[0].length; // size of one square
		for (int x = 0; x < world.length; x++){
			for(int y = 0; y < world[x].length;y++){
			// draw each square appropriately
			if (world[x][y] == EMPTY) {
				g.setColor(Color.BLACK);
				g.fillRect(squareWidth*x, squareHeight*y, squareWidth, squareHeight);
			}
			else if (world[x][y] == WALL) {
				g.setColor(Color.GRAY);
				g.fillRect(squareWidth*x,squareHeight*y,squareWidth,squareHeight);
			}
			else if(world[x][y] == PELLET) {
				g.setColor(Color.BLACK);
				g.fillRect(squareWidth*x, squareHeight*y, squareWidth, squareHeight);
				g.setColor(Color.YELLOW);
				g.fillOval(squareWidth*x + (3 * squareWidth / 8), squareHeight*y + (3 * squareHeight / 8), squareWidth / 4, squareHeight / 4);
			} // if
		} // for y
	} // for x
	// draw the images corresponding to the objects in the world.
	g.drawImage(pacmanImage, squareWidth * pacman.getX(), squareHeight * pacman.getY(), squareWidth,squareHeight,null);
	g.drawImage(bunnyImage, squareWidth * bunny.getX(), squareHeight * bunny.getY(), squareWidth, squareHeight,null);
	// only print the cherry if it is on screen
	if(cherry != null) {
		g.drawImage(cherryImage,squareWidth*cherry.getX(), squareHeight*cherry.getY(), squareWidth, squareHeight, null);
	
	}
	
	// print the score
	g.setColor(Color.YELLOW);
	g.drawString("Score:" + score, 0, squareHeight - 10);
	}
// Method to update the game state in response to a keypress
	public void keyPress(int code) {
		// update the position of the pacman, ensuring we do not run into a wall
		if (code == KeyEvent.VK_UP) {
			if(world[pacman.getX()][pacman.getY() - 1] != WALL) {
			pacman.moveBy(0, -1);
			}
		}
		else if (code == KeyEvent.VK_DOWN) {
			if (world[pacman.getX()][pacman.getY() + 1]!= WALL) {
			pacman.moveBy(0,1);
			}
		}
		else if (code == KeyEvent.VK_LEFT) {
			if (world[pacman.getX() - 1][pacman.getY()] != WALL) {
			pacman.moveBy(-1, 0);
			}
		}
		else if (code == KeyEvent.VK_RIGHT) {
			if (world[pacman.getX()+1][pacman.getY()]!= WALL) {
			pacman.moveBy(1, 0);
			}
		}
		// now, deal with the eating of various objects like the 
		// PELLET or the cherry
		if (world[pacman.getX()][pacman.getY()] == PELLET) {
			world[pacman.getX()][pacman.getY()] = EMPTY;
			score += PELLET_SCORE;
		}
	
		if ((cherry!=null) && (pacman.equals(cherry))) {
			// if we eat the cherry, then make it dissapear for a little bit.
			score += CHERRY_SCORE;
			cherry = null;
		}
	
		repaint();
	}
	// Tick method that is repeatedly called by a timer
	// to update the position of the bunny and cherry
	public void tick() {
		randomMove(bunny);
		if(cherry!=null) {
			randomMove(cherry); // only move the cerry if its on scnreen
			// check for collisions again
			if (pacman.equals(cherry)) {
				score += CHERRY_SCORE;
				cherry = null;
			}
		}
		else {
			// Figure out if a cherry should be generated
			if(Math.random() < cherryProb) {
				cherry = randomPoint();
			}
		}
		// if this is true, then we lose and the game restarts
		if (pacman.equals(bunny)) {
			reset();
		}
		repaint();
	}
	// picks random move for a legal point (i.e. isn't into a WALL) and applies it to a pt.
	public void randomMove(PacmanPoint pt) {
		int dx,dy;
		while(true) {
			double r = Math.random();
			if (r < 0.25) {
				dx = 0; dy = 1;
			}
			else if (r < 0.5) {
				dx = 0; dy = -1;
			}
			else if(r< 0.75) {
				dx = 0; dy = -1;
			}
			else {
				dx = -1; dy = 0;
			}
			if (world[pt.getX()+dx] [pt.getY()+dy] != WALL) {
				pt.moveBy(dx,dy);
				return;			
			}
			// otherwise loop around and try again
		}
	}
}
