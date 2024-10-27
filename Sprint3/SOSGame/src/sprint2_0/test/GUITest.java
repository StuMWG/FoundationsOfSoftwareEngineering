package sprint2_0.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import sprint2_0.product.Board;
import sprint2_0.product.GUI;

import javax.swing.*;

public class GUITest {

    private GUI gui;
    private Board board;

    @Before
    public void setUp() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            board = new Board(3);  // Initialize the board
            gui = new GUI(board);  // Create a new GUI instance with the board
        });
    }

    // Test to check if "Simple Game" is selected by default
    @Test
    public void testIsSimpleGameSelected_Default() {
        // By default, the "Simple Game" radio button should be selected
        assertTrue(gui.isSimpleGameSelected());
    }

    // Test to check if "General Game" can be selected
    @Test
    public void testIsSimpleGameSelected_GeneralGameSelection() throws Exception {
        // Run the test code on the EDT
        SwingUtilities.invokeAndWait(() -> {
            gui.getgGame().setSelected(true);  // Select "General Game"

            // Now "General Game" should be selected, and "Simple Game" should be deselected
            assertFalse(gui.isSimpleGameSelected());
        });
    }

    // Test to check if "S" is selected as the Blue Player's move by default
    @Test
    public void testGetBluePlayerMove_Default() {
        // The default move for the blue player should be 'S'
        assertEquals('S', gui.getBluePlayerMove());
    }

    // Test to check if "O" can be selected for the Blue Player's move
    @Test
    public void testGetBluePlayerMove_OMoveSelection() throws Exception {
        // Run the test code on the EDT
        SwingUtilities.invokeAndWait(() -> {
            gui.getbOMove().setSelected(true);  // Simulate selecting "O" as the blue player's move

            // Now the blue player's move should be 'O'
            assertEquals('O', gui.getBluePlayerMove());
        });
    }

    // Test to check if "S" is selected as the Red Player's move by default
    @Test
    public void testGetRedPlayerMove_Default() {
        // The default move for the red player should be 'S'
        assertEquals('S', gui.getRedPlayerMove());
    }

    // Test to check if "O" can be selected for the Red Player's move
    @Test
    public void testGetRedPlayerMove_OMoveSelection() throws Exception {
        // Run the test code on the EDT
        SwingUtilities.invokeAndWait(() -> {
            gui.getrOMove().setSelected(true);  // Simulate selecting "O" as the red player's move

            // Now the red player's move should be 'O'
            assertEquals('O', gui.getRedPlayerMove());
        });
    }
}