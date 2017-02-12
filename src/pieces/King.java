package pieces;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

/**
 * A derived class of Piece. In chess, a player cannot move if their king is currently
 * being threatened, or 'in check'.  The king can move 1 space in any direction, unless it
 * is 'castling', in which case it moves 2 squares to the left and right.  A king can only
 * castle if it has not moved yet. The king has a material value of 0 since it is never
 * actually captured.
 */
public class King extends Piece{

	/**
	 * A boolean to keep track if the king has moved from its home square. After the 1st time
	 * the king moves, hasMoved is set to true, and the king can no longer castle.
	 */
	private boolean hasMoved = false;

	/**
	 * Constants to determine if the king is or is not castling to a certain side.
	 */
	private final int NOT_CASTLING = 0, CASTLED_KING = 1, CASTLED_QUEEN = 2;

	/**
	 * This value will equal 0 if the king is not trying to castle, 1 if the king is trying to
	 * castle king side, and 2 if the king is trying to castle queen side.  Used as a test by the
	 * player of the king in order to move the respective rook.
	 */
	private int castled = 0;

	/**
	 * Passes its starting coordinate location on the board, its material value, and a boolean
	 * which determines if the piece is part of the white or black pieces.
	 */
	public King(int theX, int theY, boolean isWhite) {
		super(theX, theY, 0, isWhite);
	}


	/**
	 * Will be called to check if the king has just attempted to castle on the last move.
	 */
	public int getCastling(){
		return castled;
	}


	/**
	 * Draws the king at its location on the board.
	 */
	public void drawPiece(Graphics pane) {

		int topX = getX()*50, topY = getY()*50;

		// base
		pane.fillRect(topX + 5, topY + 40, 40, 7);
		pane.fillRect(topX + 12,topY + 35, 26, 5);
		pane.fillRect(topX + 20,topY + 25, 10, 10);

		// draw the top of the king
		Polygon head= new Polygon();
		head.addPoint(topX+20, topY+28);

		head.addPoint(topX+12, topY + 15);
		head.addPoint(topX+38, topY + 15);

		head.addPoint(topX+30, topY+28);
		pane.fillPolygon(head);


		// crown
		pane.fillRect(topX + 24, topY + 3, 3, 12);
		pane.fillRect(topX + 20, topY + 8, 11, 3);


		// Draw accenting lines
		pane.setColor(Color.gray);

		pane.drawLine(topX + 5 , topY + 40 , topX + 45 , topY + 40 );
		pane.drawLine(topX + 12 , topY + 35 , topX + 38 , topY + 35 );
		pane.drawLine(topX + 12 , topY + 15 , topX + 38 , topY + 15 );
	}

	/**
	 * Given the coordinate location the piece is attempting to move to, and the arrays of both 
	 * player's pieces, this method will return true if the move is a legal move.
	 */
	public boolean isLegal(int someX, int someY, Piece[] enemyPieces,
			Piece[] allyPieces) {

		boolean result = true;

		castled = NOT_CASTLING;
		int distanceX = someX - getX();
		int distanceY = someY - getY();

		Piece[] allPieces = getAllPieces(enemyPieces, allyPieces);

		if((distanceX == 0 ) && (Math.abs(distanceY) == 1)){
			result = super.isLegal(someX, someY, enemyPieces, allyPieces);
		}
		else if((Math.abs(distanceX) == 1) && ((distanceY ==0) || (Math.abs(distanceY) == 1))){
			result = super.isLegal(someX, someY, enemyPieces, allyPieces);
		}
		else if((!hasMoved) && (distanceX == 2) && (distanceY == 0)){  // castling queen side
			castled = CASTLED_QUEEN;
			for(Piece piece: allPieces){
				for(int i=1; i <= 3; i++)
					if((piece != null) && (piece.isOn(getX()+i, getY()))){
						result = false;
						castled = NOT_CASTLING;
					}
			}
		}
		else if((!hasMoved) && (distanceX == -2) && (distanceY ==0)){  // castling king side
			castled = CASTLED_KING;
			for(Piece piece: allPieces){
				for(int i=1; i <= 2; i++)
					if((piece != null) && (piece.isOn(getX()-i, getY()))){
						result = false;
						castled = NOT_CASTLING;
					}
			}

		}
		else
			result = false;


		if(result)
			hasMoved = true;
		return result;
	}

} // End King