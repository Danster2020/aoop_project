// package src.main.java;

import javax.swing.*;
import java.awt.*;

public class Game {

    JFrame jFrame;
    Block[][] blockGrid;
    private final int WIDTH, HEIGHT;
    KeyHandler kH = new KeyHandler();

    public Game() {
        WIDTH = 992; // 30 blocks * 32 px = 960. added 32px to counter Windows 11 offset.
        HEIGHT = 704; // 20 blocks * 32 px = 640. added 64px to counter title length

        // Frame
        jFrame = new JFrame("Sokoban");
        jFrame.setSize(WIDTH, HEIGHT); // 960x640 is enoguht to cover 60 levels in original Sokoban

        blockGrid = new Block[30][20];

        for (int row = 0; row < blockGrid[0].length; row++) {
            for (int col = 0; col < blockGrid.length; col++) {
                blockGrid[col][row] = new Block(col, row, false);
            }
        }

        blockGrid[29][19].setWall();

        printBlocks(blockGrid);


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

    public void printBlocks(Block[][] blockGrid) {
        System.out.print("[");
        for (Block[] row : blockGrid) {
            System.out.println();
            for (Block block : row) {
                String string = "";
                if (block.isEmpty()) {
                    string = "-";
                } else if (block.isTile()) {
                    string = "T";
                } else if (block.isWall()) {
                    string = "W";
                }
                System.out.print(string + " ");
            }
        }
        System.out.println("\n]");
    }

    public int getGridLength() {
        return this.blockGrid.length;
    }

    public int getGridHeight() {
        return this.blockGrid[0].length;
    }

    public static void main(String[] args) {
        System.out.println("starting game...");
        new Game();
    }
}
