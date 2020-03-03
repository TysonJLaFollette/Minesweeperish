package Presenter;

import Model.Model;
import Model.ArrayListModel;
import data.View;

import java.util.ArrayList;
import java.util.Collections;

public class Presenter {
    //region Properties
    private Model gameData;
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
        this.userInterface = new View(this, numCols, numRows, numMines);
        gameData.CreateMinefield(numCols, numRows);
        PlantMines(gameData,numMines);
    }
    //endregion

    //region Public Methods
    public Model GetModel(){
        return gameData;
    }

    /**
     * Creates a minefield with the desired number of mines.
     */
    public void InitializeField(){
        gameData.CreateMinefield(numRows, numCols);
        PlantMines(gameData,numMines);
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
    //endregion

    //region Private Methods
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
            if (listToShuffle.get(curIndex)){
                int row = ConvertIndexToCoordinates(curIndex, gameData.GetNumCols(),gameData.GetNumRows())[1];
                int column = ConvertIndexToCoordinates(curIndex, gameData.GetNumCols(),gameData.GetNumRows())[0];
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
        return coordinates[1]*gameData.GetNumCols() + coordinates[0];
    }
    //endregion
}
