package projectNovaGame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represent the game board and creature placement and guesses.
 * 
 * @author Sadishe Fernando
 * @version 1.0
 *
 */
public class Board implements Serializable {
	
	private int rows;
	private int columns;
	private char[][] grid;
	private boolean [][] revealed;
	private List<Creature> creature = new ArrayList<>();
	
	/**
     * Create a new board with given sizes.
     * 
     * @param rows number of rows
     * @param columns number of columns
     */
	public Board (int rows, int columns) 
	{
		this.rows = rows;
		this.columns = columns;
		grid = new char[rows][columns];
		revealed = new boolean[rows][columns];
		
		// Fill the board with emty cells
		for (int r = 0; r < rows; r++) 
		{
			for (int c = 0; c < columns; c++) 
			{
				grid[r][c] = '.';
				revealed[r][c] = false;
			}
		}
		
	}
	
	/**
	 * Gets the number of rows.
	 * 
	 * @return number of rows
	 */
	public int getRows() 
	{
		return rows;
	}
	
	/**
     * Gets the number of columns.
     *
     * @return number of columns
     */
	public int getColumns() 
	{
		return columns;
	}
	
	/**
     * Checks if a cell is revealed.
     *
     * @param r row index
     * @param c column index
     * @return true if revealed, false otherwise
     */
	public boolean isRevealed(int r, int c) 
	{
		return revealed [r][c];
	}
	
	/**
     * Gets the value stored in a cell.
     *
     * @param r row index
     * @param c column index
     * @return the character in the cell
     */
	public char getCell(int r, int c)
	{
		return grid[r][c];
	}
	
	/**
     * Randomly places all creatures on the board.
     */
	public void placeCreaturesRandomly() 
	{
		creature.clear();
		Random rand = new Random();
		
		int [][][] shapes = 
			{
					{{0,0} , {0,1} , {0,2}},
					{{0,0} , {1,0}},
					{{0,0}}
			};
		
		char id = 'A';
		
		for (int[][] shape : shapes) 
		{
			boolean placed = false;
			int tries = 0;
			
			while (!placed && tries < 200) 
			{
				tries++;
				int baseR = rand.nextInt(rows);
				int baseC = rand.nextInt(columns);
				
				if (canPlaceShape(shape, baseR, baseC)) 
				{
					Creature cr = new Creature(id++);
					for (int[] cell : shape) 
					{
						int rr = baseR + cell[0];
						int cc = baseC + cell[1];
						grid[rr][cc] = cr.getID();
						cr.addCell(rr, cc);
					}
					creature.add(cr);
					placed = true;
				}
				
				
			}
		}
	}
	
	/**
     * Checks if a shape can be placed at the given position.
     *
     * @param shape shape coordinates
     * @param baseR starting row
     * @param baseC starting column
     * @return true if it fits, false if not
     */
	private boolean canPlaceShape(int[][] shape, int baseR, int baseC) 
	{
		for (int[] cell : shape) 
		{
			int r = baseR + cell[0];
			int c = baseC + cell[1];
			if (r < 0 || r >= rows || c < 0 || c >= columns)
				return false;
			if (grid[r][c] != '.')
				return false;
		}
		return true;
	}
	
	/**
     * Processes the player's guess.
     *
     * @param r row guessed
     * @param c column guessed
     * @return 0 = miss, 1 = hit, 2 = creature fully found
     */
	public int guess(int r, int c)
	{
		if (r < 0 || r >= rows || c < 0 || c >= columns)
			return 0;
		if (revealed[r][c])
			return 0;
		
		revealed[r][c] = true;
		char id = grid[r][c];
		
		if (id == '.')
			return 0;
		
		for (Creature cr : creature)
		{
			if (cr.getID() == id) 
			{
				if (cr.isFullyReavealed(revealed)) 
				{
					return 2;
				}
				else 
				{
					return 1;
				}
			}
		}
		return 0;
		
	}
	
	/**
     * Checks if all creatures have been completely revealed.
     *
     * @return true if all creatures are found
     */
	public boolean allCreaturesFound() 
	{
		for (Creature cr : creature) 
		{
			if (!cr.isFullyReavealed(revealed))
				return false;
		}
		return true;
	}

}
