// package src.main.java;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Game {

    JFrame jFrame;
    Level currentLevel;
    private final int WIDTH, HEIGHT;
    KeyHandler kH;

    public Game()  {
        WIDTH = 992; // 30 blocks * 32 px = 960. added 32px to counter Windows 11 offset.
        HEIGHT = 704; // 20 blocks * 32 px = 640. added 64px to counter title length

        kH = new KeyHandler(this);

        // Frame
        jFrame = new JFrame("Sokoban");
        jFrame.setSize(WIDTH, HEIGHT); // 960x640 is enoguht to cover 60 levels in original Sokoban

        this.currentLevel = new Level("level1");

        // create level ##############################
        // row 0
        currentLevel.getBlock(2, 0).setWall();
        currentLevel.getBlock(3, 0).setWall();
        currentLevel.getBlock(4, 0).setWall();
        currentLevel.getBlock(5, 0).setWall();
        currentLevel.getBlock(6, 0).setWall();

        // row 1
        currentLevel.getBlock(0, 1).setWall();
        currentLevel.getBlock(1, 1).setWall();
        currentLevel.getBlock(2, 1).setWall();
        currentLevel.getBlock(6, 1).setWall();

        // row 2
        currentLevel.getBlock(0, 2).setWall();
        currentLevel.getBlock(1, 2).setTarget();

        // row 3

        // row 4

        // row 5

        // ###########################################

        currentLevel.saveLevel();
        currentLevel.printBlocks();

        BlockManager bM = new BlockManager(this);

        // Label
        JLabel mouseLabel = new JLabel();
        mouseLabel.setSize(WIDTH, HEIGHT);
        mouseLabel.addMouseListener(kH); // sets mouse coordinates to be relative to canvas area

        // Adds components
        jFrame.addKeyListener(kH);
        jFrame.add(mouseLabel);
        jFrame.add(bM);

        jFrame.getContentPane().setBackground(Color.BLACK);
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

    public void loadLevel(String fileName) {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            currentLevel = (Level) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        System.out.println("starting game...");
        new Game();
    }
}
