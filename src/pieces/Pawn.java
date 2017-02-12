package pieces;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;

/**
 * A derived class of Piece. In chess, a pawn is the only piece that moves differently than
 * it attacks.  Pawns attack 1 space in the forward diagonal direction, and move 1 space in 
 * the forward direction. If a pawn gets to the end of the board, it is promoted to a queen.
 * Additionally, a pawn can move 2 spaces forward only if it has not moved yet. This piece
 * has a material value of 1.
 */
public class Pawn extends Piece{

	/**
	 * A constant to determine the size of the circle on top of the pawn.
	 */
	private final int CIRCLE_RADIUS = 20;

	/**
	 * A boolean set to be true if the pawn makes it to the other side of the board.
	 */
	private boolean  promoted = false;


	/**
	 * Passes its starting coordinate location on the board, its material value, and a boolean
	 * which determines if the piece is part of the white or black pieces.
	 */
	public Pawn(int theX, int theY, boolean isWhite) {
		super(theX, theY, 1, isWhite);

	}

	/**
	 * Draws the pawn at its location on the board.
	 */
	public void drawPiece(Graphics pane) {


		int topX = getX()*50, topY = getY()*50;

		// Draw top portion
		pane.fillOval(topX + 25 - CIRCLE_RADIUS/2, topY + 20 - CIRCLE_RADIUS/2 ,20, 20);
		pane.fillRect(topX + 25 - CIRCLE_RADIUS/2, topY + 19 + CIRCLE_RADIUS/2, CIRCLE_RADIUS, 5);

		pane.fillRect(topX + 10, topY + 42, 30, 5);

		// Draw middle portion
		Polygon middle = new Polygon();
		middle.addPoint(topX + 12, topY + 42);
		middle.addPoint(topX + 37, topY + 42);
		middle.addPoint(topX + 30, topY + 19 + CIRCLE_RADIUS/2);
		middle.addPoint(topX + 20, topY + 19 + CIRCLE_RADIUS/2);
		pane.fillPolygon(middle);

		// Draw accenting lines
		pane.setColor(Color.gray);

		pane.drawLine(topX + 25 - CIRCLE_RADIUS/2, topY + 19 + CIRCLE_RADIUS/2, topX + 25 + CIRCLE_RADIUS/2, topY + 19 + CIRCLE_RADIUS/2);
		pane.drawLine(topX + 25 - CIRCLE_RADIUS/2, topY + 24 + CIRCLE_RADIUS/2, topX + 25 + CIRCLE_RADIUS/2, topY + 24 + CIRCLE_RADIUS/2);
		pane.drawLine(topX + 10, topY + 42, topX + 40, topY + 42);


	}

	/**
	 * Given the coordinate location the piece is attempting to move to, and the arrays of both 
	 * player's pieces, this method will return true if the move is a legal move.
	 */
	public boolean isLegal(int someX, int someY, Piece[] enemyPieces, Piece[] allyPieces) {
		boolean result = false;


		Piece[] allPieces = getAllPieces(enemyPieces, allyPieces);


		if(getWhite()){									// if the pawn is white
			if((someX == getX()) && (someY == getY() + 1)){
				result = true;
				for(Piece piece: allPieces){
					if((piece != null) && (piece.isOn(getX(), getY()+1)))
						result = false;
				}
			}
			else if((someY == getY()+2) && (getY() == 2) && (someX == getX())){
				result = true;
				for(Piece piece: allPieces){
					if((piece != null) && ((piece.isOn(getX(), getY()+2) || (piece.isOn(getX(), getY()+1)))))
						result = false;
				}
			}
			else if((someY == getY()+1) && (someX == getX()+1 )){
				for(Piece piece: enemyPieces){
					if((piece != null) && ((piece.isOn(getX()+1, getY()+1))))
						result = true;
				}
			}
			else if((someY == getY()+1) && (someX == getX()-1 )){
				for(Piece piece: enemyPieces){
					if((piece != null) && (piece.isOn(getX()-1, getY()+1)))
						result = true;
				}
			}

			if((result) && (someY == 8))		//  if pawn is at the end of the board
				promotePawn();					// promotion

		}else{ 									// if the pawn is black
			if((someX == getX()) && ((someY == getY() - 1))){
				result = true;

				for(Piece piece: allPieces){
					if((piece != null) && (piece.isOn(getX(), getY()-1)))
						result = false;
				}

			}else if((someY == getY() - 2) && (getY() == 7) && (someX == getX())){
				result = true;
				for(Piece piece: allPieces){
					if((piece != null) && ((piece.isOn(getX(), getY()-1) || piece.isOn(getX(), getY()-2))))
						result = false;


				}


			}
			else if((someY == getY()-1) && (someX == getX()+1 )){
				for(Piece piece: enemyPieces){
					if((piece != null) && (piece.isOn(getX()+1, getY()-1)))
						result = true;
				}
			}
			else if((someY == getY()-1) && (someX == getX()-1 )){		
				for(Piece piece: enemyPieces){
					if((piece != null) && (piece.isOn(getX()-1, getY()-1)))
						result = true;
				}
			}
			if((result) && (someY == 1))		//  if pawn is at the end of the board
				promotePawn();					// promotion

		}
		return result;
	}

	/**
	 * Sets promoted to true when the pawn reaches the other side of the board.
	 */
	private void promotePawn(){
		promoted = true;
	}

	/**
	 * Returns true if the pawn has just promoted.
	 */
	public boolean getPromoted(){
		return promoted;
	}

}// End Pawn