package sprint1_1.product;

public class Board {
    private int[][] grid;
    private int size;
    
    public Board() {
    	grid = new int[3][3];
    }
    
    public Board(int size) {
        this.size = size;
        grid = new int[size][size];
    }
    
    public int getSize() {
        return size;
    }
    
    public int getCells(int row, int column) {
        return grid[row][column];
    }
    
    public void setCell(int row, int column, int value) {
        grid[row][column] = value;
    }
}
