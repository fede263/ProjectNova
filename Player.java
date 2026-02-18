package projectNovaGame;

import java.io.Serializable;

/**
 * Represents the player with name and score.
 * 
 * @author Sadishe Fernando
 * @version 1.0
 * 
 */
public class Player implements Serializable {

    private String name;
    private int score;
    
    /**
     * Creates a player with the given name.
     *
     * @param name the player's name
     */
    public Player(String name) 
    {
    	this.name = name;
    	this.score = 0;
    }
    
    /**
     * Gets the player's name.
     *
     * @return player name
     */
    public String getName() 
    {
    	return name; 
    }
    
    /**
     * Gets the player's score.
     *
     * @return current score
     */
    public int getScore() 
    { 
    	return score; 
    }
    
    /**
     * Adds points to the player's score.
     *
     * @param newScore points to add
     */
    public void addScore(int newScore) 
    {
    	score += newScore;
    }
}