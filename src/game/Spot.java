package game;
import java.awt.*;

import pieces.*;

/**
 * The Spot class is essentially used to draw the board.  A 2D array of spots (8x8) will be
 *  stored in the Board class to keep track of the 64 squares on the chess board.  Spots 
 *  are capable of drawing themselves to the graphics window and changing color if they 
 *  are selected (i.e. the user clicks the square).
 * 
 */
public class Spot {
	/**
	 * The coordinate pair to determine where to be drawn. Values of x and y should be
	 * between 1 and 8.
	 */
	private int x, y;

	private boolean isRed = false;
	/**
	 * A constant to determine the size of the squares of the board.
	 */
	private final int BOX_SIZE = 50;

	/**
	 * 2 colors: one for the actual color (either white or blue) and one to store 
	 * temporary changes to the spot's color if it is selected.
	 */
	private Color spotColor, currentColor;

	/**
	 * The constructor is fairly simple.  It first takes the x and y coordinates as its 
	 * parameters to store the location of the board. Then it tests what color spotColor
	 * should be in order to create a checkered patter of the board.
	 */
	public Spot(int theX, int theY){
		x = theX;
		y = theY;	

		if((x+y)%2 == 1)
			spotColor = new Color(0, 200, 200);
		else

			spotColor = new Color(230, 230, 230);
		currentColor = spotColor;
	}

	/**
	 * This sets the fill color to be the currentColor and then draws a square 50x50 in
	 * the correct location.
	 */
	public void drawSpot(Graphics pane){

		pane.setColor(currentColor);
		pane.fillRect(x*50, y*50, BOX_SIZE, BOX_SIZE);

	}

	/**
	 * Takes 2 coordinates and tests to see if they are contained in the spot.  This will
	 * be used to test if the user has clicked a square.
	 */
	public boolean isInside(int someX, int someY){
		return(someX>=x*50 && someX<=(50*x+BOX_SIZE) && someY>=50*y && someY <=(50*y+BOX_SIZE));		
	}

	/**
	 * Sets the currentColor to red. This is called if the king is in check.
	 */
	public void setSquareRed(){
		currentColor = new Color(200, 0, 0);
		isRed = true;
	}

	/**
	 * Sets the currentColor to yellow.
	 */
	public void select(){
		currentColor = new Color(250, 250, 100);

	}

	/**
	 * Sets the currentColor back to the original color unless the king is in check.
	 */
	public void unselect(boolean reset){
		if((!isRed) || (reset))
			currentColor = spotColor;
		isRed = false;

	}

}  // end Spot
