import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

// package src.main.java;

public class Block implements Serializable {
    
    int size;
    
    private boolean isWall;
    private boolean isTile;
    private boolean isTarget;
    private boolean hasBox;
    private boolean hasPlayer;

    public Block() {
        this.size = 32;

        this.isWall = false;
        this.isTile = true;
        this.isTarget = false;
        this.hasBox = false;
        this.hasPlayer = false;
    }

    public void clearBlock() {
        this.isWall = false;
        this.isTile = false;
        this.isTarget = false;
        this.hasBox = false;
        this.hasPlayer = false;
    }


    public boolean isWall() {
        return isWall;
    }

    public boolean isTile() {
        return isTile;
    }

    public boolean isTarget() {
        return isTarget;
    }

    public boolean hasBox() {
        return hasBox;
    }

    public boolean hasPlayer() {
        return hasPlayer;
    }

    public void setWall() {
        clearBlock();
        isWall = true;
    }

    public void setTile() {
        clearBlock();
        isTile = true;
    }

    public void setTarget() {
        clearBlock();
        isTarget = true;
    }

    public void setPlayer() {
        clearBlock();
        isTile = true;
        hasPlayer = true;
    }

    public void setBox() {
        clearBlock();
        isTile = true;
        hasBox = true;
    }

    public void ToggleBlock() {
        if (hasPlayer) {
            setTile();
            return;
        }
        if (hasBox) {
            setPlayer();
            return;
        }
        if (isTarget) {
            setBox();
            return;
        }
        if (isWall) {
            setTarget();
            return;
        }
        if (isTile) {
            setWall();
            return;
        }
    }

    public int getImgSize() {
        return this.size;
    }
}
