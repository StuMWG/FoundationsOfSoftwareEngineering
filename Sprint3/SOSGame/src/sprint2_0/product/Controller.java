package sprint2_0.product;

import javax.swing.JOptionPane;

public class Controller {
    private GUI gui;
    private Board board;
    private boolean isBluePlayerTurn = true;  // Start with the Blue player

    public Controller(GUI gui, Board board) {
        this.gui = gui;
        this.board = board;
    }

    // Method to handle a player move
    public void handlePlayerMove(int row, int col) {
        char playerMove;

        if (isBluePlayerTurn) {
            playerMove = gui.getBluePlayerMove();
        }else {
            playerMove = gui.getRedPlayerMove();
        }
        if (board.makeMove(row, col, playerMove)) {
            gui.updateBoardPanel(board);
            // Switch turns after a valid move
            isBluePlayerTurn = !isBluePlayerTurn;
            gui.updateTurnLabel(isBluePlayerTurn);
        }
    }

    public void startNewGame(String boardSizeInput) {
        try {
            int newSize;
            // Check if the input is empty
            if (boardSizeInput == null || boardSizeInput.trim().isEmpty()) {
                newSize = 3;
            }else {
                newSize = Integer.parseInt(boardSizeInput);
                // Validate the input size
                if (newSize < 3 || newSize > 10) {
                    JOptionPane.showMessageDialog(gui, "Please enter a size between 3 and 10.");
                    return;
                }
            }
            board = new Board(newSize);
            // Reset the game state to Blue player's turn
            isBluePlayerTurn = true;
            
            // Update the GUI with the new board
            gui.updateBoardPanel(board);
            gui.updateTurnLabel(isBluePlayerTurn);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(gui, "Please enter a valid number for the board size.");
        }
    }

}