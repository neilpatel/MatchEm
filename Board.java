/*
 * Author: Neil Patel
 * GitHub: neilpatel
 * Project: MatchEm
*/

// import statements
import javax.swing.JPanel;
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
import java.util.Collections;
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
	private static final int VISIBLE_DELAY = (int) 2000;
	private static final int PEEK_DELAY = (int) 3000;
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

 		setBackground(Color.WHITE);
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

 	// When a card is clicked, change the board features accordingly	
 	public void actionPerformed(ActionEvent e) {
  		if (e == null) {
   			error("actionPermormed(ActionEvent) received null", false);
   			return;
  		}

  		if (!(e.getSource() instanceof Cell)) {
  	 		return;
  		}

  		if (!isCardValid((Cell) e.getSource())) {
   			return;
  		}

 		++selectedCards;
		
		if (selectedCards <= MAX_SELECTED_CARDS) {
			Point gridLoc = getCellLocation((Cell) e.getSource());
			setCardToVisible(gridLoc.x, gridLoc.y);
			mCardChecker[selectedCards - 1] = getCellAtLoc(gridLoc);
			addToChose(getCellAtLoc(gridLoc));
  		}

 		// Conditional to check if the selected number of cards is the max number
  		if (selectedCards == MAX_SELECTED_CARDS) {
   			if (!sameCellPosition(mCardChecker[FIRST].getLocation(),
     			mCardChecker[SECOND].getLocation())) {
					setSelectedCards(mCardChecker[FIRST], mCardChecker[SECOND]);
   			} else {
    			--selectedCards;
   			}
  		}
 	}

 	// Return the location of a particular Cell on the GameBoard
 	private Cell getCellAtLoc(Point point) {
  		if (point == null) {
   			error("getCellAtLoc( Point ) received null", true);
   			return null;
 		}

  		return mBoard[point.x][point.y];
 	}

 	// Set the card to visible at a specified location
 	private void setCardToVisible(int x, int y) {
 		mBoard[x][y].setSelected(true);
 		showCardImages();
 	}

 	// Allow the user to see a preview of the cards prior to the beginning of the game
 	private void peek() {
 		Action showImagesAction = new AbstractAction() {
 			private static final long versionID = 1L;
 			public void actionPerformed(ActionEvent e) {
 				showCardImages();
 			}
 		};

 		Timer timer = new Timer(PEEK_DELAY, showImagesAction);
 		timer.setRepeats(false);
 		timer.start();
 	}

 	// Display the images on the board
 	private void setImages() {
 		ImageIcon anImage;

 		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
   			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
    			URL file = getClass().getResource(DEFAULT_IMAGE_FOLDER + DEFAULT_IMAGE_FILENAME_PREFIX + mCardStorage[column + (NUMBER_OF_COLUMNS * row)] + DEFAULT_IMAGE_FILENAME_SUFFIX);
			
				if (file == null) {
     				System.err.println(TAG + "setImages() reported error \"File not found\".");
     				System.exit(-1);
    			}
    			anImage = new ImageIcon(file);
				mBoard[row][column].setIcon(anImage);
   			}
  		}
 	}

 	// Display a specific image at a certain location
 	private void showImage(int x, int y) {
 		URL file = getClass().getResource(DEFAULT_IMAGE_FOLDER + DEFAULT_IMAGE_FILENAME_PREFIX + mCardStorage[y + (NUMBER_OF_COLUMNS * x)] + DEFAULT_IMAGE_FILENAME_SUFFIX);
 		
 		if (file == null) {
 			System.err.println(TAG + "showImage(int, int) reported error \"File not found\".");
 			System.exit(-1);
 		}

 		ImageIcon anImage = new ImageIcon(file);
 		mBoard[x][y].setIcon(anImage);
 	}

 	// Display all the images on the board
 	private void showCardImages() {
 		// For each card on the board
  		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
   			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
    			// Conditional to check if the card is selected
    			if (!mBoard[row][column].isSelected()) {
     				// If true, see if the card has been matched
     				if (mBoard[row][column].isMatched()) {
      					// If it was successfully matched, empty both card selections
      					mBoard[row][column].setIcon(new ImageIcon(getClass().getResource(EMPTY_IMAGE_PATH)));
      					mBoard[row][column].setType(EMPTY_CARD_TYPE);
     				} else {
      					// If the correct selection was not made, hide the card to allow the next selection to be made
      					mBoard[row][column].setIcon(new ImageIcon(getClass().getResource(HIDDEN_IMAGE_PATH)));
      					mBoard[row][column].setType(HIDDEN_CARD_TYPE);
     				}
    			} else {
    				// The user has not been selected
     				showImage(row, column);
     				String type = mCardStorage[column + (NUMBER_OF_COLUMNS * row)];
     				int parsedType = Integer.parseInt(type);
    				mBoard[row][column].setType(parsedType);

    			} 
   			} // Inner For Loop (columns)
  		} // Outermost For Loop (rows)
 	}

 	// Generate a random image using the Random Java Class
 	private String generateRandomImageFilename(int max, int min) {
 		Random random = new Random();
 		Integer aNumber = (min + random.nextInt(max));

 		if(aNumber > 0 && aNumber <10) {
 			return "0" + aNumber;
 		} else {
 			return aNumber.toString();
 		}
 	}

 	// Create an array of String to hold all the index values of 24 random numbers. 
 	private String[] initCardStorage() {
 		String[] cardStorage = new String[MAX_NUM_OF_CARDS];
 		String[] firstPair = new String[NUMBER_OF_PAIRS];
 		String[] secondPair = new String[NUMBER_OF_PAIRS];

 		firstPair = randomListWithoutRep();

 		for (int i = 0; i < NUMBER_OF_PAIRS; i++) {
 			cardStorage[i] = firstPair[i];
 		}

 		Collections.shuffle(Arrays.asList(firstPair));

 		for (int j = 0; j < NUMBER_OF_PAIRS; j++) {
 			secondPair[j] = firstPair[j];
 		}

 		for (int k = NUMBER_OF_PAIRS; k < MAX_NUM_OF_CARDS; k++) {
 			cardStorage[k] = secondPair[k - NUMBER_OF_PAIRS];
 		}
 		return cardStorage;
 	}

 	// Generate a list of image pair types (without repetition)
 	private String[] randomListWithoutRep() {
 		String[] generatedArray = new String[NUMBER_OF_PAIRS];
 		ArrayList<String> generated = new ArrayList<String>();

 		// Goal is to determine the total number of different pairs
 		for (int i = 0; i < NUMBER_OF_PAIRS; i++) {
   			while (true) {
    			String next = generateRandomImageFilename(MAX_NUM_OF_CARDS, MIN_NUM_OF_CARDS);
    			if (!generated.contains(next)) {
     				generated.add(next);
     				generatedArray[i] = generated.get(i);
     				break; // Terminate out of the if statement
    			}
   			} // Close While Loop
  		} // Close For Loop

  		return generatedArray;
 	}

  // Return a spcecific point on the board inside a particular cell
  private Point getCellLocation(Cell aCell) {
    if(aCell == null) {
      error("getCellLocation(Cell) received null", true);
      return null;
    }

    Point p = new Point();
    for (int column = 0; column < NUMBER_OF_ROWS; column++) {
      for (int row = 0; row < NUMBER_OF_COLUMNS; row++) {
        if (mBoard[column][row] == aCell) {
          p.setLocation(column, row);
          return p; 
        }
      } // Inner For loop (rows)
    } // Outer For Loop (columns)
    return null; // All else case
  } // Method Closer

  // Check if two cards in that particular location are the same
  private boolean sameCellPosition(Point firstCell, Point secondCell) {
    if (firstCell == null || secondCell == null) {
      if (secondCell == firstCell) {
        // If null, both cells are equal
        return true; 
      }

      if (firstCell == null) {
        error("sameCellPosition(Point, Point) received (null, ??)", true);
      }

      if (secondCell == null) {
        error("sameCellPosition(Point, Point) received (??, null)" , true);
      }

      return false;
    }

    if (firstCell.equals(secondCell)) {
      return true;
    }
    return false;
  }

  // Check if any two cards are the same so it replaces them with a blank cell
  // If they are different, flips the cards back and checks if the board is solved
  private void setSelectedCards(Cell firstCell, Cell secondCell) {
    if(firstCell == null || secondCell == null) {
      if(firstCell == null) {
        error("setSelectedCards(Cell, Cell) received (null, ??)", true);
      }
      if(secondCell == null) {
        error("setSelectedCards(Cell, Cell) received (??, null)", true);
      }
      return;
    }
    if (firstCell.sameType(secondCell)) {
      firstCell.setMatched(true);
      secondCell.setMatched(true);
      firstCell.setSelected(false);
      secondCell.setSelected(false);
      showImage(getCellLocation(secondCell).x,getCellLocation(secondCell).y);
      peek();
      numOfMatchedPairs++;
      finalMessage();
    } else {
      firstCell.setMatched(false);
      secondCell.setMatched(false);
      firstCell.setSelected(false);
      secondCell.setSelected(false);
      showImage(getCellLocation(secondCell).x,getCellLocation(secondCell).y);
      peek();
      numOfFailedAttempts++;
    }
    resetSelectedCards();
  }

  // Check if a selected card is valid. The user cant select blank cells again
  private boolean isCardValid(Cell aCard) {
    if (aCard == null) {
      error("isCardValid(Cell) received null", false);
      return false;
    }

    if(!aCard.isEmpty()) {
      return true;
    } else {
      return false;
    }
  }

  // Reset all matched images
  private void resetMatchedImages() {
    for(int row = 0; row < NUMBER_OF_ROWS; row++) {
      for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
        if (mBoard[row][column].isMatched()) {
          mBoard[row][column].setMatched(false);
        } // If statement
      } // Column For Loop
    } // Row For Loop
  } // Method Closer

  // Display the result of the game when finished
  private void finalMessage() {
    @SuppressWarnings("serial") 
    Action showImagesAction = new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        if (isSolved()) {
          Float numeralScore = (((float) numOfFailedAttempts) / ((float) MAX_NUM_OF_CARDS)) * 100;
          String textualScore = numeralScore.toString();
          JOptionPane.showMessageDialog(null, "Congratulations! You completed the game in:\n" + " Failed Attempts: " + numOfFailedAttempts + "\n Error percentage: " + textualScore + " %", "Results",
          JOptionPane.INFORMATION_MESSAGE);
    } // If statement
   } // ActionPerformed Method
  }; // class implementation

    Timer timer = new Timer(VISIBLE_DELAY, showImagesAction);
    timer.setRepeats(false);
    timer.start();
  }

// Static Methods
  // Display Error Message
  private static void error(String message, boolean crash) {
    System.err.println(TAG + message);
    if (crash) {
      System.exit(-1);
    }
  }

  // Reset the number of selected cards to 0 after 2 cards have been chosen/checked
  private static void resetSelectedCards() {
    selectedCards = 0; 
  }

  // Reset the number of matched pairs on the board
  private static void resetNumMatchedCards() {
    numOfMatchedPairs = 0; 
  }

  // Reest the number of failed attempts in prior game
  private static void resetFailedAttempts() {
    numOfFailedAttempts = 0;
  }

  // Reset the board parameters
  private static void resetBoardParam() {
    resetFailedAttempts();
    resetNumMatchedCards();
  }  
}
