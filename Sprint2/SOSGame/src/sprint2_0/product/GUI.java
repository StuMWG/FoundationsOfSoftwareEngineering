package sprint2_0.product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private JPanel topPanel = new JPanel(new GridLayout(1, 5));
    private JPanel leftPanel = new JPanel(new GridLayout(7, 1));
    private JPanel rightPanel = new JPanel(new GridLayout(7, 1));
    private JPanel centerPanel = new JPanel();

    private ButtonGroup gameSelect = new ButtonGroup();
    private JRadioButton simpleGame = new JRadioButton("Simple Game", true);
    private JRadioButton generalGame = new JRadioButton("General Game");
    private JTextField squares = new JTextField(2);

    private ButtonGroup bluePlayerSelect = new ButtonGroup();
    private ButtonGroup blueGetMove = new ButtonGroup();
    private JRadioButton human = new JRadioButton("Human", true);
    private JRadioButton blueSMove = new JRadioButton("S", true);
    private JRadioButton blueOMove = new JRadioButton("O");
    private JRadioButton computer = new JRadioButton("Computer");
    private JCheckBox record = new JCheckBox("Record");

    private ButtonGroup redPlayerSelect = new ButtonGroup();
    private ButtonGroup redGetMove = new ButtonGroup();
    private JRadioButton redSMove = new JRadioButton("S", true);
    private JRadioButton redOMove = new JRadioButton("O");
    private JButton replay = new JButton("Replay");
    private JButton newGame = new JButton("New Game");
    private JLabel blueScoreLabel = new JLabel("Blue Score: 0");
    private JLabel redScoreLabel = new JLabel("Red Score: 0");
    private JLabel playerTurnLabel = new JLabel("Blue Player's Turn", SwingConstants.CENTER);
    
    private Board board;
    private BoardPanel boardPanel;
    private Controller control;

    public GUI(Board board) {
        this.board = board;
        control = new Controller(this, board);
        boardPanel = new BoardPanel(board, control);
        
        gameSelect.add(simpleGame);
        gameSelect.add(generalGame);

        topPanel.add(new JLabel("SOS"));
        topPanel.add(simpleGame);
        topPanel.add(generalGame);
        topPanel.add(new JLabel("Board Size"));
        topPanel.add(squares);

        bluePlayerSelect.add(human);
        blueGetMove.add(blueSMove);
        blueGetMove.add(blueOMove);
        bluePlayerSelect.add(computer);

        leftPanel.add(new JLabel("Blue Player"));
        leftPanel.add(human);
        leftPanel.add(blueSMove);
        leftPanel.add(blueOMove);
        leftPanel.add(record);

        redGetMove.add(redSMove);
        redGetMove.add(redOMove);

        rightPanel.add(new JLabel("Red Player"));
        rightPanel.add(computer);
        rightPanel.add(redSMove);
        rightPanel.add(redOMove);
        rightPanel.add(replay);
        rightPanel.add(newGame);
        
        leftPanel.add(blueScoreLabel);
        rightPanel.add(redScoreLabel);
        blueScoreLabel.setVisible(false);
        redScoreLabel.setVisible(false);

        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(boardPanel, BorderLayout.CENTER);

        JPanel panelGameFrame = new JPanel(new BorderLayout());
        panelGameFrame.add(topPanel, BorderLayout.NORTH);
        panelGameFrame.add(leftPanel, BorderLayout.WEST);
        panelGameFrame.add(rightPanel, BorderLayout.EAST);
        panelGameFrame.add(centerPanel, BorderLayout.CENTER);
        panelGameFrame.add(playerTurnLabel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panelGameFrame);
        setSize(610, 500);
        setVisible(true);

        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                control.startNewGame(squares.getText());
            }
        });
        
        generalGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (generalGame.isSelected()) {
                    blueScoreLabel.setVisible(true);
                    redScoreLabel.setVisible(true);
                }
            }
        });

        simpleGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (simpleGame.isSelected()) {
                    blueScoreLabel.setVisible(false);
                    redScoreLabel.setVisible(false);
                }
            }
        });

    }

    public void updateBoardPanel(Board newBoard) {
        board = newBoard;
        boardPanel = new BoardPanel(board, control); 
        centerPanel.removeAll();
        centerPanel.add(boardPanel, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }
    
    public void updateTurnLabel(boolean isBluePlayerTurn) {
        if (isBluePlayerTurn) {
            playerTurnLabel.setText("Blue Player's Turn");
        } else {
            playerTurnLabel.setText("Red Player's Turn");
        }
    }

    public boolean isSimpleGameSelected() {
        return simpleGame.isSelected();
    }

    public char getBluePlayerMove() {
        return blueSMove.isSelected() ? 'S' : 'O';
    }

    public char getRedPlayerMove() {
        return redSMove.isSelected() ? 'S' : 'O';
    }
    
    public JRadioButton getbOMove() {
        return blueOMove;
    }

    public JRadioButton getbSMove() {
        return blueSMove;
    }

    public JRadioButton getrOMove() {
        return redOMove;
    }

    public JRadioButton getrSMove() {
        return redSMove;
    }
    
    public JRadioButton getgGame() {
        return generalGame;
    }
    
    public Board getBoard() {
        return this.board;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI(new Board()));
    }
}