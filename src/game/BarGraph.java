package game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

/** 
 * By: Alex Sotis
 * 
 * The class BarGraph can be used in any game to display the score or other information
 * about the game such as health points or current level. Its data members are: 
 * the score, which determines the amount of bars on the graph, x and y, which correspond
 * to the location in the window, and 2 constants for the size of each bar.
 */
public class BarGraph {
	private int score, x, y;
	private final int BAR_WIDTH = 8, BAR_HEIGHT = 15;

	// Constructor takes the score and the location in the window as parameters
	public BarGraph(int theScore, int theX, int theY){
		score = theScore;
		x = theX;
		y = theY;

	}

	/**
	 * The drawBarGraph method take the graphics pane as a parameter so it can draw
	 * the bar graph in the window.
	 */
	public void drawBarGraph(Graphics pane){

		//First, the center marker is drawn as a triangle to show where the bar starts
		pane.setColor(Color.BLUE);
		Polygon marker = new Polygon();
		marker.addPoint(x, y);
		marker.addPoint(x + 5, y - 8);
		marker.addPoint(x - 5, y - 8);
		pane.fillPolygon(marker);

		// change color to cyan
		pane.setColor(Color.cyan);  

		// This for loop draws a number of bars equal to the score we need to specify 
		// if score is negative or positive so we know which direction to draw the bars
		for(int i=0; i < Math.abs(score); i++){ 
			if(score>0)		
				pane.fillRect(x + (BAR_WIDTH+2) * i, y, BAR_WIDTH, BAR_HEIGHT);
			if(score < 0)
				pane.fillRect(x - (BAR_WIDTH+2) * (i+1), y, BAR_WIDTH, BAR_HEIGHT);
		}	
	}

	/**
	 * This method just updates the score
	 */
	public void update(int newScore){
		score = newScore;
	}

}
