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
}