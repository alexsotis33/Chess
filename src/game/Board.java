package game;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.Scanner;

import pieces.Piece;

/**
 * The Board class is the main class which hold all of the information about the
 * players and the pieces.  It will detect mouse clicks to see if the user has
 * made a legal move, and will also alternate whose turn it is.  This class will
 * also be responsible for drawing everything to the graphics window.  Board has-a
 * array of spot objects (64 of them) and 2 Player objects.  
 */
public class Board extends Frame implements MouseListener{

	/**
	 * References to store the location of a mouse click.
	 */
	private int clickX, clickY; 

	/**
	 * References to store the last spot the user clicked.
	 */
	private int oldX, oldY;

	/**
	 * The players of the game.
	 */
	private Player white, black;

	
	/**
	 * An array of Spot objects in order to draw the board and detect mouse clicks.
	 */
	private Spot[][] spots = new Spot[8][8];

	/**
	 * A boolean which will be negated after each valid move so only the next player
	 * can move.
	 */
	private boolean whiteToMove = true;

	/**
	 * The window dimensions.
	 */
	private int windowWidth = 500, windowHeight = 550;			

	/**
	 * Allows the window to be closed.
	 */
	private UneFenetre myWindow;	

	/**
	 * A boolean to check if the square clicked should be considered for a legal move.
	 */
	private boolean readyToMove = false;

	/**
	 * A graph to represent the material difference of the players.
	 */
	private BarGraph scoreGraph;
	
	
	/**
	 * The material difference of the players.
	 */
	private int score = 0;

	/**
	 * The constructor uses the helper method initializeBoard() to instantiate 64 Spot 
	 * objects and 2 Player objects, and then sets up the window to draw the game on.
	 */
	public Board(){

		initializeBoard();

		myWindow = new UneFenetre();  			//to allow for window closing
		addMouseListener(this);
		addWindowListener(myWindow);
		setTitle("Lets Play Some Chess!");		// We set the characteristics of our
		setLocation(0, 0);					// drawing window: the location,
		setSize(windowWidth, windowHeight);		// the size, and
		setBackground(Color.lightGray);				// the color of the background
		setVisible(true);						// And we make it appear

	}

	/**
	 * This method will be called in the constructor of Board.  Its purpose is to 
	 * fill the array spots with 64 Spot objects and instantiate both players.
	 */
	private void initializeBoard(){

		for(int i=0; i<spots.length; i++){
			for(int j=0; j<spots.length; j++){
				spots[i][j] = new Spot(i+1, j+1);
			}
		}
		
		white = new Player(true);
		black = new Player(false);
		
		scoreGraph = new BarGraph(score, windowWidth/2,  windowHeight - 60);
	}

	/**
	 * Cycles through each Spot object in the array spots and draws them, then
	 * has each player draw their pieces.
	 */
	public void paint(Graphics pane){

		for(int i=0; i<spots.length; i++){
			for(int j=0; j<spots.length; j++){
				spots[i][j].drawSpot(pane);
			}
		}
		
		white.drawPieces(pane);
		black.drawPieces(pane);
		
		scoreGraph.drawBarGraph(pane);
	}



	/**
	 * Given the coordinates of the mouse click, first check if the click was
	 * inside a spot. If the user did click a square, select that square,
	 * unselect all of the other squares, and pass the selected square's 
	 * coordinates along with the coordinates of the last selected square
	 * to the update() method which will check if the move is legal.
	 */
	public void mouseReleased(MouseEvent event) {
		
		clickX = event.getX();
		clickY = event.getY();
		for(int i=0; i<spots.length; i++){
			for(int j=0; j<spots.length; j++){
				spots[i][j].unselect(false);
				if(spots[i][j].isInside(clickX, clickY)){
					spots[i][j].select();
					if(readyToMove)
						update(i+1,j+1, oldX+1, oldY+1);
					oldX = i;
					oldY = j;
					readyToMove = true;
				}			
			}
		}

		repaint();
	}



	/**
	 * Given 2 coordinate pairs, check if the player (whose turn it is), has
	 * a legal move from the 2nd coordinate pair to the 1st coordinate pair.
	 * If the move was made, have the other player check to see if their
	 * piece was taken.  Then toggle the value of whiteToMove and set
	 * readyToMove to be false.
	 */
	public void update(int newX, int newY, int theOldX, int theOldY){
		boolean moved = false, inCheck = false;
		Piece[] opponentPieces;

		if(whiteToMove){			// If it is white's move
			opponentPieces = black.getPieces(); 
			moved = white.checkMove(newX, newY, theOldX, theOldY, opponentPieces);
			inCheck = white.kingInCheck(opponentPieces, newX, newY);

		}else{						// If it is black's move
			opponentPieces = white.getPieces();
			moved = black.checkMove(newX, newY, theOldX, theOldY, opponentPieces);
			inCheck = black.kingInCheck(opponentPieces, newX, newY);
		}
		if(moved){
			spots[theOldX-1][theOldY-1].unselect(true);
			if(whiteToMove){
				black.checkPieceDied(newX, newY);
				white.checkPawnPromotion(newX, newY);
				spots[white.getKingCoordX() - 1][white.getKingCoordY() - 1].unselect(true);

			}
			else{
				white.checkPieceDied(newX, newY);
				black.checkPawnPromotion(newX, newY);
				spots[black.getKingCoordX() - 1][black.getKingCoordY() - 1].unselect(true);


			}

			whiteToMove = !whiteToMove;
			readyToMove = false;
		}
		else{
			if((inCheck) && (whiteToMove)){
				spots[white.getKingCoordX() - 1][white.getKingCoordY() - 1].setSquareRed();
			}
			else if((inCheck) && (!whiteToMove)){
				spots[black.getKingCoordX() - 1][black.getKingCoordY() - 1].setSquareRed();
			}
		}

		score = white.getMaterialValue() - black.getMaterialValue();
		scoreGraph.update(score);

	}


	/**
	 * Main method will create a new Board object.
	 */
	public static void main(String[] args) {
		Board playSomeChess = new Board();   // start playing the game
	}

	/**
	 * Unimplemented method
	 */
	public void mousePressed(MouseEvent e) {	
	}
	/**
	 * Unimplemented method
	 */
	public void mouseClicked(MouseEvent e) {
	}
	/**
	 * Unimplemented method
	 */
	public void mouseEntered(MouseEvent e) {
	}
	/**
	 * Unimplemented method
	 */
	public void mouseExited(MouseEvent e) {
	}
}  // End Board
