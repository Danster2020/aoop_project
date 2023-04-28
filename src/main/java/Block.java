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

    public Block(int col, int row, Boolean wall) {
        this.xCoordinate = col;
        this.yCoordinate = row;
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

    public boolean isEmpty() {
        return isEmpty;
    }

    public boolean isWall() {
        return isWall;
    }

    public boolean isTile() {
        return isTile;
    }

    public boolean HasBall() {
        return hasBall;
    }

    public boolean hasBox() {
        return hasBox;
    }

    public boolean hasPlayer() {
        return hasPlayer;
    }

    public void setWall() {
        isWall = true;
        isTile = false;
        isEmpty = false;
    }

    public int getImgSize() {
        return this.size;
    }
}
