package data;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Model.Model;
import Presenter.Presenter;
import view.Cell;
import view.ScorePanel;

/**
 * The MainGame class is only instantiated once. This MainGame object runs and controls everything.
 * It is a JFrame, and listens for the mouse and actions. It is the game window.
 * @author Tyson J LaFollette
 */
//TODO Separate business logic and display logic. Since display logic cannot be extracted individually, maybe extract the business logic instead?
public class View extends JFrame implements MouseListener, ActionListener{
    //region Properties
	private int minesMarked;
    public boolean win;
	private int time = 0;
	private ScorePanel scorePanel;
	private JPanel gamePanel;
	private Model gameData;
	private Presenter presenter;
    private Timer timer = new Timer(1000, this);
    private ImageIcon mineIcon = new ImageIcon("data/mine.gif");
    private ImageIcon flagIcon = new ImageIcon("data/flag.png");
    private ImageIcon questionIcon = new ImageIcon("data/question.png");
    //endregion

    //region Constructors
    public View(Presenter presenter, int viewNumRows, int viewNumCols, int viewNumMines){
        this.presenter = presenter;
        win = false;
        this.gameData = presenter.GetModel();
        InitGraphics();
    }
    //endregion

    //region Public Methods
    /**
     * Handles mouse events.
     * @param arg0 The name of the MouseEvent being handled...?
     */
    @Override
    public void mouseClicked(MouseEvent arg0) {
        Object source = arg0.getSource();
        if (! (source instanceof Cell)){ return; }
        Cell clickedCell = (Cell)source;
        if (!clickedCell.isEnabled()) { return; }
        if(SwingUtilities.isLeftMouseButton(arg0)){
            startSweep(clickedCell);
        } else if(SwingUtilities.isRightMouseButton(arg0)){
            CycleFlags(clickedCell);
        }
        presenter.flagCheck();
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

    //region Private Methods
    /**
     * Initializes view components.
     */
    private void InitGraphics(){
        scorePanel = new ScorePanel(this);
        gamePanel = new JPanel();
        this.setIconImage(mineIcon.getImage());
        this.setTitle("Minesweeperish");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel pane = (JPanel)this.getContentPane();
        this.setSize(500, 500);
        pane.setLayout(new BorderLayout());
        pane.add(scorePanel, BorderLayout.NORTH);
        pane.add(gamePanel);
        gamePanel.setLayout(new GridLayout(presenter.getNumRows(), presenter.getNumCols()));
        scorePanel.setMines(presenter.getNumMines());
        for (int curCell = 0; curCell < presenter.getNumCols() * presenter.getNumRows(); curCell++){
            int[] coords = ConvertIndexToCoordinates(curCell, presenter.getNumCols(), presenter.getNumRows());
            Cell tmpCell = new Cell();
            tmpCell.setColumn(coords[0]);
            tmpCell.setRow(coords[1]);
            tmpCell.addMouseListener(this);
            gamePanel.add(tmpCell);
        }
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Displays final messages, shows mine locations.
     */
    public void GameOver(){
        timer.stop();
        for (int curCol = 0; curCol < presenter.getNumCols(); curCol++){
            for(int curRow = 0; curRow < presenter.getNumRows(); curRow++){
                gamePanel.getComponent(ConvertCoordinatesToIndex(new int[] {curCol, curRow})).setEnabled(false);
                if (gameData.IsMine(curCol,curRow)){
                    Cell curCell = (Cell)gamePanel.getComponent(ConvertCoordinatesToIndex(new int[]{curCol,curRow}));
                    if (gameData.IsFlag(curCol,curRow)){
                        curCell.setBackground(Color.GREEN);
                        curCell.setIcon(mineIcon);
                    } else if (gameData.IsQuestion(curCol,curRow)){
                        curCell.setBackground(Color.YELLOW);
                        curCell.setIcon(mineIcon);
                    } else {
                        curCell.setBackground(Color.RED);
                        curCell.setIcon(mineIcon);
                    }
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
        return coordinates[1] * presenter.getNumCols() + coordinates[0];
    }

    /**
     * Makes many cells reveal themselves if the player clicks on one that has no adjacent mines.
     */
    private void caseZero(Cell theCell, List<Boolean> visits){
        int column = theCell.getColumn();
        int row = theCell.getRow();
        boolean prevCol = column - 1 >= 0;
        boolean nextCol = column + 1 < presenter.getNumCols();
        boolean prevRow = row - 1 >= 0;
        boolean nextRow = row + 1 < presenter.getNumRows();

        if(prevCol && prevRow){
            sweepCell((Cell)gamePanel.getComponent(ConvertCoordinatesToIndex(new int[] {column-1,row-1})), visits);
        }
        if (prevRow){
            sweepCell((Cell)gamePanel.getComponent(ConvertCoordinatesToIndex(new int[] {column,row-1})), visits);
        }
        if (nextCol && prevRow){
            sweepCell((Cell)gamePanel.getComponent(ConvertCoordinatesToIndex(new int[] {column+1,row-1})), visits);
        }
        if (prevCol) {
            sweepCell((Cell)gamePanel.getComponent(ConvertCoordinatesToIndex(new int[] {column-1,row})), visits);
        }
        if (nextCol) {
            sweepCell((Cell)gamePanel.getComponent(ConvertCoordinatesToIndex(new int[] {column+1,row})), visits);
        }
        if (prevCol && nextRow){
            sweepCell((Cell)gamePanel.getComponent(ConvertCoordinatesToIndex(new int[] {column-1,row+1})), visits);
        }
        if (nextRow){
            sweepCell((Cell)gamePanel.getComponent(ConvertCoordinatesToIndex(new int[] {column,row+1})), visits);
        }
        if (nextCol && nextRow){
            sweepCell((Cell)gamePanel.getComponent(ConvertCoordinatesToIndex(new int[] {column+1,row+1})), visits);
        }
    }

    /**
     * Starts sweep of cells.
     * @param clickedCell The Cell object to begin a sweep on.
     */
    private void startSweep(Cell clickedCell){
        timer.start();
        int column = clickedCell.getColumn();
        int row = clickedCell.getRow();
        if(gameData.IsFlag(column, row)){ return; }
        List<Boolean> visits = new ArrayList<>();
        //TODO this is where visits is created. Make it 2D.
        for(int i = 0; i < presenter.getNumRows() * presenter.getNumCols(); i++){
            visits.add(false);
        }
        sweepCell(clickedCell, visits);
    }

    /**
     * Makes the given Cell reveal its contents. This is called when the player clicks on a Cell.
     * @param theCell the Cell to reveal.
     */
    private void sweepCell(Cell theCell, List<Boolean> visits){
        int column = theCell.getColumn();
        int row = theCell.getRow();
        int index = ConvertCoordinatesToIndex(new int[]{column,row});
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
                caseZero(theCell, visits);
            } else if (numAdjacent == 1){
                theCell.setText("1");
            } else if (numAdjacent == 2){
                theCell.setText("2");
            } else if (numAdjacent == 3){
                theCell.setText("3");
            } else if (numAdjacent == 4){
                theCell.setText("4");
            } else if (numAdjacent == 5){
                theCell.setText("5");
            } else if (numAdjacent == 6) {
                theCell.setText("6");
            } else if (numAdjacent == 7){
                theCell.setText("7");
            } else if (numAdjacent == 8){
                theCell.setText("8");
            }
        }
    }

    /**
     * Starts a new game, resetting values. This is called when the player clicks on the start button.
     */
    private void newGame(){
        time = 0;
        timer.stop();
        for(int curIndex = 0; curIndex < presenter.getNumRows() * presenter.getNumCols(); curIndex++){
            int column = ConvertIndexToCoordinates(curIndex, presenter.getNumCols(), presenter.getNumRows())[0];
            int row = ConvertIndexToCoordinates(curIndex, presenter.getNumCols(), presenter.getNumRows())[1];
            gamePanel.remove(0);
            gameData.RemoveFlag(column,row);
            gameData.RemoveQuestionMark(column,row);
        }

        for(int i = 0; i < presenter.getNumRows() * presenter.getNumCols(); i++){
            int column = ConvertIndexToCoordinates(i, presenter.getNumCols(), presenter.getNumRows())[0];
            int row = ConvertIndexToCoordinates(i, presenter.getNumCols(), presenter.getNumRows())[1];
            Cell tmpCell = new Cell();
            tmpCell.setColumn(column);
            tmpCell.setRow(row);
            tmpCell.addMouseListener(this);
            gamePanel.add(tmpCell);
        }

        presenter.InitializeField();
        gamePanel.revalidate();
        update(getGraphics());
    }

    private void CycleFlags(Cell clickedCell){
        int column = clickedCell.getColumn();
        int row = clickedCell.getRow();

        if(!gameData.IsFlag(column,row) && !gameData.IsQuestion(column,row)){
            gameData.AddFlag(column,row);
            clickedCell.setImage(flagIcon);
            minesMarked++;
            scorePanel.setMines(presenter.getNumMines() - minesMarked);
        }
        else if(gameData.IsFlag(column,row)){
            gameData.AddQuestionMark(column,row);
            gameData.RemoveFlag(column,row);
            clickedCell.setImage(questionIcon);
            minesMarked--;
            scorePanel.setMines(presenter.getNumMines() - minesMarked);
        }
        else if(gameData.IsQuestion(column,row)){
            gameData.RemoveQuestionMark(column,row);
            clickedCell.setImage(null);
        }
    }
    //endregion
}
