package com.company;
import java.awt.Color;
import java.awt.Point;
import java.util.Random;
import java.util.Arrays;
import edu.princeton.cs.introcs.StdDraw;
//The Tile class is a comparable class for comparing the tiles from top to bottom
//Tile implements the Comparable interface, which means that instances of this class can be compared with one another.

public class Tile implements Comparable<Tile> {
  
	//final numbers on the tile
	private final static Integer[] numbers = {2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048};
	//final colors on the tile
	private final static Color[] colors = {
			new Color(255, 255, 204),   // 2
			new Color(255, 229, 204),   //4
			new Color(0, 102, 0),   //8
			new Color(204, 0, 204),    //16
			new Color(51, 51, 255),     //32
			new Color(102, 255, 255),     //64
			new Color(102, 255, 178),    //128
			new Color(178, 255, 102),    //256
			new Color(255, 255, 102),    //512
			new Color(255, 178, 102),    //1024
			new Color(255, 102, 102)};   //2048
		
	private int tileNumber;// number on the tiles

	private Color backgroundColor;   // color for displaying the tiles
	private Color foregroundColor = Color.BLACK; // color for displaying the number
	private Point position;          //position of tile

	//For the tile 2048 win condition, we have this methot so that if highesttilevalue reaches 2048 game is over.
	private static int highestTileValue =2 ;

	//The MaxNumber method updates the tile's value (tileNumber) by adding a given value to it.
	//If the new tileNumber is higher than the current highestTileValue, it updates highestTileValue to the new tileNumber. The method also checks if the new tileNumber is greater than 2048, the maximum value allowed for a tile. If so, it sets tileNumber to 2048.
	public void MaxNumber(int value) {
		this.tileNumber += value;

		if (this.tileNumber > highestTileValue) {
			highestTileValue = this.tileNumber;
		}
		if (this.tileNumber > 2048)
			this.tileNumber = 2048;
		int i = Arrays.asList(numbers).indexOf(this.tileNumber);
		this.backgroundColor = colors[i];
	}
	public static int getHighestTileValue() {
		return highestTileValue;
	}
//A new Random object is created using the default seed value.
	//A random integer value between 0 and 1 is generated using the nextInt() method of the Random object. The integer value is stored in the variable 'i'.
	//The tileNumber instance variable is set to the value in the 'numbers' array at index 'i'.
	//The backgroundColor instance variable is set to the value in the 'colors' array at index 'i'.
	//The position instance variable is set to the input Point object, which is cloned using the clone() method to avoid any modifications to the original object.
	Tile(Point pos){
		Random rand = new Random();
		int i = rand.nextInt(2);
		this.tileNumber = numbers[i];
		this.backgroundColor = colors[i];
		this.position = (Point) pos.clone();
	}

	public Point getPosition(){
		return (Point) position.clone();
	}

	public void setPosition(Point position){
			this.position = position;
	}

	public void move(int x, int y){
		position.translate(x, y);
	}

	public Integer getTileNumber() {
		return this.tileNumber;
	}

	//The NumberInc method is used to increase the value of a Tile's tileNumber attribute by a certain amount (value). The method first adds value to the tileNumber attribute.
	//If the result is greater than 2048, the tileNumber attribute is set to 2048 (the maximum value a tile can have in this game).
	//Then, the method searches for the index of the updated tileNumber value in the numbers array using the indexOf method.
	//This index is used to retrieve the corresponding color from the colors array, which is then assigned to the backgroundColor attribute of the Tile object. This ensures that the color of the Tile changes based on its value.
	public void NumberInc(int value) {
		
		this.tileNumber += value;
		
		if (this.tileNumber > 2048)
			this.tileNumber = 2048;

		int i = Arrays.asList(numbers).indexOf(this.tileNumber);
		this.backgroundColor = colors[i];
	}

	public Color getBackgroundColor() {
		return this.backgroundColor;
	}
//StdDraw is a class that provides a basic drawing interface for creating 2D graphics.
	//setPenColor method sets the color of the pen (used for drawing lines and text).
	//filledSquare method draws a filled square at a given position with a given size.
	//text method writes the given text at the given position.
	//In this code, setPenColor is used to set the color of the tile to the backgroundColor of the Tile. Then, filledSquare is used to draw a square at the position of the Tile with a size of 0.5.
	//
	public void display() {
		StdDraw.setPenColor(backgroundColor);
		StdDraw.filledSquare(position.x, position.y, 0.5);
		StdDraw.setPenColor(foregroundColor);
		StdDraw.text(position.x, position.y, Integer.toString(tileNumber));
	}

	public int compareTo(Tile other)  { 
        return other.getPosition().y - this.getPosition().y;
    } 
}