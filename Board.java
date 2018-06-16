/*
*  Author: Neil Patel
*  GitHub: neilpatel
*  Project: MatchEm
*/

// import statements
import javax.swing.JPanel
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.net.URL;


public class Board extends JPanel implements ActionListener {
	private static final String TAG = "Board: ";
	private static final long versionID = 1L;
	private static final int MAX_NUM_OF_CARDS = 24;
	private static final int MIN_NUM_OF_CARDS = 1;
	private static final int NUMBER_OF_ROWS = 4;
	private static final int NUMBER_OF_COLUMNS = 6;
	private static final int NUMBER_OF_PAIRS = 12;
	private static final int BOARD_BORDER_WIDTH = 20;
	private static final int MAX_SELECTED_CARDS = 2;
	private static final int FIRST = 0;
	private static final int SECOND = 1;
	private static final int VISIBLE_DELAY = (int) 2 * 1000;
	private static final int PEEK_DELAY = (int) 2 * 1000;
	private static final int EMPTY_CELL_TYPE = 0;
	private static final int HIDDEN_CARD_TYPE = 26;
	private static final int EMPTY_CARD_TYPE = 25;
	private static final String DEFAULT_IMAGE_FILENAME_SUFFIX = ".jpg";
	private static final String DEFAULT_IMAGE_FILENAME_PREFIX = "img-";	
 	private static final String DEFAULT_IMAGE_FOLDER = "/images/";
 	private static final String HIDDEN_IMAGE_PATH = DEFAULT_IMAGE_FOLDER + DEFAULT_IMAGE_FILENAME_PREFIX + "26" + DEFAULT_IMAGE_FILENAME_SUFFIX;
 	private static final String EMPTY_IMAGE_PATH = DEFAULT_IMAGE_FOLDER + DEFAULT_IMAGE_FILENAME_PREFIX + "25" + DEFAULT_IMAGE_FILENAME_SUFFIX;

 	private static ArrayList<Cell> chosenCards = new ArrayList<Cell>();
 	private static int numOfMatchedPairs = 0;
 	private static int numOfFailedAttempts = 0;
 	private static int selectedCards = 0;
 	private Cell[][] mBoard = null;
 	private Cell [] mCardChecker = new Cell[MAX_SELECTED_CARDS];
 	private String[] mCardStorage = initCardStorage();

 	// Constructor; Setup the board to be ready to use
 	public Board() {
 		super();

 		setBackground(Color, BLUE);
 		setBorder(BorderFactory.createEmptyBorder(BOARD_BORDER_WIDTH,
   		BOARD_BORDER_WIDTH, BOARD_BORDER_WIDTH, BOARD_BORDER_WIDTH));
  		setLayout(new GridLayout(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS));
  		mBoard = new Cell[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];

  		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
   			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
    			mBoard[row][column] = new Cell(EMPTY_CELL_TYPE);
    			mBoard[row][column].addActionListener(this);
    			add(mBoard[row][column]);
   			}
  		}
  		init();
 	}

 // Public Interface Methods
 	// Create the board with a new set of cards
 	public void init() {
 		resetMatchedImages();
 		resetBoardParam();
 		peek();
 		mCardStorage = initCardStorage();
 		setImages();
 	}

 	// Re-set up the gameboard with a new set of cards
 	public void reInit() {
 		resetMatchedImages();
 		resetBoardParam();
 		peek();
 		setImages();
 	}

 	// Check to see if the board has been solved
 	public boolean isSolved() {
 		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
   			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
    			if (!mBoard[row][column].isEmpty()) {
     				return false; // If there remains card that have to be matched
    			}
   			}
  		}

  		return true; // If the board is solved
 	}

 	// Add a card to selected card list

 	private void addToChose(Cell aCard) {
 		// Conditional to check if the card is not empty
 		if (aCard != null) {
   			if (!chosenCards.contains(aCard)) {
    			chosenCards.add(aCard);
   			}
  		} else {
   			error("addToChose( Cell ) received null.", true);
  		}
 	}

 	/* ToDo:
 	*	Add actionPerformed( with some action) method
 	*	Add Cell Location Method to give the exact location of the cell on board
 	*	Add a method to determine if the card is visible to the user at a certain location
	*	Add methods to handle the images
	* 	Random generation of cards method
 	*	*If possible, implement a preview/peek method to allow user to see the board before game starts
 	* 		many more...
 	*/



}