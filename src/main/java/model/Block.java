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
     * @return void
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
     * @return void
     */
    public void setWall() {
        clearBlock();
        isWall = true;
    }

    /**
     * @return void
     */
    public void setTile() {
        clearBlock();
        isTile = true;
    }

    /**
     * @return void
     */
    public void setTarget() {
        clearBlock();
        isTarget = true;
    }

    /**
     * @return void
     */
    public void setPlayer() {
        clearBlock();
        isTile = true;
        hasPlayer = true;
    }

    /**
     * @return void
     */
    public void setBox() {
        clearBlock();
        isTile = true;
        hasBox = true;
    }

    /**
     * @return void
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
     * @return void
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
     * @return void
     */
    public void placeBox() {
        hasBox = true;
    }

    /**
     * @return void
     */
    public void removeBox() {
        hasBox = false;
    }

    /**
     * Toggles between blocks.
     * 
     * Used in the level editor.
     * 
     * @return void
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
