package org.sample.mavensample.product;

import javax.swing.*;
import java.io.File;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GUI extends JFrame {
    private JPanel leftPanel = new JPanel(new GridBagLayout());
    private JPanel rightPanel = new JPanel(new GridBagLayout());
    private JPanel centerPanel = new JPanel();

    private ButtonGroup gameSelect = new ButtonGroup();
    private JRadioButton simpleGame = new JRadioButton("Simple Game", true);
    private JRadioButton generalGame = new JRadioButton("General Game");
    private JTextField squares = new JTextField(2);

    private ButtonGroup bluePlayerSelect = new ButtonGroup();
    private ButtonGroup redPlayerSelect = new ButtonGroup();
    private ButtonGroup blueGetMove = new ButtonGroup();
    private JRadioButton blueHuman = new JRadioButton("Human", true);
    private JRadioButton blueSMove = new JRadioButton("S", true);
    private JRadioButton blueOMove = new JRadioButton("O");
    private JRadioButton blueComputer = new JRadioButton("Computer");
    
    private ButtonGroup redGetMove = new ButtonGroup();
    private JRadioButton redHuman = new JRadioButton("Human", true);
    private JRadioButton redSMove = new JRadioButton("S", true);
    private JRadioButton redOMove = new JRadioButton("O");
    private JRadioButton redComputer = new JRadioButton("Computer");

    private JCheckBox record = new JCheckBox("Record");
    private JComboBox<String> gameSelector = new JComboBox<>();
    private JButton replay = new JButton("Replay");
    private JButton newGame = new JButton("New Game");
    private JLabel blueScoreLabel = new JLabel("Blue Score: 0");
    private JLabel redScoreLabel = new JLabel("Red Score: 0");
    private JLabel playerTurnLabel = new JLabel("Blue Player's Turn", SwingConstants.CENTER);

    private Board board;
    private BoardPanel boardPanel;
    private Controller control;

    private boolean isBlueComputerEnabled = false;
    private boolean isRedComputerEnabled = false;

    public GUI(Board board) {
        this.setBoard(board);
        control = new Controller(this, board);
        setBoardPanel(new BoardPanel(board, control));

        initializeUI();
        loadSavedGames();
        toggleScoreVisibility(false);
    }

    private void initializeUI() {
        int panelWidth = 150;
        leftPanel.setPreferredSize(new Dimension(panelWidth, leftPanel.getPreferredSize().height));
        rightPanel.setPreferredSize(new Dimension(panelWidth, rightPanel.getPreferredSize().height));
        
        JLabel sosLabel = new JLabel("SOS");
        sosLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        playerTurnLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Top panel with GridBagLayout for precise positioning
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // "SOS" label over the left panel with right offset
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 35, 0, 200);  // Top, left, bottom, right
        topPanel.add(sosLabel, gbc);

        // Simple and General Game radio buttons over the center with spacing
        gameSelect.add(getSimpleGame());
        gameSelect.add(getGeneralGame());

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 30);  // Add horizontal space between radio buttons
        topPanel.add(getSimpleGame(), gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 30, 0, 0);
        topPanel.add(getGeneralGame(), gbc);

        // "Board Size" label and text box over the right panel with left offset
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 100, 0, 0);  // Offset to the left
        topPanel.add(new JLabel("Board Size"), gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 5, 0, 40);  // Small offset between label and text box
        squares.setPreferredSize(new Dimension(40, squares.getPreferredSize().height));
        topPanel.add(squares, gbc);
        // Blue player settings setup
        JPanel bluePlayerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.insets = new Insets(5, 5, 5, 5);
        gbcLeft.fill = GridBagConstraints.HORIZONTAL;
        gbcLeft.gridx = 0;

        gbcLeft.gridy = 0;
        bluePlayerPanel.add(new JLabel("Blue Player"), gbcLeft);

        gbcLeft.gridy++;
        bluePlayerPanel.add(blueHuman, gbcLeft);
        bluePlayerSelect.add(blueHuman);

        gbcLeft.gridy++;
        JPanel blueMovePanel = new JPanel(new GridLayout(2, 1, 5, 0)); // Set as 2 rows, 1 column
        blueMovePanel.add(blueSMove); // Add S move first
        blueMovePanel.add(blueOMove); // Add O move below S
        blueGetMove.add(blueSMove);
        blueGetMove.add(blueOMove);
        bluePlayerPanel.add(blueMovePanel, gbcLeft);

        gbcLeft.gridy++;
        bluePlayerPanel.add(blueComputer, gbcLeft);
        bluePlayerSelect.add(blueComputer);

        gbcLeft.gridy++;
        bluePlayerPanel.add(getBlueScoreLabel(), gbcLeft);

        gbcLeft.gridy++;
        bluePlayerPanel.add(record, gbcLeft);

        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(bluePlayerPanel, BorderLayout.NORTH);

        // Red player settings setup
        JPanel redPlayerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.insets = new Insets(5, 5, 5, 5);
        gbcRight.fill = GridBagConstraints.HORIZONTAL;
        gbcRight.gridx = 0;

        gbcRight.gridy = 0;
        redPlayerPanel.add(new JLabel("Red Player"), gbcRight);

        gbcRight.gridy++;
        redPlayerPanel.add(redHuman, gbcRight);
        redPlayerSelect.add(redHuman);

        gbcRight.gridy++;
        JPanel redMovePanel = new JPanel(new GridLayout(2, 1, 5, 0)); // Set as 2 rows, 1 column
        redMovePanel.add(redSMove); // Add S move first
        redMovePanel.add(redOMove); // Add O move below S
        redGetMove.add(redSMove);
        redGetMove.add(redOMove);
        redPlayerPanel.add(redMovePanel, gbcRight);

        gbcRight.gridy++;
        redPlayerPanel.add(redComputer, gbcRight);
        redPlayerSelect.add(redComputer);

        gbcRight.gridy++;
        redPlayerPanel.add(getRedScoreLabel(), gbcRight);

        // Saved games setup - place directly below red player score label
        JPanel savedGamesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints sgGbc = new GridBagConstraints();
        sgGbc.insets = new Insets(5, 5, 5, 5);
        sgGbc.fill = GridBagConstraints.HORIZONTAL;
        sgGbc.gridx = 0;

        sgGbc.gridy = 0;
        savedGamesPanel.add(new JLabel("Saved Games"), sgGbc);

        sgGbc.gridy++;
        gameSelector.setPreferredSize(new Dimension(100, 30));
        savedGamesPanel.add(gameSelector, sgGbc);

        sgGbc.gridy++;
        savedGamesPanel.add(replay, sgGbc);

        sgGbc.gridy++;
        savedGamesPanel.add(newGame, sgGbc);

        gbcRight.gridy++;
        gbcRight.insets = new Insets(15, 5, 5, 5); // Add spacing above the saved games section
        redPlayerPanel.add(savedGamesPanel, gbcRight);

        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(redPlayerPanel, BorderLayout.NORTH);

        // Center panel setup for board display
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
        setSize(800, 600);
        setVisible(true);

        // Action listeners for game controls
        getNewGame().addActionListener(e -> control.startNewGame(getSquares().getText()));

        getGeneralGame().addActionListener(e -> {
            if (getGeneralGame().isSelected()) {
                toggleScoreVisibility(true);
            }
        });

        getSimpleGame().addActionListener(e -> {
            if (getSimpleGame().isSelected()) {
                toggleScoreVisibility(false);
            }
        });

        replay.addActionListener(e -> startReplay());

        getBlueComputer().addActionListener(e -> isBlueComputerEnabled = getBlueComputer().isSelected());
        getBlueHuman().addActionListener(e -> isBlueComputerEnabled = !isBlueComputerEnabled);
        getRedComputer().addActionListener(e -> isRedComputerEnabled = getRedComputer().isSelected());
        getRedHuman().addActionListener(e -> isRedComputerEnabled = !isRedComputerEnabled);
    }

    public boolean isRecordEnabled() {
        return record.isSelected();
    }

    private void toggleScoreVisibility(boolean isGeneralGame) {
        if (isGeneralGame) {
            // Display actual score text
            getBlueScoreLabel().setText("Blue Score: 0");
            getRedScoreLabel().setText("Red Score: 0");
        } else {
            // Use placeholder text to keep layout consistent
            getBlueScoreLabel().setText(" ");
            getRedScoreLabel().setText(" ");
        }
    }

    private void loadSavedGames() {
        String[] savedGames = control.getSavedGames();
        if (savedGames != null) {
            Arrays.sort(savedGames, Collections.reverseOrder());
            for (String game : savedGames) {
                gameSelector.addItem(game);
            }
        }
    }

    public void addRecordedGame(String recordedGameFilePath) {
        String fileName = new File(recordedGameFilePath).getName();
        gameSelector.addItem(fileName);
    }

    public void uncheckRecordCheckbox() {
        record.setSelected(false);
    }

    private void startReplay() {
        if (isRecordEnabled()) {
            JOptionPane.showMessageDialog(this, "Recording is enabled. Disable it to start replay.");
        } else {
            String selectedGame = (String) gameSelector.getSelectedItem();
            if (selectedGame != null) {
                control.replayGame(selectedGame);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a saved game to replay.");
            }
        }
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
        getPlayerTurnLabel().setText(isBluePlayerTurn ? "Blue Player's Turn" : "Red Player's Turn");
    }

    public void updateScore(boolean isBluePlayer, int newScore) {
        if (!isSimpleGameSelected()) {
            if (isBluePlayer) {
                getBlueScoreLabel().setText("Blue Score: " + newScore);
            } else {
                getRedScoreLabel().setText("Red Score: " + newScore);
            }
        }
    }

    public boolean isBlueComputerEnabled() { return isBlueComputerEnabled; }
    public boolean isRedComputerEnabled() { return isRedComputerEnabled; }
    public JRadioButton getBlueHuman() { return blueHuman; }
    public JRadioButton getRedHuman() { return redHuman; }
    public void updateSOSSequences(List<int[]> sosSequences, boolean isBluePlayer) { getBoardPanel().repaint(); }
    public boolean isSimpleGameSelected() { return getSimpleGame().isSelected(); }
    public char getBluePlayerMove() { return blueSMove.isSelected() ? 'S' : 'O'; }
    public char getRedPlayerMove() { return redSMove.isSelected() ? 'S' : 'O'; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI(new Board()));
    }

    public JRadioButton getBlueSMove() {
		return blueSMove;
	}
	
	public void setBlueSMove(JRadioButton blueSMove) {
		this.blueSMove = blueSMove;
	}


	public JRadioButton getBlueOMove() {
		return blueOMove;
	}

	public void setBlueOMove(JRadioButton blueOMove) {
		this.blueOMove = blueOMove;
	}
	
	public JRadioButton getRedSMove() {
		return redSMove;
	}
	
	public void setRedSMove(JRadioButton redSMove) {
		this.redSMove = redSMove;
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
	
	public JRadioButton getRedComputer() {
		return redComputer;
	}
	
	public void setRedComputer(JRadioButton redComputer) {
		this.redComputer = redComputer;
	}
	
	public JRadioButton getBlueComputer() {
		return blueComputer;
	}
	
	public void setBlueComputer(JRadioButton blueComputer) {
		this.blueComputer = blueComputer;
	}
}