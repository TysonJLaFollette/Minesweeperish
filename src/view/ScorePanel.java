package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Tyson
 * The ScorePanel is a rectangular region that displays the timer and current score. It is an extension of the JPanel class.
 */
public class ScorePanel extends JPanel{

	/**
	 * Contains number of mines remaining.
	 */
	private final JLabel minesLabel = new JLabel("Mines:");

	/**
	 * Contains the timer counter.
	 */
	private final JLabel timerLabel = new JLabel("Time:");

	/**
	 * A font to use within the ScorePanel. Only necessary because of the grading rubric.
	 */
	Font font = new Font("Serif", Font.ITALIC, 20);
	
	/**
     * Sets defaults for the ScorePanel.
	 * @param listener An ActionListener for the start button within the ScorePanel.
	 */
	public ScorePanel(ActionListener listener){
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		this.add(minesLabel, constraints);
		constraints.gridx = 1;
		constraints.gridwidth = 1;
		ImageIcon startIcon = new ImageIcon("data/mine.gif");
		JButton startButton = new JButton(startIcon);
		this.add(startButton, constraints);
		constraints.gridx = 2;
		constraints.gridwidth = 1;
		this.add(timerLabel, constraints);
        startButton.addActionListener(listener);
        startButton.setPreferredSize(new Dimension(25,25));
        minesLabel.setFont(font);
        timerLabel.setFont(font);
	}

	/**
     * Sets how many mines the ScorePanel says remain.
	 * @param num The quantity of mines to report.
	 */
	public void setMines(int num){
		minesLabel.setText("Mines: " + num);
		update(getGraphics());
	}

	/**
     * Sets the number of seconds the ScorePanel says the game has been running.
	 * @param secs The number of seconds to display.
	 */
	public void setTime(int secs){
		timerLabel.setText("Time: " + secs);
		update(getGraphics());
	}
}
