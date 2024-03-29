package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import controller.Game;
import model.Block;

public class BlockManager extends JComponent {

    private Game game;
    private BufferedImage tileImg, targetImg, wallImg, boxImg, BoxTargetImg, playerImg;
    private String assetFolder = "../assets/";

    public BlockManager(Game g) {
        this.game = g;

        if (game.getSetting("easter_egg").equals("true")) {
            this.boxImg = loadImg(assetFolder + "pipe.png");
            this.BoxTargetImg = loadImg(assetFolder + "pipe_marked.png");
        } else {
            this.boxImg = loadImg(assetFolder + "crate_cube.png");
            this.BoxTargetImg = loadImg(assetFolder + "crate_cube_marked.png");
        }

        this.tileImg = loadImg(assetFolder + "blank.png");
        this.targetImg = loadImg(assetFolder + "blankmarked.png");
        this.wallImg = loadImg(assetFolder + "wall.png");
        this.playerImg = loadImg(assetFolder + "player_knuckles.png");
    }

    /**
     * Loads image asset into memory.
     * 
     * @param path
     * @return BufferedImage
     */
    public BufferedImage loadImg(String path) {
        BufferedImage loadedImg = null;
        try {
            loadedImg = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("couldn't load image with path:" + path + " \n" + e);
        }
        return loadedImg;
    }

    /**
     * Paints all the blocks onto the jframe.
     * 
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D draw = (Graphics2D) g;

        for (int row = 0; row < game.getCurrLvl().getGridHeight(); row++) {
            for (int col = 0; col < game.getCurrLvl().getGridLength(); col++) {
                newBlock(draw, col * game.getGameView().getBlockSize(), row * game.getGameView().getBlockSize(),
                        paintBlock(col, row));
            }
        }
    }

    /**
     * Draws the image on screen.
     * 
     * @param g2
     * @param x
     * @param y
     * @param image
     */
    public void newBlock(Graphics2D g2, int x, int y, BufferedImage image) {
        g2.drawImage(image, x, y, game.getGameView().getBlockSize(), game.getGameView().getBlockSize(), null);
    }

    /**
     * Paints a specified block.
     * 
     * @param col
     * @param row
     * @return BufferedImage
     */
    public BufferedImage paintBlock(int col, int row) {
        Block block = game.getCurrLvl().getBlock(col, row);

        if (block.hasPlayer()) {
            return this.playerImg;
        }
        if (block.hasTargetBox()) {
            return this.BoxTargetImg;
        }
        if (block.hasBox()) {
            return this.boxImg;
        }
        if (block.isTarget()) {
            return this.targetImg;
        }
        if (block.isWall()) {
            return this.wallImg;
        } else {
            return this.tileImg;
        }
    }

    /**
     * @return BufferedImage
     */
    public BufferedImage getBoxImg() {
        return boxImg;
    }
}
