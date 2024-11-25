package org.sample.mavensample;

import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import org.sample.mavensample.product.Board;
import org.sample.mavensample.product.Controller;
import org.sample.mavensample.product.GUI;

import javax.swing.SwingUtilities;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ControllerTest {
    private Board board;
    private Controller controller;
    private GUI gui;
    private static final String RECORD_DIR = "game_records";

    @Before
    public void setUp() {
        board = new Board(3);
        gui = new GUI(board);
        controller = new Controller(gui, board);
        controller.setTestMode(true);
        
        File dir = new File(RECORD_DIR);
        if (dir.exists()) {
            for (File file : dir.listFiles()) {
                file.delete();
            }
        }
    }
    
    @After
    public void tearDown() {
        // Clean up the record directory after each test
        File dir = new File(RECORD_DIR);
        if (dir.exists()) {
            for (File file : dir.listFiles()) {
                file.delete();
            }
            dir.delete();
        }
    }

    @Test
    public void testPlayerMoveAndScoreUpdate() {
    	gui.getGeneralGame().setSelected(true);
        controller.handlePlayerMove(0, 0);
        gui.getRedOMove().setSelected(true);
        controller.handlePlayerMove(0, 1);
        controller.handlePlayerMove(0, 2);
        
        assertEquals(3, controller.getSosSequenceMap().size());
        assertEquals("Blue Score: 1", gui.getBlueScoreLabel().getText());
    }
    
    public void testTurnLabelUpdate() {
    	assertEquals("Blue Player's Turn", gui.getPlayerTurnLabel().getText());
    	controller.handlePlayerMove(0, 0);
    	assertEquals("Red Player's Turn", gui.getPlayerTurnLabel().getText());
    }

    @Test
    public void testIsBoardFull() {
        assertFalse(controller.isBoardFull());
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
            	controller.handlePlayerMove(row, col);
            }
        }
        assertTrue(controller.isBoardFull());
    }

    @Test
    public void testStartNewGame() {
        controller.startNewGame("3");
        assertEquals(0, controller.getSosSequenceMap().size());
        assertEquals(Board.Cell.EMPTY, board.getCell(0, 0));
    }
    
    @Test
    public void testWinnerSimpleGame() {
        // Simulate a simple game where Blue Player wins
        controller.handlePlayerMove(0, 0);  // Blue Player (S)
        gui.getRedOMove().setSelected(true);  // Switch to Red Player (O)
        controller.handlePlayerMove(0, 1);  // Red Player (O)
        controller.handlePlayerMove(0, 2);  // Blue Player (S) completes "SOS"

        // Verify that the game correctly sets the winner message for Blue Player
        assertEquals("Blue Player wins!", controller.getWinnerMessage());
    }

    public void testGameDrawSimpleGame() throws Exception {
        for (int row = 0; row < gui.getBoard().getSize(); row++) {
            for (int col = 0; col < gui.getBoard().getSize(); col++) {
            	controller.handlePlayerMove(row, col);
            }
        }

        SwingUtilities.invokeAndWait(() -> {
            assertEquals("It's a tie!", controller.getWinnerMessage());
        });
    } 
    
    @Test
    public void testWinnerGeneralGame() throws Exception {
        // Set game mode to General
        gui.getGeneralGame().setSelected(true);
        gui.getRedOMove().setSelected(true);  // Switch to Red Player

        // Simulate moves for the general game
        controller.handlePlayerMove(0, 0);  // Blue Player (S)
        controller.handlePlayerMove(1, 1);  // Red Player (O)
        controller.handlePlayerMove(2, 2);  // Blue Player (S)
        controller.handlePlayerMove(1, 2);  // Red Player (O)
        controller.handlePlayerMove(0, 2);  // Blue Player (S)
        controller.handlePlayerMove(2, 1);  // Red Player (O)
        controller.handlePlayerMove(2, 0);  // Blue Player (S)
        controller.handlePlayerMove(0, 1);  // Red Player (O)
        controller.handlePlayerMove(1, 0);  // Blue Player (S)

        // Allow GUI to process state updates
        SwingUtilities.invokeAndWait(() -> {
            // Verify that Blue Player wins with the correct score
            assertEquals("Blue Player wins with a score of 3!", controller.getWinnerMessage());
        });
    }
     
    @Test
    public void testDrawGeneralGame() throws Exception {
        gui.getGeneralGame().setSelected(true);

        for (int row = 0; row < gui.getBoard().getSize(); row++) {
            for (int col = 0; col < gui.getBoard().getSize(); col++) {
                controller.handlePlayerMove(row, col);
            }
        }

        SwingUtilities.invokeAndWait(() -> {
            assertEquals("It's a tie!", controller.getWinnerMessage());
        });
    }
    
    @Test
    public void testLLMMoveParsingAndMakeMove() throws Exception{
        String response = """
        {
            "choices": [
                {
                    "message": {
                        "content": "Place S at 1,1"
                    }
                }
            ]
        }
        """;
        
        String parsedMove = controller.parseMoveFromResponse(response);
        assertEquals("Place S at 1,1", parsedMove);

        controller.applyComputerMove(parsedMove);
        SwingUtilities.invokeAndWait(() -> {
        	assertEquals(Board.Cell.S, board.getCell(0, 0));
        });

    }

    @Test
    public void testLLMMoveWithO() {
        String response = """
        {
            "choices": [
                {
                    "message": {
                        "content": "Place O at 2,2"
                    }
                }
            ]
        }
        """;
        
        String parsedMove = controller.parseMoveFromResponse(response);
        controller.applyComputerMove(parsedMove);

        assertEquals(Board.Cell.O, board.getCell(1, 1));
    }
    
    @Test
    public void testLLMResponseMove() throws Exception {
    	controller.handlePlayerMove(0, 0);
    	
    	String response = """
    	        {
    	            "choices": [
    	                {
    	                    "message": {
    	                        "content": "Place O at 2,2"
    	                    }
    	                }
    	            ]
    	        }
    	        """;
    	String parsedMove= controller.parseMoveFromResponse(response);
    	controller.applyComputerMove(parsedMove);
    	SwingUtilities.invokeAndWait(() -> {
        	assertEquals(Board.Cell.O, board.getCell(1, 1));
        });
    }
    
    @Test
    public void testValidLLMMove() throws Exception {
        String response = """
            {
                "choices": [
                    {
                        "message": {
                            "content": "Place S at 1,1"
                        }
                    }
                ]
            }
        """;

        String parsedMove = controller.parseMoveFromResponse(response);

        boolean moveApplied = controller.applyComputerMove(parsedMove) != null;

        assertTrue(moveApplied);
        assertEquals(Board.Cell.S, board.getCell(0, 0));
    }

    @Test
    public void testFallbackRandomMove() throws Exception {
        board.makeMove(0, 0, 'S');

        String response = """
            {
                "choices": [
                    {
                        "message": {
                            "content": "Place O at 1,1"
                        }
                    }
                ]
            }
        """;

        String parsedMove = controller.parseMoveFromResponse(response);

        controller.applyComputerMove(parsedMove);

        boolean randomMovePlaced = false;
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if ((row != 0 || col != 0) && board.getCell(row, col) != Board.Cell.EMPTY) {
                    randomMovePlaced = true;
                    break;
                }
            }
        }

        assertTrue(randomMovePlaced);
        assertEquals(Board.Cell.S, board.getCell(0, 0));
    }
    
    @Test
    public void testCreateRecordDirectory() {
        controller.createRecordDirectory();
        File dir = new File(RECORD_DIR);
        assertTrue("Record directory should be created.", dir.exists() && dir.isDirectory());
    }

    @Test
    public void testStartNewRecordFile() throws IOException {
        gui.getSimpleGame().setSelected(true); // Set to simple game mode
        controller.startNewRecordFile();

        File dir = new File(RECORD_DIR);
        assertTrue("Record directory should exist.", dir.exists() && dir.isDirectory());

        // Check for a file in the directory
        File[] files = dir.listFiles();
        assertNotNull(files);
        assertTrue("There should be a new file created for the record.", files.length > 0);

        // Verify the content of the file
        try (BufferedReader reader = new BufferedReader(new FileReader(files[0]))) {
            String header = reader.readLine();
            assertEquals("File should contain the board size and game mode.", "BoardSize,3,GameMode,Simple", header);
        }
    }

    @Test
    public void testRecordMove() throws IOException {
        gui.getSimpleGame().setSelected(true); // Ensure correct game mode for file naming
        controller.startNewRecordFile();

        // Simulate a move
        int row = 1, col = 1;
        char playerMove = 'S';
        controller.recordMove(row, col, playerMove, false, null);

        File dir = new File(RECORD_DIR);
        File[] files = dir.listFiles();
        assertNotNull(files);
        assertTrue("There should be a file created for the record.", files.length > 0);

        // Check the move in the file
        try (BufferedReader reader = new BufferedReader(new FileReader(files[0]))) {
            reader.readLine(); // Skip header line
            String moveLine = reader.readLine();
            assertEquals("Move should be recorded correctly in the file.", "Blue,1,1,S,false", moveLine);
        }
    }

    @Test
    public void testStopRecording() {
        gui.getSimpleGame().setSelected(true);
        controller.startNewRecordFile();

        controller.stopRecording();

        File dir = new File(RECORD_DIR);
        File[] files = dir.listFiles();
        assertNotNull(files);
        assertTrue("The record file should exist after stopping recording.", files.length > 0);

        // Check if the record file path is reset
        assertNull("Current record file path should be cleared after stopping recording.", controller.getCurrentRecordFile());
    }



    @Test
    public void testReplayGame() throws Exception {
        gui.getSimpleGame().setSelected(true);
        controller.startNewRecordFile();
        
        // Simulate a game with moves and record them
        controller.recordMove(0, 0, 'S', true, new int[]{0, 0, 0, 1, 0, 2});
        controller.recordMove(1, 0, 'O', false, null);

        controller.stopRecording();

        // Get the name of the recorded file
        String recordedGameFile = controller.getSavedGames()[0];
        assertNotNull("There should be a saved game file to replay.", recordedGameFile);

        // Replay the game
        controller.replayGame(recordedGameFile);

        // Allow time for replay actions to process (if replay runs asynchronously)
        Thread.sleep(1000);

        // Validate the board state
        assertEquals("Cell (0, 0) should have 'S'.", Board.Cell.S, controller.getBoard().getCell(0, 0));
        assertEquals("Cell (1, 0) should have 'O'.", Board.Cell.O, controller.getBoard().getCell(1, 0));
    }

    @Test
    public void testGetSavedGames() {
        gui.getSimpleGame().setSelected(true);
        controller.startNewRecordFile();
        controller.stopRecording();

        String[] savedGames = controller.getSavedGames();
        assertNotNull("Saved games should not be null.", savedGames);
        assertTrue("There should be at least one saved game.", savedGames.length > 0);
        assertTrue("Saved game files should have .csv extension.", savedGames[0].endsWith(".csv"));
    }
}