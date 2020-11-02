
public class randPoint {

private Point randomPoint( ) {
	   Point p = new Point((int) (Math.random( ) * (getWidth( ) – PIXELS)),
	                       (int) (Math.random( ) * (getHeight( ) – PIXELS)));
	   return(p);
	}

	// Reset things for the start of a game
	public void reset( ) {
	   myPoints.clear( );   // removes all the points
	   for (int i=0; i < CARROTS; i++) {
	      myPoints.add( randomPoint( ) );
	   }
	 
	   myX = getWidth( ) / 2;
	   myY = 0;
	   myDy = 0;
	   
	   secondX = getWidth()/2;
	   secondY = 0;
	   secondDy = 0;		
	   
	   repaint( );
	}
	
	private boolean checkPoint() {
		if(randomPoint. ) {
			
		return false;
		}
		
	}
}