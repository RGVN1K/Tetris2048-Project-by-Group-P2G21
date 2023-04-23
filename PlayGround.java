package com.company;
import java.awt.*;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Collections;
import edu.princeton.cs.introcs.StdDraw;

//The Grid class representing the Tetris2048 playground
public class PlayGround {

	//This code defines a class PlayGround with private data fields Height and Width that determine the dimensions of the grid.
	//It also has a private List of Tile objects called TileList that represents the tiles on the grid, and an integer Score that keeps track of the score for the game.
	private int Height;
	private int Width;
	private List<Tile> listOfTiles = new ArrayList<Tile>();
	private int Score;
	public int getScore() {
		return Score;
	}

	//This is a constructor for the PlayGround class which takes two integer parameters, n_rows and n_cols.
	// The constructor initializes the Height and Width instance variables with the values passed as arguments and sets the Score to 0.
	PlayGround(int n_rows, int n_cols) {
		this.Height = n_rows;
		this.Width = n_cols;
		this.Score = 0;
	}
	//This method checks whether a specific block position on the grid is occupied by a tile or not.
	//It does so by iterating through the TileList and checking whether the position of any tile in the list is equal to the block position passed as a parameter.
	// If there is a tile in that position, the method returns true indicating that the block is occupied. Otherwise, it returns false indicating that the block is empty.
	public boolean OccupiedOrNot(Point blockPos) {
		Boolean response = false;
		for (Tile tile : listOfTiles) {

			if (tile.getPosition().equals(blockPos)) {
				response = true;
				break;
			}
		}
		return response;
	}
	
	//This method updates the game grid by adding the tiles of the current tetromino. It also checks if any of the tiles should move down due to the placement of the tetromino.
	//The method first adds all the tiles of the tetromino to the TileList, which holds every tile in the game grid.
	//Then it goes through each tile in the list, and for each tile, it finds the y-coordinate of the first full block below it.
	//If there is an empty block below the tile, it moves the tile down by one line. Finally, it updates the TileList with the new position of the tile
	//The method also sorts the list in reverse order by y-coordinate to ensure that the tiles move down in the correct order.
	public void UpdatePlayground(Tetrominoes tet) {
		
		listOfTiles.addAll(tet.getTileList());

		List<Tile> list = new ArrayList<Tile>();
		list = tet.getTileList();

		Collections.sort(list, Collections.reverseOrder());
		for (Tile tile : list) {
			Point position = tile.getPosition();
			int i;
			for (i = position.y-1; i >= 0; i--) {

				if (OccupiedOrNot(new Point(position.x, i))) {
					break;
				}
			}
				if (i+1 != position.y) {
				Point newPosition = new Point(position.x, i+1);
				listOfTiles.get( listOfTiles.indexOf(tile) ).setPosition(newPosition);
			}
		}
	}
	//For thisplaying playground
	public void displayPlayground() throws InterruptedException {
		for (int row = 0; row < this.Width; row++)
			for (int col = 0; col < this.Height; col++) {
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.filledSquare(row, col, 0.5);
			}

		//drawing the Playground
		StdDraw.setPenColor(StdDraw.BOOK_BLUE);
		for (double x = -0.5; x < this.Width; x++)
			StdDraw.line(x, -0.5, x, this.Height - 0.5);
		for (double y = -0.5; y < this.Height; y++)
			StdDraw.line(-0.5, y, this.Width - 0.5, y);
		//For thisplaying the tiles on the playground
		for (Tile tile : listOfTiles){
			tile.display();
		}
	}


	//The method where full lines are determined and deleted.
	public void linechecker() {
		int[] tileChecker = new int[this.Height];  //An array that holds how many lines are filled
		for (int i = 0; i < this.Height; i++) {   //Empty lines are filled with 0
			tileChecker[i] = 0;
		}
		List<Integer> listoflines = new ArrayList<Integer>(); //keep the numbers of the rows to be deleted in the array
		for (Tile tile : listOfTiles) {
			Point position = tile.getPosition();
			tileChecker[position.y]++;                   //Number of tiles are increasing when a new tile is added.
			if (tileChecker[position.y] == this.Width) {
				listoflines.add(position.y);
			}
		}
        //If the number of elements in listoflines is greater than 0 that means there are lines or line needs to be deleted.
		if (listoflines.size() > 0) {
			List<Tile> removeList = new ArrayList<Tile>();           //list of tiles to be deleted			
			for (Tile tile : listOfTiles) {
				if (listoflines.indexOf(tile.getPosition().y) > -1) {
					removeList.add(tile);
					this.Score += tile.getTileNumber();
				}
			}
			listOfTiles.removeAll(removeList);

			Collections.sort(listoflines, Collections.reverseOrder());
			

			for (int line_no : listoflines) {
				for (Tile tile : listOfTiles) {
					if (tile.getPosition().y > line_no) {
						tile.move(0, -1);
					}
				}
			}		
		}
	}
	//The purpose of this method is to determine in which columns tiles will be merged.
	//A list called x_list is created to store the x-coordinates of the tiles in the tetromino.
	//The getTileList method of the Tetrominoes object is called to get a list of all the tiles in the tetromino.
	//For each tile, the getPosition method is called to get its position, and if the x-coordinate of the tile's position is not already in x_list, it is added to the list. This ensures that there is only one tile from each column in the list.
	//Finally, the checkColumn method is called for each x-coordinate in x_list. This method checks the column for possible tile merging.
	public void mergingTiles(Tetrominoes tet) {
		List<Integer> x_list = new ArrayList<Integer>();

		for (Tile tile : tet.getTileList()) {
			Point position = tile.getPosition();
			if (!x_list.contains(position.x))
				x_list.add(position.x);
		}
		for (int X : x_list) {           
			ColumnChecker(X);
		}
	}
	// This method checks a specified column for possible tile merging.
	//A list called list is created to store the tiles that may be merged.
	//The getPosition method of each tile in the listOfTiles is called to get its position, and if the x-coordinate of the tile's position matches the specified column, it is added to the list
	//This way, all the tiles in the specified column are kept in the list.
	//If the size of the list is greater than 1, it means there are two or more tiles in the column, so the list is sorted from top to bottom using the Collections.sort() method.
	//A ListIterator is created to iterate over the list and access its elements. The next method is called to get the first element of the list, and it is stored in prevTile.
	//While the ListIterator has a next element, the next method is called to get the next element of the list, and it is stored in currentTile.
	// If currentTile is not equal to prevTile, the method checks if the number on the current tile is equal to the number on the previous tile. If they are equal, the number on the previous tile is added to the current tile using the incNumber method
	// the score is increased by the same amount. Then, the removeTile method is called to remove the previous tile, and the checkColumn method is called recursively to continue the same operation in the column. If they are not equal, prevTile is set to currentTile.
	//The method terminates when the ListIterator has no more elements.
	private void ColumnChecker(int column) {
		List<Tile> list = new ArrayList<Tile>();

		for (Tile tile : listOfTiles) {
			Point position = tile.getPosition();
			if (column == position.x)
				list.add(tile);  
		}
		
		if (list.size() > 1) {
			Collections.sort(list);

			ListIterator<Tile> it = list.listIterator();
			Tile prevTile = it.next();
			Tile currentTile;
			while (it.hasNext()) {
				currentTile = it.next();					
				if (currentTile != prevTile) {
					if (currentTile.getTileNumber() == prevTile.getTileNumber()) {
						currentTile.NumberInc(prevTile.getTileNumber());
						this.Score += currentTile.getTileNumber();
						TileRemover(prevTile);
						ColumnChecker(column);
					}
					prevTile = currentTile;
				}
			}
		}
	}
//This is a method that removes a tile from the listOfTiles and makes the tiles above it fall down to fill the empty space.
	//The input to this method is a Tile object that needs to be removed.
	//The method removes this Tile object from the listOfTiles using the remove() method.
	//A new ArrayList called list is created to keep track of the tiles that need to be moved down.
	//The method then iterates over all the rows above the position of the removed Tile object (i.e., for all y values greater than the y position of the removed tile). For each row, it checks if there is a Tile object in the same column (x value) as the removed Tile object.
	//If there is a Tile object, it is added to the list of tiles that need to be moved down.
	//After finding all the tiles that need to move down, the method checks if the list is empty. If it is, there are no tiles to move down, so the method simply returns.
	//If the list is not empty, the method calculates how much the tiles on the list should move down by subtracting the y position of the removed tile from the y position of the first tile in the list.
	//Finally, the method moves each tile on the list down by the calculated difference using the move() method.
	//Additionally, there is a comment indicating that the toString() method is used to convert an integer score value to a string. However, there is no actual code for this method provided.
	private void TileRemover(Tile tile) {
		listOfTiles.remove(tile);
		List<Tile> list = new ArrayList<Tile>();
		Point position = tile.getPosition();
		for (int i = position.y+1; i < this.Height; i++) { 

			Point newPosition = new Point(position.x, i);  	
			for (Tile x : listOfTiles) {

				if (x.getPosition().equals(newPosition)) {
					list.add(x);
				}
			}
		}
		if (list.isEmpty()) return; 
		int differences = list.get(0).getPosition().y - position.y;
		for (Tile aTile : list) {
			aTile.move(0, -differences);
		}
	}

	public String getScoreAsString() {
		return Integer.toString(this.Score);
	}

}