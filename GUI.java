package projectNovaGame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * GUI class for Project Nova, menu and game screens.
 * 
 * @author Sadishe Fernando
 * @version 1.0
 *
 */
public class GUI  extends JFrame{
	
	private CardLayout cardLayout = new CardLayout();
	private JPanel mainPanel = new JPanel(cardLayout);
	
	private JPanel menuPanel;
	private JPanel gamePanel;
	private JButton[][] cellButtons;

	private JLabel lblPlayerTurn;
	private JLabel lblScores;
	
	private Game game;
	
	/**
     * Creates the main window and initializes the GUI.
     */
	public GUI() 
	{
		setTitle("Project Nova - Java Swing");
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		createMenuPanel();
		createGamePanel();
		
		mainPanel.add(menuPanel, "MENU");
		mainPanel.add(gamePanel, "GAME");
		
		add(mainPanel);
		cardLayout.show(mainPanel, "MENU");
		
	}
	
	/**
     * Creates the main menu screen with buttons.
     */
	private void createMenuPanel() 
	{
		menuPanel = new JPanel();
		menuPanel.setLayout(new GridBagLayout());
		menuPanel.setBackground(new Color(30,30,80));
		
		JLabel title = new JLabel ("PROJECT NOVA");
		title.setFont(new Font("SansSerif", Font.BOLD,36));
		title.setForeground(new Color(255,215,0));
		
		JButton BtnStart = new JButton("1. Start new game");
		JButton BtnLoad = new JButton("2. Load a saved game");
		JButton BtnHelp = new JButton("3. View help");
		JButton BtnExit = new JButton("4. Exit");
		
		JButton[] button = {BtnStart, BtnLoad, BtnHelp, BtnExit};
		for (JButton b : button) 
		{
			b.setFocusPainted(false);
			b.setFont(new Font("SansSerif", Font.BOLD,16));
			b.setBackground(new Color(0,0,0));
			
		}
		
		BtnStart.addActionListener(e -> startNewGameDialog());
		BtnLoad.addActionListener(e -> loadGameDialog());
		BtnHelp.addActionListener(e -> showHelpDialog());
		BtnExit.addActionListener(e -> System.exit(0));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10,10,10,10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		menuPanel.add(title,gbc);
		
		gbc.gridy++;
		menuPanel.add(BtnStart, gbc);
		gbc.gridy++;
		menuPanel.add(BtnLoad, gbc);
		gbc.gridy++;
		menuPanel.add(BtnHelp, gbc);
		gbc.gridy++;
		menuPanel.add(BtnExit, gbc);
		
		
	}
	
	/**
     * Creates the main game screen (top, center and bottom panels).
     */
	private void createGamePanel() 
	{
		gamePanel = new JPanel(new BorderLayout());
		gamePanel.setBackground(new Color(10,50,80));
		
		JPanel topPanel = new JPanel(new GridLayout(2, 1));
		topPanel.setBackground(new Color(10,70,120));
		
		lblPlayerTurn = new JLabel("Current Player: ");
		lblScores = new JLabel("Scores: ");
		lblPlayerTurn.setForeground(Color.WHITE);
		lblScores.setForeground(Color.WHITE);
		lblPlayerTurn.setHorizontalAlignment(SwingConstants.CENTER);
		lblScores.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayerTurn.setFont(new Font("SansSerif",Font.BOLD,16));
		lblScores.setFont(new Font("SansSerif",Font.PLAIN,14));
		
		topPanel.add(lblPlayerTurn);
		topPanel.add(lblScores);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		gamePanel.add(topPanel, BorderLayout.NORTH);
		gamePanel.add(centerPanel, BorderLayout.CENTER);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(10,70,120));
		JButton BtnSave = new JButton("Save Game");
		JButton BtnMenu = new JButton("Back to Main Menu");
		
		for (JButton b : new JButton [] {BtnSave,BtnMenu}) 
		{
			b.setBackground(new Color(46,139,87));
			b.setFont(new Font("SansSerif", Font.BOLD, 14));
			b.setFocusPainted(false);
			
		}
		
		BtnSave.addActionListener(e -> saveGameDialog());
		BtnMenu.addActionListener(e ->{game = null; cardLayout.show(mainPanel, "MENU");});
		
		bottomPanel.add(BtnSave);
		bottomPanel.add(BtnMenu);
		
		gamePanel.add(bottomPanel,BorderLayout.SOUTH);
		
	}
	
	/**
     * Build the clickable grid for the opponent's board.
     */
	private void buildGrid() 
	{
		Board board = game.getBoard(1 - game.getCurrentPlayerIndex());
		int rows = board.getRows();
		int columns = board.getColumns();
		
		JPanel gridPanel = new JPanel(new GridLayout(rows,columns));
		gridPanel.setBackground(new Color(0,0,50));
		
		cellButtons = new JButton[rows][columns];
		
		for (int r = 0; r < rows; r++) 
		{
			for (int c = 0; c < columns; c++) 
			{
				JButton Btn = new JButton();
				Btn.setMargin(new Insets (0,0,0,0));
				Btn.setBackground(new Color(25,25,112));
				Btn.setForeground(Color.WHITE);
				Btn.setFont(new Font("MonoSpaced",Font.BOLD,14));
				
				int rr = r; 
				int cc = c;
				
				Btn.addActionListener(e -> handleCellClick(rr,cc));
				
				cellButtons[r][c] = Btn;
				gridPanel.add(Btn);
				
			}
		}
		
		gamePanel.add(gridPanel,BorderLayout.CENTER);
	}
	
	/**
     * Updates the grid buttons and labels to match the current game state.
     */
	private void refreshBoardview() 
	{
		Board board = game.getBoard(1 - game.getCurrentPlayerIndex());
		int rows = board.getRows();
		int columns = board.getColumns();
		
		for (int r = 0; r < rows; r++) 
		{
			for (int c = 0; c < columns; c++) 
			{
				JButton Btn = cellButtons[r][c];
				if (board.isRevealed(r, c)) 
				{
					char ch = board.getCell(r, c);
					if (ch == '.') 
					{
						Btn.setBackground(new Color(70,130,180));
					}
					else 
					{
						Btn.setBackground(new Color(34,139,34));
					}
					
					Btn.setEnabled(false);
				}
				else 
				{
					Btn.setBackground(new Color(25,25,112));
					Btn.setEnabled(true);
				}
			}
		}
		
		Player p0 = game.getPlayer(0);
		Player p1 = game.getPlayer(1);
		
		lblPlayerTurn.setText("Current Player: " + game.getCurrentPlayer().getName());
		lblScores.setText("Scores: " + p0.getName() + ":" + p0.getScore() + " | " + p1.getName() + ": " + p1.getScore());
		
	}
	
	/**
     * Handles clicks on a grid cell.
     *
     * @param r row of the clicked cell
     * @param c column of the clicked cell
     */
	private void handleCellClick(int r, int c) 
	{
		if (game == null || game.isGameOver())
			return;
		
		Player winner = game.getCurrentPlayer();
		int result = game.makeGuess(r, c);
		
		String msg;
		if (result == 0) msg = "Miss!";
		else if (result == 1) msg = "Hit! +5 points";
		else msg = "Creature completed! +10 points";
		
		JOptionPane.showMessageDialog(this, msg);
		
		if (game.isGameOver()) 
		{
			JOptionPane.showMessageDialog(this,"All creatures found!\nWinner: " +  winner.getName() + "\nScore: " + winner.getScore());
		}
		

		refreshBoardview();
		gamePanel.revalidate();
		gamePanel.repaint();
		
	}
	
	/**
     * Starts the dialog to get player names and begin a new game.
     */
	private void startNewGameDialog() 
	{
		String p1 = JOptionPane.showInputDialog(this,"Enter Player 1 name: ", "Player 1");
		if (p1 == null || p1.trim().isEmpty()) p1 = "Player 1";
		
		String p2 = JOptionPane.showInputDialog(this,"Enter Player 2 name: ", "Player 2");
		if (p2 == null || p2.trim().isEmpty()) p2 = "Player 2";
		
		game = new Game(p1,p2);
		enterGameScreen();
	}
	
	/**
     * Switches to the game screen and prepares the board view.
     */
	private void enterGameScreen() 
	{
		Component[] comps = gamePanel.getComponents();
		for (Component c : comps) 
		{
			if (BorderLayout.CENTER.equals(((BorderLayout)gamePanel.getLayout()).getConstraints(c)))
			{
				gamePanel.remove(c);
				break;
			}
		}
		
		buildGrid();
		refreshBoardview();
		cardLayout.show(mainPanel, "GAME");
		gamePanel.revalidate();
		gamePanel.repaint();

	}
	
	/**
     * Opens a file chooser dialog to save the current game.
     */
	private void saveGameDialog() 
	{
		if (game == null)
			return;
		JFileChooser chooser = new JFileChooser();
		int result = chooser.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) 
		{
			File file = chooser.getSelectedFile();
			try 
			{
				FileHandling.saveGame(game, file);
				JOptionPane.showMessageDialog(this, "Game Saved");
			}
			catch (Exception ex) 
			{
				JOptionPane.showMessageDialog(this, "Failed to save game");
			}
			
		}
	}
	
	/**
     * Opens a file chooser dialog to load a saved game.
     */
	private void loadGameDialog() 
	{
		JFileChooser chooser = new JFileChooser();
		int result = chooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) 
		{
			File file = chooser.getSelectedFile();
			try 
			{
				game = FileHandling.loadGame(file);
				JOptionPane.showMessageDialog(this, "Game loaded");
				enterGameScreen();
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this, "Failed to load game");
			}
		}
	}
	
	/**
     * Shows a help dialog with basic game instructions.
     */
	private void showHelpDialog() 
	{
		String msg = "Project Nova \n\n" 
	+ "Two players take turns guessing boxes to find hidden creatures.\n" 
	+ "Hit: +5 points, Complete creature: +10 points.\n" 
	+ "First player to reveal all creatures on their board wins.\n"
	+ "Use the menu to Start, Load, or Exit";
		JOptionPane.showMessageDialog(this, msg, "Help", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
     * Main method. Starts the GUI.
     *
     * @param args command line arguments
     */
	public static void main(String[]args) 
	{
		SwingUtilities.invokeLater(() -> {GUI gui = new GUI(); gui.setVisible(true);});
	}
	
}
