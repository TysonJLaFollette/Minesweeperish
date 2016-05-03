/**
 * 
 */
package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Tyson
 * A scorepanel that appears atop the game.
 */
public class ScorePanel extends JPanel{
	/**
	 * Contains number of mines remaining.
	 */
	private JLabel minesLabel = new JLabel("Mines:");
	/**
	 * Contains the timer counter.
	 */
	private JLabel timerLabel = new JLabel("Time:");
	/**
	 * The icon that appears on the start button.
	 */
	private ImageIcon startIcon = new ImageIcon("data/mine.gif");
	/**
	 * the start button.
	 */
	private JButton startButton = new JButton(startIcon);
	/**
	 * different font, because grading rubric.
	 */
	Font font = new Font("Serif", Font.ITALIC, 20);
	
	/**
	 * @param listener
	 * constructor
	 */
	public ScorePanel(ActionListener listener){
		startButton.addActionListener(listener);
		
		
        minesLabel.setFont(font);
        timerLabel.setFont(font);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		startButton.setPreferredSize(new Dimension(25,25));
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		this.add(minesLabel, c);
		c.gridx = 1;
		c.gridwidth = 1;
		this.add(startButton, c);
		c.gridx = 2;
		c.gridwidth = 1;
		this.add(timerLabel, c);
	}
	/**
	 * @param num
	 * sets number of mines to display
	 */
	public void setMines(int num){
		minesLabel.setText("Mines: " + Integer.toString(num));
		update(getGraphics());
	}
	/**
	 * @param secs
	 * sets number of seconds to display.
	 */
	public void setTime(int secs){
		timerLabel.setText("Time: " + Integer.toString(secs));
		update(getGraphics());
	}
}
