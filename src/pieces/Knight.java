package pieces;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

/**
 * A derived class of Piece. In chess, the knight is unique as it is the only piece which can
 * jump over other pieces.  Knights move in an L shape and have a material value of 3.
 */
public class Knight extends Piece {

	/**
	 * Passes its starting coordinate location on the board, its material value, and a boolean
	 * which determines if the piece is part of the white or black pieces.
	 */
	public Knight(int theX, int theY, boolean isWhite) {
		super(theX, theY, 3, isWhite);

	}

	/**
	 * Draws the knight at its location on the board.
	 */
	public void drawPiece(Graphics pane) {

		int topX = getX()*50, topY = getY()*50;

		// Draw body 
		Polygon body= new Polygon();
		body.addPoint(topX+16, topY+36);
		body.addPoint(topX+15, topY+18);
		body.addPoint(topX+16, topY+15);
		body.addPoint(topX+24, topY+5);
		body.addPoint(topX+27, topY+3);
		body.addPoint(topX+27, topY+8);
		body.addPoint(topX+38, topY+15);
		body.addPoint(topX+36, topY+23);
		body.addPoint(topX+33, topY+18);
		body.addPoint(topX+25, topY+18);
		body.addPoint(topX+29, topY+36);
		body.addPoint(topX+30, topY+36);
		body.addPoint(topX+30, topY+41);
		body.addPoint(topX+35, topY+42);
		body.addPoint(topX+35, topY+47);
		body.addPoint(topX+9, topY+47);
		body.addPoint(topX+9, topY+42);
		body.addPoint(topX+14, topY+41);

		pane.fillPolygon(body);


		// Draw accenting lines
		pane.setColor(Color.gray);
		pane.drawLine(topX + 9, topY + 42, topX + 35, topY + 42);



	}

	/**
	 * Given the coordinate location the piece is attempting to move to, and the arrays of both 
	 * player's pieces, this method will return true if the move is a legal move.
	 */
	public boolean isLegal(int someX, int someY, Piece[] enemyPieces, Piece[] allyPieces) {
		boolean result = false;



		if(((someX == getX()+1) || (someX == getX()-1)) && ((someY == getY()+2) || (someY == getY()-2)))
			result = super.isLegal(someX, someY, enemyPieces, allyPieces);
		else if(((someX == getX()+2) || (someX == getX()-2)) && ((someY == getY()+1) || (someY == getY()-1)))
			result = super.isLegal(someX, someY, enemyPieces, allyPieces);
		return result;
	}

} // End Knight