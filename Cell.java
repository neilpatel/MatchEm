/*
*  Author: Neil Patel
*  GitHub: neilpatel
*  Project: MatchEm
*/

// import statements
import javax.swing.JButton;

/** 
 * A cell can be described as a hidden card, a card that has an image, or a cell
 * that has been matched which needs to be cleared from the board. 
 */
public class Cell extends JButton {
	private static final String TAG = "Cell: ";
	private static final long versionID = 1L;
	private static final int MAX_TYPE_RANGE = 26;
	private static final int MIN_TYPE_RANGE = 0;
	private static final int EMPTY_CELL_TYPE = 25;
	private boolean mIsSelected = false;
	private boolean mIsMatched = false;
	private int mType = EMPTY_CELL_TYPE;

	// Constructor; Create a cell of a specified type
	public Cell(int aType) {
		super();
		mType = aType;
	}

	// Simple method to return the type of the cell
	public int getType() {
		return mType;
	}

	// Method for printing any errors that are detected
	private static void error( String message, boolean crash ){
  		System.err.println( TAG + message );
  		if (crash) {
  			System.exit(1);
  		}
 }

	// Simply method to set the type of the returned cell
 	// @param aType is an integer value
 		// Invalid integer value will mean an error with the caller has occurred and program needs to terminate. 
	public void setType(int aType) {
		if (aType > MAX_TYPE_RANGE || aType < MIN_TYPE_RANGE) {
			error("setType(int) reported \"Invalid type code\", true");
		}
		mType = aType;
	}

	// Method to check if two cells are the same type (selected or matched)
	// @param other is the second Cell used to compare against
	public boolean sameType(Cell other) {
		if (other == null) {
			error("sameType(Cell) received null", false);
			return false; // If the cells are not related
		}
		// Conditional to check if the two types are the same
		if (this.getType() == other.getType()) {
			return true; // Cell types match
		} else {
			return false; // Cell types did not match
		}
	}

	// Check if the type of returned type from the sameType() is an empty cell
	public boolean isEmpty() {
		if(this.mType != EMPTY_CELL_TYPE) {
			return false; // If the cell contains something (not paired with another cell)
		}
		return true; // If the cell is empty
	}

	// Assign the individual cell to the selected cell Type
	public void setSelected(boolean selected) {
		mIsSelected = selected;
	}

	// Set the individual cell to the matched selection
	public void setMatched(boolean matched) {
		mIsMatched = matched;
	}

	// Conditional method to check if two cells that are selected are equal
	public boolean mIsSelected() {
		if (mIsSelected == true) {
			return true; // If the user has currently selected this cell
		}

		return false; // If the cell has not been selected
	}

	// Conditional method to check if the cell has been successfully matched with its pair
	public boolean isMatched() {
		if (mIsMatched == true) {
			return true; // The selected cards match
		} else {
			return false; // The selected cards do not match
		}
	}

	/**	
	*	ToDo Methods:
	*	1.	> Check if two cells have been selected
	*			> May need to adjust the GUI features to show selections have been made
	*	2.	> Check if the two cells that are selected match (aka equal)
	* 	3. 	> Simple method to set the two selections be compared to each other
	* 	4. 	> Method to check if they have been matched and next steps
	*/

}