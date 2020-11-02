/***********************************
 * Keith Wong
 * CIS 255
 * 
 * Paints and moves the caterpillar
 **********************************/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Caterpillar {
	private Color color;
	private Point position;
	private char direction = 'E';
	private int score;
	private Queue<Point> body = new LinkedList<Point>();
	private Queue<Character> commands = new LinkedList<Character>();
	private int caterpillarLength = 10;
	
	public Caterpillar (Color c, Point sp) {
		color = c;
		score = 0;
		for (int i = 0; i < caterpillarLength; i++) { 
			position = new Point(sp.x + i, sp.y); // each caterpillar is 10 pieces (circles)
			body.add(position);
		}
	}
	
	void setDirection (char d) {
		commands.add(new Character(d));
	}
	
	public void move (CaterpillarGame game) {
		// first see if we should change direction
		if (commands.size()>0) {
			Character c = (Character) commands.peek(); // just peek
			commands.remove();
			direction = c.charValue(); // Character wrapper to char
			if (direction == 'Z') return;
		}
		// then find new position
		Point np = newPosition();
		if (game.canMove(np)) {
			// erase one segment, add another
			body.remove();
			body.add(np);
			position = np;
		}
	}
	
	private Point newPosition() {
		int x = position.x;
		int y = position.y;
		if (direction == 'E') 
			x++;
		else if (direction == 'W') 
			x--;
		else if (direction == 'N') 
			y--;
		else if (direction == 'S') 
			y++;
		return new Point(x,y);
	}
	
	public boolean inPosition (Point np) { // checks if position may be occupied
		Iterator <Point>it = body.iterator();
        while(it.hasNext()){
            Point location = it.next();
			if (np.equals(location)) 
				return true;
		}
		return false;
	}
	
	public int getScore() {
	        return score;
	}
	 
	public void paint (Graphics g) {
		g.setColor(color);
		Iterator <Point>it = body.iterator();
		// iterator stuff
		while(it.hasNext()){
            Point p = it.next();
			g.fillOval(5 + CaterpillarGame.SegmentSize * p.x,
					15 + CaterpillarGame.SegmentSize * p.y,
					CaterpillarGame.SegmentSize,
					CaterpillarGame.SegmentSize);
		}
	}
	
	//Increases the size of the caterpiller
	public void addCaterpillarLength() {
		caterpillarLength = caterpillarLength +1; 
	}
	
	//Decreases the size of the caterpiller
	public void subtractCaterpillarLength() {
		caterpillarLength = caterpillarLength +1; 
	}
}
