/**
 * This defines the minimum functionality a class must have to fulfill the role of a data model for minesweeper.
 */
public interface Model {
    public int GetNumRows();
    public int GetNumCols();
    public int GetNumMines();
    public void AddMine(int row, int column);
    public void AddFlag(int row, int column);
    public void RemoveFlag(int row, int column);
    public boolean IsFlag(int row, int column);
    public void AddQuestionMark(int row, int column);
    public void RemoveQuestionMark(int row, int column);
    public boolean IsQuestion(int row, int column);
    public boolean IsMine(int row, int column);
    public int GetNumAdjacent(int row, int column);
    public boolean IsSwept(int row, int column);
    public void SetSwept(int row, int column);
    public void CreateMinefield(int numRows, int numCols);
    public void IncrementAdjacencies(int row, int column);
}
