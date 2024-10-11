package sprint1_0.product;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private JFrame frame = new JFrame("SOS Game");
    private JPanel topPanel = new JPanel(new GridLayout(1, 5));
    private JPanel leftPanel = new JPanel(new GridLayout(4, 1));
    private JPanel rightPanel = new JPanel(new GridLayout(5, 1));
    private JPanel centerPanel = new JPanel(); // Panel to hold the board

    private ButtonGroup gSelect = new ButtonGroup();
    private JRadioButton sGame = new JRadioButton("Simple Game");
    private JRadioButton gGame = new JRadioButton("General Game");
    private JTextField squares = new JTextField(2);

    private ButtonGroup bPlayerSelect = new ButtonGroup();
    private JRadioButton human = new JRadioButton("Human");
    private JRadioButton computer = new JRadioButton("Computer");
    private JCheckBox record = new JCheckBox("Record");

    private ButtonGroup rPlayerSelect = new ButtonGroup();
    private JRadioButton human2 = new JRadioButton("Human");
    private JRadioButton computer2 = new JRadioButton("Computer");
    private JButton replay = new JButton("Replay");
    private JButton nGame = new JButton("New Game");

    private Board board;
    private BoardPanel boardPanel;

    public GUI(Board board) {
        this.board = board;
        boardPanel = new BoardPanel(board);

        gSelect.add(sGame);
        gSelect.add(gGame);

        topPanel.add(new JLabel("SOS"));
        topPanel.add(sGame);
        topPanel.add(gGame);
        topPanel.add(new JLabel("Board Size"));
        topPanel.add(squares);

        bPlayerSelect.add(human);
        bPlayerSelect.add(computer);

        leftPanel.add(new JLabel("Blue Player"));
        leftPanel.add(human);
        leftPanel.add(computer);
        leftPanel.add(record);

        rPlayerSelect.add(human2);
        rPlayerSelect.add(computer2);

        rightPanel.add(new JLabel("Red Player"));
        rightPanel.add(human2);
        rightPanel.add(computer2);
        rightPanel.add(replay);
        rightPanel.add(nGame);

        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(boardPanel, BorderLayout.CENTER);

        JPanel panelGameFrame = new JPanel(new BorderLayout());
        panelGameFrame.add(topPanel, BorderLayout.NORTH);
        panelGameFrame.add(leftPanel, BorderLayout.WEST);
        panelGameFrame.add(rightPanel, BorderLayout.EAST);
        panelGameFrame.add(centerPanel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panelGameFrame);
        frame.setSize(800, 600); // Adjust size as needed
        frame.setVisible(true);
    }

    private class BoardPanel extends JPanel {
        private Board board;

        public BoardPanel(Board board) {
            this.board = board;
            //setPreferredSize(new Dimension(400, 400)); // Adjust size as needed
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawBoard(g);
        }

        private void drawBoard(Graphics g) {
            int size = board.getSize();
            int cellSize = Math.min(getWidth(), getHeight()) / size;
            g.setColor(Color.BLACK);
            for (int i = 0; i <= size; i++) {
                g.drawLine(i * cellSize, 0, i * cellSize, getHeight());
                g.drawLine(0, i * cellSize, getWidth(), i * cellSize);
            }
        }
    }

    public Board getBoard() {
        return board;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Start with a default board size, e.g., 3x3
                new GUI(new Board(3));
            }
        });
    }
}
