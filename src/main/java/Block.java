package src.main.java;

public class Block {
    
    boolean isWall;
    boolean isTile;
    boolean hasBall;
    boolean hasBox;
    boolean hasPlayer;

    public Block(Boolean wall) {
        if (wall == true) {
            setWall();
        } else {
            isWall = false;
            isTile = true;
        }

        this.hasBall = false;
        this.hasBox = false;
        this.hasPlayer = false;
    }


    public void setWall() {
        isWall = true;
        isTile = false;
    }

    public static void main(String[] args) {
        Block block1 = new Block(false);
        Block block2 = new Block(true);
    }
}
