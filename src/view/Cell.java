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
 * @author Tyson
 * Cells to populate the minefield with.
 */
public class Cell extends JButton{
	/**
	 * the cell's index number.
	 */
	private int index;

	/**
	 * constructor.
	 */
	public Cell(){
		index = -1;
		this.setSize(50, 50);
	}
	/**
	 * @return
	 * returns the index number.
	 */
	public int getIndex(){
		return index;
	}
	/**
	 * @param newIndex
	 * Sets the index number.
	 */
	public void setIndex(int newIndex){
		index = newIndex;
	}
	/**
	 * @param img
	 * sets the cell's image.
	 */
	public void setImage(ImageIcon img){
		setIcon(img);
		
		this.update(this.getGraphics());
	}
	/**
	 * @param color
	 * sets the cell's background color.
	 */
	public void setColor(Color color){
		setBackground(color);
	}
}
