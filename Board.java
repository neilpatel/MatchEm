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
	


}