package org.sample.mavensample.product;

public class Board {
    public enum Cell {EMPTY, S, O}
    private Cell[][] grid;
    private int size;
    private char turn;

    public Board() {
        this(3);
    }
    
    public Board(int size) {
        this.size = size;
        grid = new Cell[size][size];
        initialBoard();
    }

    public void initialBoard() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                grid[row][col] = Cell.EMPTY;
            }
        }
    }

    public boolean makeMove(int row, int col, char playerMove) {
        if (isValidCell(row, col) && grid[row][col] == Cell.EMPTY) {
            grid[row][col] = (playerMove == 'S') ? Cell.S : Cell.O;
            return true;
        }
        return false;
    }
    
    public void clearBoard() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                grid[row][col] = Cell.EMPTY;
            }
        }
    }

    public boolean isValidCell(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
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