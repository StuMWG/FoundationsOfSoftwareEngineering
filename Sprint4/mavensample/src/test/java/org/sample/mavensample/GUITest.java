package org.sample.mavensample;

import org.junit.Before;

import org.junit.Test;
import org.sample.mavensample.product.Board;
import org.sample.mavensample.product.GUI;

import static org.junit.Assert.*;

import javax.swing.SwingUtilities;

public class GUITest {
    private GUI gui;
    private Board board;

    @Before
    public void setUp() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            board = new Board(3);
            gui = new GUI(board);
        });
    }

    @Test
    public void testIsSimpleGameSelectedByDefault() {
        assertTrue(gui.isSimpleGameSelected());
    }

    @Test
    public void testGetBluePlayerMove_Default() {
        assertEquals('S', gui.getBluePlayerMove());
    }

    @Test
    public void testGetBluePlayerMove_OMoveSelection() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            gui.getBlueOMove().setSelected(true);

            assertEquals('O', gui.getBluePlayerMove());
        });
    }

    @Test
    public void testGetRedPlayerMove_Default() {
        assertEquals('S', gui.getRedPlayerMove());
    }

    @Test
    public void testGetRedPlayerMove_OMoveSelection() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            gui.getRedOMove().setSelected(true);

            assertEquals('O', gui.getRedPlayerMove());
        });
    }

    @Test
    public void testGameModeSwitch() {
        assertTrue(gui.isSimpleGameSelected());
        gui.getGeneralGame().doClick();
        assertFalse(gui.isSimpleGameSelected());
    }

    @Test
    public void testScoreDisplayUpdate() {
        gui.updateScore(true, 2);
        assertEquals("Blue Score: 2", gui.getBlueScoreLabel().getText());

        gui.updateScore(false, 1);
        assertEquals("Red Score: 1", gui.getRedScoreLabel().getText());
    }
    

    @Test
    public void testNewGameButton() {
        gui.getNewGame().doClick();
        assertEquals(Board.Cell.EMPTY, board.getCell(0, 0));
    }
}