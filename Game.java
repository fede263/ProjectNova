package projectNovaGame;

import java.io.Serializable;

/**
 * Manages players, boards, turns, and the overall game.
 * 
 * @author Sadishe Fernando
 * @version 1.0
 * 
 */
public class Game implements Serializable {
	
	private Player[] players = new Player[2];
	private Board[] boards = new Board[2];
	private int currentPlayerIndex = 0;
	private boolean gameOver = false;
	
	/**
     * Creates a new game with two players and initializes boards.
     *
     * @param p1 name of player 1
     * @param p2 name of player 2
     */
	public Game(String p1, String p2) 
	{
		players[0] = new Player(p1);
		players[1] = new Player(p2);
		
		boards[0] = new Board(5,5);
		boards[1] = new Board(5,5);
		
		boards[0].placeCreaturesRandomly();
		boards[1].placeCreaturesRandomly();
		
	}
	
	/**
     * Gets the player whose turn it is.
     *
     * @return current player
     */
	public 	Player getCurrentPlayer() 
	{
		return players[currentPlayerIndex];
	}
	
	/**
     * Gets a player by index.
     *
     * @param idx index of player (0 or 1)
     * @return the selected player
     */
	public Player getPlayer(int idx) 
	{
		return players[idx];
	}
	
	/**
     * Gets the board belonging to the player whose turn it is.
     *
     * @return current player's board
     */
	public Board getCurrentBoard() 
	{
		return boards[currentPlayerIndex];
	}
	
	/**
     * Gets a board by index.
     *
     * @param idx board index (0 or 1)
     * @return the selected board
     */
	public Board getBoard(int idx) 
	{
		return boards[idx];
	}
	
	/**
     * Gets the current player's index.
     *
     * @return index of current player
     */
	public int getCurrentPlayerIndex() 
	{
		return currentPlayerIndex;
	}
	
	/**
     * Checks if the game has ended.
     *
     * @return true if game is over
     */
	public boolean isGameOver() 
	{
		return gameOver;
	}
	
	/**
     * Processes a guess made by the current player.
     *
     * @param r row guessed
     * @param c column guessed
     * @return result of the guess (0, 1, or 2)
     */
	public int makeGuess(int r, int c) 
	{
		if (gameOver)
			return 0;
		
		Player current = getCurrentPlayer();
	    int opponentIndex = 1 - currentPlayerIndex;
	    Board targetBoard = boards[opponentIndex];
	    
		int result = targetBoard.guess(r,c);
		
		// Score based on hit type
		if (result == 1) 
		{
			current.addScore(5);
		}
		else if (result == 2) 
		{
			current.addScore(10);
		}
		
		// End game if opponent has no creatures left
		if (targetBoard.allCreaturesFound()) 
		{
			gameOver = true;
			return result;
		}
		
		// Switch turn
		currentPlayerIndex = opponentIndex;
	
		return result;
	}

}
