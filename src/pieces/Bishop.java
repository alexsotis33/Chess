package pieces;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

/**
 * A derived class of Piece. In chess, the bishop can move any amount of spaces diagonally as long
 * as it does not jump over other pieces.  Bishops have a material value of 3.
 */
public class Bishop extends Piece{

	/**
	 * Passes its starting coordinate location on the board, its material value, and a boolean
	 * which determines if the piece is part of the white or black pieces.
	 */
	public Bishop(int theX, int theY, boolean isWhite) {
		super(theX, theY, 3, isWhite);
	}


	/**
	 * Draws the bishop at its location on the board.
	 */
	public void drawPiece(Graphics pane) {

		int topX = getX()*50, topY = getY()*50;


		// Draw body 
		pane.fillOval(topX + 22, topY + 5, 6, 13);
		pane.fillOval(topX + 16, topY + 12, 18, 16);

		pane.fillRect(topX+20, topY+27, 10, 10);
		pane.fillRect(topX+18, topY+32, 14, 8);

		pane.fillRect(topX+13, topY+37, 24, 4);
		pane.fillRect(topX+10, topY+41, 30, 4);


		// Draw accenting lines
		pane.setColor(Color.gray);
		pane.drawLine(topX + 20, topY + 27, topX + 29, topY + 27);
		pane.drawLine(topX + 13, topY + 37, topX + 36, topY + 37);
		pane.drawLine(topX + 10, topY + 41, topX + 39, topY + 41);

		// Draw cross 
		pane.drawLine(topX + 25, topY + 16, topX + 25, topY + 22);
		pane.drawLine(topX + 22, topY + 20, topX + 28, topY + 20);
	}


	/**
	 * Given the coordinate location the piece is attempting to move to, and the arrays of both 
	 * player's pieces, this method will return true if the move is a legal move.
	 */
	public boolean isLegal(int someX, int someY, Piece[] enemyPieces,
			Piece[] allyPieces) {

		boolean result = false;
		int distanceX = someX - getX();
		int distanceY = someY - getY();
		Piece[] allPieces = getAllPieces(enemyPieces, allyPieces);


		if(Math.abs(distanceX) == Math.abs(distanceY)){
			result = super.isLegal(someX, someY, enemyPieces, allyPieces);
		}
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
		return result;
	}

} //  End Bishop