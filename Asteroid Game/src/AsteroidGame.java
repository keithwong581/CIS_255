/**********************************************
 * Keith Wong
 * CIS 255
 * 
 * Creates the Asteroid Game window
 * Takes key inputs from keyboard
 * Moves and removes asteroids from the game
 **********************************************/

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class AsteroidGame extends Frame {
	private int FrameWidth = 500;
	private int FrameHeight = 400;
	
	static public void main (String [ ] args)
	{ AsteroidGame world = new AsteroidGame( ); world. isVisible( ); world.run( ); }
	
	public AsteroidGame ( ) {
		setTitle("Asteroid Game"); setSize(FrameWidth, FrameHeight);
		setSize(500, 400);
		addKeyListener (new keyDown( ));
		addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent e) {
			      dispose();
			   }
			});
		
	}
	
	public void run ( ) { // move pieces
		while (true) {
			movePieces( );
			repaint( );
			try { // pause 100 milliseconds in order
				Thread.sleep(100); // to create animation illusion
			} catch (Exception e) { } // must be in try-catch
		} // more details later...
	}

	private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>( );
	private ArrayList<Rocket> rockets = new ArrayList<Rocket>( );
	// Station position middle of baseline
	private Station station = new Station (FrameWidth/2, FrameHeight-20);

	public void paint (Graphics g) {
		station.paint(g);
		//<< start with the beginning of the asteroids container >> ;
		//Asteroid((0));
		Iterator<Asteroid> astIt = asteroids.iterator();
		//asteroids(0);
		 while (astIt.hasNext()) {
	            Asteroid rock = (Asteroid) astIt.next();
	            if (rock.y >= 400 || rock.x >= 500){
	            	astIt.remove();
	            } else {
	                rock.paint(g);
	            }

	        }
		 Iterator<Rocket> rocIt = rockets.iterator();
	        while (rocIt.hasNext()) {
	            Rocket rock = (Rocket) rocIt.next();
	            if (rock.y >= 400 || rock.x >= 500) {
	            	astIt.remove();
	            } else {
	                rock.paint(g);
	            }
	        }

	    }
	
	private void movePieces ( ) {
		// create a random new asteroid – 30% of the time
		if (Math.random( ) < 0.3) {
			Asteroid newRock = new Asteroid(
					FrameWidth * Math.random( ), 20,
					10 * Math.random( ) - 5, 3 + 3 * Math.random( ));
			if (newRock.y >= 500 || newRock.x >= 500){

                asteroids.remove(0);

            } else{
                asteroids.add(newRock);
            }
            System.out.println(asteroids.size());

        }
		}
		
		// then move everything
		Iterator<Asteroid> astIt = asteroids.iterator(); {
		while (astIt.hasNext()) {
			Asteroid rock = (Asteroid) astIt.next();
			rock.move( );
			station.checkHit(rock);
		}
		Iterator rocIt = rockets.iterator();
        while (rocIt.hasNext()) {
        	Rocket rock = (Rocket) rocIt.next();
        	if (rock.y >= 400 || rock.x >= 500) {
        		astIt.remove();
            } else {
                rock.move(asteroids);
		}
	}
     public class keyDown extends KeyAdapter{
    	 public void keyPressed (KeyEvent e) {
    		char key = e.getKeyChar( );
    		switch (key) {
    		case 'j': station.moveLeft( ); break; // turn left
    		case 'k': station.moveRight( ); break; // turn right
    		case ' ': station.fire(rockets); break; // space: fire
    		case 'q': System.exit(0); // q: quit
    		}
    	}
    }
}