package data;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Model.Model;
import view.Cell;
import view.ScorePanel;

//TODO Separate business logic and display logic. Since display logic cannot be extracted individually, maybe extract the business logic instead?
public class View extends JFrame implements MouseListener, ActionListener{
    //region Properties
	private int minesMarked;
    public boolean win;
	private int time = 0;
	private ScorePanel scorePanel;
	private JPanel gamePanel;
	private final Model gameData;
    private final Timer timer = new Timer(1000, this);
    private final ImageIcon mineIcon = new ImageIcon("data/mine.gif");
    private final ImageIcon flagIcon = new ImageIcon("data/flag.png");
    //endregion

    //region Constructors
    public View(Model gameData){
        this.gameData = gameData;
        win = false;
        InitGraphics();
    }
    //endregion

    //region Public Methods
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
        gameData.flagCheck();
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void actionPerformed(ActionEvent arg0) {
        Object source = arg0.getSource();
        if(source!= timer){
            newGame();
        }
        if(source == timer){
            //TODO make the presenter keep track of time, and the view only display it.
            time++;
            scorePanel.setTime(time);
        }

    }
    //endregion

    //region Private Methods
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
        gamePanel.setLayout(new GridLayout(gameData.GetNumRows(), gameData.GetNumCols()));
        scorePanel.setMines(gameData.GetNumMines());
        for (int curCell = 0; curCell < gameData.GetNumCols() * gameData.GetNumRows(); curCell++){
            int[] coords = ConvertIndexToCoordinates(curCell, gameData.GetNumCols(), gameData.GetNumRows());
            Cell tmpCell = new Cell();
            tmpCell.setColumn(coords[0]);
            tmpCell.setRow(coords[1]);
            tmpCell.addMouseListener(this);
            gamePanel.add(tmpCell);
        }
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void GameOver(){
        timer.stop();
        for (int curCol = 0; curCol < gameData.GetNumCols(); curCol++){
            for(int curRow = 0; curRow < gameData.GetNumRows(); curRow++){
                Cell curCell = (Cell)gamePanel.getComponent(ConvertCoordinatesToIndex(new int[]{curCol,curRow}));
                curCell.setEnabled(false);
                if (gameData.IsMine(curCol,curRow)){
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

    private int[] ConvertIndexToCoordinates(int index, int numCols, int numRows){
        int row = index / numRows;
        int column = index % numCols;
        return new int[] {column, row};
    }

    private int ConvertCoordinatesToIndex(int[] coordinates){
        return coordinates[1] * gameData.GetNumCols() + coordinates[0];
    }

    private void caseZero(Cell theCell, ArrayList<ArrayList<Boolean>> visits){
        int column = theCell.getColumn();
        int row = theCell.getRow();
        boolean prevCol = column - 1 >= 0;
        boolean nextCol = column + 1 < gameData.GetNumCols();
        boolean prevRow = row - 1 >= 0;
        boolean nextRow = row + 1 < gameData.GetNumRows();

        if(prevCol && prevRow){
            int cellIndex = ConvertCoordinatesToIndex(new int[] {column-1,row-1});
            Cell cellToSweep = (Cell)gamePanel.getComponent(cellIndex);
            sweepCell(cellToSweep, visits);
        }
        if (prevRow){
            int cellIndex = ConvertCoordinatesToIndex(new int[] {column,row-1});
            Cell cellToSweep = (Cell)gamePanel.getComponent(cellIndex);
            sweepCell(cellToSweep, visits);
        }
        if (nextCol && prevRow){
            int cellIndex = ConvertCoordinatesToIndex(new int[] {column+1,row-1});
            Cell cellToSweep = (Cell)gamePanel.getComponent(cellIndex);
            sweepCell(cellToSweep, visits);
        }
        if (prevCol) {
            int cellIndex = ConvertCoordinatesToIndex(new int[] {column-1,row});
            Cell cellToSweep = (Cell)gamePanel.getComponent(cellIndex);
            sweepCell(cellToSweep, visits);
        }
        if (nextCol) {
            int cellIndex = ConvertCoordinatesToIndex(new int[] {column+1,row});
            Cell cellToSweep = (Cell)gamePanel.getComponent(cellIndex);
            sweepCell(cellToSweep, visits);
        }
        if (prevCol && nextRow){
            int cellIndex = ConvertCoordinatesToIndex(new int[] {column-1,row+1});
            Cell cellToSweep = (Cell)gamePanel.getComponent(cellIndex);
            sweepCell(cellToSweep, visits);
        }
        if (nextRow){
            int cellIndex = ConvertCoordinatesToIndex(new int[] {column,row+1});
            Cell cellToSweep = (Cell)gamePanel.getComponent(cellIndex);
            sweepCell(cellToSweep, visits);
        }
        if (nextCol && nextRow){
            int cellIndex = ConvertCoordinatesToIndex(new int[] {column+1,row+1});
            Cell cellToSweep = (Cell)gamePanel.getComponent(cellIndex);
            sweepCell(cellToSweep, visits);
        }
    }

    private void startSweep(Cell clickedCell){
        timer.start();
        int column = clickedCell.getColumn();
        int row = clickedCell.getRow();
        if(gameData.IsFlag(column, row)){ return; }
        ArrayList<ArrayList<Boolean>> visits = new ArrayList<>();
        for (int curCol = 0; curCol < gameData.GetNumCols(); curCol++){
            visits.add(new ArrayList<>());
            for (int curRow = 0; curRow < gameData.GetNumRows(); curRow++){
                visits.get(curCol).add(false);
            }
        }
        sweepCell(clickedCell, visits);
    }

    private void sweepCell(Cell theCell, ArrayList<ArrayList<Boolean>> visits){
        //TODO make the presenter handle sweeps and cascades.
        int column = theCell.getColumn();
        int row = theCell.getRow();
        int numAdjacent = gameData.GetNumAdjacent(column,row);
        if(visits.get(column).get(row)){
            return;
        }
        visits.get(column).set(row, true);
        if(theCell.isEnabled()){
            theCell.setEnabled(false);
            theCell.setBackground(theCell.getBackground().darker());
            if (gameData.IsMine(column,row)){
                GameOver();
            } else if (numAdjacent == 0) {
                caseZero(theCell, visits);
            } else {
                theCell.setText(Integer.toString(numAdjacent));
            }
        }
    }

    private void newGame(){
        time = 0;
        timer.stop();
        for(int curIndex = 0; curIndex < gameData.GetNumRows() * gameData.GetNumCols(); curIndex++){
            int column = ConvertIndexToCoordinates(curIndex, gameData.GetNumCols(), gameData.GetNumRows())[0];
            int row = ConvertIndexToCoordinates(curIndex, gameData.GetNumCols(), gameData.GetNumRows())[1];
            gamePanel.remove(0);
            gameData.RemoveFlag(column,row);
            gameData.RemoveQuestionMark(column,row);
        }

        for(int i = 0; i < gameData.GetNumRows() * gameData.GetNumCols(); i++){
            int column = ConvertIndexToCoordinates(i, gameData.GetNumCols(), gameData.GetNumRows())[0];
            int row = ConvertIndexToCoordinates(i, gameData.GetNumCols(), gameData.GetNumRows())[1];
            Cell tmpCell = new Cell();
            tmpCell.setColumn(column);
            tmpCell.setRow(row);
            tmpCell.addMouseListener(this);
            gamePanel.add(tmpCell);
        }

        gameData.InitializeField(24,24, 100);
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
            scorePanel.setMines(gameData.GetNumMines() - minesMarked);
        }
        else if(gameData.IsFlag(column,row)){
            gameData.AddQuestionMark(column,row);
            gameData.RemoveFlag(column,row);
            clickedCell.setImage(null);
            clickedCell.setText("?");
            minesMarked--;
            scorePanel.setMines(gameData.GetNumMines() - minesMarked);
        }
        else if(gameData.IsQuestion(column,row)){
            gameData.RemoveQuestionMark(column,row);
            clickedCell.setImage(null);
            clickedCell.setText("");
        }
    }
    //endregion
}
