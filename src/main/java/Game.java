// package src.main.java;

import javax.swing.*;
import java.awt.*;

public class Game {

    JFrame jFrame;
    Block[][] blockGrid;
    Level currentLevel;
    private final int WIDTH, HEIGHT;
    KeyHandler kH = new KeyHandler();

    public Game() {
        WIDTH = 992; // 30 blocks * 32 px = 960. added 32px to counter Windows 11 offset.
        HEIGHT = 704; // 20 blocks * 32 px = 640. added 64px to counter title length

        // Frame
        jFrame = new JFrame("Sokoban");
        jFrame.setSize(WIDTH, HEIGHT); // 960x640 is enoguht to cover 60 levels in original Sokoban

        // blockGrid = new Block[30][20];
        Level level1 = new Level(new Block[30][20], "level1");
        this.currentLevel = level1;

        for (int row = 0; row < level1.getGrid()[0].length; row++) {
            for (int col = 0; col < level1.getGrid().length; col++) {
                level1.setBlock(col, row, new Block());
            }
        }

        level1.getBlock(0, 0).setWall();
        level1.getBlock(29, 19).setWall();

        level1.printBlocks();

        BlockManager bM = new BlockManager(this);

        // Label
        JLabel mouseLabel = new JLabel();
        mouseLabel.setSize(WIDTH, HEIGHT);
        mouseLabel.addMouseListener(kH); // sets mouse coordinates to be relative to canvas area

        // Adds components
        jFrame.addKeyListener(kH);
        jFrame.add(mouseLabel);
        jFrame.add(bM);
        
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // exit app on close
    }

    public int getWidth() {
        return this.WIDTH;
    }

    public int getHeight() {
        return this.HEIGHT;
    }

    public Level getCurrLvl() {
        return this.currentLevel;
    }

    public static void main(String[] args) {
        System.out.println("starting game...");
        new Game();
    }
}
