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
    private int viewNumRows;
    private int viewNumCols;
    private int viewNumMines;
	private int minesMarked;
    public boolean win;
	private int time = 0;
	//TODO mineCells2D was built to allow interacting with the buttons using coordinates instead of an index.
    //TODO this data structure should be replaced with the new presenter.ConvertIndexToCoords()
    private ArrayList<ArrayList<Cell>> mineCells2D;
	private ScorePanel scorePanel;
	private JPanel gamePanel;
	private Model gameData;
	private Presenter presenter;
    private Timer timer = new Timer(1000, this);
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
    //endregion

    //region Constructors
    public View(Presenter presenter, int viewNumRows, int viewNumCols, int viewNumMines){
        this.presenter = presenter;
        this.viewNumRows = viewNumRows;
        this.viewNumCols = viewNumCols;
        this.viewNumMines = viewNumMines;
        win = false;
        mineCells2D = new ArrayList<>();
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
        gamePanel.setLayout(new GridLayout(viewNumRows, viewNumCols));
        scorePanel.setMines(viewNumMines);
        for (int curCol = 0; curCol < viewNumCols; curCol++){
            mineCells2D.add(new ArrayList<>());
            for (int curRow = 0; curRow < viewNumRows; curRow++){
                Cell tmpCell = new Cell();
                tmpCell.setColumn(curCol);
                tmpCell.setRow(curRow);
                tmpCell.addMouseListener(this);
                mineCells2D.get(curCol).add(tmpCell);
                gamePanel.add(tmpCell);

            }
        }
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Displays final messages, shows mine locations.
     */
    public void GameOver(){
        timer.stop();
        for (int curCol = 0; curCol < viewNumCols; curCol++){
            for(int curRow = 0; curRow < viewNumRows; curRow++){
                mineCells2D.get(curCol).get(curRow).setEnabled(false);
                if (gameData.IsMine(curCol,curRow)){
                    if (gameData.IsFlag(curCol,curRow)){
                        mineCells2D.get(curCol).get(curRow).setBackground(Color.GREEN);
                        mineCells2D.get(curCol).get(curRow).setIcon(mineIcon);
                    } else if (gameData.IsQuestion(curCol,curRow)){
                        mineCells2D.get(curCol).get(curRow).setBackground(Color.YELLOW);
                        mineCells2D.get(curCol).get(curRow).setIcon(mineIcon);
                    } else {
                        mineCells2D.get(curCol).get(curRow).setBackground(Color.RED);
                        mineCells2D.get(curCol).get(curRow).setIcon(mineIcon);
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
        return coordinates[1]* viewNumCols + coordinates[0];
    }

    /**
     * Makes many cells reveal themselves if the player clicks on one that has no adjacent mines.
     */
    private void caseZero(Cell theCell, List<Boolean> visits){
        int column = theCell.getColumn();
        int row = theCell.getRow();
        boolean prevCol = column - 1 > 0;
        boolean nextCol = column + 1 < viewNumCols;
        boolean prevRow = row - 1 > 0;
        boolean nextRow = row + 1 < viewNumRows;

        if(prevCol && prevRow){
            sweepCell(mineCells2D.get(column-1).get(row-1), visits);
        }
        if (prevRow){
            sweepCell(mineCells2D.get(column).get(row-1),visits);
        }
        if (nextCol && prevRow){
            sweepCell(mineCells2D.get(column+1).get(row -1),visits);
        }
        if (prevCol) {
            sweepCell(mineCells2D.get(column-1).get(row),visits);
        }
        if (nextCol) {
            sweepCell(mineCells2D.get(column + 1).get(row),visits);
        }
        if (prevCol && nextRow){
            sweepCell(mineCells2D.get(column - 1).get(row + 1),visits);
        }
        if (nextRow){
            sweepCell(mineCells2D.get(column).get(row + 1),visits);
        }
        if (nextCol && nextRow){
            sweepCell(mineCells2D.get(column + 1).get(row + 1),visits);
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
        for(int i = 0; i < viewNumRows * viewNumCols; i++){
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
        for(int curIndex = 0; curIndex < viewNumRows * viewNumCols; curIndex++){
            int column = ConvertIndexToCoordinates(curIndex, viewNumCols, viewNumRows)[0];
            int row = ConvertIndexToCoordinates(curIndex, viewNumCols, viewNumRows)[1];
            gamePanel.remove(0);
            gameData.RemoveFlag(column,row);
            gameData.RemoveQuestionMark(column,row);
        }

        for (int curCol = 0; curCol < viewNumCols; curCol++){
            mineCells2D.remove(0);
        }

        for (int curCol = 0; curCol < viewNumCols; curCol++){
            mineCells2D.add(new ArrayList<>());
        }

        for(int i = 0; i < viewNumRows * viewNumCols; i++){
            int column = ConvertIndexToCoordinates(i, viewNumCols, viewNumRows)[0];
            int row = ConvertIndexToCoordinates(i, viewNumCols, viewNumRows)[1];
            Cell tmpCell = new Cell();
            tmpCell.setColumn(column);
            tmpCell.setRow(row);
            tmpCell.addMouseListener(this);
            mineCells2D.get(column).add(tmpCell);
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
            scorePanel.setMines(viewNumMines -minesMarked);
        }
        else if(gameData.IsFlag(column,row)){
            gameData.AddQuestionMark(column,row);
            gameData.RemoveFlag(column,row);
            clickedCell.setImage(questionIcon);
            minesMarked--;
            scorePanel.setMines(viewNumMines -minesMarked);
        }
        else if(gameData.IsQuestion(column,row)){
            gameData.RemoveQuestionMark(column,row);
            clickedCell.setImage(null);
        }
    }
    //endregion
}
