package game;

import java.awt.*;

import pieces.*;

/**
 *There will be 2 instances of data members in the Board class, one for white and 
 *one for black.  The Player class has-a array of piece objects and a color. Players are
 *able to remove a piece if it has been taken, check if a move is valid for their
 *piece, and draw their pieces to the board.
 */
public class Player {

	/**
	 * The array of pieces the player has. An index of the array will be set to null
	 * if the piece is taken.
	 */
	private Piece[] pieces = new Piece[16];

	/**
	 * The color of the player.  This color will determine the color of their pieces.
	 */
	private Color color;
	
	/**
	 * True for the white player, false for the black player.
	 */
	private boolean isWhite;
	



	/**
	 * The Constructor sets the player to have a color (white or black) and calls the 
	 * correct method for initializing the pieces based on the boolean parameter.
	 */
	public Player(boolean isWhite){
		this.isWhite = isWhite;
		if(isWhite)
			color = Color.white;		
		else
			color = Color.black;
		

		initializePieces();
	}

	/**
	 * Fills the array of Pieces that the player has.
	 */
	private void initializePieces(){
		int homeRow;
		int pawnRow;
		
		if(isWhite){
			homeRow = 1;
			pawnRow = 2;
			
		}
		else{
			homeRow = 8;
			pawnRow = 7;
		}
		pieces[0] = new Rook(8,homeRow, isWhite);	// queen side rook
		pieces[1] = new Rook(1,homeRow, isWhite);
		pieces[2] = new Pawn(1,pawnRow, isWhite);
		pieces[3] = new Pawn(2,pawnRow, isWhite);
		pieces[4] = new Pawn(3,pawnRow, isWhite);
		pieces[5] = new Pawn(4,pawnRow, isWhite);
		pieces[6] = new Pawn(5,pawnRow, isWhite);
		pieces[7] = new Pawn(6,pawnRow, isWhite);
		pieces[8] = new Pawn(7,pawnRow, isWhite);
		pieces[9] = new Pawn(8,pawnRow, isWhite);
		pieces[10] = new Knight(2,homeRow, isWhite);
		pieces[11] = new Knight(7,homeRow, isWhite);
		pieces[12] = new Bishop(3,homeRow, isWhite);
		pieces[13] = new Bishop(6,homeRow, isWhite);
		pieces[14] = new Queen(5,homeRow, isWhite);
		pieces[15] = new King(4,homeRow, isWhite);
		
	}
	
	
	
	/**
	 * Returns the material value of all of the player's pieces.
	 */
	public int getMaterialValue(){
		int materialValue = 0;
		
		for(Piece piece: pieces){
			if(piece != null)
				materialValue += piece.getValue();
		}
	return materialValue;	
	}


	/**
	 * If an enemy piece has just moved to a square with this
	 * player's piece on it, remove that piece.
	 */
	public void checkPieceDied(int theX, int theY){

		for(int i=0; i<pieces.length; i++){
			if((pieces[i] != null) && (pieces[i].isOn(theX, theY)))
				pieces[i] = null;

		}
	}

	/**
	 * Promotes the pawn to a queen if the pawn has made it to the
	 * other side of the board.
	 */
	public void checkPawnPromotion(int theX, int theY){
		
		for(int i=0; i< pieces.length; i++){
			if((pieces[i] != null) && (pieces[i].getPromoted()))
					pieces[i] = new Queen(theX, theY, isWhite);
			
		}
		checkCastling();
	}
	
	/**
	 * Called in the checkPawnPromotion method.  This method moves the corresponding
	 * rook to its proper spot if the king has just castled.
	 */
	private void checkCastling(){
		
		int castled = pieces[15].getCastling();
		if((castled != 0) && (pieces[0] != null)){
			if(castled == 1)		// castling king side
				pieces[1].movePiece(pieces[1].getX() + 2, (pieces[1].getY()));
			else if(castled == 2)	// castling queen side
				pieces[0].movePiece(pieces[0].getX() - 3, (pieces[0].getY()));
		}
		
			
		
	}
	
	
	/**
	 * Given 2 coordinate pairs and all of the enemy's pieces, decide if a piece
	 * on the first coordinate has a legal move to the second coordinate.  If so,
	 * move the piece to the new square.
	 */
	public boolean checkMove(int newX, int newY, int theOldX, int theOldY,
			Piece[] otherPieces){
		boolean legal = false;
		for(Piece piece: pieces){
			if((piece != null) && ( piece.isOn(theOldX, theOldY)) && 
					(piece.isLegal(newX, newY, otherPieces, pieces))){
				legal = true;
				if(legal){
					piece.movePiece(newX, newY);
					
					if(kingInCheck(otherPieces, newX, newY)){
						legal = false;
						piece.movePiece(theOldX, theOldY);
					}
				}
			}
		}
		return legal;
	}
	
	
	/**
	 * Returns the X coordinate of the king.
	 */
	public int getKingCoordX(){
		return pieces[15].getX();
	}
	
	/**
	 * Returns the Y coordinate of the king.
	 */
	public int getKingCoordY(){
		return pieces[15].getY();
	}

	/**
	 * Returns true if the king is in check.
	 */
	public boolean kingInCheck(Piece[] enemyPieces, int someX, int someY){
		
		boolean result = false;
		int kingX = getKingCoordX();
		int kingY = getKingCoordY();
		
		for(Piece piece: enemyPieces){
			if((piece != null) && (!piece.isOn(someX, someY)) && 
					(piece.isLegal(kingX, kingY, pieces, enemyPieces)))
				result = true;			
		}
		
		return result;
	}

	
	/**
	 * Returns all of the player's pieces.  This will be used by individual
	 * pieces to check if their move is legal.
	 */
	public Piece[] getPieces(){

		return pieces;
	}

	/**
	 * Draws each piece which is still alive.
	 */
	public void drawPieces(Graphics pane){

		for(Piece piece: pieces){
			pane.setColor(color);
			if(piece != null)
				piece.drawPiece(pane);
		}
	}
}  // end Player