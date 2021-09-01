package Model;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class ArrayListModelTest {
    @org.junit.jupiter.api.Test
    void createMinefield() {
        ArrayListModel gameData = new ArrayListModel();
        gameData.CreateMinefield(24,24);
        assertEquals(24, gameData.GetNumRows());
        assertEquals(24,gameData.GetNumCols());
    }

    @org.junit.jupiter.api.Test
    void getNumRows() {
        ArrayListModel gameData = new ArrayListModel();
        gameData.CreateMinefield(24,24);
        assertEquals(24,gameData.GetNumRows());
    }

    @org.junit.jupiter.api.Test
    void getNumCols() {
        ArrayListModel gameData = new ArrayListModel();
        gameData.CreateMinefield(24,24);
        assertEquals(24,gameData.GetNumCols());
    }

    @org.junit.jupiter.api.Test
    void addMine() {
        ArrayListModel gameData = new ArrayListModel();
        gameData.CreateMinefield(24,24);
        gameData.AddMine(0,0);
        gameData.AddMine(23,23);
        assertTrue(gameData.IsMine(0, 0));
        assertTrue(gameData.IsMine(23, 23));
    }

    @org.junit.jupiter.api.Test
    void isMine() {
        ArrayListModel gameData = new ArrayListModel();
        gameData.CreateMinefield(24,24);
        gameData.AddMine(1,1);
        assertFalse(gameData.IsMine(0, 0));
        assertTrue(gameData.IsMine(1, 1));
    }

    @org.junit.jupiter.api.Test
    void getNumMines() {
        ArrayListModel gameData = new ArrayListModel();
        gameData.CreateMinefield(24,24);
        assertEquals(0,gameData.GetNumMines());
        gameData.AddMine(0,0);
        gameData.AddMine(0,1);
        gameData.AddMine(0,2);
        assertEquals(3,gameData.GetNumMines());
    }

    @org.junit.jupiter.api.Test
    void incrementAdjacencies() {
        ArrayListModel gameData = new ArrayListModel();
        gameData.CreateMinefield(24,24);
        gameData.AddMine(0,0);
        gameData.AddMine(0,1);
        gameData.AddMine(1,1);
        assertEquals(0, gameData.GetNumAdjacent(5,5));
        assertEquals(1, gameData.GetNumAdjacent(2,1));
        assertEquals(1, gameData.GetNumAdjacent(2,2));
        assertEquals(2, gameData.GetNumAdjacent(0,2));
        assertEquals(3, gameData.GetNumAdjacent(1,0));
    }

    @org.junit.jupiter.api.Test
    void getNumAdjacent() {
        ArrayListModel gameData = new ArrayListModel();
        gameData.CreateMinefield(3,3);
        gameData.AddMine(1,1);
        assertEquals(1, gameData.GetNumAdjacent(0,0));
        assertEquals(1, gameData.GetNumAdjacent(0,1));
        assertEquals(1, gameData.GetNumAdjacent(0,2));
        assertEquals(1, gameData.GetNumAdjacent(1,0));
        assertEquals(1, gameData.GetNumAdjacent(1,2));
        assertEquals(1, gameData.GetNumAdjacent(2,0));
        assertEquals(1, gameData.GetNumAdjacent(2,1));
        assertEquals(1, gameData.GetNumAdjacent(2,2));

        gameData = new ArrayListModel();
        gameData.CreateMinefield(3,3);
        gameData.AddMine(0,0);
        gameData.AddMine(0,1);
        assertEquals(1, gameData.GetNumAdjacent(0,2));
        assertEquals(2, gameData.GetNumAdjacent(1,0));
        assertEquals(2, gameData.GetNumAdjacent(1,1));
        assertEquals(1, gameData.GetNumAdjacent(1,2));
        assertEquals(0, gameData.GetNumAdjacent(2,0));
        assertEquals(0, gameData.GetNumAdjacent(2,1));
        assertEquals(0, gameData.GetNumAdjacent(2,2));

        gameData = new ArrayListModel();
        gameData.CreateMinefield(3,3);
        gameData.AddMine(0,0);
        gameData.AddMine(0,1);
        gameData.AddMine(0,2);
        assertEquals(3, gameData.GetNumAdjacent(1,1));

        gameData = new ArrayListModel();
        gameData.CreateMinefield(3,3);
        gameData.AddMine(0,0);
        gameData.AddMine(0,1);
        gameData.AddMine(0,2);
        gameData.AddMine(1,0);
        assertEquals(4, gameData.GetNumAdjacent(1,1));
    }




    @org.junit.jupiter.api.Test
    void addFlag() {
        ArrayListModel gameData = new ArrayListModel();
        gameData.CreateMinefield(24,24);
        assertEquals(false,gameData.IsFlag(0,0));
        assertEquals(false,gameData.IsFlag(0,1));
        assertEquals(false,gameData.IsFlag(0,2));
        gameData.AddFlag(0,0);
        gameData.AddFlag(0,1);
        gameData.AddFlag(0,2);
        assertEquals(true,gameData.IsFlag(0,0));
        assertEquals(true,gameData.IsFlag(0,1));
        assertEquals(true,gameData.IsFlag(0,2));
    }

    @org.junit.jupiter.api.Test
    void removeFlag() {
        ArrayListModel gameData = new ArrayListModel();
        gameData.CreateMinefield(24,24);
        gameData.AddFlag(0,0);
        gameData.AddFlag(0,1);
        gameData.AddFlag(0,2);
        assertEquals(true,gameData.IsFlag(0,0));
        assertEquals(true,gameData.IsFlag(0,1));
        assertEquals(true,gameData.IsFlag(0,2));
        gameData.RemoveFlag(0,0);
        gameData.RemoveFlag(0,1);
        gameData.RemoveFlag(0,2);
        assertEquals(false,gameData.IsFlag(0,0));
        assertEquals(false,gameData.IsFlag(0,1));
        assertEquals(false,gameData.IsFlag(0,2));
    }

    @org.junit.jupiter.api.Test
    void isFlag() {
        ArrayListModel gameData = new ArrayListModel();
        gameData.CreateMinefield(24,24);
        assertEquals(false,gameData.IsFlag(0,0));
        assertEquals(false,gameData.IsFlag(0,1));
        assertEquals(false,gameData.IsFlag(0,2));
        gameData.AddFlag(0,0);
        gameData.AddFlag(0,1);
        gameData.AddFlag(0,2);
        assertEquals(true,gameData.IsFlag(0,0));
        assertEquals(true,gameData.IsFlag(0,1));
        assertEquals(true,gameData.IsFlag(0,2));
    }

    @org.junit.jupiter.api.Test
    void addQuestionMark() {
        ArrayListModel gameData = new ArrayListModel();
        gameData.CreateMinefield(24,24);
        assertEquals(false,gameData.IsQuestion(0,0));
        assertEquals(false,gameData.IsQuestion(0,1));
        assertEquals(false,gameData.IsQuestion(0,2));
        gameData.AddQuestionMark(0,0);
        gameData.AddQuestionMark(0,1);
        gameData.AddQuestionMark(0,2);
        assertEquals(true,gameData.IsQuestion(0,0));
        assertEquals(true,gameData.IsQuestion(0,1));
        assertEquals(true,gameData.IsQuestion(0,2));
    }

    @org.junit.jupiter.api.Test
    void removeQuestionMark() {
        ArrayListModel gameData = new ArrayListModel();
        gameData.CreateMinefield(24,24);
        gameData.AddQuestionMark(0,0);
        gameData.AddQuestionMark(0,1);
        gameData.AddQuestionMark(0,2);
        assertEquals(true,gameData.IsQuestion(0,0));
        assertEquals(true,gameData.IsQuestion(0,1));
        assertEquals(true,gameData.IsQuestion(0,2));
        gameData.RemoveQuestionMark(0,0);
        gameData.RemoveQuestionMark(0,1);
        gameData.RemoveQuestionMark(0,2);
        assertEquals(false,gameData.IsQuestion(0,0));
        assertEquals(false,gameData.IsQuestion(0,1));
        assertEquals(false,gameData.IsQuestion(0,2));
    }

    @org.junit.jupiter.api.Test
    void isQuestion() {
        ArrayListModel gameData = new ArrayListModel();
        gameData.CreateMinefield(24,24);
        assertEquals(false,gameData.IsQuestion(0,0));
        assertEquals(false,gameData.IsQuestion(0,1));
        assertEquals(false,gameData.IsQuestion(0,2));
        gameData.AddQuestionMark(0,0);
        gameData.AddQuestionMark(0,1);
        gameData.AddQuestionMark(0,2);
        assertEquals(true,gameData.IsQuestion(0,0));
        assertEquals(true,gameData.IsQuestion(0,1));
        assertEquals(true,gameData.IsQuestion(0,2));
    }

    @org.junit.jupiter.api.Test
    void isSwept() {
        ArrayListModel gameData = new ArrayListModel();
        gameData.CreateMinefield(24,24);
        assertEquals(false,gameData.IsSwept(0,0));
        assertEquals(false,gameData.IsSwept(0,1));
        assertEquals(false,gameData.IsSwept(0,2));
        gameData.SetSwept(0,0);
        gameData.SetSwept(0,1);
        gameData.SetSwept(0,2);
        assertEquals(true,gameData.IsSwept(0,0));
        assertEquals(true,gameData.IsSwept(0,1));
        assertEquals(true,gameData.IsSwept(0,2));
    }

    @org.junit.jupiter.api.Test
    void setSwept() {
        ArrayListModel gameData = new ArrayListModel();
        gameData.CreateMinefield(24,24);
        assertEquals(false,gameData.IsSwept(0,0));
        assertEquals(false,gameData.IsSwept(0,1));
        assertEquals(false,gameData.IsSwept(0,2));
        gameData.SetSwept(0,0);
        gameData.SetSwept(0,1);
        gameData.SetSwept(0,2);
        assertEquals(true,gameData.IsSwept(0,0));
        assertEquals(true,gameData.IsSwept(0,1));
        assertEquals(true,gameData.IsSwept(0,2));
    }

    @org.junit.jupiter.api.Test
    void plantMines() {
        ArrayListModel gameData = new ArrayListModel();
        gameData.CreateMinefield(24,24);
        gameData.PlantMines(5);
        int countedMines = gameData.mineLocations.stream().mapToInt(
                column -> column.stream().mapToInt(
                        cell -> cell ? 1 : 0).sum()
                ).sum();

        assertEquals(5, countedMines);
    }
}