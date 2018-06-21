/*
*  Author: Neil Patel
*  GitHub: neilpatel
*  Project: MatchEm
*/

// import statements
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JSplitPane;

// Start of MatchEm Main Source File
public class MatchEm extends JFrame {
	private static final long versionID = 1L;

	private Board mBoard;
	private JButton mRetryButton;
	private JButton mNewButton;
	private JSplitPane mSplitPane;

	// Constructor; Create a Frame for starting/diplaying the game
	public MatchEm() {
		super(); 		// Access/Call Functions on object's parent with no args
		setTitle("Neil's Custom Matching Card Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mBoard = new Board();			// Create a Board
		add(mBoard, BorderLayout.CENTER);

		mSplitPane = new JSplitPane();
		add(mSplitPane, BorderLayout.SOUTH);

		mRetryButton = new JButton("Retry");		// Create a Retry button
		mRetryButton.addMouseListener(btnMouseListener); 
		mRetryButton.setFocusPainted(false);
		mSplitPane.setLeftComponent(mRetryButton);

		mNewButton = new JButton("New Game"); 	// Create a New Game button
		mNewButton.setFocusPainted(false);
		mNewButton.addMouseListener(btnMouseListener);
		mSplitPane.setRightComponent(mNewButton);

		setResizable(true); 	// Allow the user to resize the window if needed
		setVisible(true); 		// Keep the Components and his subchildren visible
		pack();		// 'Pack' the components within the window based on the respective sizes
	}

	// Mouse Listener
	private MouseListener btnMouseListener = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1 && e.getComponent() == mRetryButton) {
				mBoard.reInit();
			} else if (e.getClickCount() == 1 && e.getComponent() == mNewButton) {
				mBoard.init();
			}
		}
	};

	// Main Method
	public static void main(String[] args) {
		new MatchEm();
	}
}