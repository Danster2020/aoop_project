package model;

import java.io.Serializable;

public class Block implements Serializable {

    private static final long serialVersionUID = 2L;

    private boolean isWall;
    private boolean isTile;
    private boolean isTarget;
    private boolean hasBox;
    private boolean hasPlayer;

    public Block() {

        this.isWall = false;
        this.isTile = true;
        this.isTarget = false;
        this.hasBox = false;
        this.hasPlayer = false;
    }

    /**
     * Resets the block from all configs.
     * 
     * Makes sure that data is not retained when toggling between blocks in the
     * level editor.
     * 
     */
    public void clearBlock() {
        this.isWall = false;
        this.isTile = false;
        this.isTarget = false;
        this.hasBox = false;
        this.hasPlayer = false;
    }

    /**
     * @return boolean
     */
    public boolean isWall() {
        return isWall;
    }

    /**
     * @return boolean
     */
    public boolean isTile() {
        return isTile;
    }

    /**
     * @return boolean
     */
    public boolean isTarget() {
        return isTarget;
    }

    /**
     * @return boolean
     */
    public boolean hasBox() {
        return hasBox;
    }

    /**
     * @return boolean
     */
    public boolean hasTargetBox() {
        if (hasBox && isTarget) {
            return true;
        }
        return false;
    }

    /**
     * @return boolean
     */
    public boolean hasPlayer() {
        return hasPlayer;
    }

    /**
     */
    public void setWall() {
        clearBlock();
        isWall = true;
    }

    /**
     */
    public void setTile() {
        clearBlock();
        isTile = true;
    }

    /**
     */
    public void setTarget() {
        clearBlock();
        isTarget = true;
    }

    /**
     */
    public void setPlayer() {
        clearBlock();
        isTile = true;
        hasPlayer = true;
    }

    /**
     */
    public void setBox() {
        clearBlock();
        isTile = true;
        hasBox = true;
    }

    /**
     */
    public void setTargetBox() {
        clearBlock();
        isTarget = true;
        hasBox = true;
    }

    /**
     * Places the player on the block.
     * 
     * Prevents overwriting of block(target)
     * 
     */
    public void placePlayer() {
        hasPlayer = true;
    }

    public void removePlayer() {
        hasPlayer = false;
    }

    /**
     * Places the box on the block.
     * 
     */
    public void placeBox() {
        hasBox = true;
    }

    /**
     */
    public void removeBox() {
        hasBox = false;
    }

    /**
     * Toggles between blocks.
     * 
     * Used in the level editor.
     * 
     */
    public void ToggleBlock() {
        if (hasPlayer) {
            setTile();
            return;
        }
        if (hasTargetBox()) {
            setPlayer();
            return;
        }
        if (hasBox && !isTarget) {
            setTargetBox();
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

}
