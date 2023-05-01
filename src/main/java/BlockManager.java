import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class BlockManager extends JComponent {

    Game game;
    int blockSize;
    BufferedImage tileImg, targetImg, wallImg, boxImg, playerImg;
    String assetFolder = "../assets/";
    BufferedImage[][] imgBlocks;

    BlockManager(Game g) {
        this.game = g;
        this.blockSize = 32;

        this.tileImg = loadImg(assetFolder + "blank.png");
        this.targetImg = loadImg(assetFolder + "blankmarked.png");
        this.wallImg = loadImg(assetFolder + "wall.png");
        this.boxImg = loadImg(assetFolder + "crate.png");
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

        for (int row = 0; row < game.getCurrLvl().getGridHeight(); row++) { // FIXME height not entirely lined up
            for (int col = 0; col < game.getCurrLvl().getGridLength(); col++) {
                newBlock(draw, col * blockSize, row * blockSize, paintBlock(col, row));
            }
        }
    }

    public void newBlock(Graphics2D g2, int x, int y, BufferedImage image) {
        g2.drawImage(image, x, y, blockSize, blockSize, null);
    }

    public BufferedImage paintBlock(int col, int row) {
        Block block = game.getCurrLvl().getBlock(col, row);
        BufferedImage imageToPaint = null;

        if (block.isTarget()) {
            imageToPaint = this.targetImg;
        } else if (block.hasPlayer()) {
            imageToPaint = this.playerImg;
        } else if (block.hasBox()) {
            imageToPaint = this.boxImg;
        } else if (block.isWall()) {
            imageToPaint = this.wallImg;
        } else {
            imageToPaint = this.tileImg;
        }

        return imageToPaint;
    }
}
