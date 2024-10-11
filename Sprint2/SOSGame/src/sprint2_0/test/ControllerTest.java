package sprint2_0.test;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

import sprint2_0.product.Controller;
import sprint2_0.product.Board;
import sprint2_0.product.GUI;

import javax.swing.*;

public class ControllerTest {

    private Controller controller;
    private Board board;
    private GUI gui;

    @Before
    public void setUp() throws Exception {
        // Run GUI initialization on the EDT to avoid threading issues
        SwingUtilities.invokeAndWait(() -> {
            board = new Board(3);  // Initialize the board
            gui = new GUI(board);  // Create a new GUI instance with the board
            controller = new Controller(gui, board);  // Create the Controller instance
        });
    }

    // Test for the initialization of a new game with default size when no input is given
    @Test
    public void testStartNewGame_DefaultSize() {
        controller.startNewGame("");  // Empty string, should default to 3x3

        // The board size should now be 3
        assertEquals(3, board.getSize());
    }

    @Test
    public void testStartNewGame_CustomSize() {
        controller.startNewGame("5");  // Input of 5, should set the board to 5x5

        // Retrieve the updated board from the GUI
        Board updatedBoard = gui.getBoard();  // You will need to add a getter in the GUI class to access the board

        // The board size should now be 5
        assertEquals(5, updatedBoard.getSize());
    }
    
    // Test for starting a new game with an invalid board size (below range)
    @Test
    public void testStartNewGame_InvalidSizeTooSmall() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> {
            // Show dialog for invalid size < 3, but board size remains unchanged
            controller.startNewGame("2");
        });

        // The board size should remain the same, as 2 is invalid
        assertEquals(3, board.getSize());
    }

    // Test for starting a new game with an invalid board size (above range)
    @Test
    public void testStartNewGame_InvalidSizeTooLarge() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> {
            // Show dialog for invalid size > 10, but board size remains unchanged
            controller.startNewGame("11");
        });

        // The board size should remain the same, as 11 is invalid
        assertEquals(3, board.getSize());
    }

    // Test for invalid input in startNewGame (non-numeric)
    @Test
    public void testStartNewGame_InvalidInputNonNumeric() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> {
            // Show dialog for non-numeric input, but board size remains unchanged
            controller.startNewGame("abc");
        });

        // The board size should remain the same, as "abc" is invalid
        assertEquals(3, board.getSize());
    }

    // Test for handling player move and switching turns
    @Test
    public void testHandlePlayerMove_SwitchTurns() {
        // Initial state: Blue player's turn, placing "S"
        assertEquals('S', gui.getBluePlayerMove());
        controller.handlePlayerMove(0, 0);  // Blue player moves
        assertEquals(Board.Cell.S, board.getCell(0, 0));  // Board updated with "S"

        // Now it's Red player's turn
        assertEquals('S', gui.getRedPlayerMove());  // Red player's default move is "S"
        controller.handlePlayerMove(1, 1);  // Red player moves
        assertEquals(Board.Cell.S, board.getCell(1, 1));  // Board updated with "S"
    }

    // Test for invalid move (cell already occupied)
    @Test
    public void testHandlePlayerMove_InvalidMove() {
        controller.handlePlayerMove(0, 0);  // First move by Blue player
        controller.handlePlayerMove(0, 0);  // Try to place again in the same cell

        // The move should be ignored, and the cell should still contain the first move's "S"
        assertEquals(Board.Cell.S, board.getCell(0, 0));
    }

    // Test for resetting the game and ensuring Blue player starts again
    @Test
    public void testStartNewGame_ResetsTurnToBlue() {
        controller.handlePlayerMove(0, 0);  // Blue player moves
        controller.handlePlayerMove(1, 1);  // Red player moves
        controller.startNewGame("");  // Start a new game with default size

        // Ensure Blue player goes first again
        assertEquals('S', gui.getBluePlayerMove());
    }
}