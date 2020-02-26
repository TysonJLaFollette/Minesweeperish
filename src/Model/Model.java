package Model;

/**
 * This defines the minimum functionality a class must have to fulfill the role of a data model for minesweeper.
 */
public interface Model {
    /**
     * Fetches the number of rows in the minefield.
     * @return The number of rows.
     */
    public int GetNumRows();

    /**
     * Fetches the number of columns in the minefield.
     * @return The number of columns.
     */
    public int GetNumCols();

    /**
     * Fetches the number of mines hidden in the minefield.
     * @return The number of mines.
     */
    public int GetNumMines();

    /**
     * Adds a mine at the given coordinates.
     * @param column The horizontal coordinate.
     * @param row The vertical coordinate.
     */
    public void AddMine(int column, int row);

    /**
     * Adds a flag at the given coordinates.
     * @param column The horizontal coordinate.
     * @param row The vertical coordinate.
     */
    public void AddFlag(int column, int row);

    /**
     * Removes a flag from the given coordinates.
     * @param column The horizontal coordinate.
     * @param row The vertical coordinate.
     */
    public void RemoveFlag(int column, int row);

    /**
     * Determines whether there is a flag the the given coordinates.
     * @param column The horizontal coordinate.
     * @param row The vertical coordinate.
     * @return Whether or not there is a flag at the given coordinates.
     */
    public boolean IsFlag(int column, int row);

    /**
     * Places a question mark at the given coordinates.
     * @param column The horizontal coordinate.
     * @param row The vertical coordinate.
     */
    public void AddQuestionMark(int column, int row);

    /**
     * Removes a question mark from the given coordinates.
     * @param column The horizontal coordinate.
     * @param row The vertical coordinate.
     */
    public void RemoveQuestionMark(int column, int row);

    /**
     * Checks whether there is a question mark at the given coordinates.
     * @param column The horizontal coordinate.
     * @param row The vertical coordinate.
     * @return Whether or not there is a question mark at the given coordinates.
     */
    public boolean IsQuestion(int column, int row);

    /**
     * Checks whether there is a mine at the given coordinates.
     * @param column The horizontal coordinate.
     * @param row The vertical coordinate.
     * @return Whether or not there is a mine at the given coordinates.
     */
    public boolean IsMine(int column, int row);

    /**
     * Determines the number of mines adjacent to the given coordinates, excluding any mines at the exact coordinates.
     * @param column The horizontal coordinate.
     * @param row The vertical coordinate.
     * @return The number of mines adjacent to the given coordinates.
     */
    public int GetNumAdjacent(int column, int row);

    /**
     * Checks whether the given coordinates have been swept by the player.
     * @param column The horizontal coordinates.
     * @param row The vertical coordinates.
     * @return Whether the given coordinates have been swept by the player.
     */
    public boolean IsSwept(int column, int row);

    /**
     * Marks the given coordinates as having been swept.
     * @param column The horizontal coordinate.
     * @param row The vertical coordinate.
     */
    public void SetSwept(int column, int row);

    /**
     * Creates a new, empty minefield of the given dimensions.
     * @param numCols The number of columns in the new minefield.
     * @param numRows The number of rows in the new minefield.
     */
    public void CreateMinefield(int numCols, int numRows);

    /**
     * Increments the adjacency count of all cells adjacent to the given coordinates.
     * @param column The horizontal coordinate at center of effect.
     * @param row The vertical coordinate at center of effect.
     */
    public void IncrementAdjacencies(int column, int row);
}
