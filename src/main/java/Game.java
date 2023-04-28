// package src.main.java;

import javax.swing.*;
import java.awt.*;

public class Game {

    JFrame jFrame;
    Block[][] blockArray;
    private final int WIDTH, HEIGHT;
    KeyHandler kH = new KeyHandler();

    public Game() {
        jFrame = new JFrame("Sokoban");
        WIDTH = 960;
        HEIGHT = 640;

        blockArray = new Block[10][10];

        // for (int row = 0; row < blockArray.length; row++) {
        //     for (int col = 0; col < blockArray[row].length; col++) {
        //         blockArray[row][col] = new Block(16 * col + 1, 16 * row + 1, false);
        //         jFrame.add(blockArray[row][col]);
        //     }
        // }

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

    public static void main(String[] args) {
        System.out.println("starting game...");
        new Game();
    }
}
