package com.company;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Font;
import java.awt.Point;
import java.util.Random;
import java.awt.event.KeyEvent;
public class Tetris2048 {


	
	//This method creates a new tetris block(named tetromino) for game grid.
	public static Tetrominoes createNewTetromino(int gridHeight, int gridWidth) {
		//Shape of the new tetromino block needs to determined randomly.
		char[] TetrominoShapes = {'I', 'S', 'Z', 'O', 'T', 'L', 'J'};
		Random random = new Random();
		int randomIndex = random.nextInt(7);		
		char randomName = TetrominoShapes[randomIndex];
		Tetrominoes tet = new Tetrominoes(randomName, gridHeight, gridWidth);
		return tet;
	}
    //This method is for displaying the next Tetromino Block.
	public static void drawNextTetromino(Tetrominoes tet) {
		/*
		 This code draws a tetromino on the screen using the StdDraw library.
		 It iterates through the tiles in the tet object and gets the position of each tile.
		 Then it translates the position of the tile by 8 to the right and -20 downwards.
		 After that, it sets the pen color to the background color of the tile and fills a square
		 at the translated position with a side length of 0.5 using the StdDraw.filledSquare() function.
		 Next, it sets the pen color to black and uses the StdDraw.text() function
		  to print the number of the tile at the center of the square.
		 */
		for (Tile tile: tet.getTileList()) {
			Point p = tile.getPosition();
			p.translate(8, -20); 
            //StdDraw commands for printing the next tetromino block for the place we wanted in the GUI
			StdDraw.setPenColor(tile.getBackgroundColor());
			StdDraw.filledSquare(p.x, p.y, 0.5);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.text(p.x, p.y, Integer.toString(tile.getTileNumber())); //On each tile of the block, we need a number
			// and this line does exactly that.
		}
		//StdDraw commands for writing "NEXT BLOCK" and "SCORE" to GUI
		StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
		StdDraw.text(14, 4 , "NEXT BLOCK");
		StdDraw.setPenColor(StdDraw.BLUE);
		StdDraw.text(14, 17 , "SCORE");
	}

	public static void main(String[] args) throws InterruptedException {
		
		int gridWidth = 12, gridHeight = 20;       //for setting the height and width of our playground(you may call it as grid)
		StdDraw.setCanvasSize(800, 700);  //for setting the size of the canvas(this will be our page when we run the code
		StdDraw.setXscale(-1.5, gridWidth + 4.5);  // for setting the x scale of the coordinate system
		StdDraw.setYscale(-1.5, gridHeight - 0.5); // for setting the y scale of the coordinate system
		//these codes are important, Without these lines, the coordinate system would not be set to the desired range
		// and the graphics could be displayed incorrectly or not be displayed at all.
		//If these lines are deleted, the coordinate system would default to its original scale, which is -1.0 to 1.0 on both the x and y axes.
		//The resulting graphics could be distorted, truncated, or not visible depending on the size and shape of the objects being drawn.
		//
		//  Double buffering is a technique used in computer graphics to reduce flickering and improve the smoothness of animations.
		StdDraw.enableDoubleBuffering();
		PlayGround gamePlayGround = new PlayGround(gridHeight, gridWidth);  // create a grid as the tetris game environment
		// create the first tetromino block to enter the playground
		Tetrominoes tet = createNewTetromino(gridHeight, gridWidth);
		// create the next tetromino block to enter the game playground
		Tetrominoes nextTetromino = createNewTetromino(gridHeight, gridWidth);
		boolean createNewTetromino = false;
		

		label1:   
			while (true)  {
				//keyboard interaction for moving the currently active tetromino block to the left, right or rotate.
				boolean success = false;
				if(StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
					success = tet.goLeftbyOne(gamePlayGround);
				}
				else if(StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
					success = tet.goRightbyOne(gamePlayGround);
				}
				else if(StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
					success = tet.TetrominoeRotate(gamePlayGround);
				}
	
				//If we don't give input by using keyboard, block needs to go down and this part of the code does it.
				if (!success)
					success = tet.goDownbyOne(gamePlayGround);
				//We need to display game over massage. There are two win conditions to end game in our code.
				//First one is winning by reaching score limit, in this code it is 10000 but it can be changed by setting
				// 10000 on the "if (gameGrid.getScore() > 10000)" line.
				//Then we have second win condition which is reaching 2048 tile. If we reach 2048, we get game over massage.
				//Then we have a fail condition, If the tetris blocks reach the maximum height, there is no room left on the playing field.
				//In this case, the game ends and the error message appears on the screen.
				//the code looks at score first and then 2048 tile. If desired, the order of them can be changed easily
				    if(!success && tet.GameOver) {
						if (gamePlayGround.getScore() > 10000) {
							String gameOverMsg = "You won by reaching the max score!";
							StdDraw.setPenColor(StdDraw.BLUE);
							Font gameOverFont = new Font("New York Times Roman", Font.BOLD, 30);
							StdDraw.setFont(gameOverFont);
							StdDraw.clear(StdDraw.BLACK);
							StdDraw.text((gridWidth + 3) / 2, gridHeight / 2, gameOverMsg);
							StdDraw.show();
							break;
						}
						else if (Tile.getHighestTileValue() >= 2048){
							String gameOverMsg = "You won by reaching 2048 tile!";
							StdDraw.setPenColor(StdDraw.YELLOW);
							Font gameOverFont = new Font("New York Times Roman", Font.BOLD, 30);
							StdDraw.setFont(gameOverFont);
							StdDraw.clear(StdDraw.BLACK);
							StdDraw.text((gridWidth + 3) / 2, gridHeight / 2, gameOverMsg);
							StdDraw.show();
							break;
						}
						else{
							String gameOverMsg = "Your failed reaching 2048 tile or 10000 score!";
							StdDraw.setPenColor(StdDraw.RED);
							Font gameOverFont = new Font("New York Times Roman", Font.BOLD, 20);
							StdDraw.setFont(gameOverFont);
							StdDraw.clear(StdDraw.BLACK);
							StdDraw.text((gridWidth + 3) / 2, gridHeight / 2, gameOverMsg);
							StdDraw.show();
							break;
						}
					}

					//If the success variable is false (meaning the active tetromino cannot go down anymore), createNewTetromino is set to true.
				//Then, the game grid is updated by adding the placed tetromino, checking if there is a 2048 tile formed and checking if any lines are full.
				//The active tetromino is then set to the next tetromino, and a new tetromino is created as the next tetromino.
				createNewTetromino = !success;  //place (stop) the active tetromino on the game grid if it cannot go down anymore
				if (createNewTetromino) {
					gamePlayGround.UpdatePlayground(tet);
					gamePlayGround.mergingTiles(tet);
					gamePlayGround.linechecker();
					tet = nextTetromino;
					nextTetromino = createNewTetromino(gridHeight, gridWidth);
				}
	
			//This part of the code updates the game display by clearing the background (with black color), drawing the next tetromino
				// displaying the game grid, drawing the active tetromino, showing the current score in red color
				//copying the offscreen buffer to onscreen (double buffering), and pausing for 200 milliseconds.
				// The StdDraw.clear(StdDraw.BLACK) function clears the current offscreen buffer with a black color to prepare for the next frame.
				//drawNextTetromino(nextTetromino) function draws the next tetromino in the next tetromino preview area.
				//gameGrid.display() function displays the current state of the game grid.
				//tet.display() function displays the active tetromino in its current position.
				//StdDraw.setPenColor(StdDraw.RED) sets the color of the text to be displayed next to red.
				//StdDraw.text(14, 16, gameGrid.getScoreAsString()) displays the current score at a specific position (x = 14, y = 16) on the screen.
				//Finally, StdDraw.show() copies the offscreen buffer to the onscreen buffer to display the changes
				// and StdDraw.pause(200) pauses for 200 milliseconds before the next iteration of the loop.
				StdDraw.clear(StdDraw.BLACK);
				drawNextTetromino(nextTetromino);
				gamePlayGround.displayPlayground();
				tet.TetrominoeDisplay();
				StdDraw.setPenColor(StdDraw.RED);
				StdDraw.text(14, 16, gamePlayGround.getScoreAsString());
				StdDraw.show();
				StdDraw.pause(200);
			} 
		}
}
