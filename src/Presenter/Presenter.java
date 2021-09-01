package Presenter;

import Model.ArrayListModel;
import data.View;

public class Presenter {
    //region Properties
    private ArrayListModel gameData;
    private View userInterface;
    private int numCols;
    private int numRows;
    private int numMines;
    //endregion

    //region Constructors
    public Presenter(int numCols, int numRows, int numMines){
        this.numCols = numCols;
        this.numRows = numRows;
        this.numMines = numMines;
        this.gameData = new ArrayListModel();
        gameData.CreateMinefield(numCols, numRows);
        gameData.PlantMines(numMines);
        this.userInterface = new View(this, numCols, numRows, numMines);
        gameData.CreateMinefield(numCols, numRows);
        gameData.PlantMines(numMines);
    }
    //endregion

    //region Public Methods
    public ArrayListModel GetModel(){
        return gameData;
    }



    /**
     * Checks whether the player has won each time they press a cell.
     */
    public void flagCheck(){
        int minesRight = 0;
        for (int curCol = 0; curCol < numCols; curCol++){
            for (int curRow = 0; curRow < numRows; curRow++){
                if (gameData.IsMine(curCol,curRow) && gameData.IsFlag(curCol,curRow)){
                    minesRight++;
                }
            }
        }
        if(minesRight == numMines){
            userInterface.win = true;
            userInterface.GameOver();
        }
    }

    public int getNumCols(){
        return gameData.GetNumCols();
    }

    public int getNumRows(){
        return gameData.GetNumRows();
    }

    public int getNumMines(){
        return gameData.GetNumMines();
    }
    //endregion

    //region Private Methods
    //endregion
}
