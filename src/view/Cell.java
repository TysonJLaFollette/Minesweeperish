package view;

import java.awt.*;

import javax.swing.*;

/**
 * Cells are JButtons that populate the minefield. They are the square regions that can either contain a mine or not.
 * As JButtons, they can be clicked, which signals an event in any relevant listeners.
 * @author Tyson
 */
public class Cell extends JButton{
	private int column;

	private int row;

	/**
	 * Sets default parameters for the Cell.
	 */
	public Cell(){
		column = -1;
		row = -1;
		this.setSize(50, 50);
		this.setFont(new Font("Serif", Font.ITALIC, 14));
		this.setMargin(new Insets(0, 0, 0, 0));
		this.setForeground(Color.BLACK);
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
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
