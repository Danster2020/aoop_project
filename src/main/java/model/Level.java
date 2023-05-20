package model;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Level implements Serializable {

    private static final long serialVersionUID = 2L;
    Block[][] blockGrid;
    String levelName;

    public Level(String lvlName) {

        this.blockGrid = new Block[20][30]; // [row][col]

        // defaults all blocks to be blank tiles
        for (int row = 0; row < this.getGrid().length; row++) {
            for (int col = 0; col < this.getGrid()[0].length; col++) {
                this.setBlock(col, row, new Block());
            }
        }

        this.levelName = lvlName;
    }

    public Block[][] getGrid() {
        return this.blockGrid;
    }

    public String getName() {
        return this.levelName;
    }

    public void setName(String levelName) {
        this.levelName = levelName;
    }

    public void setBlock(int col, int row, Block data) {
        this.blockGrid[row][col] = data;
    }

    public int getGridLength() {
        return this.blockGrid[0].length;
    }

    public int getGridHeight() {
        return this.blockGrid.length;
    }

    public Block getBlock(int col, int row) {
        return this.blockGrid[row][col];
    }

    public int getBlockCol(Block targetBlock) {
        int colIndex = 0;
        for (Block[] row : blockGrid) {
            for (Block block : row) {
                if (block.equals(targetBlock)) {
                    return colIndex;
                }
                colIndex++;
            }
            colIndex = 0;
        }
        return -1;
    }

    public int getBlockRow(Block targetBlock) {
        int rowIndex = 0;
        for (Block[] row : blockGrid) {
            for (Block block : row) {
                if (block.equals(targetBlock)) {
                    return rowIndex;
                }
            }
            rowIndex++;
        }
        return -1;
    }

    public void saveLevel() {
        ObjectOutputStream dataOut = null;
        try {
            dataOut = new ObjectOutputStream(new FileOutputStream("../levels/custom/" + this.levelName + ".dat"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            dataOut.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            dataOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Level saved!");
    }
}
