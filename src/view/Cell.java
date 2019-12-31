/**
 * 
 */
package view;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Cells are JButtons that populate the minefield. They are the square regions that can either contain a mine or not.
 * As JButtons, they can be clicked, which signals an event in any relevant listeners.
 * @author Tyson
 */
public class Cell extends JButton{

	/**
	 * Each Cell is identified by a unique index number.
	 */
	private int index;

	/**
	 * Sets default parameters for the Cell.
	 */
	public Cell(){
		index = -1;
		this.setSize(50, 50);
	}

	/**
     * Returns the Cell's unique index number.
	 * @return A unique index number for this Cell.
	 */
	public int getIndex(){
		return index;
	}

	/**
     * Sets the Cell's unique index number.
	 * @param newIndex A unique number to identify this Cell.
	 */
	public void setIndex(int newIndex){
		index = newIndex;
	}

	/**
     * Sets the image that is displayed on the Cell.
	 * @param img An ImageIcon to replace the current one the Cell has.
	 */
	public void setImage(ImageIcon img){
		setIcon(img);
		this.update(this.getGraphics());
	}

	/**
     * Sets the Cell's background color.
	 * @param color A Color to replace the Cell's current background Color.
	 */
	public void setColor(Color color){
		setBackground(color);
	}
}
