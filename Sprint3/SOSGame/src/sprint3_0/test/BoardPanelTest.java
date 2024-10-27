package sprint3_0.test;

import org.junit.Before;
import org.junit.Test;
import sprint3_0.product.Board;
import sprint3_0.product.Controller;
import sprint3_0.product.GUI;
import sprint3_0.product.BoardPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static org.junit.Assert.*;

public class BoardPanelTest {
    private Board board;
    private Controller controller;
    private BoardPanel boardPanel;
    private GUI gui;

    @Before
    public void setUp() {
        board = new Board(3);
        gui = new GUI(board);
        controller = new Controller(gui, board);
        boardPanel = new BoardPanel(board, controller);
        
        boardPanel.setSize(300, 300);
    }

    @Test
    public void testMouseClickOnEmptyCell() {
        int cellSize = boardPanel.getWidth() / board.getSize();
        int x = cellSize / 2;
        int y = cellSize / 2;

        MouseAdapter mouseListener = (MouseAdapter) boardPanel.getMouseListeners()[0];
        MouseEvent clickEvent = new MouseEvent(boardPanel, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, x, y, 1, false);
        
        mouseListener.mouseClicked(clickEvent);

        assertEquals("Cell (0, 0) should contain 'S' after the click", Board.Cell.S, board.getCell(0, 0));
    }
    
    @Test
    public void testDrawSymbolInCell() {
        controller.handlePlayerMove(0, 0);
        boardPanel.repaint();
        
        assertEquals(Board.Cell.S, board.getCell(0, 0));
    }
    
    @Test
    public void testOccupiedCellIgnored() {
        GUI gui = new GUI(new Board());
        Controller controller = new Controller(gui, gui.getBoard());
        
        controller.handlePlayerMove(0, 0);
        Board.Cell initialCell = gui.getBoard().getCell(0, 0);

        controller.handlePlayerMove(0, 0);

        assertEquals(initialCell, gui.getBoard().getCell(0, 0));
    }

    @Test
    public void testOutOfBoundsMoveIgnored() {
        GUI gui = new GUI(new Board());
        
        assertFalse(gui.getBoard().isValidCell(10, 10));
    }
}