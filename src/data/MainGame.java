package data;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import view.Cell;
import view.ScorePanel;

/**
 * The MainGame class is only instantiated once. This MainGame object runs and controls everything.
 * It is a JFrame, and listens for the mouse and actions. It is the game window.
 * @author Tyson J LaFollette
 */
public class MainGame extends JFrame implements MouseListener, ActionListener{

	/**
	 * How many mines the player has marked.
	 */
	private int minesMarked;

	/**
	 * Whether the player has won.
	 */
    private boolean win;

	/**
	 * Number of seconds elapsed this game.
	 */
	private int time = 0;

	/**
	 * Stores mine locations, and adjacency counts.
	 */
    private ArrayList<Character> minefield;

	/**
	 * What state the player has left each cell in.
	 */
    private ArrayList<Character> cellStates;

	/**
	 * List of the cells themselves. Contain no data, only display.
	 */
    private ArrayList<Cell>mineCells;

	/**
	 * Whether the given cell has been visited in the current sweep.
	 */
    private ArrayList<Boolean>visits;

	/**
	 * The scoreboard.
	 */
	private ScorePanel scorePanel;

	/**
	 * Contains all mine cells.
	 */
	private JPanel gamePanel;


	/**
	 * Images for the various states of Cells.
	 */
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
	 * Constructor. The program begins with this.
	 */
	public MainGame(){
		InitGraphics();
		populateField();
		calculateAdjacencies();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
        win = false;
        minefield = new ArrayList<>();
        cellStates = new ArrayList<>();
        mineCells = new ArrayList<>();
        visits = new ArrayList<>();
    }
	
	/**
	 * Creates the desired number of mines within the playing field.
	 */
    private void populateField(){
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
	 * Checks if the Cell related to this index number contains a mine.
	 * @param index the index number of the Cell to check.
	 * @return A boolean indicating whether the Cell at the given index has a mine or not.
	 */
    private int isMine(int index){
		try{
			if(minefield.get(index)=='m'){
				return 1;
			}
		}catch(IndexOutOfBoundsException ignored){}
		
		return 0;
	}
	
	/**
	 * Determines the number of adjacent mines for every cell. Marks info.
	 */
    private void calculateAdjacencies(){
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
	 * Makes many cells reveal themselves if the player clicks on one that has no adjacent mines.
     * @param index the unique index of the Cell to examine.
     */
    private void caseZero(int index){
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
	 * Starts sweep of cells.
	 * @param theCell The Cell object to begin a sweep on.
	 */
    private void startSweep(Cell theCell){
		int index = theCell.getIndex();
		for(int i = 0; i < 576; i++){
			visits.set(index, false);
		}
		sweepCell(theCell);
	}

	/**
	 * Makes the given Cell reveal its contents. This is called when the player clicks on a Cell.
	 * @param theCell the Cell to reveal.
	 */
    private void sweepCell(Cell theCell){
		int index = theCell.getIndex();
		if(visits.get(theCell.getIndex())){
			return;
		}
		visits.set(index, true);
		if(theCell.isEnabled()){
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
    private void InitGraphics(){
		scorePanel = new ScorePanel(this);
		gamePanel = new JPanel();
		this.setIconImage(mineIcon.getImage());
		this.setTitle("minesweeperish");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel pane = (JPanel)this.getContentPane();
		this.setSize(500, 500);
		pane.setLayout(new BorderLayout());
		pane.add(scorePanel, BorderLayout.NORTH);
		pane.add(gamePanel);
		gamePanel.setLayout(new GridLayout(24,24));
		scorePanel.setMines(100);
	}

	/**
	 * Starts the game timer.
	 */
	public void startGame(){
		timer.start();
	}

	/**
	 * Starts a new game, resetting values. This is called when the player clicks on the start button.
	 */
    private void newGame(){
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
    private void flagCheck(){
		int minesRight = 0;
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
    private void GameOver(){
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
	 * Main, simply creates a thread for the game and instantiates a MainGame object which handles everything.
	 * @param args command line arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				new MainGame();
			}});
	}

	/**
	 * Handles mouse events.
	 * @param arg0 The name of the MousEvent being handled...?
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
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
				if(((Cell) source).isEnabled()){
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
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	/**
	 * Handles generic action events. Primarily used to update game timer.
	 * @param arg0 The name of the ActionEvent being handled.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
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
