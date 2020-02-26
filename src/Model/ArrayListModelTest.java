package Model;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class ArrayListModelTest {
    @org.junit.jupiter.api.Test
    void createMinefield() {
        Model gameData = new ArrayListModel();
        gameData.CreateMinefield(24,24);
        assertEquals(24, gameData.GetNumRows());
        assertEquals(24,gameData.GetNumCols());
    }

    @org.junit.jupiter.api.Test
    void getNumRows() {
        Model gameData = new ArrayListModel();
        gameData.CreateMinefield(24,24);
        assertEquals(24,gameData.GetNumRows());
    }

    @org.junit.jupiter.api.Test
    void getNumCols() {
        Model gameData = new ArrayListModel();
        gameData.CreateMinefield(24,24);
        assertEquals(24,gameData.GetNumCols());
    }

    @org.junit.jupiter.api.Test
    void addMine() {
        Model gameData = new ArrayListModel();
        gameData.CreateMinefield(24,24);
        gameData.AddMine(0,0);
        gameData.AddMine(23,23);
        assertTrue(gameData.IsMine(0, 0));
        assertTrue(gameData.IsMine(23, 23));
    }

    @org.junit.jupiter.api.Test
    void isMine() {
        Model gameData = new ArrayListModel();
        gameData.CreateMinefield(24,24);
        gameData.AddMine(1,1);
        assertFalse(gameData.IsMine(0, 0));
        assertTrue(gameData.IsMine(1, 1));
    }

    @org.junit.jupiter.api.Test
    void getNumMines() {
        Model gameData = new ArrayListModel();
        gameData.CreateMinefield(24,24);
        assertEquals(0,gameData.GetNumMines());
        gameData.AddMine(0,0);
        gameData.AddMine(0,1);
        gameData.AddMine(0,2);
        assertEquals(3,gameData.GetNumMines());
    }

    @org.junit.jupiter.api.Test
    void incrementAdjacencies() {
        Model gameData = new ArrayListModel();
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
        Model gameData = new ArrayListModel();
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
    }

    @org.junit.jupiter.api.Test
    void removeFlag() {

    }

    @org.junit.jupiter.api.Test
    void isFlag() {

    }

    @org.junit.jupiter.api.Test
    void addQuestionMark() {

    }

    @org.junit.jupiter.api.Test
    void removeQuestionMark() {

    }

    @org.junit.jupiter.api.Test
    void isQuestion() {

    }

    @org.junit.jupiter.api.Test
    void isSwept() {

    }

    @org.junit.jupiter.api.Test
    void setSwept() {

    }
}