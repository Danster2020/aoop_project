import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

// package src.main.java;

public class Block {
    
    int xCoordinate, yCoordinate;
    int size;
    
    boolean isWall;
    boolean isTile;
    boolean hasTarget;
    boolean hasBox;
    boolean hasPlayer;

    public BufferedImage image;

    public Block(int col, int row) {
        this.xCoordinate = col;
        this.yCoordinate = row;
        this.size = 32;

        this.isWall = false;
        this.isTile = true;
        this.hasTarget = false;
        this.hasBox = false;
        this.hasPlayer = false;
    }


    public boolean isWall() {
        return isWall;
    }

    public boolean isTile() {
        return isTile;
    }

    public boolean hasTarget() {
        return hasTarget;
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
    }

    public void setTile() {
        isWall = false;
        isTile = true;
    }

    public int getImgSize() {
        return this.size;
    }
}
