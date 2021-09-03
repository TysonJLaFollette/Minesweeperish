package Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class Model implements ActionListener {
    //region Properties
    ArrayList<ArrayList<Boolean>> mineLocations;
    ArrayList<ArrayList<Boolean>> flagLocations;
    ArrayList<ArrayList<Boolean>> questionLocations;
    ArrayList<ArrayList<Boolean>> sweptLocations;
    ArrayList<ArrayList<Integer>> adjacencyCounts;
    int numRows;
    int numCols;
    int numMines;
    int secondsElapsed = 0;
    private boolean gameIsOver = true;
    private boolean playerVictorious = false;
    private final Timer timer = new Timer(1000, this);
    //endregion

    public Model(){
        //If not given parameters, just start with defaults.
        this(24,24,100);
    }

    public Model(int numCols, int numRows, int numMines){
        this.numMines = numMines;
        this.numCols = numCols;
        this.numRows = numRows;
        this.mineLocations = new ArrayList<>();
        this.flagLocations = new ArrayList<>();
        this.questionLocations = new ArrayList<>();
        this.sweptLocations = new ArrayList<>();
        this.adjacencyCounts = new ArrayList<>();
        NewGame(numRows, numCols, numMines);
    }

    public int GetNumRows() {
        return numRows;
    }

    public int GetNumCols() { return numCols; }

    public int GetNumMines() {
        return numMines;
    }

    public void AddMine(int column, int row) {
        mineLocations.get(column).set(row,true);
        IncrementAdjacencies(column, row);
        numMines += 1;
    }

    public void AddFlag(int column, int row) {
        timer.start();
        flagLocations.get(column).set(row,true);
        flagCheck();
    }

    public void RemoveFlag(int column, int row) {
        timer.start();
        flagLocations.get(column).set(row,false);
        flagCheck();
    }

    public boolean IsFlag(int column, int row) {
        return flagLocations.get(column).get(row);
    }

    public void AddQuestionMark(int column, int row) {
        timer.start();
        questionLocations.get(column).set(row, true);
    }

    public void RemoveQuestionMark(int column, int row) {
        timer.start();
        questionLocations.get(column).set(row,false);
    }

    public boolean IsQuestion(int column, int row) {
        return questionLocations.get(column).get(row);
    }

    public boolean IsMine(int column, int row) {
        return mineLocations.get(column).get(row);
    }

    public int GetNumAdjacent(int column, int row) {
        return adjacencyCounts.get(column).get(row);
    }

    public boolean IsSwept(int column, int row) {
        return sweptLocations.get(column).get(row);
    }

    protected void SetSwept(int column, int row) {
        sweptLocations.get(column).set(row, true);
    }

    protected void CreateMinefield(int numCols, int numRows) {
        this.numCols = numCols;
        this.numRows = numRows;
        this.numMines = 0;
        mineLocations = new ArrayList<>();
        flagLocations = new ArrayList<>();
        sweptLocations = new ArrayList<>();
        questionLocations = new ArrayList<>();
        adjacencyCounts = new ArrayList<>();

        for(int curCol = 0; curCol < numCols; curCol++){
            mineLocations.add(new ArrayList<>());
            flagLocations.add(new ArrayList<>());
            sweptLocations.add(new ArrayList<>());
            questionLocations.add(new ArrayList<>());
            adjacencyCounts.add(new ArrayList<>());
            for(int curRow = 0; curRow < numRows; curRow++){
                mineLocations.get(curCol).add(false);
                flagLocations.get(curCol).add(false);
                sweptLocations.get(curCol).add(false);
                questionLocations.get(curCol).add(false);
                adjacencyCounts.get(curCol).add(0);
            }
        }
    }

    protected void IncrementAdjacencies(int column, int row) {
        if (column - 1 >= 0 && row - 1 >= 0){
            int curValue = adjacencyCounts.get(column - 1).get(row - 1);
            adjacencyCounts.get(column - 1).set(row - 1,curValue + 1);
        }
        if (row - 1 >= 0){
            int curValue = adjacencyCounts.get(column).get(row - 1);
            adjacencyCounts.get(column).set(row - 1,curValue + 1);
        }
        if (row - 1 >= 0 && column + 1 < numCols){
            int curValue = adjacencyCounts.get(column + 1).get(row - 1);
            adjacencyCounts.get(column + 1).set(row - 1,curValue + 1);
        }
        if (column - 1 >= 0){
            int curValue = adjacencyCounts.get(column - 1).get(row);
            adjacencyCounts.get(column - 1).set(row,curValue + 1);
        }
        if (column + 1 < numCols){
            int curValue = adjacencyCounts.get(column + 1).get(row);
            adjacencyCounts.get(column + 1).set(row,curValue + 1);
        }
        if (column - 1 >=0 && row + 1 < numRows){
            int curValue = adjacencyCounts.get(column - 1).get(row + 1);
            adjacencyCounts.get(column - 1).set(row + 1,curValue + 1);
        }
        if (row + 1 < numRows) {
            int curValue = adjacencyCounts.get(column).get(row + 1);
            adjacencyCounts.get(column).set(row + 1,curValue + 1);
        }
        if (row + 1 < numRows && column + 1 < numCols){
            int curValue = adjacencyCounts.get(column + 1).get(row + 1);
            adjacencyCounts.get(column + 1).set(row + 1,curValue + 1);
        }
    }

    /**
     * Creates a minefield with the desired number of mines.
     */
    public void NewGame(int numRows, int numCols, int numMines){
        CreateMinefield(numRows, numCols);
        PlantMines(numMines);
        gameIsOver = false;
        timer.stop();
        secondsElapsed = 0;
    }

    /**
     * Plants the desired number of mines randomly within the given minefield.
     * @param numMines The number of mines to place.
     */
    protected void PlantMines(int numMines){
        ArrayList<Boolean> listToShuffle = new ArrayList<>();
        for (int i = 0; i < GetNumRows()*GetNumCols(); i++){
            boolean cellValue = i < numMines;
            listToShuffle.add(cellValue);
        }
        Collections.shuffle(listToShuffle);
        for (int curIndex = 0; curIndex < GetNumRows()*GetNumCols(); curIndex++){
            if (listToShuffle.get(curIndex)){
                int row = ConvertIndexToCoordinates(curIndex, GetNumCols(),GetNumRows())[1];
                int column = ConvertIndexToCoordinates(curIndex, GetNumCols(),GetNumRows())[0];
                AddMine(column,row);
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

    /**
     * Checks whether the player has won each time they press a cell.
     */
    public void flagCheck(){
        int minesRight = 0;
        for (int curCol = 0; curCol < numCols; curCol++){
            for (int curRow = 0; curRow < numRows; curRow++){
                if (IsMine(curCol,curRow) && IsFlag(curCol,curRow)){
                    minesRight++;
                }
            }
        }
        if(minesRight == numMines){
            timer.stop();
            playerVictorious = true;
            gameIsOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == timer){
            secondsElapsed++;
        }
    }

    public int getSecondsElapsed(){
        return secondsElapsed;
    }

    public boolean isTheGameOver(){
        return gameIsOver;
    }

    public boolean didPlayerWin(){
        return playerVictorious;
    }

    public void startSweep(int column, int row){
        timer.start();
        if(IsFlag(column, row)){ return; }
        ArrayList<ArrayList<Boolean>> visits = new ArrayList<>();
        for (int curCol = 0; curCol < GetNumCols(); curCol++){
            visits.add(new ArrayList<>());
            for (int curRow = 0; curRow < GetNumRows(); curRow++){
                visits.get(curCol).add(false);
            }
        }
        sweepCell(column, row, visits);
    }

    private void sweepCell(int column, int row, ArrayList<ArrayList<Boolean>> visits) {
        int numAdjacent = GetNumAdjacent(column, row);
        if (visits.get(column).get(row)) {
            return;
        }
        visits.get(column).set(row, true);
        if (!IsSwept(column, row)) {
            if (IsMine(column, row)) {
                timer.stop();
                gameIsOver = true;
                playerVictorious = false;
            } else if (numAdjacent == 0) {
                SetSwept(column,row);
                caseZero(column, row, visits);
            } else {
                SetSwept(column, row);
            }
        }
    }

    private void caseZero(int column, int row, ArrayList<ArrayList<Boolean>> visits){
        boolean prevCol = column - 1 >= 0;
        boolean nextCol = column + 1 < GetNumCols();
        boolean prevRow = row - 1 >= 0;
        boolean nextRow = row + 1 < GetNumRows();

        if(prevCol && prevRow){
            sweepCell(column -1, row -1, visits);
        }
        if (prevRow){
            sweepCell(column, row - 1, visits);
        }
        if (nextCol && prevRow){
            sweepCell(column + 1, row -1, visits);
        }
        if (prevCol) {
            sweepCell(column -1, row, visits);
        }
        if (nextCol) {
            sweepCell(column + 1, row, visits);
        }
        if (prevCol && nextRow){
            sweepCell(column - 1, row + 1, visits);
        }
        if (nextRow){
            sweepCell(column, row + 1, visits);
        }
        if (nextCol && nextRow){
            sweepCell(column + 1, row + 1, visits);
        }
    }
}
