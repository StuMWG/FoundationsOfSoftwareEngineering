package sprint3_0.product;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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
        this.setBoard(board);
        control = new Controller(this, board);
        setBoardPanel(new BoardPanel(board, control));

        gameSelect.add(getSimpleGame());
        gameSelect.add(getGeneralGame());

        topPanel.add(new JLabel("SOS"));
        topPanel.add(getSimpleGame());
        topPanel.add(getGeneralGame());
        topPanel.add(new JLabel("Board Size"));
        topPanel.add(getSquares());

        bluePlayerSelect.add(human);
        blueGetMove.add(blueSMove);
        blueGetMove.add(getBlueOMove());
        bluePlayerSelect.add(computer);

        leftPanel.add(new JLabel("Blue Player"));
        leftPanel.add(human);
        leftPanel.add(blueSMove);
        leftPanel.add(getBlueOMove());
        leftPanel.add(record);

        redGetMove.add(redSMove);
        redGetMove.add(getRedOMove());

        rightPanel.add(new JLabel("Red Player"));
        rightPanel.add(computer);
        rightPanel.add(redSMove);
        rightPanel.add(getRedOMove());
        rightPanel.add(replay);
        rightPanel.add(getNewGame());

        leftPanel.add(getBlueScoreLabel());
        rightPanel.add(getRedScoreLabel());
        getBlueScoreLabel().setVisible(false);
        getRedScoreLabel().setVisible(false);

        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(getBoardPanel(), BorderLayout.CENTER);

        JPanel panelGameFrame = new JPanel(new BorderLayout());
        panelGameFrame.add(topPanel, BorderLayout.NORTH);
        panelGameFrame.add(leftPanel, BorderLayout.WEST);
        panelGameFrame.add(rightPanel, BorderLayout.EAST);
        panelGameFrame.add(centerPanel, BorderLayout.CENTER);
        panelGameFrame.add(getPlayerTurnLabel(), BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panelGameFrame);
        setSize(610, 500);
        setVisible(true);

        getNewGame().addActionListener(e -> control.startNewGame(getSquares().getText()));

        getGeneralGame().addActionListener(e -> {
            if (getGeneralGame().isSelected()) {
                getBlueScoreLabel().setVisible(true);
                getRedScoreLabel().setVisible(true);
            }
        });

        getSimpleGame().addActionListener(e -> {
            if (getSimpleGame().isSelected()) {
                getBlueScoreLabel().setVisible(false);
                getRedScoreLabel().setVisible(false);
            }
        });
    }

    public void updateBoardPanel(Board newBoard) {
        setBoard(newBoard);
        setBoardPanel(new BoardPanel(getBoard(), control));
        centerPanel.removeAll();
        centerPanel.add(getBoardPanel(), BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    public void updateTurnLabel(boolean isBluePlayerTurn) {
        if (isBluePlayerTurn) {
            getPlayerTurnLabel().setText("Blue Player's Turn");
        } else {
            getPlayerTurnLabel().setText("Red Player's Turn");
        }
    }

    public void updateScore(boolean isBluePlayer, int newScore) {
        if (isBluePlayer) {
            getBlueScoreLabel().setText("Blue Score: " + newScore);
        } else {
            getRedScoreLabel().setText("Red Score: " + newScore);
        }
    }

    public void updateSOSSequences(List<int[]> sosSequences, boolean isBluePlayer) {
        getBoardPanel().repaint();
    }

    public boolean isSimpleGameSelected() {
        return getSimpleGame().isSelected();
    }
    

    public char getBluePlayerMove() {
        return blueSMove.isSelected() ? 'S' : 'O';
    }

    public char getRedPlayerMove() {
        return redSMove.isSelected() ? 'S' : 'O';
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI(new Board()));
    }

	public JRadioButton getBlueOMove() {
		return blueOMove;
	}

	public void setBlueOMove(JRadioButton blueOMove) {
		this.blueOMove = blueOMove;
	}

	public JRadioButton getRedOMove() {
		return redOMove;
	}

	public void setRedOMove(JRadioButton redOMove) {
		this.redOMove = redOMove;
	}

	public BoardPanel getBoardPanel() {
		return boardPanel;
	}

	public void setBoardPanel(BoardPanel boardPanel) {
		this.boardPanel = boardPanel;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public JLabel getRedScoreLabel() {
		return redScoreLabel;
	}

	public void setRedScoreLabel(JLabel redScoreLabel) {
		this.redScoreLabel = redScoreLabel;
	}

	public JLabel getBlueScoreLabel() {
		return blueScoreLabel;
	}

	public void setBlueScoreLabel(JLabel blueScoreLabel) {
		this.blueScoreLabel = blueScoreLabel;
	}

	public JLabel getPlayerTurnLabel() {
		return playerTurnLabel;
	}

	public void setPlayerTurnLabel(JLabel playerTurnLabel) {
		this.playerTurnLabel = playerTurnLabel;
	}

	public JTextField getSquares() {
		return squares;
	}

	public void setSquares(JTextField squares) {
		this.squares = squares;
	}

	public JButton getNewGame() {
		return newGame;
	}

	public void setNewGame(JButton newGame) {
		this.newGame = newGame;
	}

	public JRadioButton getGeneralGame() {
		return generalGame;
	}

	public void setGeneralGame(JRadioButton generalGame) {
		this.generalGame = generalGame;
	}

	public JRadioButton getSimpleGame() {
		return simpleGame;
	}

	public void setSimpleGame(JRadioButton simpleGame) {
		this.simpleGame = simpleGame;
	}
}