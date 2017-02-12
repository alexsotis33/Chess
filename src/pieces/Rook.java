package pieces;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

/**
 * A derived class of Piece. In chess, the rook can move down columns and through rows as 
 * many spaces as it wants as long as it does not jump over another piece.  It has a material
 * value of 5.
 */
public class Rook extends Piece{

	/**
	 * Passes its starting coordinate location on the board, its material value, and a boolean
	 * which determines if the piece is part of the white or black pieces.
	 */
	public Rook(int theX, int theY, boolean isWhite){
		super(theX, theY, 5, isWhite);
	}


	/**
	 * Draws the rook at its location on the board.
	 */
	public void drawPiece(Graphics pane1){


		int xBody = getX()*250;
		int yBody = getY()*250;
		Graphics2D pane = (Graphics2D) pane1;
		AffineTransform saveTransform = pane.getTransform();

		try {
			AffineTransform scaleMatrix = new AffineTransform();
			scaleMatrix.scale(0.2, 0.2);
			scaleMatrix.translate(84,72);

			pane.setTransform(scaleMatrix);


			// here
			int bodyHeight=100, bodyWidth=80;

			// draw bottom portion
			Polygon base= new Polygon();
			base.addPoint(xBody,yBody + bodyHeight);
			base.addPoint(xBody + bodyWidth,yBody + bodyHeight);
			base.addPoint(xBody + bodyWidth + 30,yBody + bodyHeight + 30);
			base.addPoint(xBody-30,yBody + bodyHeight + 30);
			pane.fillPolygon(base);
			pane.fillRect(xBody - 30, yBody + bodyHeight + 30, bodyWidth+60, 25);
			pane.fillRect(xBody-40, yBody+bodyHeight+55, bodyWidth+80, 20);


			//draw middle portion
			pane.fillRect(xBody,yBody,bodyWidth,bodyHeight);

			// draw top portion
			Polygon top = new Polygon();
			top.addPoint(xBody, yBody);
			top.addPoint(xBody - 30, yBody - 20);
			top.addPoint(xBody + bodyWidth + 30, yBody - 20);
			top.addPoint(xBody + bodyWidth, yBody);
			pane.fillPolygon(top);
			pane.fillRect(xBody - 30, yBody - 40, bodyWidth + 60, 20);
			pane.fillRect(xBody - 30, yBody - 60, 28, 40);
			pane.fillRect(xBody + 26, yBody - 60, 28, 40);
			pane.fillRect(xBody + 82, yBody - 60, 28, 40);

			//draw accenting lines
			pane.setColor(Color.gray);
			pane.drawLine(xBody - 30, yBody - 20, xBody+bodyWidth + 30, yBody-20);
			pane.drawLine(xBody, yBody, xBody + bodyWidth, yBody);
			pane.drawLine(xBody, yBody + bodyHeight, xBody+bodyWidth, yBody + bodyHeight);
			pane.drawLine(xBody - 30, yBody + bodyHeight + 30, xBody + bodyWidth + 30,yBody + bodyHeight + 30);
			pane.drawLine(xBody - 40, yBody + bodyHeight + 55, xBody + bodyWidth + 40,yBody + bodyHeight + 55);

			//shading
			pane.setColor(Color.lightGray);
			pane.fillRect(xBody + 5, yBody + 5, 10, bodyHeight-10);

			Polygon baseShade = new Polygon();
			baseShade.addPoint(xBody ,yBody + bodyHeight + 5);
			baseShade.addPoint(xBody + 15, yBody + bodyHeight + 5);
			baseShade.addPoint(xBody - 5,yBody + bodyHeight + 25);
			baseShade.addPoint(xBody - 20, yBody + bodyHeight + 25);
			pane.fillPolygon(baseShade);

			pane.fillRect(xBody - 25, yBody + bodyHeight + 35, 10, 15);
			pane.fillRect(xBody - 35, yBody + bodyHeight + 60, 10, 10);


		} finally {
			pane.setTransform(saveTransform);
		}
	}


	/**
	 * Given the coordinate location the piece is attempting to move to, and the arrays of both 
	 * player's pieces, this method will return true if the move is a legal move.
	 */
	public boolean isLegal(int someX, int someY, Piece[] enemyPieces, Piece[] allyPieces){
		boolean result = false;

		Piece[] allPieces = getAllPieces(enemyPieces, allyPieces);

		int distance = Math.max(Math.abs(getX()-someX), Math.abs(getY()-someY));

		if((getX() == someX) ^ (getY() == someY)){
			result = super.isLegal(someX, someY, enemyPieces, allyPieces);
			if(getX() == someX){
				for(Piece piece: allPieces){
					if(piece != null){

						for(int i=1; i < Math.abs(distance); i++){
							if(getY()-someY <0){
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
						for(int i=1; i < Math.abs(distance); i++){
							if(getX()-someX < 0){
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
} // End Rook