/*
 * Stores a grid[x][y] of booleans for tetris with 0,0 as the upper left.
 */
public class SimpleTetris {
   public static final int WIDTH = 10;    // don't assume it is always 10
   public static final int HEIGHT = 20;   // don't assume it is always 20
 
   private boolean[i][j] grid;

   public void Tetris() {
      grid = new boolean[WIDTH][HEIGHT];
   }
 
   // Clears the given row y, moving rows above it down one position
   // Then erase the top row
   public void clearRow(int y) {
     /*
      * Complete the rest of this method
      * Write your code in the answer window, mark it as Part 1 of 3
      * Select the "Preformatted" font so that indentation is not lost
      * Font selection in lower-right corner of editor options
      */
   }
 
   // True if the given row is full all the way across
   public boolean isFull(int y) {
     /*
      * Complete the rest of this method
      * Write your code in the answer window, mark it as Part 2 of 3
      * Select the "Preformatted" font so that indentation is not lost
      * Font selection in lower-right corner of editor options
      */
   }
 
   // Clears all the full rows
	public void clearRows() {
		boolean gap;
		int numClears = 0;
		
		for (int j = 21; j > 0; j--) {
			gap = false;
			for (int i = 1; i < 11; i++) {
				if (well[i][j] == Color.BLACK) {
					gap = true;
					break;
				}
			}
			if (!gap) {
				deleteRow(j);
				j += 1;
				numClears += 1;
			}
		}
   }
}