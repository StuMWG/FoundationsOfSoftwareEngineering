package sprint2_0.product;

public class Board {
    public enum Cell {EMPTY, S, O}
    private boolean isSimpleGame;

    private Cell[][] grid;
    private int size;
    private char turn;

    // Default board constructor
    public Board() {
        this(3);
    }

    // Constructor for custom board size
    public Board(int size) {
        this.size = size;
        setGrid(new Cell[size][size]);
        initalBoard(); 
    }

    // Initialize or reset the board
    public void initalBoard() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                getGrid()[row][col] = Cell.EMPTY;
            }
        }
    }

    public boolean makeMove(int row, int column, char playerMove) {
        if (row >= 0 && row < size && column >= 0 && column < size && getGrid()[row][column] == Cell.EMPTY) {
            getGrid()[row][column] = (playerMove == 'S') ? Cell.S : Cell.O;
            return true; 
        }
        return false;
    }

    public Board(int size, boolean isSimpleGame) {
        this.size = size;
        this.isSimpleGame = isSimpleGame;
        setGrid(new Cell[size][size]);
        initalBoard();
    }
    
    public int getSize() {
        return size;
    }

    public char getTurn() {
        return turn;
    }

    public Cell getCell(int row, int column) {
        return getGrid()[row][column];
    }

	public Cell[][] getGrid() {
		return grid;
	}

	public void setGrid(Cell[][] grid) {
		this.grid = grid;
	}
}