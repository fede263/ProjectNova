package projectNovaGame;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represent the creatures made of one or more connected cells.
 * 
 * @author Sadishe Fernando
 * @version 1.0
 * 
 */
public class Creature implements Serializable {
	
	private char id;
	private List<Point> cells;
	
	/**
     * Creates a creature with the given ID.
     *
     * @param id character used to identify the creature
     */
	public Creature(char id) 
	{
		this.id = id;
		this.cells = new ArrayList<>();
	}
	
	/**
     * Gets the creature's ID character.
     *
     * @return creature ID
     */
	public char getID() 
	{
		return id;
	}
	
	/**
     * Gets the list of cells that belong to this creature.
     *
     * @return list of cell positions
     */
	public List<Point> getCells() 
	{
		return cells;
	}
	
	/**
     * Adds a cell location to the creature.
     *
     * @param r row of the cell
     * @param c column of the cell
     */
	public void addCell(int r, int c) 
	{
		cells.add(new Point(r, c));
	}
	
	
	/**
     * Checks if all cells of the creature are revealed.
     *
     * @param revealed board's revealed status array
     * @return true if every cell is revealed
     */
	public boolean isFullyReavealed(boolean[][] revealed) 
	{
		for (Point p : cells) 
		{
			if (!revealed[p.x][p.y]) 
				return false;
		}
		return true;
	}

}
