// package src.main.java;

import javax.swing.*;
import java.awt.*;

public class Game {

    JFrame jFrame;
    Block[][] blockGrid;
    private final int WIDTH, HEIGHT;
    KeyHandler kH = new KeyHandler();

    public Game() {
        jFrame = new JFrame("Sokoban");
        WIDTH = 960; // 30 blocks
        HEIGHT = 640; // 20 blocks

        blockGrid = new Block[30][20];

        // for (int row = 0; row < getHeight() / 32; row++) {
        // for (int col = 0; col < getWidth() / 32; col++) {
        // blockGrid[col][row] = new Block(16 * col + 1, 16 * row + 1, false);
        // }
        // }

        for (int row = 0; row < blockGrid[0].length; row++) {
            for (int col = 0; col < blockGrid.length; col++) {
                blockGrid[col][row] = new Block(col, row, false);
            }
        }

        blockGrid[29][17].setWall();

        printBlocks(blockGrid);

        jFrame.addKeyListener(kH);
        jFrame.addMouseListener(kH);

        BlockManager bM = new BlockManager(this);
        jFrame.add(bM);

        jFrame.pack();
        jFrame.setSize(WIDTH, HEIGHT); // 960x640 is enoguht to cover 60 levels in original Sokoban
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
                    string = "E";
                } else if (block.isTile()) {
                    string = "T";
                } else if (block.isWall()) {
                    string = "W";
                }
                System.out.print(string + ", ");
            }
        }
        System.out.println("\n]");
    }

    public static void main(String[] args) {
        System.out.println("starting game...");
        new Game();
    }
}
