package org.sample.mavensample.product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardPanel extends JPanel {
    private Board board;
    private Controller control;

    public BoardPanel(Board board, Controller control) {
        this.board = board;
        this.control = control;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = e.getY() / (getHeight() / board.getSize());
                int col = e.getX() / (getWidth() / board.getSize());
                control.handlePlayerMove(row, col);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
    }

    private void drawBoard(Graphics g) {
        int size = board.getSize();
        int cellSize = Math.min(getWidth(), getHeight()) / size;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Point point = new Point(row, col);

                // Access the sosSequenceMap from Controller
                if (control.getSosSequenceMap().containsKey(point)) {
                    g.setColor(control.getSosSequenceMap().get(point));
                    g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                }
                
                // Draw grid lines
                g.setColor(Color.BLACK);
                g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
                drawSymbol(g, row, col, cellSize);
            }
        }
    }

    // Draw symbols "S" or "O" based on board state
    private void drawSymbol(Graphics g, int row, int col, int cellSize) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("SansSerif", Font.BOLD, cellSize / 2));

        int x = col * cellSize + cellSize / 4;
        int y = row * cellSize + (3 * cellSize / 4);

        if (board.getCell(row, col) == Board.Cell.S) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("S", x, y);
        } else if (board.getCell(row, col) == Board.Cell.O) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("O", x, y);
        }
    }
}