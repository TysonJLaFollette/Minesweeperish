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
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Model.Model;
import view.Cell;
import view.ScorePanel;
import Model.ArrayListModel;

/**
 * The MainGame class is only instantiated once. This MainGame object runs and controls everything.
 * It is a JFrame, and listens for the mouse and actions. It is the game window.
 * @author Tyson J LaFollette
 */
public class Presenter extends JFrame implements MouseListener, ActionListener{
    //region Properties
    private int numRows;
    private int numCols;
    private int numMines;
	private int minesMarked;
    private boolean win;
	private int time = 0;
    private ArrayList<Character> cellStates;
    private ArrayList<Cell>mineCells;
	private ScorePanel scorePanel;
	private JPanel gamePanel;
	private Model gameData;
    //endregion

    //region Constructors
    public Presenter(int numRows, int numCols, int numMines){
        this.numRows = numRows;
        this.numCols = numCols;
        this.numMines = numMines;
        win = false;
        cellStates = new ArrayList<>();
        mineCells = new ArrayList<>();
        this.gameData = new ArrayListModel();
        InitializeField();
        InitGraphics();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    //endregion

    //region Model
    //endregion

    //region View
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
     * Initializes view components.
     */
    private void InitGraphics(){
        System.out.println("Initializing graphics.");
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
        gamePanel.setLayout(new GridLayout(numRows,numCols));
        scorePanel.setMines(numMines);
        for (int curIndex = 0; curIndex < numCols*numRows; curIndex++){
            Cell tmpCell = new Cell();
            tmpCell.setIndex(curIndex);
            tmpCell.addMouseListener(this);
            mineCells.add(tmpCell);
            gamePanel.add(tmpCell);
        }
    }

    /**
     * Displays final messages, shows mine locations.
     */
    private void GameOver(){
        timer.stop();
        for(int curIndex = 0; curIndex < numRows * numCols; curIndex++){
            int column = ConvertIndexToCoordinates(curIndex, gameData.GetNumCols(), gameData.GetNumRows())[0];
            int row = ConvertIndexToCoordinates(curIndex, gameData.GetNumCols(), gameData.GetNumRows())[1];
            mineCells.get(curIndex).setEnabled(false);
            if(gameData.IsMine(column,row)){
                if(cellStates.get(curIndex) == '?'){
                    mineCells.get(curIndex).setBackground(Color.YELLOW);
                    mineCells.get(curIndex).setIcon(mineIcon);
                }
                else if(cellStates.get(curIndex) == 'b'){
                    mineCells.get(curIndex).setBackground(Color.RED);
                    mineCells.get(curIndex).setIcon(mineIcon);
                }
                else{
                    mineCells.get(curIndex).setBackground(Color.GREEN);
                    mineCells.get(curIndex).setIcon(mineIcon);
                }
            }
        }
        if(win){
            JOptionPane.showMessageDialog(null,"End! Your time was " + time + " seconds.");
        }
        else{
            JOptionPane.showMessageDialog(null,"End!");
        }
        //TODO finish this new version.
        /*for (int curCol = 0; curCol < numCols; curCol++){
            for(int curRow = 0; curRow < numRows; curRow++){
                int curIndex = ConvertCoordinatesToIndex(new int[]{curCol,curRow});
                mineCells.get(curIndex).setEnabled(false);

            }
        }*/
    }

    /**
     * Handles mouse events.
     * @param arg0 The name of the MousEvent being handled...?
     */
    @Override
    public void mouseClicked(MouseEvent arg0) {
        Object source = arg0.getSource();
        if(source instanceof Cell){
            int clickedIndex = ((Cell)source).getIndex();
            int column = ConvertIndexToCoordinates(clickedIndex, gameData.GetNumCols(), gameData.GetNumRows())[0];
            int row = ConvertIndexToCoordinates(clickedIndex, gameData.GetNumCols(), gameData.GetNumRows())[1];
            if(SwingUtilities.isLeftMouseButton(arg0)){
                timer.start();
                if(cellStates.get(clickedIndex)!='f'){
                    startSweep((Cell)source);
                }
            }

            else if(SwingUtilities.isRightMouseButton(arg0)){
                if(((Cell) source).isEnabled()){
                    if(cellStates.get(clickedIndex)=='b'){
                        cellStates.set(clickedIndex, 'f');
                        ((Cell)source).setImage(flagIcon);
                        minesMarked++;
                        scorePanel.setMines(numMines-minesMarked);
                    }
                    else if(cellStates.get(clickedIndex)=='f'){
                        cellStates.set(clickedIndex,'?');
                        ((Cell)source).setImage(questionIcon);
                        minesMarked--;
                        scorePanel.setMines(numMines-minesMarked);
                    }
                    else if(cellStates.get(clickedIndex)=='?'){
                        cellStates.set(clickedIndex,'b');
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
    //endregion

    //region Presenter
    /**
     * Creates a minefield with the desired number of mines.
     */
    private void InitializeField(){
        System.out.println("Initializing field.");
        gameData.CreateMinefield(numRows, numCols);
        PlantMines(gameData,numMines);
        System.out.println("Mines according to ArrayListModel:");
        for (int curRow = 0; curRow < numRows; curRow++){
            for (int curCol = 0; curCol < numCols; curCol++){
                if (gameData.IsMine(curCol,curRow)){
                    System.out.print("m");
                }else{
                    System.out.print("0");
                }
            }
            System.out.println("");
        }
        //TODO this is the perfect Model refactor! We use the new model to fill the old one. Then we replace use cases one at a time.
        //The game remains fully functional the whole time.
        //First, initialize the 1D data structure with empty values.
        for (int curIndex = 0; curIndex < numRows*numCols; curIndex++){
            cellStates.add('b');
        }
    }

    /**
     * Plants the desired number of mines randomly within the given minefield.
     * @param gameData The Model containing the minefield.
     * @param numMines The number of mines to place.
     */
    private void PlantMines(Model gameData, int numMines){
        ArrayList<Boolean> listToShuffle = new ArrayList<>();
        for (int i = 0; i < gameData.GetNumRows()*gameData.GetNumCols(); i++){
            boolean cellValue = i < numMines;
            listToShuffle.add(cellValue);
        }
        Collections.shuffle(listToShuffle);
        for (int curIndex = 0; curIndex < gameData.GetNumRows()*gameData.GetNumCols(); curIndex++){
            if (listToShuffle.get(curIndex) == true){
                int row = ConvertIndexToCoordinates(curIndex, numCols,numRows)[1];
                int column = ConvertIndexToCoordinates(curIndex, numCols,numRows)[0];
                gameData.AddMine(column,row);
            }
        }
    }

    /**
     * Converts a 1D index into 2D coordinates for a minefield of the given dimensions.
     * @param index The 1D index to convert.
     * @param numRows The number of rows in the 2D minefield.
     * @param numCols The number of columns in the 2D minefield.
     * @return An array containing the vertical and horizontal coordinates the 1D index corresponds to.
     */
    private int[] ConvertIndexToCoordinates(int index, int numCols, int numRows){
        int row = index / numRows;
        int column = index % numCols;
        return new int[] {column, row};
    }

    private int ConvertCoordinatesToIndex(int[] coordinates){
        return coordinates[1]*numCols + coordinates[0];
    }

    /**
     * Makes many cells reveal themselves if the player clicks on one that has no adjacent mines.
     * @param index the unique index of the Cell to examine.
     */
    private void caseZero(int index, List<Boolean> visits){
        //TODO refactor this hideous thing. These cases are not mutually exclusive.
        if(index == 0){
            sweepCell(mineCells.get(index+1), visits);
            sweepCell(mineCells.get(index+24), visits);
            sweepCell(mineCells.get(index+25), visits);
        }
        else if (index == 23){
            sweepCell(mineCells.get(index-1), visits);
            sweepCell(mineCells.get(index+23), visits);
            sweepCell(mineCells.get(index+24), visits);
        }
        else if(index < 24){
            sweepCell(mineCells.get(index-1), visits);
            sweepCell(mineCells.get(index+1), visits);
            sweepCell(mineCells.get(index+23), visits);
            sweepCell(mineCells.get(index+24), visits);
            sweepCell(mineCells.get(index+25), visits);
        }
        else if (index == numRows * numCols){
            sweepCell(mineCells.get(index-25), visits);
            sweepCell(mineCells.get(index-24), visits);
            sweepCell(mineCells.get(index-1), visits);
        }
        else if (index ==552){
            sweepCell(mineCells.get(index-24), visits);
            sweepCell(mineCells.get(index-23), visits);
            sweepCell(mineCells.get(index+1), visits);
        }
        else if(index >552){
            sweepCell(mineCells.get(index-25), visits);
            sweepCell(mineCells.get(index-24), visits);
            sweepCell(mineCells.get(index-23), visits);
            sweepCell(mineCells.get(index-1), visits);
            sweepCell(mineCells.get(index+1), visits);
        }
        else if(index%24 == 0){//on left edge
            sweepCell(mineCells.get(index-24), visits);
            sweepCell(mineCells.get(index-23), visits);
            sweepCell(mineCells.get(index+1), visits);
            sweepCell(mineCells.get(index+24), visits);
            sweepCell(mineCells.get(index+25), visits);
        }
        else if ((index+1)%23 == 0){//on right edge
            sweepCell(mineCells.get(index-25), visits);
            sweepCell(mineCells.get(index-24), visits);
            sweepCell(mineCells.get(index-1), visits);
            sweepCell(mineCells.get(index+23), visits);
            sweepCell(mineCells.get(index+24), visits);
        }
        else {
            sweepCell(mineCells.get(index-25), visits);
            sweepCell(mineCells.get(index-24), visits);
            sweepCell(mineCells.get(index-23), visits);
            sweepCell(mineCells.get(index-1), visits);
            sweepCell(mineCells.get(index+1), visits);
            sweepCell(mineCells.get(index+23), visits);
            sweepCell(mineCells.get(index+24), visits);
            sweepCell(mineCells.get(index+25), visits);
        }
    }

    /**
     * Starts sweep of cells.
     * @param theCell The Cell object to begin a sweep on.
     */
    private void startSweep(Cell theCell){
        List<Boolean> visits = new ArrayList<>();
        for(int i = 0; i < numRows * numCols; i++){
            visits.add(false);
        }
        sweepCell(theCell, visits);
    }

    /**
     * Makes the given Cell reveal its contents. This is called when the player clicks on a Cell.
     * @param theCell the Cell to reveal.
     */
    private void sweepCell(Cell theCell, List<Boolean> visits){
        int index = theCell.getIndex();
        int column = ConvertIndexToCoordinates(index, numCols,numRows)[0];
        int row = ConvertIndexToCoordinates(index, numCols,numRows)[1];
        int numAdjacent = gameData.GetNumAdjacent(column,row);
        if(visits.get(index)){
            return;
        }
        visits.set(index, true);
        if(theCell.isEnabled()){
            theCell.setEnabled(false);
            theCell.setBackground(theCell.getBackground().darker());
            if (gameData.IsMine(column,row)){
                GameOver();
            } else if (numAdjacent == 0) {
                caseZero(index, visits);
            } else if (numAdjacent == 1){
                theCell.setIcon(oneIcon);
            } else if (numAdjacent == 2){
                theCell.setIcon(twoIcon);
            } else if (numAdjacent == 3){
                theCell.setIcon(threeIcon);
            } else if (numAdjacent == 4){
                theCell.setIcon(fourIcon);
            } else if (numAdjacent == 5){
                theCell.setIcon(fiveIcon);
            } else if (numAdjacent == 6) {
                theCell.setIcon(sixIcon);
            } else if (numAdjacent == 7){
                theCell.setIcon(sevenIcon);
            } else if (numAdjacent == 8){
                theCell.setIcon(eightIcon);
            }
        }
    }

    /**
     * Starts a new game, resetting values. This is called when the player clicks on the start button.
     */
    private void newGame(){
        time = 0;
        timer.stop();
        for(int curIndex = 0; curIndex < numRows * numCols; curIndex++){
            gamePanel.remove(0);
            mineCells.remove(0);
            cellStates.set(curIndex,'b');
        }
        for(int i = 0; i < numRows * numCols; i++){
            Cell tmpCell = new Cell();
            tmpCell.setIndex(i);
            tmpCell.addMouseListener(this);
            mineCells.add(tmpCell);
            gamePanel.add(tmpCell);
        }
        InitializeField();
        update(getGraphics());
    }

    /**
     * Checks whether the player has won each time they press a cell.
     */
    private void flagCheck(){
        int minesRight = 0;
        for(int curIndex = 0; curIndex < numRows * numCols; curIndex++){
            int column = ConvertIndexToCoordinates(curIndex,numCols,numRows)[0];
            int row = ConvertIndexToCoordinates(curIndex,numCols,numRows)[1];
            if(gameData.IsMine(column,row) && cellStates.get(curIndex) == 'f'){
                minesRight++;
            }
        }
        if(minesRight == numMines){
            win = true;
            GameOver();
        }
    }
    //endregion


	/**
	 * Timer for keeping score.
	 */
	private Timer timer = new Timer(1000, this);



	/**
	 * Starts the game timer.
	 */
	public void startGame(){
		timer.start();
	}
}
