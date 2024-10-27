package sprint3_0.product;

import javax.swing.JOptionPane;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Controller {
    private GUI gui;
    private Board board;
    private boolean isBluePlayerTurn = true;
    private int blueScore = 0;
    private int redScore = 0;
    private Map<Point, Color> sosSequenceMap = new HashMap<>();
    private String winnerMessage;

    public Controller(GUI gui, Board board) {
        this.gui = gui;
        this.board = board;
    }

    public void handlePlayerMove(int row, int col) {
        char playerMove;

        if (isBluePlayerTurn) {
            playerMove = gui.getBluePlayerMove();
        } else {
            playerMove = gui.getRedPlayerMove();
        }

        if (board.makeMove(row, col, playerMove)) {
            List<int[]> sosSequences = findSOSSequences(row, col, playerMove);

            updateSOSSequences(sosSequences, isBluePlayerTurn);
            gui.updateSOSSequences(sosSequences, isBluePlayerTurn);

            if (!sosSequences.isEmpty()) {
                int newSequencesCount = sosSequences.size();

                if (isBluePlayerTurn) {
                    blueScore += newSequencesCount;
                    gui.updateScore(true, blueScore);
                } else {
                    redScore += newSequencesCount;
                    gui.updateScore(false, redScore);
                }

                if (gui.isSimpleGameSelected()) {
                    announceWinner();
                    return;
                }
            }
            
            if(gui.isSimpleGameSelected() && isBoardFull()) {
            	announceWinner();
            }

            if (!gui.isSimpleGameSelected() && isBoardFull()) {
                announceWinner();
            } else {
                isBluePlayerTurn = !isBluePlayerTurn;
                gui.updateTurnLabel(isBluePlayerTurn);
                gui.updateBoardPanel(board);
            }
        }
    }

    public boolean isBoardFull() {
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.getCell(row, col) == Board.Cell.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private void announceWinner() {
        if (gui.isSimpleGameSelected()) {
            if(redScore == blueScore) {
            	winnerMessage = "It's a tie!";
            	JOptionPane.showMessageDialog(gui, winnerMessage);
            }
            else {
            winnerMessage = isBluePlayerTurn ? "Blue Player wins!" : "Red Player wins!";
            JOptionPane.showMessageDialog(gui, winnerMessage);
            }
        } else {
            if (blueScore > redScore) {
                winnerMessage = "Blue Player wins with a score of " + blueScore + "!";
                JOptionPane.showMessageDialog(gui, winnerMessage);
            } else if (redScore > blueScore) {
            	winnerMessage = "Red Player wins with a score of " + redScore + "!";
                JOptionPane.showMessageDialog(gui, winnerMessage);
            } else {
            	winnerMessage = "It's a tie!";
                JOptionPane.showMessageDialog(gui, winnerMessage);
            }
        }
    }

    public void startNewGame(String boardSizeInput) {
        try {
            int newSize;

            if (boardSizeInput == null || boardSizeInput.trim().isEmpty()) {
                newSize = 3;
            } else {
                newSize = Integer.parseInt(boardSizeInput);
                if (newSize < 3 || newSize > 10) {
                    JOptionPane.showMessageDialog(gui, "Please enter a size between 3 and 10.");
                    return;
                }
            }

            board = new Board(newSize);

            isBluePlayerTurn = true;
            blueScore = 0;
            redScore = 0;
            sosSequenceMap.clear();
            gui.updateScore(true, blueScore);
            gui.updateScore(false, redScore);

            gui.updateSOSSequences(new ArrayList<>(), true);
            gui.updateBoardPanel(board);
            gui.updateTurnLabel(isBluePlayerTurn);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(gui, "Please enter a valid number for the board size.");
        }
    }

    public List<int[]> findSOSSequences(int row, int col, char playerMove) {
        List<int[]> sosSequences = new ArrayList<>();

        if (playerMove == 'O') {
            sosSequences.addAll(findAroundO(row, col));
        } else if (playerMove == 'S') {
            sosSequences.addAll(findAroundS(row, col));
        }

        return sosSequences;
    }

    private List<int[]> findAroundO(int row, int col) {
        List<int[]> sosList = new ArrayList<>();
        int[][] directions = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {1, 1}, {-1, 1}, {1, -1}
        };

        Set<Set<Point>> uniqueSequences = new HashSet<>();

        for (int[] dir : directions) {
            int newRowS1 = row + dir[0];
            int newColS1 = col + dir[1];
            int newRowS2 = row - dir[0];
            int newColS2 = col - dir[1];

            if (board.isValidCell(newRowS1, newColS1) && board.isValidCell(newRowS2, newColS2)) {
                if (board.getCell(newRowS1, newColS1) == Board.Cell.S && board.getCell(newRowS2, newColS2) == Board.Cell.S) {
                    
                    Set<Point> sequence = new HashSet<>();
                    sequence.add(new Point(newRowS1, newColS1));
                    sequence.add(new Point(row, col));
                    sequence.add(new Point(newRowS2, newColS2));
                    
                    if (!uniqueSequences.contains(sequence)) {
                        uniqueSequences.add(sequence);
                        sosList.add(new int[]{newRowS1, newColS1, row, col, newRowS2, newColS2});
                    }
                }
            }
        }
        return sosList;
    }

    private List<int[]> findAroundS(int row, int col) {
        List<int[]> sosList = new ArrayList<>();
        int[][] directions = {
            {0, 1}, {1, 0}, {1, 1}, {1, -1}, {0, -1}, {-1, 0}, {-1, -1}, {-1, 1}
        };

        for (int[] dir : directions) {
            int newRowO = row + dir[0];
            int newColO = col + dir[1];
            int newRowS2 = row + 2 * dir[0];
            int newColS2 = col + 2 * dir[1];

            if (board.isValidCell(newRowO, newColO) && board.isValidCell(newRowS2, newColS2)) {
                if (board.getCell(newRowO, newColO) == Board.Cell.O && board.getCell(newRowS2, newColS2) == Board.Cell.S) {
                    sosList.add(new int[]{row, col, newRowO, newColO, newRowS2, newColS2});
                }
            }
        }
        return sosList;
    }

    // Method to update SOS sequences and their colors in the map
    public void updateSOSSequences(List<int[]> sosSequences, boolean isBluePlayer) {
        Color playerColor = isBluePlayer ? new Color(173, 216, 230) : new Color(255, 182, 193);

        for (int[] seq : sosSequences) {
            Point start = new Point(seq[0], seq[1]);
            Point middle = new Point(seq[2], seq[3]);
            Point end = new Point(seq[4], seq[5]);

            updateCellColor(start, playerColor);
            updateCellColor(middle, playerColor);
            updateCellColor(end, playerColor);
        }
    }

    private void updateCellColor(Point cell, Color playerColor) {
        if (sosSequenceMap.containsKey(cell)) {
            if (!sosSequenceMap.get(cell).equals(playerColor)) {
                sosSequenceMap.put(cell, Color.MAGENTA);
            }
        } else {
            sosSequenceMap.put(cell, playerColor);
        }
    }

    public Map<Point, Color> getSosSequenceMap() {
        return sosSequenceMap;
    }
    
    public String getWinnerMessage() {
        return winnerMessage;
    }
    
    public int getBlueScore() {
    	return this.blueScore;
    }
    
    public int getRedScore() {
    	return this.redScore;
    }
}