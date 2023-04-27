import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

// package src.main.java;

public class Block {
    
    int xCoordinate, yCoordinate;
    int size;
    
    boolean isEmpty;
    boolean isWall;
    boolean isTile;
    boolean hasBall;
    boolean hasBox;
    boolean hasPlayer;

    public BufferedImage image;

    public Block(int x, int y, Boolean wall) {
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.size = 32;

        this.hasBall = false;
        this.hasBox = false;
        this.hasPlayer = false;

        if (wall == true) {
            setWall();
        } else {
            this.isEmpty = true;
            this.isWall = false;
            this.isTile = false;
        }
    }

    public void setWall() {
        isWall = true;
        isTile = false;
    }

    public int getImgSize() {
        return this.size;
    }
}
