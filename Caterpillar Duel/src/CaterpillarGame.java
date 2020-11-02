/*********************************
 * Keith Wong
 * CIS 255
 * 
 * Creates the universe of objects,
 * repaints window, moves caterpillars,
 * and takes keyboard inputs
 ********************************/

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class CaterpillarGame extends Frame {
	// setup board dimensions
	final static int BoardWidth = 60;
	final static int BoardHeight = 40;
	final static int SegmentSize = 10;
	
    Random rd = new Random(); // creating Random object
    
	// Random Game Points variables 
	private Point square = new Point(20, 10);
	private int number = 0;
	
	// Player Scores
	private int playerOnePoints = 0;
	private int playerTwoPoints = 0; 
    
	// setup the two Caterpillars in their starting positions
	private Caterpillar playerOne = new Caterpillar (Color.blue, new Point (20,10));
	private Caterpillar playerTwo = new Caterpillar (Color.red, new Point(20,30));
	
	// constructor
	public CaterpillarGame() {
		setSize ((BoardWidth+1)*SegmentSize,BoardHeight*SegmentSize+30);
		setTitle ("Caterpillar Game");
		addKeyListener (new KeyReader());
	}
	
	public static void main (String [ ] args){
		CaterpillarGame world = new CaterpillarGame();
		world.setVisible(true);
		world.run();
	}
	
	public void paint (Graphics g) {
		String num = Integer.toString(number);
		super.paint(g);
		playerOne.paint(g);
		playerTwo.paint(g);
		g.drawString("Player One Score: " + playerOnePoints, 30, 40);
		g.drawString("Player Two Score: " + playerTwoPoints, 450, 40);
		g.drawString(num, 5 + CaterpillarGame.SegmentSize * square.x, 
                25 + CaterpillarGame.SegmentSize * square.y);
	}
	
	public void movePieces() {
		playerOne.move(this);
		playerTwo.move(this);
	}
	
	public void run ( ) {
		boolean GameOver = false;
		while (GameOver) {
			movePieces();
			repaint();
			
			if (playerOne.inPosition(square)) {
				playerOnePoints = playerOnePoints + number; 
				playerOne.addCaterpillarLength();
				playerTwo.subtractCaterpillarLength();
				newNumberSquare(); 
			}
			
			if (playerTwo.inPosition(square)) {
				playerTwoPoints = playerTwoPoints + number; 
				playerTwo.addCaterpillarLength();
				playerOne.subtractCaterpillarLength();
				newNumberSquare(); 
			}
			
			try {
				Thread.sleep(100);
			} catch (Exception e) { }
			
			if (playerOne.) {
				GameOver = true;
			}
				
			if (playerTwo) {
				GameOver = true;
			}	
		}
	}
	
	public boolean canMove (Point np) {
		// get x,y coordinate
		int x = np.x;
		int y = np.y;
		
		// test playing board boundaries
		if ((x <= 0)|| (y <= 0)) return false;
		if ((x >= BoardWidth) || (y >= BoardHeight)) return false;
		
		// test caterpillars: can't move through self or other Caterpillar
		if (playerOne.inPosition(np))return false;
		if (playerTwo.inPosition(np))return false;
		
		//playerOne and playerTwo are checked here,
		//each caterpillar checks itself and its companion(s)
		// ok, safe square
		return true;
	}
	
	private class KeyReader extends KeyAdapter {
		public void keyPressed (KeyEvent e) {
			char c = e.getKeyChar();
			switch(c) {
			// player One keys
			case 'q': playerOne.setDirection('Z'); break;
			case 'a': playerOne.setDirection('W'); break;
			case 'd': playerOne.setDirection('E'); break;
			case 'w': playerOne.setDirection('N'); break;
			case 's': playerOne.setDirection('S'); break;
			// player Two keys
			case 'p': playerTwo.setDirection('Z'); break;
			case 'j': playerTwo.setDirection('W'); break;
			case 'l': playerTwo.setDirection('E'); break;
			case 'i': playerTwo.setDirection('N'); break;
			case 'k': playerTwo.setDirection('S'); break;
			// ignore all other keys
			} // end switch
		} // end key pressed
	}// end KeyReader private inner (nested) class
	

	private void newNumberSquare () {
		Random rd = new Random();
		number = rd.nextInt(9) + 1; 
		square = new Point(rd.nextInt(400) +1, rd.nextInt(400) + 1); 
	}
	
	public int squareScore(Point sp) {
		if (sp.equals(square)) {
			newNumberSquare();
			return number;
		}
		return 0;
	}
} /// public class CaterpillarGame

