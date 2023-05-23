package controller;

import model.Block;
import model.Box;
import model.Event;
import model.GameDataPublisher;
import model.Level;
import model.Player;
import view.TerminalView;
import view.GameView;
import view.SoundView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Game {

    private final String GAMENAME;
    private Level currentLevel;
    private boolean isCustomLevel;

    private Player player;
    private ArrayList<Box> boxes;

    // Flags
    private boolean isLvlEditorOn;

    private GameView gameView;
    private GameDataPublisher gameData;
    private boolean isLevelComplete;

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public Game() {
        System.out.println("starting game...");
        GAMENAME = "Sokoban";
        this.player = new Player(this);
        this.isLvlEditorOn = false;
        this.isCustomLevel = false;
        this.currentLevel = new Level("noname");
        this.gameData = new GameDataPublisher(this);
        this.isLevelComplete = false;
        this.gameView = new GameView(gameData);

        // View observers
        gameData.registerObserver(this.gameView);
        gameData.registerObserver(new TerminalView(gameData));
        gameData.registerObserver(new SoundView(gameData));
        // gameData.registerObserver(new WindowTextView(gameData));

        // Init config
        loadLevel("level1", false);
        System.out.println("Game started!");
    }

    /**
     * @return Level
     */
    public Level getCurrLvl() {
        return this.currentLevel;
    }

    /**
     * @return Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return String
     */
    public String getGameName() {
        return GAMENAME;
    }

    /**
     * @return ArrayList
     */
    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    /**
     * @return GameView
     */
    public GameView getGameView() {
        return gameView;
    }

    /**
     * @return GameDataPublisher
     */
    public GameDataPublisher getGameData() {
        return gameData;
    }

    /**
     * @param status
     */
    public void setIsCustomLevel(boolean status) {
        this.isCustomLevel = status;
    }

    /**
     * @return boolean
     */
    public boolean isCustomLevel() {
        return isCustomLevel;
    }

    /**
     * @return boolean
     */
    public boolean isLvlEditorOn() {
        return isLvlEditorOn;
    }

    /**
     * @return boolean
     */
    public boolean isLevelComplete() {
        return isLevelComplete;
    }

    /**
     * @param status
     */
    public void setIsLevelComplete(boolean status) {
        this.isLevelComplete = status;
    }

    /**
     * @param status
     */
    public void setLvlEditorOn(boolean status) {
        this.isLvlEditorOn = status;
    }

    /**
     * Looks at map and spawns boxes logically.
     */
    public void spawnBoxes() {
        boxes = new ArrayList<Box>();
        for (int row = 0; row < this.getCurrLvl().getGrid().length; row++) {
            for (int col = 0; col < this.getCurrLvl().getGrid()[0].length; col++) {
                if (this.getCurrLvl().getBlock(col, row).hasBox()) {
                    Box box = new Box(this);
                    box.setRow(row);
                    box.setCol(col);
                    boxes.add(box);
                }
            }
        }
    }

    /**
     * Check if the game has been won
     */
    public void checkGameState() {

        if (isLvlEditorOn) {
            return;
        }

        for (Block[] row : currentLevel.getGrid()) {
            for (Block block : row) {
                if (block.isTarget() && !block.hasBox()) {
                    return;
                }
            }
        }

        gameData.notifyObservers(Event.LVL_COMPLETE);
        gameView.showGameWonPrompt();
        System.out.println("game won!");
    }

    /**
     * Saves the current level using {@link #saveLevel(String myPath)}.
     */
    public void saveLevel() {
        saveLevel("");
    }

    /**
     * Saves the current level to the filesystem.
     * 
     * @param myPath
     */
    public void saveLevel(String myPath) {
        ObjectOutputStream dataOut = null;
        String path = "../levels/custom/" + this.currentLevel.getName(); // default path

        if (myPath != "") {
            path = myPath;
        }

        try {
            dataOut = new ObjectOutputStream(new FileOutputStream(path + ".dat"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            dataOut.writeObject(currentLevel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            dataOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Level saved!");
    }

    /**
     * Loads in a level from filesystem.
     * 
     * @param fileName
     * @param isCustom
     */
    public void loadLevel(String fileName, boolean isCustom) {

        String customPath = "";
        if (isCustom) {
            isCustomLevel = true;
            customPath = "custom/";
        } else {
            isCustomLevel = false;
        }

        ObjectInputStream in = null;
        String fullPath = "";
        if (fileName == "SAVED_GAME") {
            fullPath = "saved_game.dat";
        } else {
            fullPath = "../levels/" + customPath + fileName + ".dat";
        }

        try {
            in = new ObjectInputStream(new FileInputStream(fullPath));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not find: " + fullPath + "when loading level!");
            return;
        }

        try {
            currentLevel = (Level) in.readObject();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not convert/load level file!");
            return;
        }
        isLevelComplete = false;
        getPlayer().spawnPlayer();
        spawnBoxes();

        gameView.getjFrame().setTitle(GAMENAME + " - " + currentLevel.getName());

        gameData.notifyObservers(Event.LVL_LOADED);
        System.out.println("Level loaded!");
    }

    /**
     * Saves the current level into a save file. This is retained after closing the
     * game.
     */
    public void saveGame() {
        if (isCustomLevel) {
            System.out.println("Can't save game progress on custom levels");
            return;
        }
        saveLevel("saved_game");
    }

    /**
     * Loads a saved game from the file system.
     */
    public void loadGame() {
        loadLevel("SAVED_GAME", false);
    }

    /**
     * Restarts the whole game by loading in the first level.
     */
    public void restartGame() {
        loadLevel("level1", false);
    }

    /**
     * Restarts the current level.
     */
    public void restartLevel() {
        loadLevel(getCurrLvl().getName(), isCustomLevel);
        System.out.println("Level restarted!");
    }

    /**
     * Returns a specific key settings value saved in a config file.
     * 
     * @param key
     * @return String
     */
    public String getSetting(String key) {
        Properties prop = new Properties();
        String fileName = "settings.config";
        try (FileInputStream file = new FileInputStream(fileName)) {
            prop.load(file);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
        return prop.getProperty(key);
    }
}
