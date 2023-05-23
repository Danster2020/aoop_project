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

    Game game;
    BufferedImage tileImg, targetImg, wallImg, boxImg, BoxTargetImg, playerImg;
    String assetFolder = "../assets/";

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

    public BufferedImage loadImg(String path) {
        BufferedImage loadedImg = null;
        try {
            loadedImg = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("couldn't load image with path:" + path + " \n" + e);
        }
        return loadedImg;
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D draw = (Graphics2D) g;

        for (int row = 0; row < game.getCurrLvl().getGridHeight(); row++) {
            for (int col = 0; col < game.getCurrLvl().getGridLength(); col++) {
                newBlock(draw, col * game.gameView.getBlockSize(), row * game.gameView.getBlockSize(), paintBlock(col, row));
            }
        }
    }

    public void newBlock(Graphics2D g2, int x, int y, BufferedImage image) {
        g2.drawImage(image, x, y, game.gameView.getBlockSize(), game.gameView.getBlockSize(), null);
    }

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
}
