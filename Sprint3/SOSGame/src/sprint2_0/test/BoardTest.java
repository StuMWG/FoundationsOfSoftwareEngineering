package sprint2_0.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import sprint2_0.product.Board;

public class BoardTest {
    
    private Board board;

    // Initialize the board before each test
    @Before
    public void setUp() {
        board = new Board(3);
    }

    // Test default constructor
    @Test
    public void testDefaultConstructor() {
        Board defaultBoard = new Board();
        assertEquals(3, defaultBoard.getSize());
        for (int row = 0; row < defaultBoard.getSize(); row++) {
            for (int col = 0; col < defaultBoard.getSize(); col++) {
                assertEquals(Board.Cell.EMPTY, defaultBoard.getCell(row, col));
            }
        }
    }

    // Test custom size constructor
    @Test
    public void testCustomSizeConstructor() {
        int customSize = 5;
        Board customBoard = new Board(customSize);
        assertEquals(customSize, customBoard.getSize());
        for (int row = 0; row < customBoard.getSize(); row++) {
            for (int col = 0; col < customBoard.getSize(); col++) {
                assertEquals(Board.Cell.EMPTY, customBoard.getCell(row, col));
            }
        }
    }

    // Test initialization of the board
    @Test
    public void testInitalBoard() {
        board.initalBoard();
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                assertEquals(Board.Cell.EMPTY, board.getCell(row, col));
            }
        }
    }

    // Test making a valid move
    @Test
    public void testMakeValidMove() {
        assertTrue(board.makeMove(1, 1, 'S'));
        assertEquals(Board.Cell.S, board.getCell(1, 1));
    }

    // Test making an invalid move (out of bounds)
    @Test
    public void testMakeInvalidMoveOutOfBounds() {
        assertFalse(board.makeMove(-1, 0, 'S'));
        assertFalse(board.makeMove(3, 3, 'O'));  // Out of bounds for 3x3 board
    }

    // Test making an invalid move (cell already occupied)
    @Test
    public void testMakeInvalidMoveOccupied() {
        board.makeMove(1, 1, 'S');
        assertFalse(board.makeMove(1, 1, 'O'));
    }

    // Test getTurn method
    @Test
    public void testGetTurn() {
        char initialTurn = board.getTurn();
        assertEquals(initialTurn, board.getTurn());
    }

    // Test constructor with isSimpleGame parameter
    @Test
    public void testBoardWithGameType() {
        Board simpleGameBoard = new Board(3, true);
        assertEquals(3, simpleGameBoard.getSize());
        for (int row = 0; row < simpleGameBoard.getSize(); row++) {
            for (int col = 0; col < simpleGameBoard.getSize(); col++) {
                assertEquals(Board.Cell.EMPTY, simpleGameBoard.getCell(row, col));
            }
        }
    }
}