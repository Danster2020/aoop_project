package controller;

import model.Block;
import model.Box;
import model.GameData;
import model.Level;
import model.Player;
import view.TerminalView;
import view.BlockManager;
import view.GameView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Game {

    final String GAMENAME;
    private Level currentLevel;
    boolean isCustomLevel;
    KeyHandler kH;

    private Player player;
    private ArrayList<Box> boxes;

    // Flags
    private boolean isLvlEditorOn;

    public GameView gameView;
    public GameData gameData;
    public boolean isLevelComplete;

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public Game() {
        GAMENAME = "Sokoban";

        this.player = new Player(this);
        this.isLvlEditorOn = false;
        this.isCustomLevel = false;
        this.currentLevel = new Level("noname");
        this.gameData = new GameData(this);
        this.isLevelComplete = false;

        // this.gameView = new GameView(this);

        // this is a required observer to run the game
        this.gameView = new GameView(gameData);
        gameData.registerObserver(this.gameView);

        // View observers
        gameData.registerObserver(new TerminalView(gameData));

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

    public boolean isLvlEditorOn() {
        return isLvlEditorOn;
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

        gameView.showGameWonPrompt();
        System.out.println("game won!");
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
        String fullPath = "../levels/" + customPath + fileName + ".dat";
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

        // gameView.jFrame.setTitle(GAMENAME + " - " + currentLevel.getName());

        gameData.notifyObservers();
        gameView.sound.stopMusic(); // stops current music if any
        gameView.sound.playMusic(gameView.sound.bg_music);
        System.out.println("Level loaded!");
    }

    public void restartLevel() {
        loadLevel(getCurrLvl().getName(), isCustomLevel);
        System.out.println("Level restarted!");
    }

}
