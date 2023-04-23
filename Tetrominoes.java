package com.company;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;


//The Tetrominoes class for representing tetrominoes with 7 different shapes ('I', 'S', 'Z', 'O', 'T', 'L', 'J')
public class Tetrominoes {
    // Private data fields in order of:
	//dimensions of the tetris game grid
	//keep each shape of tetromino in tileMatrix
	//reference point of the matrix
	//tetromino's name
	//check the Game Over
	private int gridWidth, gridHeight;
	private Tile[][] matrixOfTile;
	private Point bottomLeftCorner;
	private char Name;
	public Boolean GameOver = false;

	// Constructor for tetrominoes
	Tetrominoes (char randomName, int gridHeight, int gridWidth) {
		this.gridHeight = gridHeight;
		this.gridWidth = gridWidth;
		this.Name = randomName;
		
		//Each of the tetrominos have a shape. We need to give them a name so that we can match shapes and names and call
		//shapes by their names.
		if (randomName == 'I') {
			matrixOfTile = new Tile[4][4];
			matrixOfTile[1][0] = new Tile(new Point(5, gridHeight + 3));
			matrixOfTile[1][1] = new Tile(new Point(5, gridHeight + 2));
			matrixOfTile[1][2] = new Tile(new Point(5, gridHeight + 1));
			matrixOfTile[1][3] = new Tile(new Point(5, gridHeight));
		}
		else if (randomName == 'Z') {
			matrixOfTile = new Tile[3][3];
			matrixOfTile[1][0] = new Tile(new Point(6, gridHeight + 2));
			matrixOfTile[2][0] = new Tile(new Point(7, gridHeight + 2));
			matrixOfTile[0][1] = new Tile(new Point(5, gridHeight + 1));
			matrixOfTile[1][1] = new Tile(new Point(6, gridHeight + 1));
		}
		else if (randomName == 'S') {
			matrixOfTile = new Tile[3][3];
			matrixOfTile[0][0] = new Tile(new Point(5, gridHeight + 2));
			matrixOfTile[1][0] = new Tile(new Point(6, gridHeight + 2));
			matrixOfTile[1][1] = new Tile(new Point(6, gridHeight + 1));
			matrixOfTile[2][1] = new Tile(new Point(7, gridHeight + 1));
		}	
		else if (randomName == 'O') {
			matrixOfTile = new Tile[2][2];
			matrixOfTile[0][0] = new Tile(new Point(5, gridHeight));
			matrixOfTile[0][1] = new Tile(new Point(5, gridHeight + 1));
			matrixOfTile[1][0] = new Tile(new Point(6, gridHeight));
			matrixOfTile[1][1] = new Tile(new Point(6, gridHeight + 1));
		}			
		else if (randomName == 'T') {
			matrixOfTile = new Tile[3][3];
			matrixOfTile[0][1] = new Tile(new Point(5, gridHeight + 1));
			matrixOfTile[1][1] = new Tile(new Point(6, gridHeight + 1));
			matrixOfTile[2][1] = new Tile(new Point(7, gridHeight + 1));
			matrixOfTile[1][2] = new Tile(new Point(6, gridHeight));
		}
		else if (randomName == 'L') {
			matrixOfTile = new Tile[3][3];
			matrixOfTile[1][0] = new Tile(new Point(5, gridHeight + 2));
			matrixOfTile[1][1] = new Tile(new Point(5, gridHeight + 1));
			matrixOfTile[1][2] = new Tile(new Point(5, gridHeight));
			matrixOfTile[2][2] = new Tile(new Point(6, gridHeight));
		}	
		else {
			matrixOfTile = new Tile[3][3];
			matrixOfTile[1][0] = new Tile(new Point(6, gridHeight + 2));
			matrixOfTile[1][1] = new Tile(new Point(6, gridHeight + 1));
			matrixOfTile[0][2] = new Tile(new Point(5, gridHeight));
			matrixOfTile[1][2] = new Tile(new Point(6, gridHeight));
		}
		//The purpose of this code is to determine the starting position of the tetromino on the game grid.
		bottomLeftCorner = new Point(5, gridHeight);
	}
	public char Name() {
		return Name;
	}


	//The first conditional statement checks whether the tetromino can be rotated or not. If the bottom-left corner of the tetromino plus the length of the tetromino is greater than the height of the grid, then the method returns false indicating that the tetromino cannot be rotated. This check ensures that the tetromino stays within the bounds of the grid when it is rotated.
	//If the tetromino can be rotated, the method initializes a new 2D array of Tile objects called rotatedMatrix with the same dimensions as the matrixOfTile array.
	//The next two nested for loops iterate through each block of the matrixOfTile array. For each block, the code checks if the block is empty or not. If the block is not empty, it retrieves its current position, and if it is empty, it calculates its new position based on the empty blocks.
	//The code checks if the new position of the block is within the bounds of the grid. If it is not within the bounds of the grid, the method returns false.
	//The code then checks if the new position of the block is already occupied by another tile in the gamePlayGround. If the position is already occupied, the method returns false.
	//Once all the blocks in the matrixOfTile array have been checked, the method updates the positions of the Tile objects in the matrixOfTile array to reflect the new rotated positions.
	//Finally, the matrixOfTile array is assigned the rotatedMatrix array, which contains the updated positions of the Tile objects.
	//The method returns true to indicate that the tetromino was successfully rotated.
	public boolean TetrominoeRotate(PlayGround gamePlayGround) {
		if (bottomLeftCorner.y + matrixOfTile.length > gridHeight)
			return false;
		int n = matrixOfTile.length;
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {				
				Point position = new Point();
				if(matrixOfTile[row][col] != null)
					position = matrixOfTile[row][col].getPosition();
				else {
					position.x = bottomLeftCorner.x + col;
					position.y = bottomLeftCorner.y + (n-1) - row;
				}
				if(position.x < 0 || position.x >= gridWidth)
					return false;
				if (gamePlayGround.OccupiedOrNot(position))
					return false;
			}
		}

		Tile [][] rotatedMatrix = new Tile[n][n];
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				if(matrixOfTile[row][col]==null)
					continue;
				Point position = matrixOfTile[row][col].getPosition();
				rotatedMatrix[col][n-1-row] = matrixOfTile[row][col];
				position.x=bottomLeftCorner.x + (n-1-row);
				position.y=bottomLeftCorner.y + (n-1) - col;
				matrixOfTile[row][col].setPosition(position);
			}
		}	    
		matrixOfTile = rotatedMatrix;
		return true;
	}
//The first method, TetrominoeDisplay(), simply iterates through each tile in the Tetromino and calls its display() method.
//This method is likely used to render the Tetromino on the game screen.
	//canMoveorNot(PlayGround gamePlayGround, Point move), takes a PlayGround object and a Point object representing the movement to be made.
	//The method checks if the Tetromino can move to the new position by checking each tile's next position after the movement is made.
	//For each tile in the Tetromino, the method creates a new Point object representing the tile's next position.
	//The x and y values of this Point are determined by adding the x and y values of the current tile's position to the corresponding values of the move parameter.
	//The method then checks if the x value of the next position is less than 0 (outside the left edge of the grid), greater than or equal to the gridWidth (outside the right edge of the grid), or if the y value is less than 0 (above the top edge of the grid).
	//If any of these conditions are true, the method returns false, indicating that the Tetromino cannot be moved to the new position.
	//If the position is within the grid boundaries, the method checks if the position is already occupied by a tile on the gamePlayGround using the OccupiedOrNot() method of the gamePlayGround object. If the position is occupied, the method also returns false.
	//If none of the above conditions are met, the method returns true, indicating that the Tetromino can be moved to the new position.
	public void TetrominoeDisplay() {
		for (Tile tile : getTileList()){
			tile.display();
		}
	}
	public boolean canMoveorNot(PlayGround gamePlayGround, Point move)
	{
		for (Tile tile : getTileList())
		{
			Point nextPosition = new Point(tile.getPosition().x + move.x, tile.getPosition().y + move.y);
			if (nextPosition.x < 0 || nextPosition.x >= gridWidth || nextPosition.y < 0)
				return false;
			if (gamePlayGround.OccupiedOrNot(nextPosition))
				return false;
		}
		return true;
	}

	//The method for moving tetromino down by 1 in the playground
	public boolean goDownbyOne(PlayGround gamePlayGround) {

		if (!canMoveorNot(gamePlayGround, new Point(0, -1)))
		{
			//the game ends if any column of the playground is full
			if (bottomLeftCorner.y + matrixOfTile.length >= gridHeight) {
				GameOver = true;
			}			
			return false;
		}
      
		for (Tile tile : getTileList())	{
			tile.move(0, -1);
		}
		bottomLeftCorner.translate(0, -1);
		return true;
	}
	
	//The method for moving tetromino left by 1 in the playground
	public boolean goLeftbyOne(PlayGround gamePlayGround) {
		if (!canMoveorNot(gamePlayGround, new Point(-1, 0)))
			return false;
		for (Tile tile : getTileList()){
			tile.move(-1, 0);
		}
		bottomLeftCorner.translate(-1, 0);
		return true;
	}
	//The method for moving tetromino right by 1 in the playground
	public boolean goRightbyOne(PlayGround gamePlayGround) {

		if (!canMoveorNot(gamePlayGround, new Point(1, 0)))
			return false;
		
		for (Tile tile : getTileList())	{
			tile.move(1, 0);
		}
		bottomLeftCorner.translate(1, 0);
		return true;
	}	
    //The method of the list holding blocks filled in tetromino
	public List<Tile> getTileList() {
		List<Tile> aList = new ArrayList<Tile>(4);
		int n = matrixOfTile.length;

		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				if(matrixOfTile[row][col]==null)
					continue;
				aList.add(matrixOfTile[row][col]);
			}
		}
		return aList;	
	}
}