package org.sample.mavensample;

import org.junit.Before;

import org.junit.Test;
import org.sample.mavensample.product.Board;
import org.sample.mavensample.product.Controller;
import org.sample.mavensample.product.GUI;

import static org.junit.Assert.*;

public class BoardTest {
    private Board board;

    @Before
    public void setUp() {
        board = new Board(3);
    }
    
    @Test
    public void testValidBoardSize() {
    	GUI gui = new GUI(new Board());
    	Controller controller = new Controller(gui, new Board());
    	
    	gui.getSquares().setText("5");
    	controller.startNewGame(gui.getSquares().getText());
    	
    	assertEquals(5, gui.getBoard().getSize());
    }
    
    @Test
    public void testInvalidBoardSizeTooSmall() {
        GUI gui = new GUI(new Board());
        Controller controller = new Controller(gui, new Board());
        
        gui.getSquares().setText("2");
        controller.startNewGame(gui.getSquares().getText());
        
        assertEquals(3, gui.getBoard().getSize());
    }

    @Test
    public void testInvalidBoardSizeNonNumeric() {
        GUI gui = new GUI(new Board());
        Controller controller = new Controller(gui, new Board());
        
        gui.getSquares().setText("abc");
        controller.startNewGame(gui.getSquares().getText());
        
        assertEquals("abc", gui.getSquares().getText());
        assertEquals(3, gui.getBoard().getSize());
    }

    @Test
    public void testInitialBoardIsEmpty() {
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                assertEquals(Board.Cell.EMPTY, board.getCell(row, col));
            }
        }
    }

    @Test
    public void testMakeMoveS() {
        assertTrue(board.makeMove(0, 0, 'S'));
        assertEquals(Board.Cell.S, board.getCell(0, 0));
    }

    @Test
    public void testMakeMoveO() {
        assertTrue(board.makeMove(0, 0, 'O'));
        assertEquals(Board.Cell.O, board.getCell(0, 0));
    }

    @Test
    public void testInvalidMove() {
        board.makeMove(0, 0, 'S');
        assertFalse(board.makeMove(0, 0, 'O'));
    }

    @Test
    public void testIsValidCell() {
        assertTrue(board.isValidCell(0, 0));
        assertFalse(board.isValidCell(-1, 0));
        assertFalse(board.isValidCell(3, 3));
    }
}