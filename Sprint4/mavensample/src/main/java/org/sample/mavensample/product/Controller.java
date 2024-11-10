package org.sample.mavensample.product;

import javax.swing.JOptionPane;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.swing.Timer;

import okhttp3.*;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

public class Controller {
	private static final String API_KEY = "APIKEY Here";
	 
    private GUI gui;
    private Board board;
    private boolean isBluePlayerTurn = true;
    private int blueScore = 0;
    private int redScore = 0;
    private Map<Point, Color> sosSequenceMap = new HashMap<>();
    private String winnerMessage;
    private boolean testMode = false; // Test mode flag
    

    public Controller(GUI gui, Board board) {
        this.gui = gui;
        this.board = board;
    }

    public void handlePlayerMove(int row, int col) {
        char playerMove;

        if (isBluePlayerTurn) {
            playerMove = gui.getBluePlayerMove();
        } else {
            playerMove = gui.getRedPlayerMove();
        }

        if (board.makeMove(row, col, playerMove)) {
            List<int[]> sosSequences = findSOSSequences(row, col, playerMove);

            updateSOSSequences(sosSequences, isBluePlayerTurn);
            gui.updateSOSSequences(sosSequences, isBluePlayerTurn);

            boolean madeSequence = !sosSequences.isEmpty();

            if (madeSequence) {
                int newSequencesCount = sosSequences.size();
                if (isBluePlayerTurn) {
                    blueScore += newSequencesCount;
                    gui.updateScore(true, blueScore);
                } else {
                    redScore += newSequencesCount;
                    gui.updateScore(false, redScore);
                }

                if (gui.isSimpleGameSelected()) {
                    announceWinner();
                    return;
                }
            }

            if (isBoardFull()) {
                if (blueScore == redScore) {
                   announceWinner();
                    return;
                } else if (!gui.isSimpleGameSelected()) {
                    announceWinner();
                    return;
                }
            }

            if (!madeSequence) {
                isBluePlayerTurn = !isBluePlayerTurn;
            }

            Timer timer = new Timer(200, e -> {
                gui.updateTurnLabel(isBluePlayerTurn);

                if (isBluePlayerTurn && gui.isBlueComputerEnabled()) {
                    makeComputerMove();
                } else if (!isBluePlayerTurn && gui.isRedComputerEnabled()) {
                    makeComputerMove(); 
                } else {
                    gui.updateBoardPanel(board);
                }
            });

            timer.setRepeats(false);
            timer.start();
        }
    }

    public boolean isBoardFull() {
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.getCell(row, col) == Board.Cell.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private void announceWinner() {
        if (gui.isSimpleGameSelected()) {
            if(redScore == blueScore) {
            	winnerMessage = "It's a tie!";
            	JOptionPane.showMessageDialog(gui, winnerMessage);
            }
            else {
            winnerMessage = isBluePlayerTurn ? "Blue Player wins!" : "Red Player wins!";
            JOptionPane.showMessageDialog(gui, winnerMessage);
            }
        } else {
            if (blueScore > redScore) {
                winnerMessage = "Blue Player wins with a score of " + blueScore + "!";
                JOptionPane.showMessageDialog(gui, winnerMessage);
            } else if (redScore > blueScore) {
            	winnerMessage = "Red Player wins with a score of " + redScore + "!";
                JOptionPane.showMessageDialog(gui, winnerMessage);
            } else {
            	winnerMessage = "It's a tie!";
                JOptionPane.showMessageDialog(gui, winnerMessage);
            }
        }
    }

    public void startNewGame(String boardSizeInput) {
        try {
            int newSize;

            if (boardSizeInput == null || boardSizeInput.trim().isEmpty()) {
                newSize = 3;
            } else {
                newSize = Integer.parseInt(boardSizeInput);
                if (newSize < 3 || newSize > 10) {
                    JOptionPane.showMessageDialog(gui, "Please enter a size between 3 and 10.");
                    return;
                }
            }

            board = new Board(newSize);
            isBluePlayerTurn = true;
            blueScore = 0;
            redScore = 0;
            sosSequenceMap.clear();
            gui.updateScore(true, blueScore);
            gui.updateScore(false, redScore);

            gui.updateSOSSequences(new ArrayList<>(), true);
            gui.updateBoardPanel(board);
            gui.updateTurnLabel(isBluePlayerTurn);

            if (gui.isBlueComputerEnabled()) {
                makeComputerMove();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(gui, "Please enter a valid number for the board size.");
        }
    }

    public List<int[]> findSOSSequences(int row, int col, char playerMove) {
        List<int[]> sosSequences = new ArrayList<>();

        if (playerMove == 'O') {
            sosSequences.addAll(findAroundO(row, col));
        } else if (playerMove == 'S') {
            sosSequences.addAll(findAroundS(row, col));
        }

        return sosSequences;
    }

    private List<int[]> findAroundO(int row, int col) {
        List<int[]> sosList = new ArrayList<>();
        int[][] directions = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {1, 1}, {-1, 1}, {1, -1}
        };

        Set<Set<Point>> uniqueSequences = new HashSet<>();

        for (int[] dir : directions) {
            int newRowS1 = row + dir[0];
            int newColS1 = col + dir[1];
            int newRowS2 = row - dir[0];
            int newColS2 = col - dir[1];

            if (board.isValidCell(newRowS1, newColS1) && board.isValidCell(newRowS2, newColS2)) {
                if (board.getCell(newRowS1, newColS1) == Board.Cell.S && board.getCell(newRowS2, newColS2) == Board.Cell.S) {
                    
                    Set<Point> sequence = new HashSet<>();
                    sequence.add(new Point(newRowS1, newColS1));
                    sequence.add(new Point(row, col));
                    sequence.add(new Point(newRowS2, newColS2));
                    
                    if (!uniqueSequences.contains(sequence)) {
                        uniqueSequences.add(sequence);
                        sosList.add(new int[]{newRowS1, newColS1, row, col, newRowS2, newColS2});
                    }
                }
            }
        }
        return sosList;
    }

    private List<int[]> findAroundS(int row, int col) {
        List<int[]> sosList = new ArrayList<>();
        int[][] directions = {
            {0, 1}, {1, 0}, {1, 1}, {1, -1}, {0, -1}, {-1, 0}, {-1, -1}, {-1, 1}
        };

        for (int[] dir : directions) {
            int newRowO = row + dir[0];
            int newColO = col + dir[1];
            int newRowS2 = row + 2 * dir[0];
            int newColS2 = col + 2 * dir[1];

            if (board.isValidCell(newRowO, newColO) && board.isValidCell(newRowS2, newColS2)) {
                if (board.getCell(newRowO, newColO) == Board.Cell.O && board.getCell(newRowS2, newColS2) == Board.Cell.S) {
                    sosList.add(new int[]{row, col, newRowO, newColO, newRowS2, newColS2});
                }
            }
        }
        return sosList;
    }

    public void updateSOSSequences(List<int[]> sosSequences, boolean isBluePlayer) {
        Color playerColor = isBluePlayer ? new Color(173, 216, 230) : new Color(255, 182, 193);

        for (int[] seq : sosSequences) {
            Point start = new Point(seq[0], seq[1]);
            Point middle = new Point(seq[2], seq[3]);
            Point end = new Point(seq[4], seq[5]);

            updateCellColor(start, playerColor);
            updateCellColor(middle, playerColor);
            updateCellColor(end, playerColor);
        }
    }

    private void updateCellColor(Point cell, Color playerColor) {
        if (sosSequenceMap.containsKey(cell)) {
            if (!sosSequenceMap.get(cell).equals(playerColor)) {
                sosSequenceMap.put(cell, Color.MAGENTA);
            }
        } else {
            sosSequenceMap.put(cell, playerColor);
        }
    }
    
    public void makeComputerMove() {
        String prompt = generateBoardStatePrompt();

        OkHttpClient client = new OkHttpClient();
        String chatUrl = "https://api.openai.com/v1/chat/completions";

        JSONObject json = new JSONObject();
        json.put("model", "gpt-4-turbo");

        JSONArray messages = new JSONArray();
        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", prompt);
        messages.put(message);
        json.put("messages", messages);
        json.put("max_tokens", 50);

        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(chatUrl)
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    System.out.println("Success! Response: " + responseBody);
                    String move = parseMoveFromResponse(responseBody);
                    applyComputerMove(move);
                } else {
                    System.err.println("Failed to get response from OpenAI.");
                    System.err.println("Response code: " + response.code());
                    System.err.println("Response message: " + response.message());
                    System.err.println("Response body: " + response.body().string());
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                System.err.println("Request failed: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private String generateBoardStatePrompt() {
        StringBuilder prompt = new StringBuilder("The current SOS board state is as follows:\n");

        prompt.append("Occupied Spaces:\n");
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                Board.Cell cell = board.getCell(row, col);
                if (cell != Board.Cell.EMPTY) {
                    prompt.append("[").append(row + 1).append(", ").append(col + 1).append("]: ").append(cell.name()).append("\n");
                }
            }
        }

        prompt.append("Available Spaces:\n");
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.getCell(row, col) == Board.Cell.EMPTY) {
                    prompt.append("[").append(row + 1).append(", ").append(col + 1).append("]\n");
                }
            }
        }

        prompt.append("\nStrictly write your response in the format: 'Place S at x,y' or 'Place O at x,y'\n");
        prompt.append("The objective is to form more 'SOS' sequences on the board than your opponent. The top-left corner is (1,1), and the bottom-right is (")
              .append(board.getSize()).append(",").append(board.getSize()).append(").");

        return prompt.toString();
    }

    public String parseMoveFromResponse(String responseBody) {
        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray choices = jsonObject.getJSONArray("choices");
        JSONObject choice = choices.getJSONObject(0);
        JSONObject message = choice.getJSONObject("message");
        String content = message.getString("content").trim();
        return content;
    }

    public String applyComputerMove(String move) {
        try {
            String[] parts = move.replace(".", "").trim().split(" ");
            char playerMove = parts[1].charAt(0);

            if ((isBluePlayerTurn && gui.isBlueComputerEnabled())||testMode && isBluePlayerTurn) {
                if (playerMove == 'O') {
                    gui.getBlueOMove().doClick();
                } else {
                    gui.getBlueSMove().doClick();
                }
            } else if ((!isBluePlayerTurn && gui.isRedComputerEnabled())||testMode && !isBluePlayerTurn) {
                if (playerMove == 'O') {
                    gui.getRedOMove().doClick();
                } else {
                    gui.getRedSMove().doClick();
                }
            }
            
            Thread.sleep(300); // 300ms delay to ensure the correct letter is fully registered

            String[] coords = parts[3].split(",");
            int row = Integer.parseInt(coords[0].trim()) - 1;
            int col = Integer.parseInt(coords[1].trim()) - 1;

            if (board.getCell(row, col) == Board.Cell.EMPTY) {
                handlePlayerMove(row, col); 
            } else {
                System.out.println("LLM selected an occupied cell. Choosing a random cell as fallback.");
                makeRandomMove(playerMove);
            }
            return move;
        } catch (Exception e) {
            System.out.println("Failed to parse LLM response or invalid format. Choosing a random move as fallback.");
            makeRandomMove('S');
            return "Fallback move due to error";
        }
    }

    private void makeRandomMove(char playerMove) {
        Random random = new Random();
        int row, col;

        do {
            row = random.nextInt(board.getSize());
            col = random.nextInt(board.getSize());
        } while (board.getCell(row, col) != Board.Cell.EMPTY);

        handlePlayerMove(row, col);
    }

    public Map<Point, Color> getSosSequenceMap() {
        return sosSequenceMap;
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }
    
    public String getWinnerMessage() {
        return winnerMessage;
    }
    
    public int getBlueScore() {
    	return this.blueScore;
    }
    
    public int getRedScore() {
    	return this.redScore;
    }
}