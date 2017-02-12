package pieces;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

/**
 * A derived class of Piece. In chess, the queen can move like a bishop and a rook combined.  Like
 * most pieces, it cannot jump over other pieces.  It is the strongest attacking piece on the board
 * and therefore has a material value of 9.
 */
public class Queen extends Piece{

	/**
	 * Passes its starting coordinate location on the board, its material value, and a boolean
	 * which determines if the piece is part of the white or black pieces.
	 */
	public Queen(int theX, int theY, boolean isWhite) {
		super(theX, theY, 9, isWhite);
	}

	/**
	 * Draws the queen at its location on the board.
	 */
	public void drawPiece(Graphics pane) {
		int topX = getX()*50, topY = getY()*50;

		// Draw the crown
		pane.fillOval(topX + 3, topY + 10, 10, 10) ;
		pane.fillOval(topX + 14, topY + 3, 10, 10) ;
		pane.fillOval(topX + 28, topY + 3, 10, 10) ;
		pane.fillOval(topX + 38, topY + 10, 10, 10) ;

		Polygon body= new Polygon();
		body.addPoint(topX+11, topY+36);
		body.addPoint(topX+6, topY+18);
		body.addPoint(topX+9, topY+16);
		body.addPoint(topX+14, topY+23);
		body.addPoint(topX+17, topY+12);
		body.addPoint(topX+21, topY+11);
		body.addPoint(topX+25, topY+23);
		body.addPoint(topX+29, topY+11);
		body.addPoint(topX+33, topY+12);
		body.addPoint(topX+35, topY+23);
		body.addPoint(topX+41, topY+17);
		body.addPoint(topX+44, topY+18);
		body.addPoint(topX+38, topY+36);
		pane.fillPolygon(body);



		// Draw base 
		pane.fillRoundRect(topX + 8, topY + 41, 34, 7, 3, 3);
		pane.fillOval(topX+8, topY + 39, 34, 6);


		// Draw accenting lines
		pane.setColor(Color.gray);
		pane.drawLine(topX + 11 , topY + 35 , topX + 38 , topY + 35 );
		pane.drawLine(topX + 8 , topY + 47 , topX + 42 , topY + 47 );


	}


	/**
	 * Given the coordinate location the piece is attempting to move to, and the arrays of both 
	 * player's pieces, this method will return true if the move is a legal move.
	 */
	public boolean isLegal(int someX, int someY, Piece[] enemyPieces,
			Piece[] allyPieces) {

		boolean result = false;
		boolean moveDiagonal = false;
		int distanceX = someX - getX();
		int distanceY = someY - getY();
		Piece[] allPieces = getAllPieces(enemyPieces, allyPieces);

		if(Math.abs(distanceX) == Math.abs(distanceY))		// moving like a bishop
			moveDiagonal = true;
		else if((getX() == someX) ^ (getY() == someY))		// moving like a rook
			moveDiagonal = false;


		if(((Math.abs(distanceX) == Math.abs(distanceY))) || ((getX() == someX) ^ (getY() == someY))){
			result = super.isLegal(someX, someY, enemyPieces, allyPieces);
		}
		if(moveDiagonal){
			for(Piece piece: allPieces){
				if(piece != null){


					if(distanceX < 0){ 			// if moving to the left
						for(int i=1; i < Math.abs(distanceX); i++){
							if(distanceY <0){  // if moving up
								if(piece.isOn(getX()-i, getY()-i))
									result = false;
							}else{				// if moving down

								if(piece.isOn(getX()-i, getY()+i))
									result = false;
							}
						}

					}else { 			// if moving to the right
						for(int i=1; i < Math.abs(distanceX); i++){
							if(distanceY <0){		// if moving up
								if(piece.isOn(getX()+i, getY()-i))
									result = false;
							}else				// if moving down
								if(piece.isOn(getX()+i, getY()+i))
									result = false;
						}


					}
				}
			}
		}else{
			if(getX() == someX){
				for(Piece piece: allPieces){
					if(piece != null){

						for(int i=1; i < Math.abs(distanceY); i++){
							if(distanceY > 0){
								if(piece.isOn(someX, someY-i))
									result = false;
							}else
								if(piece.isOn(someX, someY+i))
									result = false;
						}
					}
				}

			}else if (getY() == someY){
				for(Piece piece: allPieces){
					if(piece != null)
						for(int i=1; i < Math.abs(distanceX); i++){
							if(distanceX > 0){
								if(piece.isOn(someX-i, someY))
									result = false;
							}else

								if(piece.isOn(someX+i, someY))
									result = false;
						}
				}
			}
		}
		return result;
	}

}  //  End Queen