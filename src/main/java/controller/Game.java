package controller;

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
        // this.gameView = new GameView(this);

        // Init config
        loadLevel("test123", true);
        System.out.println("Game started!");

        // View observers
        this.gameData = new GameData(this);
        gameData.registerObserver(new TerminalView(gameData));
        
        this.gameView = new GameView(gameData); // FIXME
        gameData.registerObserver(this.gameView);
    }

    public Level getCurrLvl() {
        return this.currentLevel;
    }

    public Player getPlayer() {
        return player;
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

    public void loadLevel(String fileName, boolean isCustom) {

        String customPath = "";
        if (isCustom) {
            isCustomLevel = true;
            customPath = "custom/";
        } else {
            isCustomLevel = false;
        }

        try {
            // gameView.sound.stopMusic();
        } catch (Exception e) {
            // TODO: handle exception
        }

        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("../levels/" + customPath + fileName + ".dat"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            currentLevel = (Level) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getPlayer().spawnPlayer();
        spawnBoxes();

        // gameView.updateView();
        // gameView.jFrame.setTitle(GAMENAME + " - " + currentLevel.getName());
        // gameView.sound.playMusic(gameView.sound.bg_music);
        System.out.println("Level loaded!");
    }

    public void restartLevel() {
        loadLevel(getCurrLvl().getName(), isCustomLevel);
        System.out.println("Level restarted!");
    }

}
