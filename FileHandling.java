package projectNovaGame;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Handles saving and loading game files.
 * 
 * @author Sadishe Fernando
 * @version 1.0
 *
 */
public class FileHandling {
	
	/**
     * Saves a Game object to the specified file.
     *
     * @param game the game to save
     * @param file the file to save to
     * @throws IOException if an error occurs during writing
     */
	public static void saveGame(Game game , File file )throws IOException
	{
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject(game);
		out.close();
	}
	
	/**
     * Loads and returns a Game object from a file.
     *
     * @param file the file to load from
     * @return the loaded Game object
     * @throws IOException if an error occurs during reading
     * @throws ClassNotFoundException if the Game class cannot be found
     */
	public static Game loadGame(File file) throws IOException, ClassNotFoundException
	{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		Game game = (Game) in.readObject();
		in.close();
		return game;
	}
	
}
