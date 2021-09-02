package Model;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {
    @org.junit.jupiter.api.Test
    void createMinefield() {
        Model gameData = new Model();
        gameData.CreateMinefield(24,24);
        assertEquals(24, gameData.GetNumRows());
        assertEquals(24,gameData.GetNumCols());
    }

    @org.junit.jupiter.api.Test
    void getNumRows() {
        Model gameData = new Model();
        gameData.CreateMinefield(24,24);
        assertEquals(24,gameData.GetNumRows());
    }

    @org.junit.jupiter.api.Test
    void getNumCols() {
        Model gameData = new Model();
        gameData.CreateMinefield(24,24);
        assertEquals(24,gameData.GetNumCols());
    }

    @org.junit.jupiter.api.Test
    void addMine() {
        Model gameData = new Model();
        gameData.CreateMinefield(24,24);
        gameData.AddMine(0,0);
        gameData.AddMine(23,23);
        assertTrue(gameData.IsMine(0, 0));
        assertTrue(gameData.IsMine(23, 23));
    }

    @org.junit.jupiter.api.Test
    void isMine() {
        Model gameData = new Model();
        gameData.CreateMinefield(24,24);
        gameData.AddMine(1,1);
        assertFalse(gameData.IsMine(0, 0));
        assertTrue(gameData.IsMine(1, 1));
    }

    @org.junit.jupiter.api.Test
    void getNumMines() {
        Model gameData = new Model();
        gameData.CreateMinefield(24,24);
        assertEquals(0,gameData.GetNumMines());
        gameData.AddMine(0,0);
        gameData.AddMine(0,1);
        gameData.AddMine(0,2);
        assertEquals(3,gameData.GetNumMines());
    }

    @org.junit.jupiter.api.Test
    void incrementAdjacencies() {
        Model gameData = new Model();
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
        Model gameData = new Model();
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

        gameData = new Model();
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

        gameData = new Model();
        gameData.CreateMinefield(3,3);
        gameData.AddMine(0,0);
        gameData.AddMine(0,1);
        gameData.AddMine(0,2);
        assertEquals(3, gameData.GetNumAdjacent(1,1));

        gameData = new Model();
        gameData.CreateMinefield(3,3);
        gameData.AddMine(0,0);
        gameData.AddMine(0,1);
        gameData.AddMine(0,2);
        gameData.AddMine(1,0);
        assertEquals(4, gameData.GetNumAdjacent(1,1));
    }




    @org.junit.jupiter.api.Test
    void addFlag() {
        Model gameData = new Model();
        gameData.CreateMinefield(24,24);
        assertFalse(gameData.IsFlag(0, 0));
        assertFalse(gameData.IsFlag(0, 1));
        assertFalse(gameData.IsFlag(0, 2));
        gameData.AddFlag(0,0);
        gameData.AddFlag(0,1);
        gameData.AddFlag(0,2);
        assertTrue(gameData.IsFlag(0, 0));
        assertTrue(gameData.IsFlag(0, 1));
        assertTrue(gameData.IsFlag(0, 2));
    }

    @org.junit.jupiter.api.Test
    void removeFlag() {
        Model gameData = new Model();
        gameData.CreateMinefield(24,24);
        gameData.AddFlag(0,0);
        gameData.AddFlag(0,1);
        gameData.AddFlag(0,2);
        assertTrue(gameData.IsFlag(0, 0));
        assertTrue(gameData.IsFlag(0, 1));
        assertTrue(gameData.IsFlag(0, 2));
        gameData.RemoveFlag(0,0);
        gameData.RemoveFlag(0,1);
        gameData.RemoveFlag(0,2);
        assertFalse(gameData.IsFlag(0, 0));
        assertFalse(gameData.IsFlag(0, 1));
        assertFalse(gameData.IsFlag(0, 2));
    }

    @org.junit.jupiter.api.Test
    void isFlag() {
        Model gameData = new Model();
        gameData.CreateMinefield(24,24);
        assertFalse(gameData.IsFlag(0, 0));
        assertFalse(gameData.IsFlag(0, 1));
        assertFalse(gameData.IsFlag(0, 2));
        gameData.AddFlag(0,0);
        gameData.AddFlag(0,1);
        gameData.AddFlag(0,2);
        assertTrue(gameData.IsFlag(0, 0));
        assertTrue(gameData.IsFlag(0, 1));
        assertTrue(gameData.IsFlag(0, 2));
    }

    @org.junit.jupiter.api.Test
    void addQuestionMark() {
        Model gameData = new Model();
        gameData.CreateMinefield(24,24);
        assertFalse(gameData.IsQuestion(0, 0));
        assertFalse(gameData.IsQuestion(0, 1));
        assertFalse(gameData.IsQuestion(0, 2));
        gameData.AddQuestionMark(0,0);
        gameData.AddQuestionMark(0,1);
        gameData.AddQuestionMark(0,2);
        assertTrue(gameData.IsQuestion(0, 0));
        assertTrue(gameData.IsQuestion(0, 1));
        assertTrue(gameData.IsQuestion(0, 2));
    }

    @org.junit.jupiter.api.Test
    void removeQuestionMark() {
        Model gameData = new Model();
        gameData.CreateMinefield(24,24);
        gameData.AddQuestionMark(0,0);
        gameData.AddQuestionMark(0,1);
        gameData.AddQuestionMark(0,2);
        assertTrue(gameData.IsQuestion(0, 0));
        assertTrue(gameData.IsQuestion(0, 1));
        assertTrue(gameData.IsQuestion(0, 2));
        gameData.RemoveQuestionMark(0,0);
        gameData.RemoveQuestionMark(0,1);
        gameData.RemoveQuestionMark(0,2);
        assertFalse(gameData.IsQuestion(0, 0));
        assertFalse(gameData.IsQuestion(0, 1));
        assertFalse(gameData.IsQuestion(0, 2));
    }

    @org.junit.jupiter.api.Test
    void isQuestion() {
        Model gameData = new Model();
        gameData.CreateMinefield(24,24);
        assertFalse(gameData.IsQuestion(0, 0));
        assertFalse(gameData.IsQuestion(0, 1));
        assertFalse(gameData.IsQuestion(0, 2));
        gameData.AddQuestionMark(0,0);
        gameData.AddQuestionMark(0,1);
        gameData.AddQuestionMark(0,2);
        assertTrue(gameData.IsQuestion(0, 0));
        assertTrue(gameData.IsQuestion(0, 1));
        assertTrue(gameData.IsQuestion(0, 2));
    }

    @org.junit.jupiter.api.Test
    void isSwept() {
        Model gameData = new Model();
        gameData.CreateMinefield(24,24);
        assertFalse(gameData.IsSwept(0, 0));
        assertFalse(gameData.IsSwept(0, 1));
        assertFalse(gameData.IsSwept(0, 2));
        gameData.SetSwept(0,0);
        gameData.SetSwept(0,1);
        gameData.SetSwept(0,2);
        assertTrue(gameData.IsSwept(0, 0));
        assertTrue(gameData.IsSwept(0, 1));
        assertTrue(gameData.IsSwept(0, 2));
    }

    @org.junit.jupiter.api.Test
    void setSwept() {
        Model gameData = new Model();
        gameData.CreateMinefield(24,24);
        assertFalse(gameData.IsSwept(0, 0));
        assertFalse(gameData.IsSwept(0, 1));
        assertFalse(gameData.IsSwept(0, 2));
        gameData.SetSwept(0,0);
        gameData.SetSwept(0,1);
        gameData.SetSwept(0,2);
        assertTrue(gameData.IsSwept(0, 0));
        assertTrue(gameData.IsSwept(0, 1));
        assertTrue(gameData.IsSwept(0, 2));
    }

    @org.junit.jupiter.api.Test
    void plantMines() {
        Model gameData = new Model();
        gameData.CreateMinefield(24,24);
        gameData.PlantMines(5);
        int countedMines = gameData.mineLocations.stream().mapToInt(
                column -> column.stream().mapToInt(
                        cell -> cell ? 1 : 0).sum()
                ).sum();

        assertEquals(5, countedMines);
    }
}