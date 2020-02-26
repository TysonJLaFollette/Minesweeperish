package Model;

import java.util.ArrayList;

public class ArrayListModel implements Model {
    //region Properties
    ArrayList<ArrayList<Boolean>> mineLocations;
    ArrayList<ArrayList<Boolean>> flagLocations;
    ArrayList<ArrayList<Boolean>> questionLocations;
    ArrayList<ArrayList<Boolean>> sweptLocations;
    ArrayList<ArrayList<Integer>> adjacencyCounts;
    int numRows;
    int numCols;
    int numMines;
    //endregion

    public ArrayListModel(){
        this.numMines = 0;
        this.numCols = 0;
        this.numRows = 0;
        this.mineLocations = new ArrayList<>();
        this.flagLocations = new ArrayList<>();
        this.questionLocations = new ArrayList<>();
        this.sweptLocations = new ArrayList<>();
        this.adjacencyCounts = new ArrayList<>();
    }

    @Override
    public int GetNumRows() {
        return numRows;
    }

    @Override
    public int GetNumCols() { return numCols; }

    @Override
    public int GetNumMines() {
        return numMines;
    }

    @Override
    public void AddMine(int column, int row) {
        mineLocations.get(column).set(row,true);
        IncrementAdjacencies(column, row);
        numMines += 1;
    }

    @Override
    public void AddFlag(int column, int row) {
        flagLocations.get(column).set(row,true);
    }

    @Override
    public void RemoveFlag(int column, int row) {
        flagLocations.get(column).set(row,false);
    }

    @Override
    public boolean IsFlag(int column, int row) {
        return flagLocations.get(column).get(row);
    }

    @Override
    public void AddQuestionMark(int column, int row) {
        questionLocations.get(column).set(row, true);
    }

    @Override
    public void RemoveQuestionMark(int column, int row) {
        questionLocations.get(column).set(row,false);
    }

    @Override
    public boolean IsQuestion(int column, int row) {
        return questionLocations.get(column).get(row);
    }

    @Override
    public boolean IsMine(int column, int row) {
        return mineLocations.get(column).get(row);
    }

    @Override
    public int GetNumAdjacent(int column, int row) {
        return adjacencyCounts.get(column).get(row);
    }

    @Override
    public boolean IsSwept(int column, int row) {
        return sweptLocations.get(column).get(row);
    }

    @Override
    public void SetSwept(int column, int row) {
        sweptLocations.get(column).set(row, true);
    }

    @Override
    public void CreateMinefield(int numCols, int numRows) {
        this.numCols = numCols;
        this.numRows = numRows;
        this.numMines = 0;
        mineLocations = new ArrayList<ArrayList<Boolean>>();
        flagLocations = new ArrayList<ArrayList<Boolean>>();
        sweptLocations = new ArrayList<ArrayList<Boolean>>();
        questionLocations = new ArrayList<>();
        adjacencyCounts = new ArrayList<ArrayList<Integer>>();

        for(int curCol = 0; curCol < numCols; curCol++){
            mineLocations.add(new ArrayList<Boolean>());
            flagLocations.add(new ArrayList<Boolean>());
            sweptLocations.add(new ArrayList<Boolean>());
            questionLocations.add(new ArrayList<>());
            adjacencyCounts.add(new ArrayList<Integer>());
            for(int curRow = 0; curRow < numRows; curRow++){
                mineLocations.get(curCol).add(false);
                flagLocations.get(curCol).add(false);
                sweptLocations.get(curCol).add(false);
                questionLocations.get(curCol).add(false);
                adjacencyCounts.get(curCol).add(0);
            }
        }
    }

    @Override
    public void IncrementAdjacencies(int column, int row) {
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
}
