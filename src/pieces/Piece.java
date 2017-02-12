package pieces;
import java.awt.*;
import java.awt.geom.AffineTransform;


/**
 * The abstract class Piece will be the parent class of each different chess piece.  A Piece
 * has a coordinate pair, a material value, and a boolean for whether it is a white piece 
 * or a black piece.
 */
public abstract class Piece {

	/**
	 * A coordinate pair to determine the piece's location on the board.
	 */
	private int x, y, value;

	/**
	 * A boolean to distinguish white and black pieces.  Some methods will act differently
	 * depending on this value.
	 */
	private boolean isWhite;


	/**
	 * The constructor takes a coordinate pair and a boolean as its parameters and
	 * initializes the data members.
	 */
	public Piece(int theX, int theY, int theValue, boolean isWhite){
		x = theX;
		y = theY;
		value = theValue;
		this.isWhite = isWhite;
	}

	/**
	 * Returns true if the piece is white, false if black.
	 */
	public boolean getWhite(){
		return isWhite;
	}

	/**
	 * The Pawn class is the only derived class that overrides this method.  For all other
	 * pieces, it returns false since only pawns can become a different piece.
	 */
	public boolean getPromoted(){
		return false;
	}

	/**
	 * Returns the material value of the piece.
	 */
	public int getValue(){
		return value;
	}

	/**
	 * Returns the X coordinate of the piece.
	 */
	public int getX(){
		return x;
	}

	/**
	 * Returns the Y coordinate of the piece.
	 */
	public int getY(){
		return y;
	}

	/**
	 * The King class is the only derived class that overrides this method.  For all other
	 * pieces, it returns 0 since only kings can castle.
	 */
	public int getCastling(){
		return 0;
	}

	/**
	 * This method is abstract because each piece will need to be able to draw itself
	 * but no two pieces are drawn the same.
	 */
	public abstract void drawPiece(Graphics pane);

	/**
	 * This method will be overridden by each piece, but will call the super for this method
	 * to check if there is an ally piece on the square they are attempting to move to.
	 */
	public boolean isLegal(int someX, int someY, Piece[] enemyPieces, Piece[] allyPieces){

		boolean result = true;
		for(Piece piece: allyPieces){
			if((piece != null) && (piece.isOn(someX, someY)))
				result = false;
		}
		return result;
	}

	/**
	 * Sets the location to a different coordinate pair.  This will be called after the
	 * move has been verified by isLegal().
	 */
	public void movePiece(int moveX, int moveY){
		x = moveX;
		y = moveY;
	}

	/**
	 * Determine if the piece is on a given coordinate pair received as parameters.  
	 * Returns true if the piece is on that square, false otherwise.
	 */
	public boolean isOn(int someX, int someY){
		boolean result = false;
		if((x == someX) && (y == someY))
			result = true;
		return result;

	}

	/**
	 * Given 2 arrays, one with the white player's pieces and one with the black
	 * player's pieces, return the combination of the two arrays.  This method will
	 * be called by a piece when it needs to check if there are any pieces between
	 * its current location and the other square which it wishes to move to.
	 */
	public Piece[] getAllPieces(Piece[] whitePieces, Piece[] blackPieces){

		Piece[] allPieces = new Piece[whitePieces.length + blackPieces.length];

		for(int i=0; i<allPieces.length; i++){
			if(i < whitePieces.length)
				allPieces[i] = whitePieces[i];
			else
				allPieces[i] = blackPieces[i-whitePieces.length];
		}
		return allPieces;
	}	

}  // End Piece
