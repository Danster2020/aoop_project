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
    BufferedImage blankImg, blankMarkedImg, wallImg;
    String assetFolder = "../assets/";
    BufferedImage[][] imgBlocks;

    BlockManager(Game g) {
        this.game = g;
        this.blockSize = 32;

        this.blankImg = loadImg(assetFolder + "blank.png");
        this.blankMarkedImg = loadImg(assetFolder + "blankmarked.png");
        this.wallImg = loadImg(assetFolder + "wall.png");
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

        for (int row = 0; row < game.getHeight() / blockSize; row++) { // FIXME height not entirely lined up
            for (int col = 0; col < game.getWidth() / blockSize; col++) {
                newBlock(draw, col * blockSize, blockSize * row);
            }
        }

    }

    public void newBlock(Graphics2D g2, int x, int y) {
        g2.drawImage(blankImg, x, y, blockSize, blockSize, null);
    }
}
