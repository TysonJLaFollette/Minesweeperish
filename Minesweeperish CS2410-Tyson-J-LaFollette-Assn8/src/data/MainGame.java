/**
 * 
 */
package data;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import view.Cell;
import view.ScorePanel;

/**
 * @author Tyson J LaFollette
 * Maingame object that starts everything.
 */
public class MainGame extends JFrame implements MouseListener, ActionListener{
	/**
	 * content pane.
	 */
	private JPanel pane;
	/**
	 * Number of mines the player has marked correctly.
	 */
	private int minesRight = 0;
	/**
	 * Number of mines marked.
	 */
	private int minesMarked;
	/**
	 * Whether the player has won.
	 */
	boolean win = false;
	/**
	 * Number of seconds elapsed.
	 */
	private int time = 0;
	/**
	 * Stores mine locations, and adjacency counts.
	 */
	ArrayList<Character> minefield = new ArrayList<Character>();
	/**
	 * What state the player has left each cell in.
	 */
	ArrayList<Character> cellStates = new ArrayList<Character>();
	/**
	 * List of the cells themselves. Contain no data, only display.
	 */
	ArrayList<Cell>mineCells = new ArrayList<Cell>();
	/**
	 * Whether the given cell has been visited in the current sweep.
	 */
	ArrayList<Boolean>visits = new ArrayList<Boolean>();
	/**
	 * The scoreboard.
	 */
	private ScorePanel scorePanel;
	/**
	 * Contains all mine cells.
	 */
	private JPanel gamePanel;
	private ImageIcon mineIcon = new ImageIcon("data/mine.gif");
	private ImageIcon flagIcon = new ImageIcon("data/flag.png");
	private ImageIcon questionIcon = new ImageIcon("data/question.png");
	private ImageIcon oneIcon = new ImageIcon("data/one.png");
	private ImageIcon twoIcon = new ImageIcon("data/two.png");
	private ImageIcon threeIcon = new ImageIcon("data/three.png");
	private ImageIcon fourIcon = new ImageIcon("data/four.png");
	private ImageIcon fiveIcon = new ImageIcon("data/five.png");
	private ImageIcon sixIcon = new ImageIcon("data/six.png");
	private ImageIcon sevenIcon = new ImageIcon("data/seven.png");
	private ImageIcon eightIcon = new ImageIcon("data/eight.png");
	/**
	 * Timer for keeping score.
	 */
	private Timer timer = new Timer(1000, this);
	
	/**
	 * Constructor, starts game.
	 */
	public MainGame(){
		InitGraphics();
		populateField();
		calculateAdjacencies();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * Creates mines, places them.
	 */
	public void populateField(){
		for(int i = 0; i < 576; i++){
			visits.add(false);
		}
		for(int i = 0; i <576; i++){
			Cell tmpCell = new Cell();
			tmpCell.setIndex(i);
			tmpCell.addMouseListener(this);
			mineCells.add(tmpCell);
			gamePanel.add(tmpCell);
		}
		for(int i = 0; i < 100; i++){
			minefield.add('m');
		}
		for(int i = 0; i < 476; i++){
			minefield.add('0');
		}
		Collections.shuffle(minefield);
		for(int i = 0; i < 576;  i++){
			cellStates.add('b');
		}
	}
	
	/**
	 * @param index
	 * @return
	 * Checks if given index contains a mine.
	 */
	public int isMine(int index){
		try{
			if(minefield.get(index)=='m'){
				return 1;
			}
		}catch(IndexOutOfBoundsException e){}
		
		return 0;
	}
	
	/**
	 * Determines the number of adjacent mines for every cell. Marks info.
	 */
	public void calculateAdjacencies(){
		for(int i = 0; i < 576; i++){
			int count = 0;
			if(minefield.get(i) == 'm'){
				continue;
			}
			if(i%24 == 0){//on left edge
				count += isMine(i-24);
				count += isMine(i-23);
				count += isMine(i+1);
				count += isMine(i+24);
				count += isMine(i+25);
			}
			else if ((i+1)%23 == 0){//on right edge
				count += isMine(i-25);
				count += isMine(i-24);
				count += isMine(i-1);
				count += isMine(i+23);
				count += isMine(i+24);
			}
			else {
				count += isMine(i-25);
				count += isMine(i-24);
				count += isMine(i-23);
				count += isMine(i-1);
				count += isMine(i+1);
				count += isMine(i+23);
				count += isMine(i+24);
				count += isMine(i+25);
			}
			minefield.set(i, Integer.toString(count).charAt(0));
		}
	}
	/**
	 * @param index
	 * Handles what cells to check if the current cell has no adjacent mines.
	 */
	public void caseZero(int index){
		if(index == 0){
			sweepCell(mineCells.get(index+1));
			sweepCell(mineCells.get(index+24));
			sweepCell(mineCells.get(index+25));
		}
		else if (index == 23){
			sweepCell(mineCells.get(index-1));
			sweepCell(mineCells.get(index+23));
			sweepCell(mineCells.get(index+24));
		}
		else if(index < 24){
			sweepCell(mineCells.get(index-1));
			sweepCell(mineCells.get(index+1));
			sweepCell(mineCells.get(index+23));
			sweepCell(mineCells.get(index+24));
			sweepCell(mineCells.get(index+25));
		}
		else if (index == 576){
			sweepCell(mineCells.get(index-25));
			sweepCell(mineCells.get(index-24));
			sweepCell(mineCells.get(index-1));
		}
		else if (index ==552){
			sweepCell(mineCells.get(index-24));
			sweepCell(mineCells.get(index-23));
			sweepCell(mineCells.get(index+1));
		}
		else if(index >552){
			sweepCell(mineCells.get(index-25));
			sweepCell(mineCells.get(index-24));
			sweepCell(mineCells.get(index-23));
			sweepCell(mineCells.get(index-1));
			sweepCell(mineCells.get(index+1));
		}
		else if(index%24 == 0){//on left edge
			sweepCell(mineCells.get(index-24));
			sweepCell(mineCells.get(index-23));
			sweepCell(mineCells.get(index+1));
			sweepCell(mineCells.get(index+24));
			sweepCell(mineCells.get(index+25));
		}
		else if ((index+1)%23 == 0){//on right edge
			sweepCell(mineCells.get(index-25));
			sweepCell(mineCells.get(index-24));
			sweepCell(mineCells.get(index-1));
			sweepCell(mineCells.get(index+23));
			sweepCell(mineCells.get(index+24));
		}
		else {
			sweepCell(mineCells.get(index-25));
			sweepCell(mineCells.get(index-24));
			sweepCell(mineCells.get(index-23));
			sweepCell(mineCells.get(index-1));
			sweepCell(mineCells.get(index+1));
			sweepCell(mineCells.get(index+23));
			sweepCell(mineCells.get(index+24));
			sweepCell(mineCells.get(index+25));
		}
	}
	/**
	 * @param theCell
	 * Starts sweep of cells.
	 */
	public void startSweep(Cell theCell){
		int index = theCell.getIndex();
		for(int i = 0; i < 576; i++){
			visits.set(index, false);
		}
		sweepCell(theCell);
	}
	/**
	 * @param theCell
	 * Sweeps the given cell, checking for mines, etc.
	 */
	public void sweepCell(Cell theCell){
		int index = theCell.getIndex();
		if(visits.get(theCell.getIndex())== true){
			return;
		}
		visits.set(index, true);
		if(theCell.isEnabled() == true){
			switch (minefield.get(index)){
			case 'm':
				GameOver();
				break;
			case '0':
				caseZero(index);
				theCell.setEnabled(false);
				theCell.setBackground(theCell.getBackground().darker());
				break;
            case '1':
            	theCell.setEnabled(false);
            	theCell.setIcon(oneIcon);
            	theCell.setBackground(theCell.getBackground().darker());
            	break;
            case '2':
            	theCell.setEnabled(false);
            	theCell.setIcon(twoIcon);
            	theCell.setBackground(theCell.getBackground().darker());
            	break;
            case '3':
            	theCell.setEnabled(false);
            	theCell.setIcon(threeIcon);
            	theCell.setBackground(theCell.getBackground().darker());
            	break;
            case '4':
            	theCell.setEnabled(false);
            	theCell.setIcon(fourIcon);
            	theCell.setBackground(theCell.getBackground().darker());
            	break;
            case '5':
            	theCell.setEnabled(false);
            	theCell.setIcon(fiveIcon);
            	theCell.setBackground(theCell.getBackground().darker());
            	break;
            case '6':
            	theCell.setEnabled(false);
            	theCell.setIcon(sixIcon);
            	theCell.setBackground(theCell.getBackground().darker());
            	break;
            case '7':
            	theCell.setEnabled(false);
            	theCell.setIcon(sevenIcon);
            	theCell.setBackground(theCell.getBackground().darker());
            	break;
            case '8':
            	theCell.setEnabled(false);
            	theCell.setIcon(eightIcon);
            	theCell.setBackground(theCell.getBackground().darker());
            	break;
			}
		}
	}
	/**
	 * Initializes view components.
	 */
	public void InitGraphics(){
		scorePanel = new ScorePanel(this);
		gamePanel = new JPanel();
		this.setIconImage(mineIcon.getImage());
		this.setTitle("Minesweeper-ish");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pane = (JPanel)this.getContentPane();
		this.setSize(500, 500);
		pane.setLayout(new BorderLayout());
		pane.add(scorePanel, BorderLayout.NORTH);
		pane.add(gamePanel);
		gamePanel.setLayout(new GridLayout(24,24));
		scorePanel.setMines(100);
	}
	/**
	 * Starts teh timer.
	 */
	public void startGame(){
		timer.start();
	}
	/**
	 * Starts a new game, resetting values.
	 */
	public void newGame(){
		time = 0;
		timer.stop();
		for(int i = 0; i < 576; i++){
			gamePanel.remove(0);
			mineCells.remove(0);
			cellStates.set(i,'b');
		}
		for(int i = 0; i < 576; i++){
			Cell tmpCell = new Cell();
			tmpCell.setIndex(i);
			tmpCell.addMouseListener(this);
			mineCells.add(tmpCell);
			gamePanel.add(tmpCell);
		}
		Collections.shuffle(minefield);
		calculateAdjacencies();
		update(getGraphics());
	}
	/**
	 * Checks whether the player has won each time they press a cell.
	 */
	public void flagCheck(){
		minesRight = 0;
		for(int i = 0; i < 576; i++){
			if(minefield.get(i) == 'm' && cellStates.get(i) == 'f'){
				minesRight++;
			}
		}
		if(minesRight == 100){
			win = true;
			GameOver();
		}
	}
	/**
	 * Displays final messages, shows mine locations.
	 */
	public void GameOver(){
		timer.stop();
		for(int i = 0; i < 576; i++){
			mineCells.get(i).setEnabled(false);
			if(minefield.get(i)=='m'){
				if(cellStates.get(i) == '?'){
					mineCells.get(i).setBackground(Color.YELLOW);
					mineCells.get(i).setIcon(mineIcon);
				}
				else if(cellStates.get(i) == 'b'){
					mineCells.get(i).setBackground(Color.RED);
					mineCells.get(i).setIcon(mineIcon);
				}
				else{
					mineCells.get(i).setBackground(Color.GREEN);
					mineCells.get(i).setIcon(mineIcon);
				}
			}
		}
		if(win){
			JOptionPane.showMessageDialog(null,"End! Your time was " + time + " seconds.");
		}
		else{
			JOptionPane.showMessageDialog(null,"End!");
		}
	}
	/**
	 * @param args
	 * Main, simply creates a thread for the game.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				new MainGame();
			}});
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		Object source = arg0.getSource();
		if(source instanceof Cell){
			if(SwingUtilities.isLeftMouseButton(arg0)){
				timer.start();
				int id = ((Cell)source).getIndex();
				if(cellStates.get(id)!='f'){
					startSweep((Cell)source);
				}
			}
			
			else if(SwingUtilities.isRightMouseButton(arg0)){
				int id = ((Cell)source).getIndex();
				if(((Cell)source).isEnabled() == true){
					if(cellStates.get(id)=='b'){
						cellStates.set(id, 'f');
						((Cell)source).setImage(flagIcon);
						minesMarked++;
						scorePanel.setMines(100-minesMarked);
					}
					else if(cellStates.get(id)=='f'){
						cellStates.set(id,'?');
						((Cell)source).setImage(questionIcon);
						minesMarked--;
						scorePanel.setMines(100-minesMarked);
					}
					else if(cellStates.get(id)=='?'){
						cellStates.set(id,'b');
						((Cell)source).setImage(null);
					}
				}
				
			}
		}
		flagCheck();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object source = arg0.getSource();
		if(source!= timer){
			newGame();
		}
		if(source == timer){
			time++;
			scorePanel.setTime(time);
		}
		
	}

}
