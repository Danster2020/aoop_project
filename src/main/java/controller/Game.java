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

    public Level getCurrLvl() {
        return this.currentLevel;
    }

    public Player getPlayer() {
        return player;
    }

    public String getGameName() {
        return GAMENAME;
    }

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    public GameView getGameView() {
        return gameView;
    }

    public GameDataPublisher getGameData() {
        return gameData;
    }

    public void setIsCustomLevel(boolean status) {
        this.isCustomLevel = status;
    }

    public boolean isCustomLevel() {
        return isCustomLevel;
    }

    public boolean isLvlEditorOn() {
        return isLvlEditorOn;
    }

    public boolean isLevelComplete() {
        return isLevelComplete;
    }

    public void setIsLevelComplete(boolean status) {
        this.isLevelComplete = status;
    }

    public void setLvlEditorOn(boolean status) {
        this.isLvlEditorOn = status;
    }

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

    public void saveLevel() {
        saveLevel("");
    }

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

    public void saveGame() {
        if (isCustomLevel) {
            System.out.println("Can't save game progress on custom levels");
            return;
        }
        saveLevel("saved_game");
    }

    public void loadGame() {
        loadLevel("SAVED_GAME", false);
    }

    public void restartGame() {
        loadLevel("level1", false);
    }

    public void restartLevel() {
        loadLevel(getCurrLvl().getName(), isCustomLevel);
        System.out.println("Level restarted!");
    }

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
