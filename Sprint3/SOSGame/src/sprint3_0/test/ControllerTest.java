package sprint3_0.test;

import org.junit.Before;
import org.junit.Test;
import sprint3_0.product.Board;
import sprint3_0.product.Controller;
import sprint3_0.product.GUI;
import java.awt.Point;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import static org.junit.Assert.*;

public class ControllerTest {
    private Board board;
    private Controller controller;
    private GUI gui;

    @Before
    public void setUp() {
        board = new Board(3);
        gui = new GUI(board);
        controller = new Controller(gui, board);
    }

    @Test
    public void testPlayerMoveAndScoreUpdate() {
        controller.handlePlayerMove(0, 0);
        gui.getRedOMove().setSelected(true);
        controller.handlePlayerMove(0, 1);
        controller.handlePlayerMove(0, 2);
        
        assertEquals(3, controller.getSosSequenceMap().size());
        assertEquals("Blue Score: 1", gui.getBlueScoreLabel().getText());
    }
    
    public void testTurnLabelUpdate() {
    	assertEquals("Blue Player's Turn", gui.getPlayerTurnLabel().getText());
    	controller.handlePlayerMove(0, 0);
    	assertEquals("Red Player's Turn", gui.getPlayerTurnLabel().getText());
    }

    @Test
    public void testIsBoardFull() {
        assertFalse(controller.isBoardFull());
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
            	controller.handlePlayerMove(row, col);
            }
        }
        assertTrue(controller.isBoardFull());
    }

    @Test
    public void testStartNewGame() {
        controller.startNewGame("3");
        assertEquals(0, controller.getSosSequenceMap().size());
        assertEquals(Board.Cell.EMPTY, board.getCell(0, 0));
    }
    
    @Test
    public void testWinnerSimpleGame() {
        // Initialize the GUI and Board
        GUI gui = new GUI(new Board(3));
        Controller controller = new Controller(gui, gui.getBoard());

        // Simulate a simple game where Blue Player wins
        controller.handlePlayerMove(0, 0);  // Blue Player (S)
        gui.getRedOMove().setSelected(true);  // Switch to Red Player (O)
        controller.handlePlayerMove(0, 1);  // Red Player (O)
        controller.handlePlayerMove(0, 2);  // Blue Player (S) completes "SOS"

        // Verify that the game correctly sets the winner message for Blue Player
        assertEquals("Blue Player wins!", controller.getWinnerMessage());
    }

    public void testGameDrawSimpleGame() throws Exception {
        GUI gui = new GUI(new Board(3));
        Controller controller = new Controller(gui, gui.getBoard());

        for (int row = 0; row < gui.getBoard().getSize(); row++) {
            for (int col = 0; col < gui.getBoard().getSize(); col++) {
            	controller.handlePlayerMove(row, col);
            }
        }

        SwingUtilities.invokeAndWait(() -> {
            assertEquals("It's a tie!", controller.getWinnerMessage());
        });
    } 
    
    @Test
    public void testWinnerGeneralGame() throws Exception {
        // Initialize the GUI and Board
        GUI gui = new GUI(new Board(3));
        Controller controller = new Controller(gui, gui.getBoard());

        // Set game mode to General
        gui.getGeneralGame().setSelected(true);
        gui.getRedOMove().setSelected(true);  // Switch to Red Player

        // Simulate moves for the general game
        controller.handlePlayerMove(0, 0);  // Blue Player (S)
        controller.handlePlayerMove(1, 1);  // Red Player (O)
        controller.handlePlayerMove(2, 2);  // Blue Player (S)
        controller.handlePlayerMove(1, 2);  // Red Player (O)
        controller.handlePlayerMove(0, 2);  // Blue Player (S)
        controller.handlePlayerMove(2, 1);  // Red Player (O)
        controller.handlePlayerMove(2, 0);  // Blue Player (S)
        controller.handlePlayerMove(0, 1);  // Red Player (O)
        controller.handlePlayerMove(1, 0);  // Blue Player (S)

        // Allow GUI to process state updates
        SwingUtilities.invokeAndWait(() -> {
            // Verify that Blue Player wins with the correct score
            assertEquals("Blue Player wins with a score of 4!", controller.getWinnerMessage());
        });
    }
     
    @Test
    public void testDrawGeneralGame() throws Exception {
    	GUI gui = new GUI(new Board(3));
        Controller controller = new Controller(gui, gui.getBoard());

        gui.getGeneralGame().setSelected(true);

        for (int row = 0; row < gui.getBoard().getSize(); row++) {
            for (int col = 0; col < gui.getBoard().getSize(); col++) {
                controller.handlePlayerMove(row, col);
            }
        }

        SwingUtilities.invokeAndWait(() -> {
            assertEquals("It's a tie!", controller.getWinnerMessage());
        });
    }
}