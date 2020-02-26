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
     * @param row The vertical coordinate.
     * @param column The horizontal coordinate.
     */
    public void AddMine(int row, int column);

    /**
     * Adds a flag at the given coordinates.
     * @param row The vertical coordinate.
     * @param column The horizontal coordinate.
     */
    public void AddFlag(int row, int column);

    /**
     * Removes a flag from the given coordinates.
     * @param row The vertical coordinate.
     * @param column The horizontal coordinate.
     */
    public void RemoveFlag(int row, int column);

    /**
     * Determines whether there is a flag the the given coordinates.
     * @param row The vertical coordinate.
     * @param column The horizontal coordinate.
     * @return Whether or not there is a flag at the given coordinates.
     */
    public boolean IsFlag(int row, int column);

    /**
     * Places a question mark at the given coordinates.
     * @param row The vertical coordinate.
     * @param column The horizontal coordinate.
     */
    public void AddQuestionMark(int row, int column);

    /**
     * Removes a question mark from the given coordinates.
     * @param row The vertical coordinate.
     * @param column The horizontal coordinate.
     */
    public void RemoveQuestionMark(int row, int column);

    /**
     * Checks whether there is a question mark at the given coordinates.
     * @param row The vertical coordinate.
     * @param column The horizontal coordinate.
     * @return Whether or not there is a question mark at the given coordinates.
     */
    public boolean IsQuestion(int row, int column);

    /**
     * Checks whether there is a mine at the given coordinates.
     * @param row The vertical coordinate.
     * @param column The horizontal coordinate.
     * @return Whether or not there is a mine at the given coordinates.
     */
    public boolean IsMine(int row, int column);

    /**
     * Determines the number of mines adjacent to the given coordinates, excluding any mines at the exact coordinates.
     * @param row The vertical coordinate.
     * @param column The horizontal coordinate.
     * @return The number of mines adjacent to the given coordinates.
     */
    public int GetNumAdjacent(int row, int column);

    /**
     * Checks whether the given coordinates have been swept by the player.
     * @param row The vertical coordinates.
     * @param column The horizontal coordinates.
     * @return Whether the given coordinates have been swept by the player.
     */
    public boolean IsSwept(int row, int column);

    /**
     * Marks the given coordinates as having been swept.
     * @param row The vertical coordinate.
     * @param column The horizontal coordinate.
     */
    public void SetSwept(int row, int column);

    /**
     * Creates a new, empty minefield of the given dimensions.
     * @param numRows The number of rows in the new minefield.
     * @param numCols The number of columns in the new minefield.
     */
    public void CreateMinefield(int numRows, int numCols);

    /**
     * Increments the adjacency count of all cells adjacent to the given coordinates.
     * @param row The vertical coordinate at center of effect.
     * @param column The horizontal coordinate at center of effect.
     */
    public void IncrementAdjacencies(int row, int column);
}
