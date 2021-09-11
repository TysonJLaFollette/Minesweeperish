package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import Model.Model;

public class View extends JFrame implements MouseListener, ActionListener{
    //region Properties
	private int minesMarked;
	private ScorePanel scorePanel;
	private JPanel gamePanel;
	private final Model gameData;
    private final Timer timer = new Timer(200, this);
    private final ImageIcon mineIcon = new ImageIcon("data/mine.gif");
    private final ImageIcon flagIcon = new ImageIcon("data/flag.png");
    //endregion

    //region Constructors
    public View(){
        this.gameData = new Model(24,24,10);
        InitGraphics();
        timer.start();
    }
    //endregion

    //region Public Methods
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof Cell){
            Cell clickedCell = (Cell)e.getSource();
            int column = clickedCell.getColumn();
            int row = clickedCell.getRow();
            if(SwingUtilities.isLeftMouseButton(e)){
                startSweep(column, row);
            } else if(SwingUtilities.isRightMouseButton(e)){
                CycleFlags(column, row);
            }
            gameData.flagCheck();
            if (gameData.isTheGameOver()){
                GameOver();
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == timer){
            scorePanel.setTime(gameData.getSecondsElapsed());
        } else {
            newGame();
        }
    }
    //endregion

    //region Private Methods
    private void InitGraphics(){
        scorePanel = new ScorePanel(this);
        gamePanel = new JPanel();
        this.setIconImage(mineIcon.getImage());
        this.setTitle("Minesweeperish");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel pane = (JPanel)this.getContentPane();
        this.setSize(500, 500);
        pane.setLayout(new BorderLayout());
        pane.add(scorePanel, BorderLayout.NORTH);
        pane.add(gamePanel);
        gamePanel.setLayout(new GridLayout(gameData.GetNumRows(), gameData.GetNumCols()));
        scorePanel.setMines(gameData.GetNumMines());
        for (int curCell = 0; curCell < gameData.GetNumCols() * gameData.GetNumRows(); curCell++){
            int[] coords = ConvertIndexToCoordinates(curCell, gameData.GetNumCols(), gameData.GetNumRows());
            Cell tmpCell = new Cell();
            tmpCell.setColumn(coords[0]);
            tmpCell.setRow(coords[1]);
            tmpCell.addMouseListener(this);
            gamePanel.add(tmpCell);
        }
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void GameOver(){
        for (int curCol = 0; curCol < gameData.GetNumCols(); curCol++){
            for(int curRow = 0; curRow < gameData.GetNumRows(); curRow++){
                Cell curCell = (Cell)gamePanel.getComponent(ConvertCoordinatesToIndex(new int[]{curCol,curRow}));
                curCell.setEnabled(false);
                if (gameData.IsMine(curCol,curRow)){
                    if (gameData.IsFlag(curCol,curRow)){
                        curCell.setBackground(Color.GREEN);
                        curCell.setIcon(mineIcon);
                    } else if (gameData.IsQuestion(curCol,curRow)){
                        curCell.setBackground(Color.YELLOW);
                        curCell.setIcon(mineIcon);
                        curCell.setText(null);
                    } else {
                        curCell.setBackground(Color.RED);
                        curCell.setIcon(mineIcon);
                    }
                } else {
                    curCell.setBackground(Color.gray);
                    curCell.setEnabled(false);
                    if (gameData.GetNumAdjacent(curCol, curRow) != 0){
                        curCell.setText(Integer.toString(gameData.GetNumAdjacent(curCol, curRow)));
                    }
                }
            }
        }
        if(gameData.didPlayerWin()){
            JOptionPane.showMessageDialog(null,"Congratulations! Your time was " + gameData.getSecondsElapsed() + " seconds.");
        }
        else{
            JOptionPane.showMessageDialog(null,"Game Over!");
        }
    }

    private int[] ConvertIndexToCoordinates(int index, int numCols, int numRows){
        int row = index / numRows;
        int column = index % numCols;
        return new int[] {column, row};
    }

    private int ConvertCoordinatesToIndex(int[] coordinates){
        return coordinates[1] * gameData.GetNumCols() + coordinates[0];
    }

    private void startSweep(int column, int row){
        gameData.startSweep(column,row);
        for (int curCol = 0; curCol < gameData.GetNumCols(); curCol++){
            for(int curRow = 0; curRow < gameData.GetNumRows(); curRow++){
                Cell curCell = (Cell)gamePanel.getComponent(ConvertCoordinatesToIndex(new int[]{curCol,curRow}));
                if (gameData.IsSwept(curCol,curRow)){
                    int numAdjacent = gameData.GetNumAdjacent(curCol,curRow);
                    curCell.setEnabled(false);
                    curCell.setBackground(Color.gray);
                    if (gameData.GetNumAdjacent(curCol,curRow) > 0) {
                        curCell.setText(Integer.toString(numAdjacent));
                    }
                }
            }
        }
    }



    private void newGame(){
        gamePanel.removeAll();
        gameData.NewGame(24,24, 100);
        scorePanel.setMines(gameData.GetNumMines());

        for (int curRow = 0; curRow < gameData.GetNumCols(); curRow++){
            for (int curCol = 0; curCol < gameData.GetNumRows(); curCol++){
                Cell tmpCell = new Cell();
                tmpCell.setColumn(curCol);
                tmpCell.setRow(curRow);
                tmpCell.addMouseListener(this);
                gamePanel.add(tmpCell);
            }
        }

        gamePanel.revalidate();
        update(getGraphics());
    }

    private void CycleFlags(int column, int row){
        Cell clickedCell = (Cell)gamePanel.getComponent(ConvertCoordinatesToIndex(new int[]{column,row}));
        if(!gameData.IsFlag(column,row) && !gameData.IsQuestion(column,row)){
            gameData.AddFlag(column,row);
            clickedCell.setImage(flagIcon);
            minesMarked++;
            scorePanel.setMines(gameData.GetNumMines() - minesMarked);
        }
        else if(gameData.IsFlag(column,row)){
            gameData.AddQuestionMark(column,row);
            gameData.RemoveFlag(column,row);
            clickedCell.setImage(null);
            clickedCell.setText("?");
            minesMarked--;
            scorePanel.setMines(gameData.GetNumMines() - minesMarked);
        }
        else if(gameData.IsQuestion(column,row)){
            gameData.RemoveQuestionMark(column,row);
            clickedCell.setImage(null);
            clickedCell.setText("");
        }
    }
    //endregion
}
