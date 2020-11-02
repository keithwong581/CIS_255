/*************************
 * Keith Wong
 * CIS 255
 * 
 * 
 ************************/

private class gameMover extends Thread {
	// override the run( ) method of Thread
	public void run ( ) {
		while (true) {
			movePieces( );
			repaint( );
			try {
				sleep(100);
			} catch (Exception e) { }
		}
	}
}